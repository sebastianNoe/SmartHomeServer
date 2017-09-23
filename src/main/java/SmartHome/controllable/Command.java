package smartHome.controllable;

import smartHome.exceptions.CommandExecutionError;

public interface Command {
	public void execute() throws CommandExecutionError;
	public String getName();
	
}
