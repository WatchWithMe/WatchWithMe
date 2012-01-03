package com.utcn.watchwithme.services;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.util.Log;

import com.utcn.watchwithme.repository.InternalImageRepository;
import com.utcn.watchwithme.repository.RemoteImageRepository;

public class ImageService {

	private static ImageService instance;

	private InternalImageRepository card;
	private Map<String, Bitmap> images;

	public static ImageService getInstance() {
		if (instance == null) {
			instance = new ImageService();
		}
		return instance;
	}

	private ImageService() {
		this.card = InternalImageRepository.getInstance();
		this.images = Collections
				.synchronizedMap(new HashMap<String, Bitmap>());
	}

	public Bitmap getImage(String imageURL) {
		// Log.i("ImageService", "getImage(" + imageURL + ")");
		Bitmap bmp = null;
		synchronized (images) {
			bmp = images.get(imageURL);
		}
		return bmp;
	}

	public Bitmap loadImage(String imageURL) throws IOException {
		Bitmap bmp = null;
		int count = 0;
		while (bmp == null && count < 5) {
			bmp = load(imageURL);
			count++;
		}
		return bmp;
	}

	private synchronized Bitmap load(String imageURL) throws IOException {
		Bitmap bmp = null;
		if (images.containsKey(imageURL)) {
			bmp = images.get(imageURL);
			Log.i("ImageService", "image here (" + imageURL + ") " + bmp);
			return bmp;
		}
		if (card.hasImage(imageURL)) {
			bmp = card.loadImage(imageURL);
			Log.d("ImageService", "loaded from card " + bmp + " " + imageURL);
		} else {
			bmp = RemoteImageRepository.getBitmapFromURL(imageURL);
			card.save(bmp, imageURL);
			Log.d("ImageService", "loaded from internet " + bmp + " "
					+ imageURL);
		}
		if (bmp != null) {
			images.put(imageURL, bmp);
		}
		return bmp;
	}
}
