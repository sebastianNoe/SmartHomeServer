package smartHome.controllable;

import java.util.List;

public interface Controllable {
	public String getType();
	public String getName();
	public List<Command> getCommands();
}