package ApeBot;

import java.util.*;

public class ApeGame {
    public static void main(String[] args) {
        List<Bot> bots = new ArrayList<>();
        bots.add(new HonestBot("Onesto"));
        bots.add(new RandomBot("Randomino"));
        
        

        Game g = new Game(bots);

        int rounds = 10;
        System.out.println("Lanciamo " + rounds + " round tra " + bots.size() + " bot...");
        g.playMultipleRounds(rounds);
        List<PublicAction> log = g.getPublicLog();
        for (int i = 0; i < log.size(); ++i) {
            System.out.println((i+1) + ": " + log.get(i));
        }
        System.out.println("\nRisultati (penalità = numero di round persi):");
        Map<String,Integer> scores = g.getPenaltyPoints();
        scores.entrySet().stream().sorted((a,b) -> Integer.compare(a.getValue(), b.getValue()))
                .forEach(e -> System.out.println(e.getKey() + " -> " + e.getValue()));
        g.printCheckStats();
        
        System.out.println("\nFine simulazione.");
    }
}

