package ApeBot;

import java.util.*;

public class Game {
	private final List<Bot> players;
	private final List<PublicAction> publicLog = new ArrayList<>();
	private final Map<String, Integer> penaltyPoints = new HashMap<>();
	private final Random rnd = new Random();
	private int currentPlayerIndex = 0;

	private static class CheckStats {
		int checksDone = 0;
		int checksCorrect = 0;
		int checksWrong = 0;
	}

	private final Map<String, CheckStats> checkStats = new HashMap<>();

	private static class Turn {
		String playerName;
		int declaredValue;
		int actualValue;
	}

	private Turn lastDeclaredTurn = null;

	public Game(List<Bot> players) {
		if (players.size() < 2)
			throw new IllegalArgumentException("Servono almeno 2 giocatori");
		this.players = new ArrayList<>(players);
		for (Bot b : players) {
			penaltyPoints.put(b.getName(), 0);
			checkStats.put(b.getName(), new CheckStats());
		}
	}

	public List<PublicAction> getPublicLog() {
		return Collections.unmodifiableList(publicLog);
	}

	public Map<String, Integer> getPenaltyPoints() {
		return Collections.unmodifiableMap(penaltyPoints);
	}

	public String playRound() {
		lastDeclaredTurn = null;
		boolean roundActive = true;
		String loser = null;

		while (roundActive) {
			Bot current = players.get(currentPlayerIndex);
			GameInfo info = new GameInfo(publicLog, currentPlayerIndex,
					lastDeclaredTurn != null ? lastDeclaredTurn.declaredValue : 0,
					lastDeclaredTurn != null ? lastDeclaredTurn.playerName : null);
			current.init(info);
			ActionType choice = current.chooseAction();
			int declared=0;
			
			
			if (choice == ActionType.CHECK) {
				publicLog.add(new PublicAction(current.getName(), ActionType.CHECK, 0, 0, Decision.CHECK));
				CheckStats stats = checkStats.get(current.getName());
				stats.checksDone++;
				if (lastDeclaredTurn == null) {
					loser=current.getName();
					stats.checksWrong++;
					penaltyPoints.put(loser, penaltyPoints.get(loser) + 1);
					roundActive = false;
					currentPlayerIndex = findPlayerIndexByName(loser);
					break;	
				}
				int actualRank = DiceUtils.rankOf(lastDeclaredTurn.actualValue);
				int declaredRank = DiceUtils.rankOf(lastDeclaredTurn.declaredValue);

				if (actualRank < declaredRank) {
					// Bluff scoperto
					loser = lastDeclaredTurn.playerName;
					stats.checksCorrect++;

				} else {
					// Bluff non trovato
					loser = current.getName();
					stats.checksWrong++;
				}

				penaltyPoints.put(loser, penaltyPoints.get(loser) + 1);
				roundActive = false;
				currentPlayerIndex = findPlayerIndexByName(loser);
				break;

			} else if (choice == ActionType.DECLARE) {
				int actual=0;
				Decision decision=null;
				if (lastDeclaredTurn == null) {
					actual=DiceUtils.roll(rnd);	
				}
				
				// First, get the declared value to check if it's 21
				declared = current.declared();
				boolean forceReroll = (declared == 21);
				
				if(!current.wantsReroll() && !forceReroll) {
					actual= (lastDeclaredTurn == null) ? DiceUtils.roll(rnd):lastDeclaredTurn.actualValue;
					decision=Decision.DECLARE;
				}
				else if (current.wantsReroll() || forceReroll) {
					actual = DiceUtils.roll(rnd);
					if(!current.wantsSeeDice()) {
						decision=Decision.ROLL_DECLARE;
						declared=current.declared();
					}
					else if(current.wantsSeeDice()) {
						current.seeDice(actual);
						if(current.wantsReroll()) {
							actual = DiceUtils.roll(rnd);
							declared=current.declared();
							decision=Decision.ROLL_SEE_ROLL_DECLARE;
						}
						else if(!current.wantsReroll()) {
							declared=current.declared();
							decision=Decision.ROLL_SEE_DECLARE;
						}
					}
				}
					
				
				// assicurati che la dichiarazione sia valida
				int minRank = lastDeclaredTurn != null ? DiceUtils.rankOf(lastDeclaredTurn.declaredValue) + 1 : 1;
				if (!DiceUtils.declaredAllowed(declared, minRank)) {
					declared = valueForRank(minRank);
				}

				// salva il turno per i CHECK successivi
				lastDeclaredTurn = new Turn();
				lastDeclaredTurn.playerName = current.getName();
				lastDeclaredTurn.declaredValue = declared;
				lastDeclaredTurn.actualValue = actual;

				publicLog.add(new PublicAction(current.getName(), choice, declared, actual, decision));
				
				// Check if all players declared 21 in a full cycle
				if (declared == 21 && allPlayersDeclareSame21()) {
					// All players declared 21 - everyone loses a point
					for (Bot player : players) {
						penaltyPoints.put(player.getName(), penaltyPoints.get(player.getName()) + 1);
					}
					publicLog.add(new PublicAction("SYSTEM", ActionType.CHECK, 0, 0, Decision.CHECK));
					roundActive = false;
					// Move to next player who will start new round
					advanceTurn();
					break;
				}
				
				advanceTurn();

			} else {
				advanceTurn();
			}

		}

		return loser;
	}

	private int findPlayerIndexByName(String name) {
		for (int i = 0; i < players.size(); ++i)
			if (players.get(i).getName().equals(name))
				return i;
		return 0;
	}

	private void advanceTurn() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}

	private static int valueForRank(int rank) {
		int[] all = DiceUtils.allPossibleValues();
		if (rank <= 0)
			rank = 1;
		if (rank > all.length)
			rank = all.length;
		return all[rank - 1];
	}

	private boolean allPlayersDeclareSame21() {
		// Count consecutive DECLARE actions with value 21 from the end of the log
		int count = 0;
		for (int i = publicLog.size() - 1; i >= 0; i--) {
			PublicAction action = publicLog.get(i);
			if (action.Type() == ActionType.DECLARE && action.DeclaredValue() == 21) {
				count++;
			} else {
				break; // Stop when we hit a non-21 declare or non-declare action
			}
		}
		// Return true if all players declared 21 in this cycle
		return count == players.size();
	}

	public Map<String, Integer> playMultipleRounds(int rounds) {
		for (int i = 0; i < rounds; ++i)
			playRound();
		return getPenaltyPoints();
	}

	public void printCheckStats() {
		System.out.println("\nStatistiche CHECK:");
		for (String bot : checkStats.keySet()) {
			CheckStats s = checkStats.get(bot);
			System.out.println(bot + ":");
			System.out.println("  CHECK totali    = " + s.checksDone);
			System.out.println("  CHECK giusti    = " + s.checksCorrect);
			System.out.println("  CHECK sbagliati = " + s.checksWrong);
		}
	}
}
