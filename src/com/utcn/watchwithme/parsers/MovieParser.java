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

import com.utcn.watchwithme.objects.Movie;

/**
 * 
 * @author Vlad
 * 
 */
public class MovieParser {

	private static final String MOVIE = "Movie";
	private static final String ID = "Id";
	private static final String TITLE = "Title";
	private static final String DETAILS = "Details";
	private static final String LENGTH = "Length";
	private static final String TRAILER = "TrailerURL";
	private static final String IMAGE = "ImageURL";
	private static final String RATING = "Raiting";

	private DocumentBuilderFactory factory;
	private DocumentBuilder db;

	private static MovieParser instance;

	private MovieParser() throws ParserConfigurationException {
		factory = DocumentBuilderFactory.newInstance();
		db = factory.newDocumentBuilder();
	}

	public static MovieParser getInstance() throws ParserConfigurationException {
		if (instance == null) {
			instance = new MovieParser();
		}
		return instance;
	}

	public ArrayList<Movie> getAllMovies(String xmlString) throws SAXException,
			IOException {
		ArrayList<Movie> movies = new ArrayList<Movie>();

		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(xmlString));
		Document doc = db.parse(inStream);

		NodeList nodeList = doc.getElementsByTagName(MOVIE);

		for (int index = 0; index < nodeList.getLength(); index++) {

			Node node = nodeList.item(index);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				movies.add(getMovieByElement((Element) node));
			}
		}
		return movies;
	}

	public Movie getMovie(String xmlString) throws SAXException, IOException {
		Movie movie = null;

		InputSource inStream = new InputSource();
		inStream.setCharacterStream(new StringReader(xmlString));
		Document doc = db.parse(inStream);

		NodeList nodeList = doc.getElementsByTagName(MOVIE);

		Node node = nodeList.item(0);

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			movie = getMovieByElement((Element) node);
		}

		return movie;
	}

	private Movie getMovieByElement(Element element) {
		int id = -1;
		String title = "";
		String details = "";
		int length = 0;
		String trailer = "";
		String image = "";
		float rating = 0;

		NodeList nameNode = element.getElementsByTagName(ID);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			id = Integer.parseInt(nameElement.getFirstChild().getNodeValue()
					.trim());
		}
		nameNode = element.getElementsByTagName(TITLE);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			title = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(DETAILS);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			details = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(LENGTH);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			length = Integer.parseInt(nameElement.getFirstChild()
					.getNodeValue().trim());
		}
		nameNode = element.getElementsByTagName(TRAILER);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			trailer = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(IMAGE);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			image = nameElement.getFirstChild().getNodeValue().trim();
		}
		nameNode = element.getElementsByTagName(RATING);
		if (nameNode.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element nameElement = (Element) nameNode.item(0);
			rating = Float.parseFloat(nameElement.getFirstChild()
					.getNodeValue().trim());
		}

		return new Movie(id, title, details, length, trailer, image, rating);
	}
}
