
public class GroupCountVisitor implements TwitterElementVisitor {

	private int counter;

	public GroupCountVisitor() {
		setCounter(0);
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public void visitTwitterUser(TwitterUser u) {
		return;
	}

	@Override
	public void visitGroup(UserGroup g) {
		this.setCounter(getCounter()+1);
	}

}
