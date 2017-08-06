package SmartHome.services.requestBody;

public class CommandExecutionRequestBody {
	private String controllableName;
	private String commandName;
	
	
	public void setControllableName(String controllableName) {
		this.controllableName = controllableName;
	}
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	
	public String getCommandName() {
		return commandName;
	}
	public String getControllableName() {
		return controllableName;
	}

}
