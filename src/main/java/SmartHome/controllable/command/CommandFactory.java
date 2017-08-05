package SmartHome.controllable.command;

import org.springframework.stereotype.Controller;

import SmartHome.controllable.RadioSocket;

@Controller
public class CommandFactory {
	
	public Command createToggleRadioSocketCommand(RadioSocket radioSocketToToggle) {
		return new ToggleRadioSocketCommand(radioSocketToToggle);
	}
}
