import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class AdminPanel implements ActionListener{
	
	private static AdminPanel adminInstance = null;
	private HashMap<String, User> userMap;
	private HashMap<String, UserGroup> groupMap;
	JFrame frame;
	JPanel treePanel, buttonPanel, innerTop, innerMid, innerBot;
    JButton addUser, addGroup, userView, userTotal, groupTotal, messageTotal, positivePercentage;
	JTextField userName, groupName;
	JLabel label;
    JList<String> treeView;
	GridBagConstraints c;
	
	private AdminPanel() {
		String[] test = {"Dont", "forget", "that", "this", "is", "here"};
		userMap = new HashMap<String, User>();
		groupMap = new HashMap<String, UserGroup>();
		c = new GridBagConstraints();
		//create frame
		frame = new JFrame("Admin Control Panel.");
		frame.setPreferredSize(new Dimension(800, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridBagLayout());
		
		//Panels
		treePanel = new JPanel();
		buttonPanel = new JPanel();
		innerTop = new JPanel();
		innerMid = new JPanel();
		innerBot = new JPanel(); 
		treePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		buttonPanel.setLayout(new GridBagLayout());
		innerTop.setLayout(new GridBagLayout());
		innerMid.setLayout(new GridBagLayout());
		innerBot.setLayout(new GridBagLayout());

	    
	    //create buttons
	    addUser = new JButton("Create a new user");
	    addUser.setPreferredSize(new Dimension(100, 75));
	    addUser.addActionListener(this);
	    addGroup= new JButton("Create a new group");
	    addGroup.addActionListener(this);
	    userTotal = new JButton("Show user total");
	    userTotal.addActionListener(this);
	    groupTotal = new JButton("Show group total");
	    groupTotal.addActionListener(this);
	    messageTotal = new JButton("Show message total");
	    messageTotal.addActionListener(this);
	    positivePercentage = new JButton("Show Positive Percentage");
	    positivePercentage.addActionListener(this);
	    userView = new JButton("Open user view");
	    userView.addActionListener(this);
	    
	    //JList
	    treeView = new JList<String>(test);
	    treePanel.add(treeView);
	    
	    //JTextFields
	    userName = new JTextField(20);
	    groupName = new JTextField(20);
	    
	    //test label - use to see if the actions respond
	    label = new JLabel("Hello.");
	    
	    //configure GridBagLayout & add to frame
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.7;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(treePanel, c);
		
		c.gridx = 1;
		c.weightx = 1.0;
		frame.getContentPane().add(buttonPanel, c);
		
		c.weighty = 0.2;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(innerTop, c);
		
		c.weighty = 0.3;
		c.gridy = 2;
		buttonPanel.add(innerBot, c);
		
		c.weighty = 0.6;
		c.gridy = 1;
		buttonPanel.add(innerMid, c);
		
		c.insets = new Insets(1, 1, 1, 2);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.6;
		c.weighty = 0.5;
		innerTop.add(userName, c);
		innerBot.add(userTotal, c);
		
		c.gridx = 1;
		c.weightx = 0.4;
		innerTop.add(addUser, c);
		innerBot.add(groupTotal, c);
		
		c.gridy = 1;
		c.weighty = 0.5;
		innerTop.add(addGroup, c);
		innerBot.add(messageTotal, c);
		
		c.gridx = 0;
		c.weightx = 0.6;
		innerTop.add(groupName, c);
		innerBot.add(positivePercentage, c);
		
		
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.3;
		innerMid.add(userView, c);
		
		c.gridy = 1;
		c.weighty = 0.7;
		innerMid.add(label, c);

	    frame.pack();
	    frame.setVisible(true);
	    
	}
	
	public static AdminPanel getInstance() {
		if (adminInstance == null)
			adminInstance = new AdminPanel();
		return adminInstance;
	}
	
	public HashMap<String, User> getUserMap() {
		return userMap;
	}

	public void actionPerformed(ActionEvent a) {
	    if (a.getActionCommand().equals("Create a new user")) {
	        userMap.put(userName.getText(), new User(userName.getText()));
	        label.setText("User: " + userName.getText() + " has been created.");
	    }
	    else if (a.getActionCommand().equals("Create a new group")) {
	    	groupMap.put(groupName.getText(), new UserGroup(groupName.getText()));
	    	label.setText("Group: " + groupName.getText() + " has been created.");
	    }
	    else if (a.getActionCommand().equals("Open user view")) {
	    	UserView newView = new UserView(userMap.get(userName.getText()));
	    	label.setText(a.getActionCommand());
	    }
	    else if (a.getActionCommand().equals("Show message total"))
	    	label.setText(a.getActionCommand());
	    else if (a.getActionCommand().equals("Show user total"))
	    	label.setText(a.getActionCommand());
	    else if (a.getActionCommand().equals("Show positive percentage"))
	    	label.setText(a.getActionCommand());
	    else 
	    	label.setText(a.getActionCommand());
	}
}