package smartHome.controllable.controllableObjects;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import smartHome.controllable.Controllable;
import smartHome.controllable.command.CommandFactory;
import smartHome.controllable.controllableObjects.ControllableFactory;
import smartHome.controllable.controllableObjects.RadioSocket;
import smartHome.Settings;

public class ControllableFactoryTest {
	private CommandFactory commandFactoryDummy;
	private Settings settingsDummy;

	private ControllableFactory testObject;

	@Before
	public void setup() {
		this.commandFactoryDummy = mock(CommandFactory.class);
		this.settingsDummy = mock(Settings.class);
		when(this.settingsDummy.getRcSwitchPiLocation()).thenReturn("");

		this.testObject = new ControllableFactory(this.commandFactoryDummy, this.settingsDummy);
	}

	@Test
	public void createRadioSocket() {
		Element element = this.prepareRadioSocketElement();
		Controllable createdControllable = this.testObject.createControllable(element);
		assertThat(createdControllable, instanceOf(RadioSocket.class));
	}
	
	@Test
	public void invalidDeviceTypeReturnNull() {
		Element element = this.prepareInvalidElement();
		Controllable createdControllable = this.testObject.createControllable(element);
		assertNull(createdControllable);
	}
	
	private Element prepareInvalidElement() {
		Element element = mock(Element.class);
		NodeList nodeListDeviceType = mock(NodeList.class);
		Node nodeDeviceType = mock(Node.class);
		// Device Type
		when(nodeListDeviceType.item(0)).thenReturn(nodeDeviceType);
		when(nodeDeviceType.getTextContent()).thenReturn("");
		when(element.getElementsByTagName(anyString())).thenReturn(nodeListDeviceType);
		return element;
	}
	
	private Element prepareRadioSocketElement() {
		Element element = mock(Element.class);
		NodeList nodeListDeviceType = mock(NodeList.class);
		Node nodeDeviceType = mock(Node.class);
		// Device Type
		when(nodeListDeviceType.item(0)).thenReturn(nodeDeviceType);
		when(nodeDeviceType.getTextContent()).thenReturn(ControllableFactory.deviceTypeRadioSocket);
		when(element.getElementsByTagName(ControllableFactory.elementDeviceType)).thenReturn(nodeListDeviceType);
		// Device Info
		prepareRadioSocketElement(ControllableFactory.elementName, element);
		prepareRadioSocketElement(ControllableFactory.elementRadioSocketDeviceCode, element);
		prepareRadioSocketElement(ControllableFactory.elementRadioSocketSystemCode, element);
		return element;
	}

	private void prepareRadioSocketElement(String tagName, Element addToElement) {
		NodeList nodeListElementName = mock(NodeList.class);
		when(addToElement.getElementsByTagName(tagName)).thenReturn(nodeListElementName);
		Node nodeElementName = mock(Node.class);
		when(nodeListElementName.item(0)).thenReturn(nodeElementName);
		when(nodeElementName.getTextContent()).thenReturn("");
	}
}
