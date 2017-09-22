package SmartHome.controllable.controllableObjects;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SmartHome.controllable.Command;
import SmartHome.controllable.command.CommandFactory;
import SmartHome.controllable.command.ToggleRadioSocketCommand;
import SmartHome.controllable.command.TurnOff;
import SmartHome.controllable.command.TurnOn;
import SmartHome.exceptions.CommandExecutionError;

public class RadioSocketTest {
	private String testDeviceName = "test";
	private RuntimeExecuter runtimeExecuterDouble;
	private RadioSocket testObject;
	
	@Before
	public void setup() {
		//Setup
		this.runtimeExecuterDouble = mock(RuntimeExecuter.class);
		this.testObject = new RadioSocket(this.testDeviceName, "", "", new CommandFactory( ), new File(""), runtimeExecuterDouble);
		
	}
	
	@Test
	public void hasAllCommands() {
		//Test
		List<Command> actual = this.testObject.getCommands();
		
		//Validate
		Iterator<Command> iter = actual.iterator();
		while(iter.hasNext()) {
			Command currCommand = iter.next();
			assertTrue(currCommand.getName().equals(ToggleRadioSocketCommand.name) || currCommand.getName().equals(TurnOn.name) || currCommand.getName().equals(TurnOff.name));
		}
		assertTrue(this.testObject.getName().equals(this.testDeviceName));
		assertTrue(this.testObject.getType().equals(RadioSocket.type));
	}
	
	@Test
	public void turnOnFromOff() {
		try {
			this.testObject.turnOn();

			this.validateRuntimeCallsAndOnOffState(1, true);
		} catch (IOException | CommandExecutionError e) {
			Assert.fail();
		}		
	}
	
	@Test
	public void turnOnFromOn() {
		try {
			this.testObject.turnOn();
			this.testObject.turnOn();

			this.validateRuntimeCallsAndOnOffState(2, true);
		} catch (IOException | CommandExecutionError e) {
			Assert.fail();
		}				
	}
	
	@Test
	public void turnOffFromOn() {
		try {
			this.testObject.turnOn();
			this.testObject.turnOff();

			this.validateRuntimeCallsAndOnOffState(2, false);
		} catch (IOException | CommandExecutionError e) {
			Assert.fail();
		}						
	}
	
	@Test
	public void turnOffFromOff() {
		try {
			this.testObject.turnOff();
			this.testObject.turnOff();

			this.validateRuntimeCallsAndOnOffState(2, false);
		} catch (IOException | CommandExecutionError e) {
			Assert.fail();
		}								
	}
	
	@Test
	public void toggleOnce() {
		try {
			this.testObject.toggle();

			this.validateRuntimeCallsAndOnOffState(1, true);
		} catch (IOException | CommandExecutionError e) {
			Assert.fail();
		}								
	}
	
	@Test
	public void toggleTwice() {
		try {
			this.testObject.toggle();
			this.testObject.toggle();			

			this.validateRuntimeCallsAndOnOffState(2, false);
		} catch (IOException | CommandExecutionError e) {
			Assert.fail();
		}								
	}
	
	private void validateRuntimeCallsAndOnOffState(int timesOfRuntimeExecuterCalled, boolean isExpectedToBeOn) throws IOException{
		verify(this.runtimeExecuterDouble, times(timesOfRuntimeExecuterCalled)).execute(any(String.class), any(File.class));
		assertTrue(isExpectedToBeOn == this.testObject.isExpectedToBeOn());
	}
}
