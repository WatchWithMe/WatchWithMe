package com.utcn.watchwithme.parsers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.utcn.watchwithme.objects.Cinema;

/**
 * 
 * @author Vlad
 * 
 */
public class CinemaParser {

	private static final String CINEMA = "Cinema";
	private static final String ID = "Id";
	private static final String NAME = "Name";
	private static final String ADDRESS = "Address";
	private static final String IMAGE = "ImageURL";
	private static final String LATITUDE = "LatE6";
	private static final String LONGITUDE = "LngE6";

	private DocumentBuilderFactory factory;
	private DocumentBuilder db;

	private static CinemaParser instance;

	private CinemaParser() throws ParserConfigurationException {
		factory = DocumentBuilderFactory.newInstance();
		db = factory.newDocumentBuilder();
	}

	public static CinemaParser getInstance()
			throws ParserConfigurationException {
		if (instance == null) {
			instance = new CinemaParser();
		}
		return instance;
	}

	public ArrayList<Cinema> getAllCinemas(String xmlString)
			throws SAXException, IOException {
		ArrayList<Cinema> cinemas = new ArrayList<Cinema>();

		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(xmlString));
		Document doc = db.parse(inStream);

		NodeList nodeList = doc.getElementsByTagName(CINEMA);

		for (int index = 0; index < nodeList.getLength(); index++) {

			Node node = nodeList.item(index);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				cinemas.add(getCinemaByElement((Element) node));
			}
		}
		return cinemas;
	}

	public Cinema getCinema(String xmlString) throws SAXException, IOException {
		Cinema cinema = null;

		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(xmlString));
		Document doc = db.parse(inStream);

		NodeList nodeList = doc.getElementsByTagName(CINEMA);

		Node node = nodeList.item(0);

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			cinema = getCinemaByElement((Element) node);
		}

		return cinema;
	}

	private Cinema getCinemaByElement(Element element) {
		int id = -1;
		String name = "";
		String address = "";
		String image = "";
		int latE6 = 0;
		int lngE6 = 0;

		NodeList nameNode = element.getElementsByTagName(ID);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			id = Integer.parseInt(nameElement.getFirstChild().getNodeValue()
					.trim());
		}
		nameNode = element.getElementsByTagName(NAME);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			name = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(ADDRESS);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			address = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(IMAGE);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			image = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(LATITUDE);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			latE6 = Integer.parseInt(nameElement.getFirstChild().getNodeValue()
					.trim());
		}
		nameNode = element.getElementsByTagName(LONGITUDE);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			lngE6 = Integer.parseInt(nameElement.getFirstChild().getNodeValue()
					.trim());
		}

		return new Cinema(id, name, address, image, latE6, lngE6);
	}
}
