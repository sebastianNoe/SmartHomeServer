package smartHome.controllable.command;

import smartHome.controllable.Command;
import smartHome.controllable.OnOrOffSwitchable;
import smartHome.exceptions.CommandExecutionError;

public class TurnOff implements Command {
	private OnOrOffSwitchable switchable;

	public TurnOff(OnOrOffSwitchable switchable) {
		this.switchable = switchable;
	}

	public void execute() throws CommandExecutionError {
		this.switchable.turnOff();
	}

	public static final String name = "TurnOff";
	public String getName() {
		return TurnOff.name;
	}
}
