
public class LastUpdateVisitor implements TwitterElementVisitor {

	String id;
	long updateTime;
	
	public LastUpdateVisitor() {
		setId(null);
		setUpdateTime(0);
	}
	
	@Override
	public void visitTwitterUser(TwitterUser u) {
		if(u.getLastUpdateTime() > getUpdateTime()) {
			setUpdateTime(u.getLastUpdateTime());
			setId(u.getID());
		}
	}

	@Override
	public void visitGroup(UserGroup g) {
		// TODO Auto-generated method stub

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

}
