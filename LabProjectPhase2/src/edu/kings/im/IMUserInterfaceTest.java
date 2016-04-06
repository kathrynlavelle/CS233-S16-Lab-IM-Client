package edu.kings.im;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import edu.kings.cs.util.ArrayPositionList;
import edu.kings.cs.util.List;
import edu.kings.im.IMEventListener;
import edu.kings.im.Message;
import edu.kings.im.MockIMConnection;
import edu.kings.im.MockSendMessage;

/**
 * This class tests the Instant Messenger program.
 * 
 * @author Dave Paupst
 * @author Johnny Collado
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
	 * Tests the user online method of the message listener.
	 */
	@Test
	public void testUserOnline() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOnline("Dave");
		assertEquals("Dave should be returned.", "Dave", gui.getList()
				.getElementAt(1));
	}
	
	@Test
	public void testUserOnlinePhase3() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOnline("Dave");
		assertEquals("Dave should be returned.", "Dave", gui.getList().getElementAt(1));
	}
	
	@Test
	public void testUserOfflinePhase3() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOffline("Dave");
		assertNull("Dave should no longer be in the combo box.", gui
				.getList().getElementAt(1));
	}

	/**
	 * Tests the user offline method of the message listener.
	 */
	@Test
	public void testUserOffline() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOffline("Dave");
		assertNull("Dave should no longer be in the combo box.", gui
				.getList().getElementAt(1));
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
		messages.add(new Message("Mark", "What's up Kate?", "Kate"));
		messages.add(new Message("Kayla", "Yo! Tori!", "Tori"));

		mimc.useMessageReceived(messages);

		StringBuffer expectedOutput = new StringBuffer();
		expectedOutput.append(String.format("%s\n\t%s\n", "Dave", "Hi Johnny!",
				"Johnny"));
		expectedOutput.append(String.format("%s\n\t%s\n", "Mark",
				"What's up Kate?", "Kate"));
		expectedOutput.append(String.format("%s\n\t%s\n", "Kayla", "Yo! Tori!",
				"Tori"));

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
		gui.getSendButton().doClick();
		assertEquals("Wrong Message", "Hi, Daaaaaavvveeeeeeeeeee!!!!!!!",
				m1.getDaveMessage());
		assertEquals("Wrong Person", "", m1.getJohnnyMessage());

		// Sending Message to Johnny
		gui.getMessageBox().setText("Hey, nice shirt dude.");
		gui.getSendButton().doClick();
		assertEquals("Wrong Message", "Hey, nice shirt dude.",
				m1.getJohnnyMessage());

	}
}
