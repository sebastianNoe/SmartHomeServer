package smartHome.controllable.command;

import smartHome.controllable.Command;
import smartHome.controllable.controllableObjects.RadioSocket;
import smartHome.exceptions.CommandExecutionError;

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
