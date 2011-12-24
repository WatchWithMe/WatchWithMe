package com.utcn.watchwithme.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;

import com.utcn.watchwithme.activities.WatchWithMeActivity;

/**
 * 
 * @author Vlad
 * 
 */
public class InternalImageRepository {

	private HashSet<String> images = new HashSet<String>();
	private static InternalImageRepository instance;

	private InternalImageRepository() {
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			File myDirectory = new File(path, "/WatchWithMe/Photos/");
			String[] files = myDirectory.list();
			for (String filename : files) {
				images.add(filename);
			}
		} catch (Exception e) {
		}
	}

	public static InternalImageRepository getInstance() {
		if (instance == null) {
			instance = new InternalImageRepository();
		}
		return instance;
	}

	private static String urlToFilename(String url) {
		int i = url.lastIndexOf("/");
		return url.substring(i + 1);
	}

	public boolean hasImage(String imageURL) {
		if (imageURL == null) {
			return false;
		}
		String filename = urlToFilename(imageURL);
		return images.contains(filename);
	}

	public Bitmap loadImage(String url) {
		if (url == null || !images.contains(url)) {
			return null;
		}
		String filename = urlToFilename(url);
		Bitmap bitmap = null;
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			InputStream fIn = null;

			File myDirectory = new File(path, "/WatchWithMe/Photos/");
			myDirectory.mkdirs();

			File file = new File(myDirectory, filename);
			fIn = new FileInputStream(file);

			bitmap = BitmapFactory.decodeStream(fIn);

			fIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public boolean save(Bitmap bitmap, String url) {
		String filename = urlToFilename(url);
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			OutputStream fOut = null;

			File myDirectory = new File(path, "/WatchWithMe/Photos/");
			myDirectory.mkdirs();

			File file = new File(myDirectory, filename);
			fOut = new FileOutputStream(file);

			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
			fOut.flush();
			fOut.close();

			MediaStore.Images.Media.insertImage(WatchWithMeActivity
					.getInstance().getContentResolver(),
					file.getAbsolutePath(), file.getName(), file.getName());

			images.add(filename);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
