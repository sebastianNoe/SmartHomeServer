package SmartHome.controllable.command;

import SmartHome.controllable.Command;
import SmartHome.controllable.OnOrOffSwitchable;
import SmartHome.exceptions.CommandExecutionError;

public class TurnOn implements Command {
	private OnOrOffSwitchable switchable;

	public TurnOn(OnOrOffSwitchable switchable) {
		this.switchable = switchable;
	}

	public void execute() throws CommandExecutionError {
		this.switchable.turnOn();
	}

	public static final String name = "TurnOn";
	public String getName() {
		return TurnOn.name;
	}


}
