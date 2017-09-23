package smartHome.controllable.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import smartHome.controllable.Command;
import smartHome.controllable.OnOrOffSwitchable;
import smartHome.controllable.controllableObjects.RadioSocket;

@Controller
public class CommandFactory {
	
	public Command createToggleRadioSocketCommand(RadioSocket radioSocketToToggle) {
		return new ToggleRadioSocketCommand(radioSocketToToggle);
	}
	public List<Command> createTurnOnAndOffCommand(OnOrOffSwitchable switchable) {
		List<Command> list = new ArrayList<Command>();
		
		list.add(new TurnOn(switchable));
		list.add(new TurnOff(switchable));
		return list;
	}
}
