package com.utcn.watchwithme.activities;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

/**
 * 
 * @author Vlad
 * 
 */
public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private static OverlayItem selectedItem;

	private GoogleMapsActivity activity;

	private ArrayList<OverlayItem> myOverlays = new ArrayList<OverlayItem>();
	private Drawable marker = null;

	public MapItemizedOverlay(Drawable defaultMarker,
			GoogleMapsActivity activity) {
		super(boundCenterBottom(defaultMarker));
		marker = defaultMarker;
		this.activity = activity;
	}

	@Override
	protected OverlayItem createItem(int i) {
		return myOverlays.get(i);
	}

	@Override
	public int size() {
		return myOverlays.size();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker);
	}

	public void addOverlay(OverlayItem overlay) {
		myOverlays.add(overlay);
		populate();
	}

	public static void setSelectedItem(OverlayItem item) {
		selectedItem = item;
	}

	public static OverlayItem getSelectedItem() {
		return selectedItem;
	}

	public void clear() {
		myOverlays = new ArrayList<OverlayItem>();
	}

	@Override
	protected boolean onTap(int i) {
		selectedItem = myOverlays.get(i);
		Toast.makeText(activity,
				selectedItem.getTitle() + "\n" + selectedItem.getSnippet(),
				Toast.LENGTH_SHORT).show();
		// selectedItem.getTitle();
		// selectedItem.getSnippet();
		// selectedItem.getPoint();
		return true;
	}
}
