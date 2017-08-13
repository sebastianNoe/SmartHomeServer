package SmartHome.controllable;

import SmartHome.exceptions.CommandExecutionError;

public interface Command {
	public void execute() throws CommandExecutionError;
	public String getName();
	
}
