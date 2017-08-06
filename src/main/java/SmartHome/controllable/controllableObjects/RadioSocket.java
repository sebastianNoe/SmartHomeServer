package SmartHome.controllable.controllableObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import SmartHome.controllable.Command;
import SmartHome.controllable.Controllable;
import SmartHome.controllable.command.CommandFactory;

public class RadioSocket implements Controllable {
	private static final String type = "RadioSocket";

	
	private String name;
	private String systemCode;
	private String deviceCode;
	private boolean isExpectedToBeOn;
	private List<Command> commandsToExecute;

	RadioSocket(String name, String systemCode, String deviceCode, CommandFactory commandFactory) {
		this.name = name;
		this.systemCode = systemCode;
		this.deviceCode = deviceCode;
		
		this.commandsToExecute = new ArrayList<Command>();
		commandsToExecute.add(commandFactory.createToggleRadioSocketCommand(this));
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return type;
	}


	public List<Command> getCommands() {
		return this.commandsToExecute;
	}
	
	
	
	
	public void toggle() {
		try {
			if (isExpectedToBeOn) {
				Runtime.getRuntime().exec("./send " + this.systemCode + " " + this.deviceCode + " 0");
				this.isExpectedToBeOn = false;
			} else {
				Runtime.getRuntime().exec("./send " + this.systemCode + " " + this.deviceCode + " 1");
				this.isExpectedToBeOn = true;
			}
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

}
