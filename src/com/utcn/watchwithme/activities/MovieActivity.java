package com.utcn.watchwithme.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.repository.Utilities;

public class MovieActivity extends Activity {

	private Movie movie = Utilities.getSelectedMovie();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextView tv = (TextView) findViewById(R.id.title001);
		tv.setText(movie.getTitle());
	}
}
