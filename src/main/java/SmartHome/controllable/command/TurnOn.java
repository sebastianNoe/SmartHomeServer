package SmartHome.controllable.command;

import SmartHome.controllable.Command;
import SmartHome.controllable.OnOrOffSwitchable;

public class TurnOn implements Command {
	private OnOrOffSwitchable switchable;

	public TurnOn(OnOrOffSwitchable switchable) {
		this.switchable = switchable;
	}

	public void execute() {
		this.switchable.turnOn();
	}

	public String getName() {
		return "TurnOn";
	}

}
