package SmartHome.settings;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import SmartHome.exceptions.CorruptSettingsFile;



class SettingsReader {
	private String rcSwitchPiLocation;
	private Node devicesNode;	


	SettingsReader(File settingsLocation) throws CorruptSettingsFile {
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document xmlDocument = dBuilder.parse(settingsLocation);
			xmlDocument.getDocumentElement().normalize();
			
			this.readInSettingsNode(xmlDocument);
			this.readInDevicesNodeList(xmlDocument);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new CorruptSettingsFile();
		}
	}
	
	private void readInDevicesNodeList(Document xmlDocument) throws CorruptSettingsFile{
		this.devicesNode = xmlDocument.getElementsByTagName("devices").item(0);
		if(devicesNode == null) {
			throw new CorruptSettingsFile();
		}		
	}

	private void readInSettingsNode(Document xmlDocument) throws CorruptSettingsFile{
		NodeList settings = xmlDocument.getElementsByTagName("settings");
		Node settingsNode = settings.item(0);
		if(settingsNode == null || settingsNode.getNodeType() != Node.ELEMENT_NODE) {
			throw new CorruptSettingsFile();
		}
		
		Element settingsElement = (Element) settingsNode;
		rcSwitchPiLocation = settingsElement.getElementsByTagName("rcSwitch-piLocation").item(0).getTextContent();		
	}
	
	public String getRcSwitchPiLocation() {
		return rcSwitchPiLocation;
	}
	
	public Node getDevicesNode() {
		return this.devicesNode;
	}
}
