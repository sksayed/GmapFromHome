
package com.infoworks.lab.gmap;

import java.util.List;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonNumber;
import elemental.json.JsonObject;

/**
 * Convert objects to JSON.
 */
public final class JsonConverter {

	private JsonConverter() {
		// Empty.
	}

	public static JsonObject createLatLon(double lat, double lon) {
		JsonObject obj = Json.createObject();
		obj.put("lat", lat); //$NON-NLS-1$
		obj.put("lng", lon); //$NON-NLS-1$
		return obj;
	}

	public static JsonArray createPath(List<LatLon> path) {
		JsonArray array = Json.createArray();
		int count = 0;
		for (LatLon loc : path) {
			JsonObject latLon = createLatLon(loc.getLat(), loc.getLon());
			array.set(count++, latLon);
		}
		return array;
	}

    public static JsonNumber insertZoomLevel ( int zoomLevel) {
	    JsonNumber obj = Json.create(zoomLevel);
	    return obj ;
    }

	/**
	 * @return
	 */
	public static JsonObject createPolyLine(String id) {
		JsonObject poly = Json.createObject();
		poly.put("geodesic", true); //$NON-NLS-1$
		poly.put("strokeColor", "#FF0000"); //$NON-NLS-1$ //$NON-NLS-2$
		poly.put("strokeOpacity", 1.0); //$NON-NLS-1$
		poly.put("strokeWeight", 2); //$NON-NLS-1$
		poly.put("id", id); //$NON-NLS-1$
		return poly;
	}

	/**
	 * @return
	 */
	public static JsonObject createPolyLine(String id, List<LatLon> wayPoints) {
		JsonObject poly = createPolyLine(id);
		JsonArray path = createPath(wayPoints);
		poly.put("path", path); //$NON-NLS-1$
		return poly;
	}

	/**
	 * @param d
	 * @param e
	 * @return
	 */
	public static JsonObject createLatLon(LatLon latLon) {
		JsonObject obj = Json.createObject();
		obj.put("lat", latLon.getLat()); //$NON-NLS-1$
		obj.put("lng", latLon.getLon()); //$NON-NLS-1$
		return obj;
	}

	public static JsonObject createMarker(String id, String label, double lat, double lon) {
		JsonObject obj = Json.createObject();
		obj.put("longitude", lon); //$NON-NLS-1$
		obj.put("latitude", lat); //$NON-NLS-1$
		obj.put("id", id); //$NON-NLS-1$
		obj.put("label", label); //$NON-NLS-1$
		return obj;
	}

	public static JsonObject createBounds(MapBounds mapBounds) {
		if (mapBounds != null && mapBounds.isInitialized()) {
			JsonObject obj = Json.createObject();
			JsonObject sw = createLatLon(mapBounds.getSw());
			JsonObject ne = createLatLon(mapBounds.getNe());
			obj.put("sw", sw); //$NON-NLS-1$
			obj.put("ne", ne); //$NON-NLS-1$
			return obj;
		}
		return null;
	}
}
