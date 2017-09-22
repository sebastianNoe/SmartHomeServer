package SmartHome.controllable;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import SmartHome.Settings;
import SmartHome.controllable.controllableObjects.ControllableFactory;
import SmartHome.exceptions.CommandExecutionError;
import SmartHome.exceptions.DeviceCommandCombinationNotFound;

import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ControllableManagerTest {
	private static final String legalTestDevice1 = "legalDevice1";
	private static final String legalTestCommand1ForDevice1 = "legalTestCommand11";
	private static final String legalTestCommand2ForDevice1 = "legalTestCommand21";
	private static final String legalTestDevice2 = "legalDevice2";
	private static final String legalTestCommand1ForDevice2 = "legalTestCommand12";

	private Settings settingsDouble;
	private ControllableFactory controllableFactoryDouble;
	private CastNodeToElement castNodeToElementDouble;
	private Command command1ForDevice1;
	private Command command2ForDevice1;
	private Command command1ForDevice2;

	private ControllableManager testObject;

	@Before
	public void setup() {
		this.settingsDouble = mock(Settings.class);
		this.controllableFactoryDouble = mock(ControllableFactory.class);
		this.castNodeToElementDouble = mock(CastNodeToElement.class);
		this.testObject = new ControllableManager(this.controllableFactoryDouble, this.settingsDouble,
				this.castNodeToElementDouble);
	}

	@Test
	public void command1Device1GetsExecuted() throws DeviceCommandCombinationNotFound, CommandExecutionError {
		this.initialize();
		this.executeCommandAndVerifyCalls(legalTestDevice1, legalTestCommand1ForDevice1, 1, 0, 0);
	}

	@Test
	public void command2Device1GetsExecuted() throws DeviceCommandCombinationNotFound, CommandExecutionError {
		this.initialize();
		this.executeCommandAndVerifyCalls(legalTestDevice1, legalTestCommand2ForDevice1, 0, 1, 0);
	}

	@Test
	public void command1Device2GetsExecuted() throws DeviceCommandCombinationNotFound, CommandExecutionError {
		this.initialize();
		this.executeCommandAndVerifyCalls(legalTestDevice2, legalTestCommand1ForDevice2, 0, 0, 1);
	}

	@Test
	public void illegalCommandRaisesException() throws DeviceCommandCombinationNotFound, CommandExecutionError {
		this.initialize();
		try {
			this.testObject.executeCommand(legalTestDevice1, "illegal");
			fail();
		} catch (DeviceCommandCombinationNotFound e) {
			// wanted behavior
		}
	}

	@Test
	public void illegalDeviceRaisesException() throws DeviceCommandCombinationNotFound, CommandExecutionError {
		this.initialize();
		try {
			this.testObject.executeCommand("illegal", "illegal");
			fail();
		} catch (DeviceCommandCombinationNotFound e) {
			// wanted behavior
		}
	}

	private void executeCommandAndVerifyCalls(String device, String command, int timesCommand1Device1,
			int timesCommand2Device1, int timesCommand1Device2)
			throws DeviceCommandCombinationNotFound, CommandExecutionError {
		this.testObject.executeCommand(device, command);
		verify(this.command1ForDevice1, times(timesCommand1Device1)).execute();
		verify(this.command2ForDevice1, times(timesCommand2Device1)).execute();
		verify(this.command1ForDevice2, times(timesCommand1Device2)).execute();
	}

	private void initialize() {
		// Prepare Settings Double
		NodeList testDevices = mock(NodeList.class);
		Node testNode1 = mock(Node.class);
		when(testNode1.getNodeType()).thenReturn(Node.ELEMENT_NODE);
		Node testNode2 = mock(Node.class);
		when(testNode2.getNodeType()).thenReturn(Node.ELEMENT_NODE);
		when(testDevices.item(0)).thenReturn(testNode1);
		when(testDevices.item(1)).thenReturn(testNode2);
		when(testDevices.getLength()).thenReturn(2);
		when(this.settingsDouble.getDeviceNodeList()).thenReturn(testDevices);

		// Prepare handling of Device 1 Creation
		Element element1 = mock(Element.class);
		when(this.castNodeToElementDouble.castNodeToElement(testNode1)).thenReturn(element1);
		Controllable controllable1 = mock(Controllable.class);
		when(controllable1.getName()).thenReturn(legalTestDevice1);
		when(this.controllableFactoryDouble.createControllable(element1)).thenReturn(controllable1);

		// Prepare handling of Device 2 Creation
		Element element2 = mock(Element.class);
		when(this.castNodeToElementDouble.castNodeToElement(testNode2)).thenReturn(element2);
		Controllable controllable2 = mock(Controllable.class);
		when(controllable2.getName()).thenReturn(legalTestDevice2);
		when(this.controllableFactoryDouble.createControllable(element2)).thenReturn(controllable2);

		// Prepare Command Calls for Device 1
		ArrayList<Command> commandsForDevice1 = new ArrayList<Command>();
		command1ForDevice1 = mock(Command.class);
		when(command1ForDevice1.getName()).thenReturn(legalTestCommand1ForDevice1);
		command2ForDevice1 = mock(Command.class);
		when(command2ForDevice1.getName()).thenReturn(legalTestCommand2ForDevice1);
		commandsForDevice1.add(command1ForDevice1);
		commandsForDevice1.add(command2ForDevice1);
		when(controllable1.getCommands()).thenReturn(commandsForDevice1);

		// Prepare Command Calls for Device 2
		ArrayList<Command> commandsForDevice2 = new ArrayList<Command>();
		command1ForDevice2 = mock(Command.class);
		when(command1ForDevice2.getName()).thenReturn(legalTestCommand1ForDevice2);
		commandsForDevice2.add(command1ForDevice2);
		when(controllable2.getCommands()).thenReturn(commandsForDevice2);

		// Initialize Test Object & Validate Results
		this.testObject.init();
		verify(this.controllableFactoryDouble, times(1)).createControllable(element1);
		verify(this.controllableFactoryDouble, times(1)).createControllable(element2);
		verify(this.controllableFactoryDouble, times(2)).createControllable(any());
	}

}
