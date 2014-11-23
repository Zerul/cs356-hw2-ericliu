import java.util.List;

public interface User {

	public void subscribe(User u);

	public void tweet(String msg);

	public void notifySubscribers();

	public void update(User u);

	public String getID();

	public String getTweetMsg();

	public List<User> getSubscribers();

}
