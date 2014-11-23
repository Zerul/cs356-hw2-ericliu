
public class PositiveCountVisitor implements TwitterElementVisitor {

	private int total, count;

	public PositiveCountVisitor() {
		setTotal(0);
		setCount(0);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPercentage() {
		return (double)count/total;
	}

	@Override
	public void visitTwitterUser(TwitterUser u) {
		for(Object o: u.getNewsfeed().toArray()) {
			if (o.toString().toLowerCase().contains("good") || o.toString().toLowerCase().contains("excellent")) {
				setCount(getCount()+1);
			}
			setTotal(getTotal()+1);
		}
	}

	@Override
	public void visitGroup(UserGroup g) {
		return;
	}

}
