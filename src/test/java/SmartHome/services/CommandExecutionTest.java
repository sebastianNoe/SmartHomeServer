package SmartHome.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import SmartHome.controllable.ControllableManager;
import SmartHome.exceptions.CommandExecutionError;
import SmartHome.exceptions.DeviceCommandCombinationNotFound;
import SmartHome.services.requestBody.CommandExecutionRequestBody;

public class CommandExecutionTest {
	private static final String testCommandName = "testCommand";
	private static final String testDeviceName = "testDevice";

	private ControllableManager controllableManagerDummy;
	private CommandExecution testObject;

	@Before
	public void setup() {
		this.controllableManagerDummy = mock(ControllableManager.class);
		this.testObject = new CommandExecution(this.controllableManagerDummy);
	}

	@Test
	public void validPostBodyLeadsToExecuteCommand() throws CommandExecutionError, DeviceCommandCombinationNotFound {
		CommandExecutionRequestBody postBody = this.createPostBody(testCommandName, testDeviceName);
		ResponseEntity<Object> actualResponse = testObject.commandExecution(postBody);
		assertTrue(actualResponse.getStatusCode() == HttpStatus.ACCEPTED);
		verify(this.controllableManagerDummy, times(1)).executeCommand(CommandExecutionTest.testDeviceName,
				CommandExecutionTest.testCommandName);
	}

	@Test
	public void initialCommandNameLeadsToErrorResponse() {
		CommandExecutionRequestBody postBody = this.createPostBody("", testDeviceName);
		ResponseEntity<Object> actualResponse = testObject.commandExecution(postBody);
		assertTrue(actualResponse.getStatusCode() == HttpStatus.BAD_REQUEST);
	}

	@Test
	public void initialPostBodyLeadsToErrorResponse() {
		CommandExecutionRequestBody postBody = this.createPostBody("", "");
		ResponseEntity<Object> actualResponse = testObject.commandExecution(postBody);
		assertTrue(actualResponse.getStatusCode() == HttpStatus.BAD_REQUEST);
	}

	@Test
	public void initialDeviceNameLeadsToErrorResponse() {
		CommandExecutionRequestBody postBody = this.createPostBody(testCommandName, "");
		ResponseEntity<Object> actualResponse = testObject.commandExecution(postBody);
		assertTrue(actualResponse.getStatusCode() == HttpStatus.BAD_REQUEST);
	}

	@Test
	public void notFoundDeviceCommandLeadsToErrorResponse()
			throws CommandExecutionError, DeviceCommandCombinationNotFound {
		CommandExecutionRequestBody postBody = this.createPostBody(testCommandName, testDeviceName);
		doThrow(new DeviceCommandCombinationNotFound()).when(controllableManagerDummy).executeCommand(anyString(),
				anyString());
		ResponseEntity<Object> actualResponse = testObject.commandExecution(postBody);
		assertTrue(actualResponse.getStatusCode() == HttpStatus.BAD_REQUEST);
	}

	@Test
	public void internalErrorLeadsToErrorResponse() throws CommandExecutionError, DeviceCommandCombinationNotFound {
		CommandExecutionRequestBody postBody = this.createPostBody(testCommandName, testDeviceName);
		doThrow(new CommandExecutionError()).when(controllableManagerDummy).executeCommand(anyString(), anyString());
		ResponseEntity<Object> actualResponse = testObject.commandExecution(postBody);
		assertTrue(actualResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private CommandExecutionRequestBody createPostBody(String commandName, String deviceName) {
		CommandExecutionRequestBody postBody = new CommandExecutionRequestBody();
		postBody.setCommandName(commandName);
		postBody.setControllableName(deviceName);
		return postBody;
	}

}
