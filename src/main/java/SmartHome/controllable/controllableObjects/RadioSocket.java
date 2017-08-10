package SmartHome.controllable.controllableObjects;

import java.io.File;
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
	private File rcswitchPiLocation;
	private boolean isExpectedToBeOn;
	private List<Command> commandsToExecute;

	RadioSocket(String name, String systemCode, String deviceCode, CommandFactory commandFactory, File rcswitchPiLocation) {
		this.name = name;
		this.systemCode = systemCode;
		this.deviceCode = deviceCode;
		this.rcswitchPiLocation = rcswitchPiLocation;
		
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
		String sendingCommand;
		try {
			if (isExpectedToBeOn) {
				sendingCommand = "0";
				this.isExpectedToBeOn = false;
			} else {
				sendingCommand = "1";
				this.isExpectedToBeOn = true;
			}
			Runtime.getRuntime().exec("sudo ./send " + this.systemCode + " " + this.deviceCode + " " + sendingCommand, null, this.rcswitchPiLocation);
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

}
