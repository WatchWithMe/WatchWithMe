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

import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.services.CinemaService;
import com.utcn.watchwithme.services.MovieService;

/**
 * 
 * @author Vlad
 * 
 */
public class ShowtimeParser {

	private static final String SHOWTIME = "Showtime";
	private static final String MID = "Mid";
	private static final String CID = "Cid";
	private static final String DATES = "Showtimes";
	private static final String PRICE = "Price";

	private DocumentBuilderFactory factory;
	private DocumentBuilder db;

	private static ShowtimeParser instance;

	private ShowtimeParser() throws ParserConfigurationException {
		factory = DocumentBuilderFactory.newInstance();
		db = factory.newDocumentBuilder();
	}

	public static ShowtimeParser getInstance()
			throws ParserConfigurationException {
		if (instance == null) {
			instance = new ShowtimeParser();
		}
		return instance;
	}

	public ArrayList<Showtime> getShowtimes(String xmlString)
			throws SAXException, IOException {
		ArrayList<Showtime> showtimes = new ArrayList<Showtime>();

		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(xmlString));
		Document doc = db.parse(inStream);

		NodeList nodeList = doc.getElementsByTagName(SHOWTIME);

		for (int index = 0; index < nodeList.getLength(); index++) {

			Node node = nodeList.item(index);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				showtimes.add(getShowtimeByElement((Element) node));
			}
		}
		return showtimes;
	}

	private Showtime getShowtimeByElement(Element element) {
		int mid = -1;
		int cid = -1;
		String dates = "";
		float price = 0;

		NodeList nameNode = element.getElementsByTagName(MID);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			mid = Integer.parseInt(nameElement.getFirstChild().getNodeValue()
					.trim());
		}
		nameNode = element.getElementsByTagName(CID);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			cid = Integer.parseInt(nameElement.getFirstChild().getNodeValue()
					.trim());
		}
		nameNode = element.getElementsByTagName(DATES);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			dates = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(PRICE);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			price = Float.parseFloat(nameElement.getFirstChild().getNodeValue()
					.trim());
		}

		return new Showtime(MovieService.getMovie(mid),
				CinemaService.getCinema(cid), price, dates);
	}
}
