package edu.kings.im.phase2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.naming.InvalidNameException;
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

/** GUI for NullPointTexting.
 * 
 * @author Dave Paupst
 * @author Johnny Collado
 * @version 02/26/2016
 */
public class IMUserInterface  implements ActionListener {
	
	private JFrame frame;
	
	private JTextArea chatBox;
	
	private JTextField messageBox;
	
	private JButton sendButton;
	
	private JComboBox<String> users;
	
	private JMenuBar menuBar;
	
	private boolean connected;
	
	
	private JFrame loginFrame;
	
	/**Represents the name for all users.*/
	public final static String BLAST;
	
	/** Keeps track of the current user. */
	private IMConnection user;
	
	static {
		 BLAST = "EVERYONE";
	}
	
	/**
	 * Constructor.
	 */
	public IMUserInterface() {
		//TODO: Implement me.
		
		//variable set up
		connected = false;
		
		//Frame set up
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 600));
		
		//Setup the chat box.
		chatBox = new JTextArea();
		chatBox.setLineWrap(true);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(chatBox);
		
		
		
		frame.add(scrollPane, BorderLayout.CENTER);
		
		
		//Setup message area.
		
		JPanel messageArea = new JPanel(new GridBagLayout());
		GridBagConstraints messageConstraints = new GridBagConstraints();
		
		messageBox = new JTextField();
		messageBox.setBorder(BorderFactory.createTitledBorder("Enter Message"));
		
		
		messageConstraints.fill = GridBagConstraints.BOTH;
		messageConstraints.gridx = 0;
		messageConstraints.gridy = 0;
		messageConstraints.weightx = .7;
		messageConstraints.weighty = 1;
		messageArea.add(messageBox, messageConstraints);
		
		
		//Send button & user list.
		
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
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.ALT_MASK));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		
		file.add(exit);
		
		frame.setJMenuBar(menuBar);
		
		frame.setVisible(false);
		
		login();
	}
	
	private void login() {
		//TODO: add Logo.
		
		//Login setup
		loginFrame = new JFrame("Login");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(300, 200);
		
		
		JPanel loginPanel = new JPanel(new GridBagLayout());
		GridBagConstraints loginC = new GridBagConstraints();
		
		final JTextField name = new JTextField();
		name.setBorder(BorderFactory.createTitledBorder("Username"));

		

		loginC.fill = GridBagConstraints.HORIZONTAL;
		loginC.gridx = 0;
		loginC.gridy = 1;
		loginC.weightx = .7;
		loginC.weighty = .8;
		
		loginPanel.add(name, loginC);
		
		//login prompt and logic
		
		loginFrame.add(loginPanel, BorderLayout.CENTER);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = name.getText();
				IMConnection newUser = RealIMConnection.getInstance(username);
				newUser.addConnectionListener(new IMStatusListener());
				
				
				try {
				newUser.connect();	
				}
				catch(IllegalNameException ine) {
					JOptionPane.showMessageDialog(frame, ine.getMessage());
				}
				
				
			}
		});
		
		loginC.fill = GridBagConstraints.NONE;
		loginC.gridx = 0;
		loginC.gridy = 2;
		loginC.weightx = .7;
		loginC.weighty = .05;
		
		loginPanel.add(loginButton, loginC);
		
		loginFrame.setVisible(true);
		
	}
	
	/**
	 * Potential protected getter for User.
	 * 
	 * @return
	 */
	protected IMConnection getUser() {
		return user;
	}
	
	/**
	 * Potential protected setter for User.
	 * 
	 * @param daUser
	 */
	protected void setUser(IMConnection daUser) {
		user = daUser;
	}
	
	/**
	 * Sends a message to specified user.
	 * 
	 * @param message
	 * @param toWhom
	 */
	public void sendMessage(String message, String toWhom) {
		if(toWhom.equals(BLAST)) {
			user.spamEveryone(message);
		} else {
			user.sendMessage(message, toWhom);
		}
	}
	
	
	private void closeWindow() {
		//TODO: Implement Logout;
		user.disconnect();
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Implement for send button.
		String msg = messageBox.getText();
		String recipient = (String) users.getSelectedItem();
		if(msg != null && !msg.trim().equals("")) {
			sendMessage(msg, recipient);
		}
	}


	
	
	
	
	private class IMStatusListener implements StatusListener {
		
		@Override
		public void connectionRejected(RealIMConnection arg0) {
			// TODO When login false.
			JOptionPane.showMessageDialog(frame, "This username is taken.");
			
		}

		@Override
		public void connectionUpdate(RealIMConnection arg0, boolean connected) {
			// TODO implement login process.
			if(connected) {
				frame.pack();
				frame.setVisible(true);
				loginFrame.dispose();
				arg0.addIMEventListener(new MessageListener());
				setUser(arg0);
			}
		}

	}
	
	private class MessageListener implements IMEventListener {

		@Override
		public void messagesReceived(Iterable<Message> arg0) {
			// TODO implement add to chatBox
			for (Message msg : arg0) {
				String message = String.format("%s\n\t%s\n", msg.getSender(), msg.getText());
				chatBox.append(message);
			}
			
		}		

		@Override
		public void userOffline(String arg0) {
			// TODO remove user from users
			users.removeItem(arg0);		
		}

		@Override
		public void userOnline(String arg0) {
			// TODO add user to users
			users.addItem(arg0);
		}
		
	}

}


