import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class TwitterUser implements ActionListener, User, TwitterElement{

	private String id, tweetMsg;
	private Vector<User> subscribers;
	private DefaultListModel<String> newsfeed, subscriptions;
	private JFrame frame, timeFrame;
	private JPanel topPanel, bottomPanel;
	private JTextField userID, msgBox;
	private JButton follow, tweet, timeButton;
	private JScrollPane userPane, newsfeedPane;
	private JList<String> userList, newsBox, timeInfo;
	private GridBagConstraints c;
	private long creationTime , lastUpdateTime;

	public TwitterUser(String name) {
		setCreationTime(System.currentTimeMillis());
		this.setID(name);
		subscribers = new Vector<User>();
		subscriptions = new DefaultListModel<String>();
		getSubscriptions().addElement("User created: " + Long.toString(creationTime));
		newsfeed = new DefaultListModel<String>();
	}

	//"User" Interface methods
	@Override
	public Vector<User> getSubscribers() {
		return subscribers;
	}

	@Override
	public void subscribe(User target) {
		target.getSubscribers().add(this);
		this.getSubscriptions().addElement(target.getID());
		this.update(target);
		if (userList != null)
			userList.setModel(subscriptions);
	}

	@Override
	public void tweet(String msg) {
		setTweetMsg(msg);
		update(this);
		notifySubscribers();
	}

	@Override
	public void notifySubscribers() {
		for (User subscriber: subscribers) {
			subscriber.update(this);
		}
	}

	@Override
	public void update(User user) {
		newsfeed.add(0, user.getID() + ": " + user.getTweetMsg());
		if (newsBox != null)
			newsBox.setModel(newsfeed);
		setLastUpdateTime(System.currentTimeMillis());
		newsfeed.add(0, "Last updated: " + Long.toString(getLastUpdateTime()));
	}

	@Override
	public String getTweetMsg() {
		return tweetMsg;
	}

	@Override
	public void accept(TwitterElementVisitor v) {
		v.visitTwitterUser(this);
	}

	//Getters and Setters
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setTweetMsg(String tweetMsg) {
		this.tweetMsg = tweetMsg;
	}

	public DefaultListModel<String> getNewsfeed() {
		return newsfeed;
	}

	public DefaultListModel<String> getSubscriptions() {
		return subscriptions;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	//Creating the GUI
	public JFrame buildGUI() {
		frame = new JFrame(getID() + " 's User View");
		frame.setPreferredSize(new Dimension(400, 450));
		frame.getContentPane().setLayout(new GridLayout(2, 1, 3, 4));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//JPanels
		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());

		//JTextFields
		userID = new JTextField(20);
		msgBox = new JTextField(20);

		//JButtons
		follow = new JButton("Subscribe to user");
		follow.addActionListener(this);
		tweet = new JButton("Post tweet");
		tweet.addActionListener(this);

		//JList
		userList = new JList<String>(subscriptions);
		newsBox = new JList<String>(newsfeed);

		//List Scroll box
		userPane = new JScrollPane(userList);
		newsfeedPane = new JScrollPane(newsBox);

		//configure GridBagLayout
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		topPanel.add(userID, c);
		bottomPanel.add(msgBox, c);

		c.gridx = 1;
		topPanel.add(follow, c);
		bottomPanel.add(tweet, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		topPanel.add(userPane, c);
		bottomPanel.add(newsfeedPane, c);

		//finalizing GUI
		frame.getContentPane().add(topPanel);
		frame.getContentPane().add(bottomPanel);
		frame.pack();
		frame.setVisible(true);
		return frame;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Subscribe to user")) {
			if (!subscriptions.contains(userID.getText()) && AdminPanel.getInstance().getUserMap().get(userID.getText().toLowerCase()) != null) {
				subscribe(AdminPanel.getInstance().getUserMap().get(userID.getText().toLowerCase()));
			}
		} else if (e.getActionCommand().equals("Post tweet")) {
			tweet(msgBox.getText());
		} else {
			return;
		}
	}

	public String toString() {
		return getID();
	}

}
