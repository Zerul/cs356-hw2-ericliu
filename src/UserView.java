import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserView implements ActionListener {

	JFrame frame;
	JPanel topPanel, bottomPanel, topButtonPanel;
	JTextField userID, tweetMsg;
	JButton follow, tweet;
	JScrollPane userPane, newsfeedPane;
	JList<String> userList, newsfeed;
	DefaultListModel<String> userModel, newsModel;
	GridBagConstraints c;
	User u;
	
	public UserView(User user) {

		u = user;
		frame = new JFrame(user.getID() + "'s User View");
		frame.setPreferredSize(new Dimension(400, 450));
		frame.getContentPane().setLayout(new GridLayout(2, 1, 3, 4));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JPanels
		topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		
		//JTextFields
		userID = new JTextField(20);
		tweetMsg = new JTextField(20);
		
		//JButtons
		follow = new JButton("Subscribe to user");
		follow.addActionListener(this);
		tweet = new JButton("Post tweet");
		tweet.addActionListener(this);
		
		//JList
		userModel = new DefaultListModel<String>();
		for (User subs : user.getSubscriptions()) {
			userModel.addElement(subs.getID());
		}
		userList = new JList<String>(userModel);
		
		newsModel = new DefaultListModel<String>();
		for (String news : user.getNewsfeed()) {
			newsModel.add(0, news);
		}
		newsfeed = new JList<String>(newsModel);
		
		//List Scroll box
		userPane = new JScrollPane(userList);
		newsfeedPane = new JScrollPane(newsfeed);
		
		//configure GridBagLayout
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		topPanel.add(userID, c);
		bottomPanel.add(tweetMsg, c);
		
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
	}
	
	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("Subscribe to user")) {
			u.subscribe(AdminPanel.getInstance().getUserMap().get(userID.getText()));
			userModel.addElement(userID.getText());
		} else if (a.getActionCommand().equals("Post tweet")) {
			u.tweet(tweetMsg.getText());
			newsModel.add(0, tweetMsg.getText());
		}
	}
}
