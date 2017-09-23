package smartHome.controllable.command;

import smartHome.controllable.Command;
import smartHome.controllable.OnOrOffSwitchable;
import smartHome.exceptions.CommandExecutionError;

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
