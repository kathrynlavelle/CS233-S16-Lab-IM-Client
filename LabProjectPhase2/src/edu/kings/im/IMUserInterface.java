package edu.kings.im;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * GUI for Instant Messaging.
 * 
 * @author Dave Paupst
 * @author Kate Lavelle
 * @version 04/07/2016
 */
public class IMUserInterface implements ActionListener {

	/** Represents the name for all users. */
	public final static String BLAST;
	
	/** The buddyframe. */
	private JFrame buddyFrame;
	
	/** The list of online users for buddyframe. */
	private JList<String> userList;
	
	/** The listModel for the online users. */
	private DefaultListModel<String> listModel;
	
	/** Currently selected buddy. */
	private String recipient;

	/** Chat frame. */
	private JFrame frame;

	/** Chat box for viewing messages. */
	private JTextArea chatBox;

	/** Message box for sending messages. */
	private JTextField messageBox;

	/** Button to send messages. */
	private JButton sendButton;

	/** Menu bar for chat frame. */
	private JMenuBar menuBar;
	
	/** Menu bar for buddy frame. */
	private JMenuBar buddyMenuBar;

	/** Login frame. */
	private JFrame loginFrame;

	/** Keeps track of the current user. */
	private IMConnection user;

	/** TabbedPane to hold chat tabs. */
	private JTabbedPane chatBoxes;

	static {
		BLAST = "EVERYONE";
	}

	/**
	 * Constructor.
	 */
	public IMUserInterface() {
		//Setup for the buddy list frame.
		buddyFrame = new JFrame("Buddy Frame");
		buddyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buddyFrame.setPreferredSize(new Dimension(300, 600));
		buddyFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				closeWindow();
			}
		});

		// Setup for the buddylist
		listModel = new DefaultListModel<String>();
		userList = new JList<String>(listModel);
		userList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		userList.setLayoutOrientation(JList.VERTICAL);
		userList.setVisibleRowCount(-1);
		userList.addListSelectionListener(new BuddyListSelectionListener());
		userList.setBackground(Color.DARK_GRAY);
		userList.setForeground(Color.GREEN);
		buddyFrame.add(userList, BorderLayout.CENTER);
		listModel.addElement(BLAST);

		// Chat frame set up
		frame = new JFrame("Chat Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 600));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				closeWindow();
			}
		});

		// Add tabbed pane to chat frame.
		chatBoxes = new JTabbedPane();
		chatBoxes.setForeground(Color.blue);
		frame.add(chatBoxes, BorderLayout.NORTH);

		// Setup the chat box.
		chatBox = new JTextArea();
		chatBox.setLineWrap(true);
		chatBox.setEditable(false);

		// Setup message area in chat frame.
		JPanel messageArea = new JPanel(new GridBagLayout());
		GridBagConstraints messageConstraints = new GridBagConstraints();

		// Text field used to send messages.
		messageBox = new JTextField();
		messageBox.setBorder(BorderFactory.createTitledBorder("Enter Message"));
		messageBox.addActionListener(this);

		messageConstraints.fill = GridBagConstraints.BOTH;
		messageConstraints.gridx = 0;
		messageConstraints.gridy = 0;
		messageConstraints.weightx = .7;
		messageConstraints.weighty = 1;
		messageArea.add(messageBox, messageConstraints);

		// Send button & user list.
		JPanel userSend = new JPanel(new GridLayout());

		sendButton = new JButton("Send");
		sendButton.addActionListener(this);

		// userSend.add(users);
		userSend.add(sendButton);

		messageConstraints.fill = GridBagConstraints.BOTH;
		messageConstraints.gridx = 1;
		messageConstraints.gridy = 0;
		messageConstraints.weightx = .3;
		messageConstraints.weighty = 1;
		messageArea.add(userSend, messageConstraints);

		frame.add(messageArea, BorderLayout.SOUTH);

		menuBar = new JMenuBar();
		buddyMenuBar = new JMenuBar();

		// File menus and exit items
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);

		JMenu buddyFile = new JMenu("File");
		buddyFile.setMnemonic(KeyEvent.VK_F);
		buddyMenuBar.add(buddyFile);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				Event.ALT_MASK));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});

		JMenuItem buddyExit = new JMenuItem("Exit");
		buddyExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				Event.ALT_MASK));
		buddyExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});

		file.add(exit);
		buddyFile.add(buddyExit);

		frame.setJMenuBar(menuBar); // Add menubar to chat frame.
		buddyFrame.setJMenuBar(buddyMenuBar); // Add menubar to buddy frame.
		frame.setVisible(false); // Set chat frame to false until login is
									// successful.

		loginSetup(); // Setup login frame.
	}

	/**
	 * Getter for text area. FOR TESTING PURPOSES ONLY !
	 * 
	 * @return the chatBox.
	 */
	protected JTextArea getTextArea() {
		JScrollPane scrollPane = (JScrollPane) chatBoxes.getComponentAt(0);
		JViewport viewPort = scrollPane.getViewport();
		JTextArea chatArea = (JTextArea) viewPort.getComponent(0);
		return chatArea;
	}

	/**
	 * Getter for tabbed area. FOR TESTING PURPOSES ONLY !
	 * @return the chatBoxes.
	 */
	protected JTabbedPane getChatBoxes() {
		return chatBoxes;
	}

	/**
	 * Getter for text field. FOR TESTING PURPOSES ONLY !
	 * 
	 * @return the messageBox.
	 */
	protected JTextField getMessageBox() {
		return messageBox;
	}

	/**
	 * Getter for send button. FOR TESTING PURPOSES ONLY !
	 * 
	 * @return the sendButton.
	 */
	protected JButton getSendButton() {
		return sendButton;
	}

	/**
	 * Getter for buddy listModel. FOR TESTING PURPOSES ONLY !
	 * 
	 * @return the listModel from the buddy list. 
	 */
	protected DefaultListModel<String> getList() {
		return listModel;
	}

	/**
	 * Sets up the login frame.
	 */
	private void loginSetup() {
		// Login setup.
		loginFrame = new JFrame("Login");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(500, 500);
		// loginFrame.getContentPane().setBackground(Color.DARK_GRAY);

		JPanel loginPanel = new JPanel(new GridBagLayout());
		GridBagConstraints loginC = new GridBagConstraints();
		loginPanel.setBackground(Color.DARK_GRAY);

		// Adding logo.
		loginC.fill = GridBagConstraints.BOTH;
		loginC.gridx = 0;
		loginC.gridy = 0;
		loginC.weightx = 1;
		loginC.weighty = .8;

		ImageView imgView = new ImageView("NullPointDexter Logo.jpg");
		loginPanel.add(imgView, loginC);

		final JTextField NAME_ENTRY = new JTextField();
		NAME_ENTRY.setBorder(BorderFactory.createTitledBorder("Username"));

		NAME_ENTRY.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login(NAME_ENTRY.getText());
			}
		});

		loginC.fill = GridBagConstraints.HORIZONTAL;
		loginC.gridx = 0;
		loginC.gridy = 1;
		loginC.weightx = .7;
		loginC.weighty = .1;

		loginPanel.add(NAME_ENTRY, loginC);

		// Login prompt and logic.
		loginFrame.add(loginPanel, BorderLayout.CENTER);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login(NAME_ENTRY.getText());
			}
		});

		loginC.fill = GridBagConstraints.NONE;
		loginC.gridx = 0;
		loginC.gridy = 2;
		loginC.weightx = .7;
		loginC.weighty = .1;

		loginPanel.add(loginButton, loginC); // Add login button to login frame.

		loginFrame.setVisible(true); // Make login frame visible.
	}

	/**
	 * Uses the username to connect to the server.
	 * 
	 * @param username
	 *            Username inserted by the user.
	 */
	public void login(String username) {
		IMConnection newUser = RealIMConnection.getInstance(username);
		newUser.addConnectionListener(new IMStatusListener());

		try {
			newUser.connect();
		} catch (IllegalNameException ine) {
			System.out.println("Username error message displayed");
			JPanel loginPanel2 = new JPanel(new GridBagLayout());
			loginPanel2.setBackground(Color.DARK_GRAY);
			JLabel incorrect = new JLabel("Please select a different Username");
			incorrect.setFont(new Font("Dialog", Font.BOLD, 16));
			incorrect.setForeground(Color.yellow);

			loginPanel2.add(incorrect); // Error message for invalid username.
			loginFrame.add(loginPanel2, BorderLayout.SOUTH);
			loginFrame.setSize(500, 550);
		}
	}

	/**
	 * Potential protected setter for User.
	 * 
	 * @param daUser
	 *            User being set.
	 */
	protected void setUser(IMConnection daUser) {
		user = daUser;
	}

	/**
	 * Sends a message to specified user.
	 * 
	 * @param message
	 *            Message to be sent.
	 * @param toWhom
	 *            User who is receiving message.
	 */
	public void sendMessage(String message, String toWhom) {
		if (toWhom.equals(BLAST)) {
			user.spamEveryone(message); // Spam all online users a message.
		} else {
			user.sendMessage(message, toWhom); // Send message to specifed
												// recipient.
		}
		for (int i = 0; i < chatBoxes.getTabCount(); i++) {
			if (toWhom.equals(chatBoxes.getTitleAt(i))) { // Get tab instance
															// for specified
															// recipient.
				chatBoxes.setSelectedIndex(i);
				chatBoxes.getComponentAt(i).setForeground(Color.GREEN);
				JScrollPane scrollPane = (JScrollPane) chatBoxes
						.getComponentAt(i);
				JViewport viewPort = scrollPane.getViewport();
				JTextArea chatArea = (JTextArea) viewPort.getComponent(0);
				chatArea.append("Me: \n\t" + message + "\n"); // Append sent
																// message to
																// chatArea in
																// tab.
			}
		}
	}

	/**
	 * Closes the IM frame and disconnects the user from the server.
	 */
	private void closeWindow() {
		user.disconnect();
		System.out.println("Disconnected");
		System.exit(0);
	}

	/**
	 * Gets a message from the messageBox and calls method to send it.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String msg = messageBox.getText(); // Get text from the message box.
		int selectedIndex = chatBoxes.getSelectedIndex(); // Get the index of
															// the recipient
															// from the tabbed
															// pane.
		recipient = chatBoxes.getTitleAt(selectedIndex); // Set the recipient to
															// the name of the
															// tab at that
															// index.
		if (msg != null && !msg.trim().equals("")) {
			sendMessage(msg, recipient); // Send message to recipient.
		}
		messageBox.setText(""); // Remove text from message box after it is
								// sent.
	}

	/**
	 * This private class implements StatusListener.
	 */
	private class IMStatusListener implements StatusListener {

		/**
		 * Displays message if user tries to login with taken username.
		 */
		@Override
		public void connectionRejected(RealIMConnection arg0) {
			JOptionPane.showMessageDialog(frame, "This username is taken.");
		}

		/**
		 * Diplays buddlist frame and chat frame if login is successful.
		 */
		@Override
		public void connectionUpdate(RealIMConnection arg0, boolean connected) {
			if (connected) {
				frame.pack();
				frame.setLocationByPlatform(true); // Avoid hiding other frames
													// by letting OS stagger
													// them.
				frame.setVisible(true); // Make chat frame visible.

				buddyFrame.pack();
				buddyFrame.setLocationByPlatform(true); // Avoid hiding other
														// frames by letting OS
														// stagger them.
				buddyFrame.setVisible(true); // Make buddy frame visible.

				loginFrame.dispose(); // Dispose of login frame.
				arg0.addIMEventListener(new MessageListener()); // Add message
																// listener to
																// IMConnection.
				setUser(arg0); // Set user for IMConnection.
			}
		}
	}

	/**
	 * Returns an instance of MessageListener. FOR TESTING PURPOSES ONLY !
	 * 
	 * @return an instance of MessageListener.
	 */
	protected MessageListener getMessageListenerInstance() {
		return new MessageListener();
	}

	/**
	 * This class implements IMEventListener.
	 * It dislays all received messages from server.
	 * 
	 * @author Dave Paupst
	 * @author Kate Lavelle
	 */
	private final class MessageListener implements IMEventListener {

		/**
		 * Gets collection of received messages.
		 */
		@Override
		public void messagesReceived(Iterable<Message> arg0) {
			for (Message msg : arg0) {
				String sender = msg.getSender();
				String message = msg.getText();
				boolean found = false;
				if (chatBoxes.getTabCount() > 0) {
					for (int i = 0; i < chatBoxes.getTabCount(); i++) {
						if (sender.equals(chatBoxes.getTitleAt(i))) {
							JScrollPane scrollPane = (JScrollPane) chatBoxes
									.getComponentAt(i);
							JViewport viewPort = scrollPane.getViewport();
							JTextArea chatArea = (JTextArea) viewPort
									.getComponent(0);
							chatArea.append(sender + ": \n\t" + message + "\n");
							found = true;
						}
					}
				}
				if (!found) {
					JTextArea chatArea = new JTextArea();
					chatArea.setEditable(false);
					chatArea.setForeground(Color.WHITE);
					chatArea.setBackground(Color.BLACK);

					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setPreferredSize(new Dimension(600, 450));
					scrollPane
							.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					scrollPane.setViewportView(chatArea);

					chatBoxes.addTab(sender, null, scrollPane);
					chatArea.append(sender + ": \n\t" + message + "\n");
				}
			}
		}

		/**
		 * Removes user from chat frame and buddy list when they go offline.
		 */
		@Override
		public void userOffline(String arg0) {
			listModel.removeElement(arg0); // Remove name from buddy list.
			// If there is a convo, close the tab
			for (int i = 0; i < chatBoxes.getTabCount(); i++) {
				if (arg0.equals(chatBoxes.getTitleAt(i))) {
					chatBoxes.removeTabAt(i); // Remove open tab.
				}
			}
		}

		/**
		 * Adds user to buddy list when they come online.
		 */
		@Override
		public void userOnline(String arg0) {
			listModel.addElement(arg0); // Add name to buddy list.
		}
	}

	/**
	 * This class constructs the logo.
	 * 
	 * @author Dave Paupst
	 * @author Kate Lavelle
	 */
	private final class ImageView extends JPanel {
		/** Generated serial UID. */
		private static final long serialVersionUID = 6902974572544496618L;

		/** The image. */
		private Image img;

		/**
		 * Constructs the tool kit for the image.
		 * 
		 * @param imgFileName
		 *            The name of the file.
		 */
		public ImageView(String imgFileName) {
			img = Toolkit.getDefaultToolkit().getImage(imgFileName);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	/**
	 * The ListSelectionListener for the buddy list.
	 * It gets the proper chat tab for the selected user on the list, or creates a new tab 
	 * if there is not already a current chat session open for that user.
	 * 
	 * @author Dave Paupst
	 * @author Kate Lavelle
	 *
	 */
	private class BuddyListSelectionListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (arg0.getValueIsAdjusting() == false) {
				if (userList.getSelectedIndex() != -1) {
					String buddyListSelectedUser = listModel
							.getElementAt(userList.getSelectedIndex());
					boolean found = false;
					if (chatBoxes.getTabCount() > 0) {
						for (int i = 0; i < chatBoxes.getTabCount(); i++) {
							if (buddyListSelectedUser.equals(chatBoxes
									.getTitleAt(i))) 
							{
								chatBoxes.setSelectedIndex(i);
								found = true;
							}
						}
					}
					if (!found) {
						JTextArea chatArea = new JTextArea();
						chatArea.setEditable(false);
						chatArea.setForeground(Color.WHITE);
						chatArea.setBackground(Color.BLACK);

						JScrollPane scrollPane = new JScrollPane();
						scrollPane.setPreferredSize(new Dimension(600, 450));
						scrollPane
								.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						scrollPane.setViewportView(chatArea);

						chatBoxes.addTab(buddyListSelectedUser, null,
								scrollPane);

						for (int i = 0; i < chatBoxes.getTabCount(); i++) {
							if (buddyListSelectedUser.equals(chatBoxes
									.getTitleAt(i))) 
							{
								chatBoxes.setSelectedIndex(i);
							}
						}
					}
				}
			}
		}
	}
}
