package SmartHome.controllable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import SmartHome.controllable.controllableObjects.ControllableFactory;

@Controller
public class ControllableManager {
	private ControllableFactory controllableFactory;
	private List<Controllable> controllables;
	
	@Autowired
	public ControllableManager(ControllableFactory controllableFactory) {
		this.controllableFactory = controllableFactory;
	}


	@PostConstruct
	public void init() {
		this.controllables = new ArrayList<Controllable>();
		
		this.controllables.add(controllableFactory.createRadioSocket("test socket", "11111", "00010"));	
	}

	public void executeCommand(String controllableName, String commandName) {
		Iterator<Controllable> controllableIterator = this.controllables.iterator();
		while (controllableIterator.hasNext()) {
			Controllable currentControllable = controllableIterator.next();
			if (currentControllable.getName().equals(controllableName)) {
				Iterator<Command> commandIterator = currentControllable.getCommands().iterator();
				while (commandIterator.hasNext()) {
					Command currentCommand = commandIterator.next();
					if (currentCommand.getName().equals(commandName)) {
						currentCommand.execute();
					}
				}
			}
		}
	}
}
