package utils;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.parsers.DOMParser;
import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;

/**
 * Generate a page source with all controls loaded from an .XML file
 * @author Thong Khuat <maddyvn@gmail.com>
 */
public abstract class ControlFactory extends SeleniumTestBase {
	private String pageTitle;
	private HashMap<String, By> controlList;
	
	private static String PAGE_TAGNAME = "Page";
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
	
//	public String getPageTitle() {
//		return pageTitle;
//	}
	
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
		pageTitle = document.getNodeValue();
//		pageTitle = document.getDocumentElement().getAttribute(ELEMENT_ATTRIBUTE_NAME);
		controlList = new HashMap<String, By>();
		
		NodeList nList = document.getElementsByTagName(ITEM_TAGNAME);
		for (int i = 0; i < nList.getLength(); i++){
			Node node = nList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) node;
				String key = element.getAttribute(ELEMENT_ATTRIBUTE_NAME);
				String byType = element.getAttribute(ELEMENT_ATTRIBUTE_BY);
				String value = element.getFirstChild().getNodeValue();
				controlList.put(key, getByWithType(byType, value));
			}
		}
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
//		document.getDocumentElement().normalize();
		
		return document;
	}
	
	/******************************************************************
	 * Return the full path of assign .XML file name
	 * 
	 * @param resource - .XML file name (not included .XML extension)
	 * @return String
	 ******************************************************************/
	private String getXMLFilePath(String resourceName){
		return "C:\\loginPage.xml";
	}
}			
