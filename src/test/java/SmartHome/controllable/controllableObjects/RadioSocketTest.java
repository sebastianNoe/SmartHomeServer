package SmartHome.controllable.controllableObjects;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import SmartHome.controllable.Command;
import SmartHome.controllable.command.CommandFactory;
import SmartHome.controllable.command.ToggleRadioSocketCommand;
import SmartHome.controllable.command.TurnOff;
import SmartHome.controllable.command.TurnOn;

public class RadioSocketTest {
	@Test
	public void hasAllCommands() {
		//Setup
		RuntimeExecuter runtimeExecuterDouble = mock(RuntimeExecuter.class);
		RadioSocket testObject = new RadioSocket("", "", "", new CommandFactory(), new File(""), runtimeExecuterDouble);
		
		//Test
		List<Command> actual = testObject.getCommands();
		
		//Validate
		Iterator<Command> iter = actual.iterator();
		while(iter.hasNext()) {
			Command currCommand = iter.next();
			assertTrue(currCommand.getName().equals(ToggleRadioSocketCommand.name) || currCommand.getName().equals(TurnOn.name) || currCommand.getName().equals(TurnOff.name));
		}
	}
}
