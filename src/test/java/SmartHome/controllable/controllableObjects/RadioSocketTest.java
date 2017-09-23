package smartHome.controllable.controllableObjects;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import smartHome.controllable.Command;
import smartHome.controllable.command.CommandFactory;
import smartHome.controllable.command.ToggleRadioSocketCommand;
import smartHome.controllable.command.TurnOff;
import smartHome.controllable.command.TurnOn;
import smartHome.controllable.controllableObjects.RadioSocket;
import smartHome.controllable.controllableObjects.RuntimeExecuter;
import smartHome.exceptions.CommandExecutionError;

public class RadioSocketTest {
	private String testDeviceName = "test";
	private RuntimeExecuter runtimeExecuterDouble;
	private RadioSocket testObject;

	@Before
	public void setup() {
		// Setup
		this.runtimeExecuterDouble = mock(RuntimeExecuter.class);
		this.testObject = new RadioSocket(this.testDeviceName, "", "", new CommandFactory(), new File(""),
				runtimeExecuterDouble);

	}

	@Test
	public void hasAllCommands() {
		// Test
		List<Command> actual = this.testObject.getCommands();

		// Validate
		Iterator<Command> iter = actual.iterator();
		while (iter.hasNext()) {
			Command currCommand = iter.next();
			assertTrue(currCommand.getName().equals(ToggleRadioSocketCommand.name)
					|| currCommand.getName().equals(TurnOn.name) || currCommand.getName().equals(TurnOff.name));
		}
		assertTrue(this.testObject.getName().equals(this.testDeviceName));
		assertTrue(this.testObject.getType().equals(RadioSocket.type));
	}

	@Test
	public void turnOnFromOff() throws IOException, CommandExecutionError {
		this.testObject.turnOn();

		this.validateRuntimeCallsAndOnOffState(1, true);
	}

	@Test
	public void turnOnFromOn() throws IOException, CommandExecutionError {
		this.testObject.turnOn();
		this.testObject.turnOn();

		this.validateRuntimeCallsAndOnOffState(2, true);
	}

	@Test
	public void turnOffFromOn() throws IOException, CommandExecutionError {
		this.testObject.turnOn();
		this.testObject.turnOff();

		this.validateRuntimeCallsAndOnOffState(2, false);
	}

	@Test
	public void turnOffFromOff() throws IOException, CommandExecutionError {
		this.testObject.turnOff();
		this.testObject.turnOff();

		this.validateRuntimeCallsAndOnOffState(2, false);
	}

	@Test
	public void toggleOnce() throws IOException, CommandExecutionError {
		this.testObject.toggle();

		this.validateRuntimeCallsAndOnOffState(1, true);
	}

	@Test
	public void toggleTwice() throws IOException, CommandExecutionError {
		this.testObject.toggle();
		this.testObject.toggle();

		this.validateRuntimeCallsAndOnOffState(2, false);
	}

	private void validateRuntimeCallsAndOnOffState(int timesOfRuntimeExecuterCalled, boolean isExpectedToBeOn)
			throws IOException {
		verify(this.runtimeExecuterDouble, times(timesOfRuntimeExecuterCalled)).execute(any(String.class),
				any(File.class));
		assertTrue(isExpectedToBeOn == this.testObject.isExpectedToBeOn());
	}
}
