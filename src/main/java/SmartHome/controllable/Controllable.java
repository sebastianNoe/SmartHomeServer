package SmartHome.controllable;

import java.util.List;

import SmartHome.controllable.command.Command;

public interface Controllable {
	public String getType();
	public String getName();
	public List<Command> getCommands();
}