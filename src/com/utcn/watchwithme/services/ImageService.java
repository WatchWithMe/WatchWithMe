package com.utcn.watchwithme.services;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import android.graphics.Bitmap;
import android.util.Log;

import com.utcn.watchwithme.repository.InternalImageRepository;
import com.utcn.watchwithme.repository.RemoteImageRepository;

public class ImageService {

	private static final int MAX_SIZE = 20;

	private static ImageService instance;

	private InternalImageRepository card;
	private Map<String, Bitmap> images;
	private LinkedList<String> urls;
	private Set<String> loading;

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
		this.urls = new LinkedList<String>();
		this.loading = Collections.synchronizedSet(new HashSet<String>());
	}

	public Bitmap getImage(String imageURL) {
		Log.i("ImageService", "getImage(" + imageURL + ")");
		Bitmap bmp = null;
		synchronized (images) {
			bmp = images.get(imageURL);
		}
		return bmp;
	}

	public Bitmap loadImage(String imageURL) throws IOException {
		boolean kill = false;
		Bitmap bmp = null;
		synchronized (loading) {
			if (loading.contains(imageURL)) {
				kill = true;
			} else {
				loading.add(imageURL);
			}
		}
		synchronized (images) {
			if (images.containsKey(imageURL)) {
				bmp = images.get(imageURL);
			}
		}
		if (bmp != null) {
			return bmp;
		}
		if (kill) {
			throw new IOException();
		}
		Log.d("ImageService", "loadImage(" + imageURL + ")");
		if (card.hasImage(imageURL)) {
			bmp = card.loadImage(imageURL);
		} else {
			bmp = RemoteImageRepository.getBitmapFromURL(imageURL);
			card.save(bmp, imageURL);
		}
		synchronized (images) {
			if (images.size() == MAX_SIZE) {
				images.remove(urls.getFirst());
				urls.removeFirst();
			}
			images.put(imageURL, bmp);
			urls.add(imageURL);
		}
		synchronized (loading) {
			loading.remove(imageURL);
		}
		return bmp;
	}
}
