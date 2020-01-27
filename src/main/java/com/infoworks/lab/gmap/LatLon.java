
package com.infoworks.lab.gmap;

/**
 * Lat/lon helper.
 */
public class LatLon {

	private double lat = Double.NaN;

	private double lon = Double.NaN;

//	public LatLon() {
//		// Empty.
//	}

	public LatLon(double latIn, double lonIn) {
		lat = latIn;
		lon = lonIn;
	}

	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @return the lon
	 */
	public double getLon() {
		return lon;
	}

	public boolean isInitialized() {
		return lat != Double.NaN && lon != Double.NaN;
	}

	/**
	 * @param lon
	 * @param lat
	 * @return LatLon
	 */
	public static LatLon fromLonLat(double lon, double lat) {
		return new LatLon(lat, lon);
	}
}
