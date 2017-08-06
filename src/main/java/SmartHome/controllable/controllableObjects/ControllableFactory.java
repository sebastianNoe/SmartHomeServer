package SmartHome.controllable.controllableObjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import SmartHome.controllable.Controllable;
import SmartHome.controllable.command.CommandFactory;

@Controller
public class ControllableFactory {
	private CommandFactory commandFactory;
	
	@Autowired
	public ControllableFactory(CommandFactory commandFactory) {
		this.commandFactory = commandFactory;
	}
	
	public Controllable createRadioSocket(String name, String systemCode, String deviceCode) {
		return new RadioSocket(name, systemCode, deviceCode, this.commandFactory);
	}
}
