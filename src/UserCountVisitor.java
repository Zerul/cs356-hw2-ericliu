
public class UserCountVisitor implements TwitterElementVisitor {

	private int counter = 0;
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public void visitTwitterUser(User u) {
		setCounter(getCounter()+1);
	}

	@Override
	public void visitGroup(UserGroup g) {
		return;
	}

}
