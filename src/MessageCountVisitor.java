
public class MessageCountVisitor implements TwitterElementVisitor {

	private int counter = 0;

	public MessageCountVisitor() {
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
		this.setCounter(getCounter()+u.getNewsfeed().size());
	}

	@Override
	public void visitGroup(UserGroup g) {
		return;
	}

}
