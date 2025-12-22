package ApeBot;

public class RandomBot extends Bot {
    public RandomBot(String name) { super(name); }

    
	public ActionType chooseAction() {
		// TODO Auto-generated method stub
		if(info.previousDeclaredNumber==21) {
			return ActionType.CHECK;
		}
		return ActionType.DECLARE;
	}

	@Override
	public boolean wantsReroll() {
		// TODO Auto-generated method stub
		if(info.previousDeclaredNumber==0) {
			return true; 
		}
		return false;
	}

	@Override
	public boolean wantsSeeDice() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int declared() {
		// TODO Auto-generated method stub
		return 0;
	}
}

