package com.utcn.watchwithme.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.repository.Utilities;

public class MovieActivity extends Activity implements OnClickListener {

	private TextView mTitleText;
	private RatingBar mRatingBar;
	private ImageView mMovieIcon;
	private ImageView mYoutubeIcon;
	private Button mRateButton;
	private TextView mRatingText;
	private Movie movie = Utilities.getSelectedMovie();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_detail);
		setUpViews();
		updateRatingText();
	}

	private void setUpViews() {
		mTitleText = (TextView) findViewById(R.id.movie_detail_title);
		mTitleText.setText(movie.getTitle());
		mRatingBar = (RatingBar) findViewById(R.id.movie_detail_rating);
		mRatingText = (TextView) findViewById(R.id.movie_detail_rating_text);
		mMovieIcon = (ImageView) findViewById(R.id.movie_detail_icon);
		mMovieIcon.setImageResource(movie.getIcon());
		mRateButton = (Button) findViewById(R.id.movie_detail_rate_button);
		mRateButton.setOnClickListener(this);
		mYoutubeIcon = (ImageView) findViewById(R.id.movie_youtube);
		mYoutubeIcon.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.movie_youtube:
			openLinkToYoutube();
			break;
		case R.id.movie_detail_rate_button:
			movie.giveRating(mRatingBar.getRating());
			updateRatingText();
			break;
		default:
			break;
		}
	}

	private void updateRatingText() {
		mRatingText.setText("Rating: " + movie.getRating() + ", Total: " + movie.getRatingCount());
		mRatingBar.setRating(movie.getRating());
	}

	private void openLinkToYoutube() {
		String trailerUrl = movie.getTrailerURL();
		if (trailerUrl != null && !trailerUrl.equals("")) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl)));
		}
	}
}
