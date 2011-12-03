package com.utcn.watchwithme.activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.utcn.watchwithme.R;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.repository.Utilities;

public class GoogleMapsActivity extends MapActivity {

	private final String DEBUG_TAG = "GoogleMapsActivity";

	private MapView mapView;
	private MapController mc;
	private MapItemizedOverlay overlay;
	private List<Overlay> mapOverlays;
	private Cinema cinema = Utilities.getSelectedCinema();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		if (!internetConection()) {
			AlertDialog ad = new AlertDialog.Builder(this).create();
			ad.setMessage("No internet connection");
			ad.setButton("OK",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							GoogleMapsActivity.this.finish();
						}
					});
			ad.setButton2("Cancel",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							initialize();
						}
					});
			ad.show();
			return;
		}

		initialize();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private void initialize() {
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);

		// move the view to see the cinema
		mc = mapView.getController();
		mc.animateTo(cinema.getGeoPoint());
		mc.setZoom(15);

		mapOverlays = mapView.getOverlays();
		overlay = new MapItemizedOverlay(this.getResources().getDrawable(
				R.drawable.green_flag), this);
		OverlayItem item = new OverlayItem(cinema.getGeoPoint(),
				cinema.getName(), cinema.getLocation());
		overlay.addOverlay(item);
		mapOverlays.add(overlay);
		mapView.invalidate();
	}

	public void findDirections(GeoPoint p1, GeoPoint p2) {
		final float c = 1e6f;
		if (p1 == null || p2 == null) {
			Toast.makeText(getApplicationContext(),
					"Your location is undefined.\nUnable to get directions.",
					Toast.LENGTH_SHORT).show();
		} else {
			float p1l = p1.getLatitudeE6() / c;
			float p1L = p1.getLongitudeE6() / c;
			float p2l = p2.getLatitudeE6() / c;
			float p2L = p2.getLongitudeE6() / c;

			String url = String.format(
					"http://maps.google.com/maps?saddr=%f,%f&daddr=%f,%f", p1l,
					p1L, p2l, p2L);
			Log.i(DEBUG_TAG, "Directions url: " + url);

			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse(url.toString()));
			startActivity(intent);
		}
	}

	public GeoPoint getCinemaGeoPoint() {
		return cinema.getGeoPoint();
	}

	public String getAddress(GeoPoint p) {
		String add = "";
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(
					p.getLatitudeE6() / 1E6, p.getLongitudeE6() / 1E6, 1);

			if (addresses.size() > 0) {
				int n = addresses.get(0).getMaxAddressLineIndex();
				for (int i = 0; i < n - 1; i++)
					add += addresses.get(0).getAddressLine(i) + ", ";
				add += addresses.get(0).getAddressLine(n - 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return add;
	}

	private boolean internetConection() {
		ConnectivityManager connectivityManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo == null || !networkInfo.isConnected()) {
			return false;
		} else {
			int netType = networkInfo.getType();
			return (netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_MOBILE);
		}
	}
}
