package SmartHome.controllable.command;

import SmartHome.controllable.RadioSocket;

public class ToggleRadioSocketCommand implements Command{
	private RadioSocket myRadioSocket;
	
	public ToggleRadioSocketCommand(RadioSocket myRadioSocket) {
		this.myRadioSocket = myRadioSocket;
	}
	
	public void execute() {
		this.myRadioSocket.toggle();
	}

	public String getName() {
		return "Toggle Radio Socket";
	}

}
