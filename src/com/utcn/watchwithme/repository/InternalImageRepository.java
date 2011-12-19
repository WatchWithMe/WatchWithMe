package com.utcn.watchwithme.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

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

	private static HashMap<String, Bitmap> map = new HashMap<String, Bitmap>();

	static {
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			File myDirectory = new File(path, "/WatchWithMe/Photos/");
			String[] files = myDirectory.list();
			for (String file : files) {
				map.put(file, null);
			}
		} catch (Exception e) {
		}
	}

	private static String urlToFilename(String url) {
		int i = url.lastIndexOf("/");
		return url.substring(i + 1);
	}

	public static boolean hasImage(String imageURL) {
		String filename = urlToFilename(imageURL);
		return map.containsKey(filename);
	}

	public static Bitmap loadImage(String url) {
		String filename = urlToFilename(url);
		Bitmap bitmap = null;
		if (map.containsKey(filename)) {
			bitmap = map.get(filename);
			if (bitmap != null) {
				return bitmap;
			} else {
				map.remove(filename);
			}
		}
		try {
			String path = Environment.getExternalStorageDirectory().toString();
			InputStream fIn = null;

			File myDirectory = new File(path, "/WatchWithMe/Photos/");
			myDirectory.mkdirs();

			File file = new File(myDirectory, filename);
			fIn = new FileInputStream(file);

			bitmap = BitmapFactory.decodeStream(fIn);

			fIn.close();

			map.put(filename, bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public static boolean save(Bitmap bitmap, String url) {
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

			map.put(filename, bitmap);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
