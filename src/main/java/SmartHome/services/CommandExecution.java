package SmartHome.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SmartHome.controllable.ControllableManager;
import SmartHome.services.requestBody.CommandExecutionRequestBody;

@RestController
@RequestMapping(path = "commandExecution")
public class CommandExecution {
	private ControllableManager controllableManager;
	
	@Autowired
	public CommandExecution(ControllableManager controllableManager) {
		this.controllableManager = controllableManager;
	}
	
	@PostMapping()
	public void commandExecution(@Valid @RequestBody CommandExecutionRequestBody postBody) {
		if(postBody.getCommandName().equals("") || postBody.getControllableName().equals("")) { return; }
		this.controllableManager.executeCommand(postBody.getControllableName(), postBody.getCommandName());
	}
}
