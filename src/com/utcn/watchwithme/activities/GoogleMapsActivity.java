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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.utcn.watchwithme.R;
import com.utcn.watchwithme.internet.NetworkUtilities;
import com.utcn.watchwithme.objects.Cinema;
import com.utcn.watchwithme.services.CinemaService;

public class GoogleMapsActivity extends MapActivity {

	private final String DEBUG_TAG = "GoogleMapsActivity";

	private MapView mapView;
	private MapController mc;
	private MapItemizedOverlay overlay, userOverlay;
	private List<Overlay> mapOverlays;
	private Cinema cinema = CinemaService.getSelected();
	private GeoPoint myLocation;

	private static final int ZOOM_LEVEL = 16;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		if (!NetworkUtilities.internetConection(this)) {
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
		}

		initialize();
		startGPS();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.map_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.cinema_menu_option:
			mc.animateTo(cinema.getGeoPoint());
			mc.setZoom(ZOOM_LEVEL);
			break;
		case R.id.directions_menu_option:
			findDirections(myLocation, getCinemaGeoPoint());
			break;
		case R.id.user_position_menu_option:
			if (myLocation != null) {
				mc.animateTo(myLocation);
				mc.setZoom(ZOOM_LEVEL);
			} else {
				Toast.makeText(
						getApplicationContext(),
						"Unable to determine your location.\nPlease turn on your GPS.",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
		return true;
	}

	private void initialize() {
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);

		// move the view to see the cinema
		mc = mapView.getController();
		mc.animateTo(cinema.getGeoPoint());
		mc.setZoom(ZOOM_LEVEL);

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

	public GeoPoint getMyGeoPoint() {
		return myLocation;
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

	private void startGPS() {
		Log.i(DEBUG_TAG, "Starting GPS...");
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2500, 5, ll);

		Log.i(DEBUG_TAG, "Obtaining last known location");
		Location lastLocation = lm
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if (lastLocation != null) {
			setMyLocation(new GeoPoint(
					(int) (lastLocation.getLatitude() * 1E6),
					(int) (lastLocation.getLongitude() * 1E6)));
		}
	}

	private class MyLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				GoogleMapsActivity.this.setMyLocation(new GeoPoint(
						(int) (location.getLatitude() * 1E6), (int) (location
								.getLongitude() * 1E6)));
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			if (provider.equals(LocationManager.GPS_PROVIDER)
					&& GoogleMapsActivity.this.hasWindowFocus()) {
				AlertDialog ad = new AlertDialog.Builder(
						GoogleMapsActivity.this).create();
				ad.setMessage("GPS was turned off");
				ad.setButton("OK",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});

				ad.show();
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	}

	private void setMyLocation(GeoPoint myLocation) {
		Log.i(DEBUG_TAG, "set my location at = " + myLocation.getLatitudeE6()
				+ "," + myLocation.getLongitudeE6());
		this.myLocation = myLocation;

		if (userOverlay == null) {
			userOverlay = new MapItemizedOverlay(this.getResources()
					.getDrawable(R.drawable.pinpoint_human), this);
			mapOverlays.add(userOverlay);
		} else {
			userOverlay.clear();
		}

		userOverlay.addOverlay(new OverlayItem(myLocation, "You", ""));
		mapView.invalidate();
	}
}
