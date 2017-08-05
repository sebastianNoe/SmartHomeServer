package SmartHome.controllable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import SmartHome.controllable.command.Command;
import SmartHome.controllable.command.CommandFactory;

public class RadioSocket implements Controllable {
	private static final String type = "RadioSocket";

	@Autowired
	private CommandFactory commandFactory;
	
	private String name;
	private String systemCode;
	private String deviceCode;
	private boolean isExpectedToBeOn;
	private List<Command> commandsToExecute;

	RadioSocket(String name, String systemCode, String deviceCode) {
		this.name = name;
		this.systemCode = systemCode;
		this.deviceCode = deviceCode;
		
		this.commandsToExecute = new ArrayList<Command>();
		commandsToExecute.add(this.commandFactory.createToggleRadioSocketCommand(this));
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
