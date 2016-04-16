package edu.kings.im;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.junit.Before;
import org.junit.Test;

import edu.kings.cs.util.ArrayPositionList;
import edu.kings.cs.util.List;


/**
 * This class tests the Instant Messenger program.
 * 
 * @author Dave Paupst
 * @author Kate Lavelle
 */
public class IMUserInterfaceTest {

	/** Instance of the IMUserInterface. */
	private IMUserInterface gui;
	/** Instance of the MockIMConnection. */
	private MockIMConnection mimc;

	/**
	 * Sets up the tests by instantiating the IMUserInterface and
	 * MockIMConnection.
	 */
	@Before
	public void setUp() {
		gui = new IMUserInterface();
		mimc = new MockIMConnection();

	}
	
	/**
	 * Checks that the JList is updated correctly when a user comes online.
	 */
	@Test
	public void testUserOnlinePhase3() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOnline("Dave");
		assertEquals("Dave should be returned.", "Dave", gui.getList().getElementAt(0));
	}
	
	/**
	 * Checks that the JList is updated correctly when a user goes offline.
	 */
	@Test
	public void testUserOfflinePhase3() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOffline("Dave");
		assertTrue("Dave should no longer be in the combo box.", gui
				.getList().isEmpty());
	}

	/**
	 * Tests to check that messages are properly received.
	 */
	@Test
	public void testReceiveMessages() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);

		List<Message> messages = new ArrayPositionList<Message>();
		messages.add(new Message("Dave", "Hi Johnny!", "Johnny"));

		mimc.useMessageReceived(messages);

		StringBuffer expectedOutput = new StringBuffer();
		expectedOutput.append(String.format("%s\n\t%s\n", "Dave: ", "Hi Johnny!",
				"Johnny"));
		
		assertEquals("Chatbox output incorrect", expectedOutput.toString(), gui
				.getTextArea().getText());
	}

	/**
	 * Tests to check that messages are properly sent.
	 */
	@Test
	public void testSendMessage() {
		MockSendMessage m1 = new MockSendMessage();
		gui.getList().addElement("Dave");
		gui.getList().addElement("Johnny");
		gui.setUser(m1);
		// Sending message to Dave
		gui.getMessageBox().setText("Hi, Daaaaaavvveeeeeeeeeee!!!!!!!");
		gui.getChatBoxes().add("Dave", new JScrollPane(new JTextArea()));
		gui.getChatBoxes().setSelectedIndex(0);
		gui.getSendButton().doClick();
		assertEquals("Wrong Message", "Hi, Daaaaaavvveeeeeeeeeee!!!!!!!",
				m1.getDaveMessage());
		assertEquals("Wrong Person", "", m1.getJohnnyMessage());

		// Sending Message to Johnny
		gui.getMessageBox().setText("Hey, nice shirt dude.");
		gui.getChatBoxes().add("Johnny", new JScrollPane(new JTextArea()));
		gui.getChatBoxes().setSelectedIndex(1);
		gui.getSendButton().doClick();
		assertEquals("Wrong Message", "Hey, nice shirt dude.",
				m1.getJohnnyMessage());

	}
}
