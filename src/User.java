import java.util.List;

public class User {
    private String id;
    private String tweetMsg;
    private List<User> subscribers;
	private List<User> subscriptions;
	private List<String> newsfeed;
    
    public User(String name) {
    	this.setID(name);
    }
    
    public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
	
	public List<User> getSubscribers() {
		return subscribers;
	}

	public List<User> getSubscriptions() {
		return subscriptions;
	}
	
	public String getTweetMsg() {
		return tweetMsg;
	}

	//Create new message, add it to the user's newsfeed, then alert subscribers
	public void tweet(String tweetMsg) {
		this.tweetMsg = tweetMsg;
		newsfeed.add(this.tweetMsg);
		notifySubscribers();
	}
	
	//Subscribes to target user, then updates the newsfeed
	public void subscribe(User target) {
		target.getSubscribers().add(this);
		this.getSubscriptions().add(target);
		this.getUpdate(target);
	}
	
	public void notifySubscribers() {
		for(User subscriber: subscribers) {
			subscriber.getUpdate(this);
		}
	}
	
	public void getUpdate(User target) {
		newsfeed.add(target.getTweetMsg());
	}
	
}
