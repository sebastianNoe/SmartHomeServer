package smartHome.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import smartHome.controllable.ControllableManager;
import smartHome.exceptions.CommandExecutionError;
import smartHome.exceptions.DeviceCommandCombinationNotFound;
import smartHome.services.requestBody.CommandExecutionRequestBody;

@RestController
@RequestMapping(path = "commandExecution")
public class CommandExecution {
	private ControllableManager controllableManager;
	
	@Autowired
	public CommandExecution(ControllableManager controllableManager) {
		this.controllableManager = controllableManager;
	}
	
	@PostMapping()
	public ResponseEntity<Object> commandExecution(@Valid @RequestBody CommandExecutionRequestBody postBody){
		try {
			this.validateCommandExecutionRequest(postBody);
			this.controllableManager.executeCommand(postBody.getControllableName(), postBody.getCommandName());
			return new ResponseEntity<Object>( "Command executed", new HttpHeaders(), HttpStatus.ACCEPTED);
		} catch (DeviceCommandCombinationNotFound e) {
			return new ResponseEntity<Object>( "Device and Command combination Not Found", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (CommandExecutionError e) {
			return new ResponseEntity<Object>( "Error while executing Command", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private void validateCommandExecutionRequest(CommandExecutionRequestBody postBody) throws DeviceCommandCombinationNotFound{
		if(postBody.getCommandName().equals("") || postBody.getControllableName().equals("")) { throw new DeviceCommandCombinationNotFound(); }
	}
}
