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

/** GUI for NullPointTexting.
 * 
 * @author Dave Paupst
 * @author Johnny Collado
 * @version 02/26/2016
 */
public class IMUserInterface  implements ActionListener  {
	
	private JFrame frame;
	
	private NullPointTexting npt;
	
	private JTextArea chatBox;
	
	private JTextField messageBox;
	
	private JButton sendButton;
	
	private JComboBox<String> users;
	
	private JMenuBar menuBar;
	
	/**
	 * Constructor.
	 */
	public IMUserInterface() {
		//TODO: Implement me.
		
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
		
		
		JButton sendButton = new JButton("Send");
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
		
		
		//frame.pack();
		//frame.setVisible(true);
		frame.setVisible(false);
		
		if(login()) {
			frame.pack();
			frame.setVisible(true);
		}
		else {
			closeWindow();
		}
		
		
	}
	
	private boolean login() {
		//TODO: add Logo.
		boolean loggedin = false;
		
		//Login setup
		JPanel loginPanel = new JPanel(new GridBagLayout());
		GridBagConstraints loginC = new GridBagConstraints();
		
		JTextField name = new JTextField();
		name.setBorder(BorderFactory.createTitledBorder("Username"));

		

		loginC.fill = GridBagConstraints.BOTH;
		loginC.gridx = 1;
		loginC.gridy = 0;
		loginC.weightx = .7;
		loginC.weighty = 1;
		
		loginPanel.add(name, loginC);
		
		//login prompt and logic
		
		int answer = JOptionPane.showConfirmDialog(frame, loginPanel,"Login",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		
		if(answer == JOptionPane.OK_OPTION) {
			String username = name.getText();
			String testUser = "Dave";
			
			//use control class login method to determine if they are able to login.
			if(testUser.equals(username)) {
				loggedin = true;
			}
			else {
				//TODO: JOption ErrorMessage
				loggedin = login();
			}
			
		}
		
		return loggedin;
	}
	
	
	private void closeWindow() {
		//TODO: Implement Logout;
		//npt.logout();
		System.exit(0);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
