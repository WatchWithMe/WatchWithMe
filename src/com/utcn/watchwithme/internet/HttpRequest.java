package com.utcn.watchwithme.internet;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpRequest {

	private static HttpClient httpClient;

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

	public InputStream getStreamForUri(String uri) throws IOException {
		HttpGet get = new HttpGet(uri);
		HttpParams params = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 1000);

		HttpResponse httpResponse = httpClient.execute(get);
		return httpResponse.getEntity().getContent();
	}
}
