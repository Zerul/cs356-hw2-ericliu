import java.util.List;

public class UserGroup {
	private String groupID;
	private List<String> users;
	
	public UserGroup(String groupID) {
		this.setGroupID(groupID);
	}
	
	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public void addUser(User user) {
		if (!users.contains(user.getID()))
            users.add(user.getID());
	}
	
	public void removeUser(User user) {
		users.remove(user.getID());
	}
}
