package SmartHome.controllable.command;

import SmartHome.controllable.Command;
import SmartHome.controllable.OnOrOffSwitchable;

public class TurnOff implements Command {
	private OnOrOffSwitchable switchable;

	public TurnOff(OnOrOffSwitchable switchable) {
		this.switchable = switchable;
	}

	public void execute() {
		this.switchable.turnOff();
	}

	public String getName() {
		return "TurnOff";
	}
}
