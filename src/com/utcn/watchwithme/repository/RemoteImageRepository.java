package com.utcn.watchwithme.repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * @author Vlad
 * 
 */
public class RemoteImageRepository {

	public static Bitmap getBitmapFromURL(String src) throws IOException {
		URL url = new URL(src);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		Bitmap myBitmap = BitmapFactory.decodeStream(input);
		return myBitmap;
	}
}
