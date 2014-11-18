import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class AdminPortal implements ActionListener{
	
	private static AdminPortal adminInstance = null;
	private HashMap<String, User> userMap;
	private HashMap<String, UserGroup> groupMap;
	JFrame frame;
    JButton addUser, addGroup, userView, userTotal, groupTotal, messageTotal, positivePercentage;
	JTextField userName, groupName;
	JLabel label;
	JPanel treePanel;
	
	private AdminPortal() {
		
		userMap = new HashMap<String, User>();
		groupMap = new HashMap<String, UserGroup>();
		
		//create frame
		frame = new JFrame("Admin Control Panel.");
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());
		
		//Panels
		treePanel = new JPanel();
		treePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		treePanel.setPreferredSize(new Dimension(400, 600));
		frame.getContentPane().add(treePanel);
	    
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
	    
	    //text fields
	    userName = new JTextField(20);
	    groupName = new JTextField(20);
	    
	    //test label - use to see if the actions respond
	    label = new JLabel("Hello.");
	    
	    //add content to frame
	    frame.getContentPane().add(label);
	    frame.getContentPane().add(addUser);
		frame.getContentPane().add(userName);
		frame.getContentPane().add(groupName);
	    frame.getContentPane().add(addGroup);
	    frame.getContentPane().add(userTotal);
	    frame.getContentPane().add(groupTotal);
	    frame.getContentPane().add(messageTotal);
	    frame.getContentPane().add(positivePercentage);
	    frame.getContentPane().add(userView); 
	    frame.pack();
	    frame.setVisible(true);
	    
	}
	
	public static AdminPortal getInstance() {
		if (adminInstance == null)
			adminInstance = new AdminPortal();
		return adminInstance;
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
	    else if (a.getActionCommand().equals("Show group total"))
	    	label.setText(a.getActionCommand());
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