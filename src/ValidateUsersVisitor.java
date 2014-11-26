import java.util.Vector;


public class ValidateUsersVisitor implements TwitterElementVisitor {

	Vector<String> invalids;
	
	public ValidateUsersVisitor() {
		invalids = new Vector<String>();
	}
	
	@Override
	public void visitTwitterUser(TwitterUser u) {
		if(u.getID().contains(" ") || u.getID().contains("\t")) {
			invalids.add(u.getID());
		}
	}

	@Override
	public void visitGroup(UserGroup g) {
		if(g.getGroupID().contains(" ") || g.getGroupID().contains("\t")) {
			invalids.add(g.getGroupID());
		}
	}
	
	public Vector<String> results() {
		if (invalids.isEmpty()) {
			invalids.add("No invalid users found.");
		} else {
			invalids.add(0, "List of invalid users: ");
		}
		return invalids;
	}

}
