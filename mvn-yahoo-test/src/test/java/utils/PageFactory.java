package utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Generate a page source with all controls loaded from an .XML file
 * @author Thong Khuat <maddyvn@gmail.com>
 */
public class PageFactory {
	private HashMap<String, HashMap<String, String>> controlList;
	
	public PageFactory(String pageName) {
		try {
			loadControls(loadXML(getXMLFilePath(pageName)));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, HashMap<String, String>> getControlList() {
		return controlList;
	}

	/******************************************************************
	 * Load all controls under root element of assigned Document object
	 * 
	 * @param document - Normalized Document object
	 ******************************************************************/
	public void loadControls(Document document){
		// TODO: generate code here
		// NodeList nList = doc.getElementsByTagName("staff");
	}
	
	/******************************************************************
	 * Return the parsed Document object from .XML file
	 * 
	 * @param filePath - .XML file path
	 * @return Document
	 ******************************************************************/
	public Document loadXML(String filePath) throws SAXException, IOException, ParserConfigurationException{
		Document document;
		File xmlFile;
		
		xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		document = dBuilder.parse(xmlFile);
		
		// Following
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		document.getDocumentElement().normalize();
		
		return document;
	}
	
	/******************************************************************
	 * Return the full path of assign .XML file name
	 * 
	 * @param filename - .XML file name (not included .XML extension)
	 * @return String
	 ******************************************************************/
	public String getXMLFilePath(String filename){
		// TODO: using relative classpath from src/test/resources/Interfaces
		return filename;
	}
}			
