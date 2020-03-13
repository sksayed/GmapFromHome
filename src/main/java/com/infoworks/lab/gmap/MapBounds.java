package com.infoworks.lab.gmap;

import java.util.List;

/**
 * Map bounds.
 */
public class MapBounds {

	private LatLon sw;

	private LatLon ne;

	/**
	 * Constructor.
	 */
	public MapBounds() {
		// Empty.
	}

	/**
	 * @return
	 */
	public LatLon getSw() {
		return sw;
	}

	/**
	 * @return
	 */
	public LatLon getNe() {
		return ne;
	}

	public boolean isInitialized() {
		return ne != null && sw != null && ne.isInitialized() && sw.isInitialized();
	}

	public void add(LatLon point) {
		if (sw == null) {
			sw = new LatLon(point.getLat(), point.getLon());
		}
		if (ne == null) {
			ne = new LatLon(point.getLat(), point.getLon());
		}

		double swLat = sw.getLat();
		double swLon = sw.getLon();
		double neLat = ne.getLat();
		double neLon = ne.getLon();
		boolean changed = false;

		if (swLat > point.getLat()) {
			swLat = point.getLat();
			changed = true;
		}
		if (swLon > point.getLon()) {
			swLon = point.getLon();
			changed = true;
		}
		if (neLat < point.getLat()) {
			neLat = point.getLat();
			changed = true;
		}
		if (neLon < point.getLon()) {
			neLon = point.getLon();
			changed = true;
		}
		if (changed) {
			sw = new LatLon(swLat, swLon);
			ne = new LatLon(neLat, neLon);
		}
	}

	/**
	 * @param waypoints
	 */
	public void add(List<LatLon> waypoints) {
		waypoints.stream().forEachOrdered(e -> addInternal(e));
	}

	private void addInternal(LatLon e) {
		add(e);
	}
}
