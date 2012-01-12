package com.utcn.watchwithme.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.utcn.watchwithme.R;
import com.utcn.watchwithme.adapters.ShowtimeAdapter;
import com.utcn.watchwithme.objects.Movie;
import com.utcn.watchwithme.objects.Showtime;
import com.utcn.watchwithme.services.CinemaService;
import com.utcn.watchwithme.services.ImageService;
import com.utcn.watchwithme.services.MovieService;
import com.utcn.watchwithme.services.ShowtimeService;

public class MovieActivity extends ListActivity implements OnClickListener {

	private TextView mTitleText;
	private RatingBar mRatingBar;
	private ImageView mMovieIcon;
	private ImageView mYoutubeIcon;
	private Button mRateButton;
	private TextView mRatingText;
	private TextView mDetailsText;
	private ListView mListView;
	private Movie movie = MovieService.getSelected();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_detail);
		setUpViews();
		updateRatingText();
		setUpAdapter();
	}

	private void setUpAdapter() {
		ShowtimeAdapter adapter = new ShowtimeAdapter(this, ShowtimeService.getForMovie(movie.getId()), false);
		setListAdapter(adapter);
	}

	private void setUpViews() {
		mListView = (ListView) findViewById(android.R.id.list);
		LayoutInflater inflater = getLayoutInflater();
		View header = (View) inflater.inflate(R.layout.movie_detail_header, null, false);
		mListView.addHeaderView(header);
		mTitleText = (TextView) header.findViewById(R.id.movie_detail_title);
		mTitleText.setText(movie.getTitle());
		mRatingBar = (RatingBar) header.findViewById(R.id.movie_detail_rating);
		mRatingText = (TextView) header.findViewById(R.id.movie_detail_rating_text);
		mMovieIcon = (ImageView) header.findViewById(R.id.movie_detail_icon);
		ImageService is = ImageService.getInstance();
		if (movie.getImageURL() == null) {
			mMovieIcon.setImageResource(R.drawable.no_image);
		} else {
			try {
				mMovieIcon.setImageBitmap(is.getImage(movie.getImageURL()));
			} catch (Exception e) {
				mMovieIcon.setImageResource(R.drawable.no_image);
			}
		}
		mRateButton = (Button) header.findViewById(R.id.movie_detail_rate_button);
		mRateButton.setOnClickListener(this);
		mYoutubeIcon = (ImageView) header.findViewById(R.id.movie_youtube);
		mYoutubeIcon.setOnClickListener(this);

		mDetailsText = (TextView) header.findViewById(R.id.movie_details_text);
		mDetailsText.setText(movie.getDetails());
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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Showtime sh = (Showtime) getListAdapter().getItem(position - 1); // compensate
																			// for
																			// header
		CinemaService.setSelected(sh.getCinema());

		Intent intent = new Intent(this, CinemaActivity.class);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}
}
