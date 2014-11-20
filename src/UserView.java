import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class UserView implements ActionListener, User {
	
    private String id, tweetMsg;
    private Vector<User> subscribers;
	private DefaultListModel<String> newsfeed, subscriptions;
	private JFrame frame;
	private JPanel topPanel, bottomPanel;
	private JTextField userID, msgBox;
	private JButton follow, tweet;
	private JScrollPane userPane, newsfeedPane;
	private JList<String> userList, newsBox;
	private GridBagConstraints c;
	
	public UserView(String name) {
        this.setID(name);
        subscribers = new Vector<User>();
        subscriptions = new DefaultListModel<String>();
        subscriptions.addElement(getID());
        newsfeed = new DefaultListModel<String>();
        buildGUI();
	}
	
	//"User" Interface methods
	@Override
	public Vector<User> getSubscribers() {
		return subscribers;
	}
	
	@Override
	public void subscribe(User target) {
		target.getSubscribers().add(this);
		this.subscriptions.addElement(target.getID());
		this.update(target);
		if (userList != null)
			userList.setModel(subscriptions);
	}

	@Override
	public void tweet(String msg) {
		tweetMsg = msg;
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
	}
	
	//Getters and Setters
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getTweetMsg() {
		return tweetMsg;
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
	
	//Creating and managing the GUI
	public void buildGUI() {
		frame = new JFrame(getID() + "'s User View");
		frame.setPreferredSize(new Dimension(400, 450));
		frame.getContentPane().setLayout(new GridLayout(2, 1, 3, 4));
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

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
		frame.setVisible(false);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Subscribe to user")) {
			if (!subscriptions.contains(userID.getText())) {
				subscribe(AdminPanel.getInstance().getUserMap().get(userID.getText().toLowerCase()));
			}
		} else if (e.getActionCommand().equals("Post tweet")) {
			tweet(msgBox.getText());
		} else {
			return;
		}
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}

}
