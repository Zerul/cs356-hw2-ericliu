import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class UserView implements ActionListener {

	JFrame frame;
	JPanel topPanel, bottomPanel;
	JTextField userID, tweetMsg;
	JButton follow, tweet;
	JScrollPane topListBox, bottomListBox;
	JList userList, newsfeed;
	
	public UserView() {
		frame = new JFrame("User View");
		frame.setPreferredSize(new Dimension(600, 480));
		frame.getContentPane().setLayout(new GridLayout(2, 1, 3, 4));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JPanels
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		//JTextFields
		userID = new JTextField(20);
		tweetMsg = new JTextField(20);
		
		//JButtons
		follow = new JButton("Subscribe to user");
		follow.addActionListener(this);
		tweet = new JButton("Post tweet");
		tweet.addActionListener(this);
		
		//JList
		userList = new JList();
		newsfeed = new JList();
		
		//List Scroll box
		topListBox = new JScrollPane();
		bottomListBox = new JScrollPane();
		
		//adding content
		frame.getContentPane().add(topPanel);
		frame.getContentPane().add(bottomPanel);
		topPanel.add(userID);
		topPanel.add(follow);
		topPanel.add(topListBox);
		topListBox.add(userList);
		bottomPanel.add(tweetMsg);
		bottomPanel.add(tweet);
		bottomPanel.add(bottomListBox);
		bottomListBox.add(newsfeed);
		
		
		//last
		frame.pack();
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("Subscribe to user")) {
			//do something
		}
	}
}
