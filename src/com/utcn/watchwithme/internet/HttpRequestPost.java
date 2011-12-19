package com.utcn.watchwithme.internet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * 
 * @author Vlad
 * 
 */
public class HttpRequestPost {

	private static HttpClient httpClient;
	private static final int TIMEOUT = 2000;

	static {
		HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "UTF-8");
		final SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));

		final ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(
				params, registry);

		httpClient = new DefaultHttpClient(manager, params);
	}

	public static InputStream getStreamForUri(String uri) throws IOException {
		HttpPost post = new HttpPost(uri);
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);

		HttpResponse httpResponse = httpClient.execute(post);
		return httpResponse.getEntity().getContent();
	}

	public static InputStream getStreamForUri(String uri, String[] args,
			String[] values) throws IOException {
		HttpPost post = new HttpPost(uri);
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);

		List<NameValuePair> l = new ArrayList<NameValuePair>(1);
		for (int i = 0; i < args.length; i++) {
			l.add(new BasicNameValuePair(args[i], values[i]));
		}
		post.setEntity(new UrlEncodedFormEntity(l));

		HttpResponse httpResponse = httpClient.execute(post);
		return httpResponse.getEntity().getContent();
	}
}
