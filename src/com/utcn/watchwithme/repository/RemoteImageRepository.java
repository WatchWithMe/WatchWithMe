package com.utcn.watchwithme.repository;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * @author Vlad
 * 
 */
public class RemoteImageRepository {

	private static Map<String, Bitmap> map = new HashMap<String, Bitmap>();

	public static Bitmap getBitmap(String imageURL) throws IOException {
		Bitmap bitmap = map.get(imageURL);
		if (bitmap == null) {
			bitmap = InternalImageRepository.loadImage(imageURL);
			if (bitmap == null) {
				bitmap = getBitmapFromURL(imageURL);
				InternalImageRepository.save(bitmap, imageURL);
			}
			map.put(imageURL, bitmap);
		}
		return bitmap;
	}

	public static boolean hasImage(String imageURL) {
		return map.containsKey(imageURL)
				|| InternalImageRepository.hasImage(imageURL);
	}

	private static Bitmap getBitmapFromURL(String src) throws IOException {
		URL url = new URL(src);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.connect();
		InputStream input = connection.getInputStream();
		Bitmap myBitmap = BitmapFactory.decodeStream(input);
		return myBitmap;
	}
}
