package utils;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.parsers.DOMParser;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Generate a page source with all controls loaded from an .XML file
 * @author Thong Khuat <maddyvn@gmail.com>
 */
public abstract class ControlFactory extends SeleniumTestBase {
	private String pageName;
	private HashMap<String, By> controlList;
	
	private static String ITEM_TAGNAME = "PageItem";
	private static String ELEMENT_ATTRIBUTE_NAME = "Name";
	private static String ELEMENT_ATTRIBUTE_BY = "By";
	
	/******************************************************************
	 * Load all controls
	 ******************************************************************/
	public void loadControls(String pageName) {
		try {
			setControlList(loadXML(getXMLFilePath(pageName)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public String getPageName() {
		return pageName;
	}
	
	/******************************************************************
	 * Get a specific control from the control factory
	 * 
	 * @param controlName - name of the control <Page Item>
	 ******************************************************************/
	public By getControl(String controlName){
		return controlList.get(controlName);
	}

	/******************************************************************
	 * Load all elements under root element to controlList
	 * 
	 * @param document - Normalized Document object
	 ******************************************************************/
	private void setControlList(Document document) {
		pageName = getNodeAttribute(ELEMENT_ATTRIBUTE_NAME, document.getFirstChild());
		controlList = new HashMap<String, By>();
		
		NodeList nList = document.getElementsByTagName(ITEM_TAGNAME);
		for (int i = 0; i < nList.getLength(); i++){
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				String key = getNodeAttribute(ELEMENT_ATTRIBUTE_NAME, node);
				String byType = getNodeAttribute(ELEMENT_ATTRIBUTE_BY, node);
				String value = getNodeValue(node);
				controlList.put(key, getByWithType(byType, value));
			}
		}
	}

	/******************************************************************
	 * Get the text value of a node
	 * 
	 * @param node - Node to get value
	 * @return String
	 ******************************************************************/
	private String getNodeValue(Node node) {
		NodeList childNodes = node.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node data = childNodes.item(i);
			if (data.getNodeType() == Node.TEXT_NODE)
				return data.getNodeValue();
		}
		return "";
	}

	/******************************************************************
	 * Get an attribute from a note
	 * 
	 * @param attributeName - attribute name
	 * @param node - Node to get attribute
	 * @return String
	 ******************************************************************/
	private String getNodeAttribute(String attributeName, Node node) {
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			if (attr.getNodeName().equalsIgnoreCase(attributeName)) {
				return attr.getNodeValue();
			}
		}
		return "";
	}

	/******************************************************************
	 * 
	 * @param type - type of By {XPath, ID, Name, LinkText , CSS}
	 * @param value - value of attribute
	 * @return By
	 ******************************************************************/
	private By getByWithType(String type, String value){
		Exception e = new Exception("Type is not {XPath, ID, Name, LinkText , CSS}");
		
		if (type.contentEquals("XPath"))
			return new By.ByXPath(value);
		else if (type.contentEquals("ID"))
			return new By.ById(value);
		else if (type.contentEquals("Name"))
			return new By.ByName(value);
		else if (type.contentEquals("LinkText"))
			return new By.ByLinkText(value);
		else if (type.contentEquals("CSS"))
			return new By.ByCssSelector(value);
		else {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/******************************************************************
	 * Return the parsed Document object from .XML file
	 * 
	 * @param filePath - .XML file path
	 * @return Document
	 ******************************************************************/
	private Document loadXML(String filePath) throws SAXException, IOException, ParserConfigurationException{
		DOMParser parser = new DOMParser();
	    parser.parse(filePath);
	    Document document = parser.getDocument();

		// Following
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		// document.getDocumentElement().normalize();

		return document;
	}
	
	/******************************************************************
	 * Return the full path of assign .XML file name
	 * 
	 * @param resource - .XML file name (not included .XML extension)
	 * @return String
	 ******************************************************************/
	private String getXMLFilePath(String resourceName){
		String path = System.getProperty("user.dir");
		return path + "/src/test/resources/Interfaces/" + resourceName + ".xml";
	}
}			
