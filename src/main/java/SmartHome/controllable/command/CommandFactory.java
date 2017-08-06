package SmartHome.controllable.command;

import org.springframework.stereotype.Controller;

import SmartHome.controllable.Command;
import SmartHome.controllable.controllableObjects.RadioSocket;

@Controller
public class CommandFactory {
	
	public Command createToggleRadioSocketCommand(RadioSocket radioSocketToToggle) {
		return new ToggleRadioSocketCommand(radioSocketToToggle);
	}
}
