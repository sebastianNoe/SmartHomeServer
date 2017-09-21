package SmartHome.controllable.controllableObjects;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Element;

import SmartHome.Settings;
import SmartHome.controllable.Controllable;
import SmartHome.controllable.command.CommandFactory;

@Controller
public class ControllableFactory {
	private CommandFactory commandFactory;
	private Settings applicationSettings;
	
	@Autowired
	public ControllableFactory(CommandFactory commandFactory, Settings applicationSettings) {
		this.commandFactory = commandFactory;
		this.applicationSettings = applicationSettings;
	}
	
	public Controllable createRadioSocket(Element controllableElement) {
		return new RadioSocket(controllableElement.getElementsByTagName("name").item(0).getTextContent(), 
							   controllableElement.getElementsByTagName("systemCode").item(0).getTextContent(), 
							   controllableElement.getElementsByTagName("deviceCode").item(0).getTextContent(), 
							   this.commandFactory,
							   new File(this.applicationSettings.getRcSwitchPiLocation()),
							   new RuntimeExecuter());
	}
	
	public Controllable createControllable(Element controllableElement) {
		Controllable createdControllable = null;
		
		String controllableType = controllableElement.getElementsByTagName("deviceType").item(0).getTextContent();
		if(controllableType.equals("RadioSocket")) {
			return this.createRadioSocket(controllableElement);
		}
		
		return createdControllable;
	}
}
