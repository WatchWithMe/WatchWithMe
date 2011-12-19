package com.utcn.watchwithme.internet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * 
 * @author Vlad
 * 
 */
public class WebServices {

	private String baseURL;

	private static WebServices instance;

	private WebServices(String baseURL) {
		this.baseURL = baseURL;
	}

	public static WebServices getInstance() {
		if (instance == null) {
			instance = new WebServices("http://78.97.204.160/wwm/Service.asmx/");
		}
		return instance;
	}

	public String getAllCinemas() throws IOException {
		InputStream is = HttpRequestGet.getStreamForUri(baseURL
				+ "getAllCinemas");
		return new Scanner(is).useDelimiter("\\A").next();
	}

	public String getAllMovies() throws IOException {
		InputStream is = HttpRequestGet.getStreamForUri(baseURL
				+ "getAllMovies");
		return new Scanner(is).useDelimiter("\\A").next();
	}

	public String getCinema(int cid) throws IOException {
		String[] args = new String[1];
		args[0] = "cid";
		String[] values = new String[1];
		values[0] = Integer.toString(cid);

		InputStream is = HttpRequestPost.getStreamForUri(baseURL + "getCinema",
				args, values);
		return new Scanner(is).useDelimiter("\\A").next();
	}

	public String getCinemasForMovie(int mid) throws IOException {
		String[] args = new String[1];
		args[0] = "mid";
		String[] values = new String[1];
		values[0] = Integer.toString(mid);

		InputStream is = HttpRequestPost.getStreamForUri(baseURL
				+ "getCinemasForMovie", args, values);
		return new Scanner(is).useDelimiter("\\A").next();
	}

	public String getMovie(int mid) throws IOException {
		String[] args = new String[1];
		args[0] = "mid";
		String[] values = new String[1];
		values[0] = Integer.toString(mid);

		InputStream is = HttpRequestPost.getStreamForUri(baseURL + "getMovie",
				args, values);
		return new Scanner(is).useDelimiter("\\A").next();
	}

	public String getMoviesForCinemas(int cid) throws IOException {
		String[] args = new String[1];
		args[0] = "cid";
		String[] values = new String[1];
		values[0] = Integer.toString(cid);

		InputStream is = HttpRequestPost.getStreamForUri(baseURL
				+ "getMoviesForCinema", args, values);
		return new Scanner(is).useDelimiter("\\A").next();
	}
}
