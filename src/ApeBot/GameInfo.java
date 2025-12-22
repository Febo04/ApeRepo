package ApeBot;

import java.util.*;

public class GameInfo {
    public final List<PublicAction> history;
    public final int currentPlayerIndex;
    public final int previousDeclaredNumber;
    public final String previousPlayerName;
    

    public GameInfo(List<PublicAction> history, int currentPlayerIndex, int previousDeclaredNumber, String previousPlayerName) {
        this.history = Collections.unmodifiableList(new ArrayList<>(history));
        this.currentPlayerIndex = currentPlayerIndex;
        this.previousDeclaredNumber = previousDeclaredNumber;
        this.previousPlayerName = previousPlayerName;

    }
}

