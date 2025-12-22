package ApeBot;

public class HonestBot extends Bot {
    public HonestBot(String name) { super(name); }

	@Override
	public ActionType chooseAction() {
		// TODO Auto-generated method stub
		return ActionType.DECLARE;
	}

	@Override
	public boolean wantsReroll() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean wantsSeeDice() {
		// TODO Auto-generated method stub
		return true;
	} 

	@Override
	public int declared() {
		// TODO Auto-generated method stub
		
		return actual;
	}

    
}