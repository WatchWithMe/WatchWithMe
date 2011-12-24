package com.utcn.watchwithme.adapters;

import java.io.IOException;
import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.widget.BaseAdapter;

import com.utcn.watchwithme.services.ImageService;

public abstract class MyBaseAdapter extends BaseAdapter {

	protected Activity activity;

	protected MyBaseAdapter(Context context) {
		this.activity = (Activity) context;
	}

	protected void loadImage(String imageURL) {
		Thread loader = new ImageThread(this, imageURL);
		loader.start();
	}

	protected static class ImageThread extends Thread {

		private String url;
		private WeakReference<MyBaseAdapter> weakAdapter;

		public ImageThread(MyBaseAdapter adapter, String url) {
			this.weakAdapter = new WeakReference<MyBaseAdapter>(adapter);
			this.url = url;
		}

		@Override
		public void run() {
			ImageService is = ImageService.getInstance();
			try {
				is.loadImage(url);
				weakAdapter.get().activity.runOnUiThread(new Runnable() {
					public void run() {
						weakAdapter.get().notifyDataSetChanged();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
