package com.utcn.watchwithme.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.utcn.watchwithme.R;

public class AboutActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);

		TextView tv = (TextView) findViewById(R.id.about_text);
		tv.setText("The application “Watch with me” wants to help users of mobile "
				+ "devices to search, find, watch trailers, view ratings and invite "
				+ "friends to movies that are currently at cinemas, and also to locate "
				+ "cinemas on the map or view their schedule.");
	}
}
