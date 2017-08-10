package SmartHome;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import SmartHome.exceptions.CorruptSettingsFile;

@Service
public class Settings {
	private String rcSwitchPiLocation;
	private NodeList devicesNodeList;


	public Settings() throws CorruptSettingsFile {
		try {
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document xmlDocument = dBuilder.parse(Application.getSettingsLocation());
			xmlDocument.getDocumentElement().normalize();

			this.readInSettingsNode(xmlDocument);
			this.readInDevicesNodeList(xmlDocument);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new CorruptSettingsFile();
		}
	}

	private void readInDevicesNodeList(Document xmlDocument) throws CorruptSettingsFile {
		this.devicesNodeList = xmlDocument.getElementsByTagName("device");
		if (devicesNodeList == null) {
			throw new CorruptSettingsFile();
		}
	}

	private void readInSettingsNode(Document xmlDocument) throws CorruptSettingsFile {
		NodeList settings = xmlDocument.getElementsByTagName("settings");
		Node settingsNode = settings.item(0);
		if (settingsNode == null || settingsNode.getNodeType() != Node.ELEMENT_NODE) {
			throw new CorruptSettingsFile();
		}

		Element settingsElement = (Element) settingsNode;
		rcSwitchPiLocation = settingsElement.getElementsByTagName("rcSwitch-piLocation").item(0).getTextContent();
	}

	public String getRcSwitchPiLocation() {
		return rcSwitchPiLocation;
	}

	public NodeList getDeviceNodeList() {
		return this.devicesNodeList;
	}
}