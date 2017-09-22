package SmartHome.controllable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import SmartHome.Settings;
import SmartHome.controllable.controllableObjects.ControllableFactory;
import SmartHome.exceptions.CommandExecutionError;
import SmartHome.exceptions.DeviceCommandCombinationNotFound;

@Controller
public class ControllableManager {
	private Settings applicationSettings;
	private ControllableFactory controllableFactory;
	private List<Controllable> controllables;
	private CastNodeToElement castNodeToElement;
	
	@Autowired
	public ControllableManager(ControllableFactory controllableFactory, Settings applicationSettings) {
		this(controllableFactory, applicationSettings, new CastNodeToElement());
	}
	
	ControllableManager(ControllableFactory controllableFactory, Settings applicationSettings, CastNodeToElement castNodeToElement) {
		this.controllableFactory = controllableFactory;
		this.applicationSettings = applicationSettings;
		this.castNodeToElement = castNodeToElement;
	}


	@PostConstruct
	public void init() {
		this.controllables = new ArrayList<Controllable>();
		
		NodeList controllablesList = this.applicationSettings.getDeviceNodeList();
		for(int controllableNumber = 0; controllableNumber < controllablesList.getLength(); controllableNumber++) {
			Node controllableNode = controllablesList.item(controllableNumber);
			if(controllableNode.getNodeType() == Node.ELEMENT_NODE) {
				Element controllableElement = this.castNodeToElement.castNodeToElement(controllableNode);
				this.controllables.add(controllableFactory.createControllable(controllableElement));
			}
		}
	}

	public void executeCommand(String controllableName, String commandName) throws DeviceCommandCombinationNotFound, CommandExecutionError {
		Iterator<Controllable> controllableIterator = this.controllables.iterator();
		while (controllableIterator.hasNext()) {
			Controllable currentControllable = controllableIterator.next();
			if (currentControllable.getName().equals(controllableName)) {
				Iterator<Command> commandIterator = currentControllable.getCommands().iterator();
				while (commandIterator.hasNext()) {
					Command currentCommand = commandIterator.next();
					if (currentCommand.getName().equals(commandName)) {
						currentCommand.execute();
						return;
					}
				}
			}
		}
		
		throw new DeviceCommandCombinationNotFound();
	}
}

class CastNodeToElement {
	Element castNodeToElement(Node node) {
		return (Element) node;
	}
}
