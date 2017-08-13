package SmartHome.controllable.controllableObjects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import SmartHome.controllable.Command;
import SmartHome.controllable.Controllable;
import SmartHome.controllable.OnOrOffSwitchable;
import SmartHome.controllable.command.CommandFactory;
import SmartHome.exceptions.CommandExecutionError;

public class RadioSocket implements Controllable, OnOrOffSwitchable {
	private static final String type = "RadioSocket";

	private String name;
	private String systemCode;
	private String deviceCode;
	private File rcswitchPiLocation;
	private boolean isExpectedToBeOn;
	private List<Command> commandsToExecute;

	RadioSocket(String name, String systemCode, String deviceCode, CommandFactory commandFactory,
			File rcswitchPiLocation) {
		this.name = name;
		this.systemCode = systemCode;
		this.deviceCode = deviceCode;
		this.rcswitchPiLocation = rcswitchPiLocation;

		this.commandsToExecute = new ArrayList<Command>();
		commandsToExecute.add(commandFactory.createToggleRadioSocketCommand(this));
		commandsToExecute.addAll(commandFactory.createTurnOnAndOffCommand(this));
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

	public void toggle() throws CommandExecutionError {
		if (isExpectedToBeOn) {
			this.turnOff();
		} else {
			this.turnOn();
		}
	}

	private void sendOnOffCommand(String onOrOffIndicator) throws IOException {
		Runtime.getRuntime().exec("sudo ./send " + this.systemCode + " " + this.deviceCode + " " + onOrOffIndicator,
				null, this.rcswitchPiLocation);
	}

	public void turnOn() throws CommandExecutionError {
		try {
			String sendingCommand = "1";
			this.isExpectedToBeOn = true;
			this.sendOnOffCommand(sendingCommand);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommandExecutionError();
		}
	}

	public void turnOff() throws CommandExecutionError {
		try {
			String sendingCommand = "0";
			this.isExpectedToBeOn = false;
			this.sendOnOffCommand(sendingCommand);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommandExecutionError();
		}
	}

}
