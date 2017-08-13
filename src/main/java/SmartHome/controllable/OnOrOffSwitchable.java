package SmartHome.controllable;

import SmartHome.exceptions.CommandExecutionError;

public interface OnOrOffSwitchable {
	public void turnOn() throws CommandExecutionError;
	public void turnOff() throws CommandExecutionError;
}
