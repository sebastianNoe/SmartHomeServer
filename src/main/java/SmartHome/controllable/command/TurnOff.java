package SmartHome.controllable.command;

import SmartHome.controllable.Command;
import SmartHome.controllable.OnOrOffSwitchable;
import SmartHome.exceptions.CommandExecutionError;

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
