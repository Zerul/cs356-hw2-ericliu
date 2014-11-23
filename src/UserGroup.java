import java.util.Vector;

public class UserGroup implements TwitterElement{
	private String groupID;
	private Vector<TwitterElement> children;

	public UserGroup(String groupID) {
		this.setGroupID(groupID);
		children = new Vector<TwitterElement>();
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public void add(TwitterElement element) {
		if (!children.contains(element))
			children.add(element);
	}

	public String toString() {
		return this.groupID;
	}

	@Override
	public void accept(TwitterElementVisitor v) {
		v.visitGroup(this);
		for(TwitterElement element: children) {
			element.accept(v);
		}
	}
}
