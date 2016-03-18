package edu.kings.im;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import edu.kings.im.IMConnection;
import edu.kings.im.IMEventListener;
import edu.kings.im.IllegalNameException;
import edu.kings.im.Message;
import edu.kings.im.RealIMConnection;
import edu.kings.im.StatusListener;

/**
 * GUI for Instant Messaging.
 * 
 * @author Dave Paupst
 * @author Johnny Collado
 * @version 02/26/2016
 */
public class IMUserInterface implements ActionListener {

	/** Represents the name for all users. */
	public final static String BLAST;

	/** Frame. */
	private JFrame frame;

	/** Chat box for viewing messages. */
	private JTextArea chatBox;

	/** Message box for sending messages. */
	private JTextField messageBox;

	/** Button to send messages. */
	private JButton sendButton;

	/** Menu containing users. */
	private JComboBox<String> users;

	/** Menu bar. */
	private JMenuBar menuBar;

	/** Login frame. */
	private JFrame loginFrame;

	/** Keeps track of the current user. */
	private IMConnection user;

	static {
		BLAST = "EVERYONE";
	}

	/**
	 * Constructor.
	 */
	public IMUserInterface() {
		// Frame set up
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 600));

		// Setup the chat box.
		chatBox = new JTextArea();
		chatBox.setLineWrap(true);
		chatBox.setEditable(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(chatBox);

		frame.add(scrollPane, BorderLayout.CENTER);

		// Setup message area.

		JPanel messageArea = new JPanel(new GridBagLayout());
		GridBagConstraints messageConstraints = new GridBagConstraints();

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

		JPanel userSend = new JPanel(new GridLayout(2, 1));

		users = new JComboBox<String>();
		users.addItem(BLAST);

		sendButton = new JButton("Send");
		sendButton.addActionListener(this);

		userSend.add(users);
		userSend.add(sendButton);

		messageConstraints.fill = GridBagConstraints.BOTH;
		messageConstraints.gridx = 1;
		messageConstraints.gridy = 0;
		messageConstraints.weightx = .3;
		messageConstraints.weighty = 1;
		messageArea.add(userSend, messageConstraints);

		frame.add(messageArea, BorderLayout.SOUTH);

		menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		menuBar.add(file);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				Event.ALT_MASK));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});

		file.add(exit);

		frame.setJMenuBar(menuBar);

		frame.setVisible(false);

		loginSetup();
	}

	/**
	 * Getter for text area used in testing.
	 * 
	 * @return the chatBox.
	 */
	protected JTextArea getTextArea() {
		return chatBox;
	}

	/**
	 * Getter for combo box used in testing.
	 * 
	 * @return the users.
	 */
	protected JComboBox<String> getComboBox() {
		return users;
	}

	/**
	 * Getter for text field used in testing.
	 * 
	 * @return the messageBox.
	 */
	protected JTextField getMessageBox() {
		return messageBox;
	}

	/**
	 * Getter for send button used in testing.
	 * 
	 * @return the sendButton.
	 */
	protected JButton getSendButton() {
		return sendButton;
	}

	/**
	 * Sets up the login frame.
	 */
	private void loginSetup() {
		// Login setup
		loginFrame = new JFrame("Login");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(500, 500);

		JPanel loginPanel = new JPanel(new GridBagLayout());
		GridBagConstraints loginC = new GridBagConstraints();

		// adding logo

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

		// login prompt and logic

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

		loginPanel.add(loginButton, loginC);

		loginFrame.setVisible(true);

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
			JOptionPane.showMessageDialog(frame, ine.getMessage());
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
			user.spamEveryone(message);
		} else {
			user.sendMessage(message, toWhom);
		}
	}

	/**
	 * Closes the IM frame and disconnects the user from the server.
	 */
	private void closeWindow() {
		user.disconnect();
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String msg = messageBox.getText();
		String recipient = (String) users.getSelectedItem();
		if (msg != null && !msg.trim().equals("")) {
			sendMessage(msg, recipient);
		}
		messageBox.setText("");
	}

	/**
	 * This private class implements StatusListener.
	 * 
	 * @author Dave Paupst
	 * @author Johnny Collado
	 */
	private class IMStatusListener implements StatusListener {

		@Override
		public void connectionRejected(RealIMConnection arg0) {
			JOptionPane.showMessageDialog(frame, "This username is taken.");
		}

		@Override
		public void connectionUpdate(RealIMConnection arg0, boolean connected) {
			if (connected) {
				frame.pack();
				frame.setVisible(true);
				loginFrame.dispose();
				arg0.addIMEventListener(new MessageListener());
				setUser(arg0);
			}
		}

	}

	/**
	 * Returns an instance of MessageListener for testing purposes only.
	 * 
	 * @return an instance of MessageListener.
	 */
	protected MessageListener getMessageListenerInstance() {
		return new MessageListener();
	}

	/**
	 * This class implements IMEventListener. It is set for protected for
	 * testing purposes.
	 * 
	 * @author Dave Paupst
	 * @author Johnny Collado
	 */
	private final class MessageListener implements IMEventListener {

		@Override
		public void messagesReceived(Iterable<Message> arg0) {
			for (Message msg : arg0) {
				String message = String.format("%s\n\t%s\n", msg.getSender(),
						msg.getText());
				chatBox.append(message);
			}
		}

		@Override
		public void userOffline(String arg0) {
			users.removeItem(arg0);
		}

		@Override
		public void userOnline(String arg0) {
			users.addItem(arg0);
		}

	}

	/**
	 * This class constructs the logo.
	 * 
	 * @author Dave Paupst
	 * @author Johnny Collado
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

}
