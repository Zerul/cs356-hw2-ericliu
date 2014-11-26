import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class AdminPanel implements ActionListener {

	private static AdminPanel adminInstance = null;
	private HashMap<String, TwitterUser> userMap;
	private HashMap<String, UserGroup> groupMap;
	private JFrame frame;
	private JPanel treePanel, buttonPanel, innerTop, innerMid, innerBot;
	private JScrollPane treePane;
	private JButton addUser, addGroup, userView, userTotal, groupTotal, messageTotal, positivePercentage, validateUsers, lastUpdated;
	private JTextField userName, groupName;
	private JLabel label;
	private JTree tree;
	private DefaultTreeModel modelTree;
	private DefaultMutableTreeNode root, selectedNode, temp;
	private GridBagConstraints c;

	private AdminPanel() {
		userMap = new HashMap<String, TwitterUser>();
		groupMap = new HashMap<String, UserGroup>();
		generateUI();
	}

	public static AdminPanel getInstance() {
		if (adminInstance == null)
			adminInstance = new AdminPanel();
		return adminInstance;
	}

	public HashMap<String, TwitterUser> getUserMap() {
		return userMap;
	}		

	public void actionPerformed(ActionEvent a) {
		switch (a.getActionCommand()) {

		case "Create a new user":
			if (selectedNode == null || !(selectedNode.getUserObject() instanceof TwitterUser))
				createUser(userName.getText().toLowerCase());
			break;

		case "Create a new group":
			if (selectedNode == null || !(selectedNode.getUserObject() instanceof TwitterUser))
				createGroup(groupName.getText().toLowerCase());
			break;  

		case "Open user view":
			if (selectedNode != null && (selectedNode.getUserObject() instanceof TwitterUser))
				openView(selectedNode);
			else
				label.setText("You must select a user.");
			break;

		case "Show user total":
			UserCountVisitor uv = new UserCountVisitor();
			((TwitterElement)root.getUserObject()).accept(uv);
			label.setText("Total # of users: " + Integer.toString(uv.getCounter()));
			break;

		case "Show group total":
			GroupCountVisitor gv = new GroupCountVisitor();
			((TwitterElement)root.getUserObject()).accept(gv);
			label.setText("Total # of groups: " + Integer.toString(gv.getCounter()));
			break;

		case "Show message total":
			MessageCountVisitor mv = new MessageCountVisitor();
			((TwitterElement)root.getUserObject()).accept(mv);
			label.setText("Total # of messages: " + Integer.toString(mv.getCounter()));
			break;

		case "Show positive percentage":
			PositiveCountVisitor pv = new PositiveCountVisitor();
			((TwitterElement)root.getUserObject()).accept(pv);
			label.setText("% of positive messages: " + Double.toString(pv.getPercentage()));
			break;
			
		case "Validate Users":
			validateUsers();
			break;
			
		case "Find last updated user":
			userUpdateTime();
			break;

		default:
			break;
		}
	}

	//User names are case insensitive, and must be longer than 2 characters
	//User cannot already exist in the userMap, and the selected tree node CANNOT be a user
	//If no tree node is selected, the method will add the user to root by default
	//The newly created user is added to the selectedNode's list of contained elements.
	public void createUser(String name) {
		if (name.length() < 2) {
			label.setText("Username is too short, must be at least 2 characters.");
		} else if (userMap.get(name) == null) {
			userMap.put(name, new TwitterUser(name));
			temp = new DefaultMutableTreeNode(userMap.get(name));
			temp.setAllowsChildren(false);
			try {
				selectedNode.add(temp);
				((UserGroup)selectedNode.getUserObject()).add(userMap.get(name));;
			} catch (NullPointerException e) {
				root.add(temp);
				((UserGroup)root.getUserObject()).add(userMap.get(name));;
				modelTree.reload(root);
			}
			modelTree.reload(selectedNode);
			label.setText("User: " + name + " has been created.");
		} else
			label.setText("User: " + name + " already exists.");
	}

	//Group names have the same requirement as user names
	//Performs the same function as createUser(), except with groups. 
	public void createGroup(String name) {
		if (name.length() < 2) {
			label.setText("Group name is too short, must be at least 2 characters.");
		} else if (groupMap.get(name) == null) {
			groupMap.put(name, new UserGroup(name));
			temp = new DefaultMutableTreeNode(groupMap.get(name));
			try {
				selectedNode.add(temp);
				((UserGroup)selectedNode.getUserObject()).add(groupMap.get(name));;
			} catch (NullPointerException e){
				root.add(temp);
				((UserGroup)root.getUserObject()).add(groupMap.get(name));;
				modelTree.reload(root);
			}
			modelTree.reload(selectedNode);
			label.setText("Group: " + name + " has been created.");
		} else
			label.setText("Group: " + name + " already exists.");
	}

	//Checks to see that the selectedNode doesn't allow children (thus is a user)
	//before calling the buildGUI() function
	public void openView(DefaultMutableTreeNode n) {
		((TwitterUser) n.getUserObject()).buildGUI();
		label.setText(n.toString() + "'s user view opened.");
	}
	
	public void validateUsers() {
		ValidateUsersVisitor vv = new ValidateUsersVisitor();
		((TwitterElement)root.getUserObject()).accept(vv);
	    JFrame vFrame = new JFrame("User Validation");
	    JList<String> vList = new JList<String>(vv.results());
	    vFrame.setPreferredSize(new Dimension(300, 200));
	    vFrame.getContentPane().setLayout(new GridLayout());
	    vFrame.getContentPane().add(vList);
	    vFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    vFrame.pack();
	    vFrame.setVisible(true);
	}
	
	public void userUpdateTime() {
		LastUpdateVisitor lv = new LastUpdateVisitor();
		((TwitterElement)root.getUserObject()).accept(lv);
	    JFrame vFrame = new JFrame("Last updated user");
	    JLabel timeLabel = new JLabel(lv.getId());
	    vFrame.setPreferredSize(new Dimension(300, 200));
	    vFrame.getContentPane().setLayout(new GridLayout());
	    vFrame.getContentPane().add(timeLabel);
	    vFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    vFrame.pack();
	    vFrame.setVisible(true);
	}

	//Creates the UI for the admin control panel
	public void generateUI() {

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
		treePanel.setLayout(new GridLayout());
		buttonPanel.setLayout(new GridBagLayout());
		innerTop.setLayout(new GridBagLayout());
		innerMid.setLayout(new GridBagLayout());
		innerBot.setLayout(new GridBagLayout());


		//create buttons
		addUser = new JButton("Create a new user");
		addUser.addActionListener(this);
		addGroup= new JButton("Create a new group");
		addGroup.addActionListener(this);
		userTotal = new JButton("Show user total");
		userTotal.addActionListener(this);
		groupTotal = new JButton("Show group total");
		groupTotal.addActionListener(this);
		messageTotal = new JButton("Show message total");
		messageTotal.addActionListener(this);
		positivePercentage = new JButton("Show positive percentage");
		positivePercentage.addActionListener(this);
		userView = new JButton("Open user view");
		userView.addActionListener(this);
		validateUsers = new JButton("Validate Users");
		validateUsers.addActionListener(this);
		lastUpdated = new JButton("Find last updated user");
		lastUpdated.addActionListener(this);

		//Tree Configuration
		root = new DefaultMutableTreeNode(new UserGroup("root"));
		modelTree = new DefaultTreeModel(root);
		tree = new JTree(modelTree);
		tree.setCellRenderer(new CustomRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeExpansionListener(new TreeExpansionListener() {
			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				TreePath tp = event.getPath();
				label.setText("Expansion: " + tp.getLastPathComponent());
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				TreePath tp = event.getPath();
				label.setText("Collapse: " + tp.getLastPathComponent());
			}
		});

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
			}
		});
		treePane = new JScrollPane(tree);
		treePanel.add(treePane);

		//JTextFields
		userName = new JTextField(20);
		groupName = new JTextField(20);

		//test label - use to see if the actions respond
		label = new JLabel("Hello.");
		label.setHorizontalAlignment(SwingConstants.CENTER);

		//configure GridBagLayout & add to frame
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.7;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(treePanel, c);

		c.gridx = 1;
		c.weightx = 0.5;
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
		innerBot.add(positivePercentage, c);

		c.gridx = 0;
		c.weightx = 0.6;
		innerTop.add(groupName, c);
		innerBot.add(messageTotal, c);

		c.gridy = 0;
		c.weightx = 0.5;
		c.weighty = 0.3;
		innerMid.add(userView, c);
		
		c.gridx = 1;
		c.weightx = 0.5;
		c.weighty = 0.3;
		innerMid.add(validateUsers, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0.3;
		c.weightx = 0.6;
		innerMid.add(label, c);
		
		c.gridx = 1;
		c.weightx = 0.5;
		innerMid.add(lastUpdated, c);
		

		frame.pack();
		frame.setVisible(true);
	}

	//Makes UserGroup icons on the tree appear as folders
	@SuppressWarnings("serial")
	private static class CustomRenderer extends DefaultTreeCellRenderer {

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean selected, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

			if (value instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) value;
				if (n.getUserObject() instanceof UserGroup) {
					setIcon(UIManager.getIcon("Tree.closedIcon"));
				}
			}
			return this;
		}
	}

}