package smartHome.controllable.controllableObjects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import smartHome.controllable.Command;
import smartHome.controllable.Controllable;
import smartHome.controllable.OnOrOffSwitchable;
import smartHome.controllable.command.CommandFactory;
import smartHome.exceptions.CommandExecutionError;

public class RadioSocket implements Controllable, OnOrOffSwitchable {
	public static final String type = "RadioSocket";

	private String name;
	private String systemCode;
	private String deviceCode;
	private File rcswitchPiLocation;
	private boolean isExpectedToBeOn;
	private List<Command> commandsToExecute;
	private RuntimeExecuter runtimeExecuter;

	RadioSocket(String name, String systemCode, String deviceCode, CommandFactory commandFactory,
			File rcswitchPiLocation, RuntimeExecuter runtimeExecuter) {
		this.name = name;
		this.systemCode = systemCode;
		this.deviceCode = deviceCode;
		this.rcswitchPiLocation = rcswitchPiLocation;
		this.runtimeExecuter = runtimeExecuter;

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

	public boolean isExpectedToBeOn() {
		return this.isExpectedToBeOn;
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

	protected void sendOnOffCommand(String onOrOffIndicator) throws IOException {
		runtimeExecuter.execute("sudo ./send " + this.systemCode + " " + this.deviceCode + " " + onOrOffIndicator, this.rcswitchPiLocation);
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
