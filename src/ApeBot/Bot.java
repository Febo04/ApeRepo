package ApeBot;

import java.util.Random; 

public abstract class Bot {
    protected final String name;
    protected final Random rnd = new Random();
    protected int actual;
    protected GameInfo info;

    public Bot(String name) { this.name = name; }
    public String getName() { return name; }

    // Fase 1: scegliere CHECK / DECLARE
    public void init(GameInfo info) {
    	this.info=info;
    	this.actual=0;
    }
    
    public abstract ActionType chooseAction();
    
    public abstract boolean wantsReroll();
    
    public abstract boolean wantsSeeDice();
    
    public void seeDice(int actual) {
    	this.actual=actual;
    } 
	
	public abstract int declared();
    
	
}

