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
//	@Test
//	public void testUserOnlinePhase3() {
//		IMEventListener messagelistener = gui.getMessageListenerInstance();
//		mimc.addIMEventListener(messagelistener);
//		mimc.useUserOnline("Dave");
//		assertEquals("Dave should be returned.", "Dave", gui.getList().getElementAt(0));
//	}
	
	/**
	 * Checks that the JList is updated correctly when a user goes offline.
	 */
//	@Test
//	public void testUserOfflinePhase3() {
//		IMEventListener messagelistener = gui.getMessageListenerInstance();
//		mimc.addIMEventListener(messagelistener);
//		mimc.useUserOffline("Dave");
//		assertTrue("Dave should no longer be in the combo box.", gui
//				.getList().isEmpty());
//	}

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
		gui.setUser(m1);
	}
	
	
	/**
	 * Test to check that the JList is properly updated when called to sort alphabetically.
	 */
	@Test
	public void testBuddyListAlpha() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOnline("Dave");
		mimc.useUserOnline("Kate");
		mimc.useUserOnline("Will");
		mimc.useUserOnline("Bailey");
		mimc.useUserOnline("BoatyMcBoatface");
		
		// select alpha radio
		JRadioButtonMenuItem alpha = gui.getAlphaRadioButton();
		alpha.setSelected(true);
		
		// assert equals each user at correct index.
		
		assertEquals("Bailey", gui.getList().getElementAt(0));
		assertEquals("BoatyMcBoatface", gui.getList().getElementAt(1));
		assertEquals("Dave", gui.getList().getElementAt(2));
		assertEquals("Kate", gui.getList().getElementAt(3));
		assertEquals("Will", gui.getList().getElementAt(4));
	}
	
	/**
	 * Test to check that the JList is properly updated when called to sort by logged in time.
	 */
	@Test
	public void testBuddyListLoggedIn() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOnline("Dave");
		mimc.useUserOnline("BoatyMcBoatface");
		mimc.useUserOnline("Kate");
		mimc.useUserOnline("Will");
		mimc.useUserOnline("Bailey");
		
		
		// select loggedIn radio.
		JRadioButtonMenuItem logged = gui.getLoggedRadioButton();
		logged.setSelected(true);
		
		// assert equals each user at correct index.
		assertEquals("Bailey", gui.getList().getElementAt(0));
		assertEquals("BoatyWillMcBoatface", gui.getList().getElementAt(1));
		assertEquals("Kate", gui.getList().getElementAt(2));
		assertEquals("BoatyMcBoatface", gui.getList().getElementAt(3));
		assertEquals("Dave", gui.getList().getElementAt(4));
	}
	
	/**
	 * Test to check that the JList is properly updated when called to sort by sent to time.
	 */
	@Test
	public void testBuddyListSentTo() {
		IMEventListener messagelistener = gui.getMessageListenerInstance();
		mimc.addIMEventListener(messagelistener);
		mimc.useUserOnline("Dave");
		mimc.useUserOnline("Kate");
		mimc.useUserOnline("Will");
		mimc.useUserOnline("Bailey");
		mimc.useUserOnline("BoatyMcBoatface");
		
		// send message to a user.
		
		// select sentTo radio.
		JRadioButtonMenuItem sent = gui.getSentRadioButton();
		sent.setSelected(true);
		
		// assert equals each user at correct index.
	}
}
