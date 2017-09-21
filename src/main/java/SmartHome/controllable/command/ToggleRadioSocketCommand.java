package SmartHome.controllable.command;

import SmartHome.controllable.Command;
import SmartHome.controllable.controllableObjects.RadioSocket;
import SmartHome.exceptions.CommandExecutionError;

public class ToggleRadioSocketCommand implements Command{
	
	
	private RadioSocket myRadioSocket;
	
	public ToggleRadioSocketCommand(RadioSocket myRadioSocket) {
		this.myRadioSocket = myRadioSocket;
	}
	
	public void execute() throws CommandExecutionError {
		this.myRadioSocket.toggle();
	}

	public static final String name = "Toggle";
	public String getName() {
		return ToggleRadioSocketCommand.name;
	}

}
