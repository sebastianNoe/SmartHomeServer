package smartHome.controllable.controllableObjects;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Element;

import smartHome.controllable.Controllable;
import smartHome.controllable.command.CommandFactory;
import smartHome.Settings;

@Controller
public class ControllableFactory {
	protected final static String elementDeviceType = "deviceType";
	protected final static String elementName = "name";
	protected final static String elementRadioSocketSystemCode = "systemCode";
	protected final static String elementRadioSocketDeviceCode = "deviceCode";
	
	protected final static String deviceTypeRadioSocket = "RadioSocket";
	
	private CommandFactory commandFactory;
	private Settings applicationSettings;
	
	@Autowired
	public ControllableFactory(CommandFactory commandFactory, Settings applicationSettings) {
		this.commandFactory = commandFactory;
		this.applicationSettings = applicationSettings;
	}
	
	public Controllable createRadioSocket(Element controllableElement) {
		return new RadioSocket(controllableElement.getElementsByTagName(elementName).item(0).getTextContent(), 
							   controllableElement.getElementsByTagName(elementRadioSocketSystemCode).item(0).getTextContent(), 
							   controllableElement.getElementsByTagName(elementRadioSocketDeviceCode).item(0).getTextContent(), 
							   this.commandFactory,
							   new File(this.applicationSettings.getRcSwitchPiLocation()),
							   new RuntimeExecuter());
	}
	
	public Controllable createControllable(Element controllableElement) {
		Controllable createdControllable = null;
		
		String controllableType = controllableElement.getElementsByTagName(elementDeviceType).item(0).getTextContent();
		if(controllableType.equals(deviceTypeRadioSocket)) {
			return this.createRadioSocket(controllableElement);
		}
		
		return createdControllable;
	}
}
