package ApeBot;

public class PublicAction {
	private final String playerName;
    private final ActionType actionType;
    private final Integer declaredValue; // es. 42, 11, 21; null se non applicabile
    private final Integer actualValue;
    private final Decision decision;
    
    public PublicAction(String playerName, ActionType actionType, Integer declaredValue, Integer actualValue, Decision decision) {
        this.playerName = playerName;
        this.actionType = actionType;
        this.declaredValue = declaredValue;
        this.actualValue = actualValue;
        this.decision=decision;
    }
    
    public String PlayerName() { return playerName; }
    public ActionType Type() { return actionType; }
    public Integer DeclaredValue() { return declaredValue; }
    public Decision getDecision() {return this.decision;}

    @Override
    public String toString() {
    	String s = playerName;
  

    	{ s = playerName + " - " + actionType;}
        if (declaredValue != null) s += " => " + declaredValue + " ("+ actualValue + ")";
        return s;
    }
}

