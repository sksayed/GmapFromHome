
package com.gmail.goran.spring;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;

import elemental.json.JsonObject;

/**
 * Google map component.
 */
@Tag("google-map-component")
@HtmlImport("html/googlemap.html")
public class GoogleMapComponent extends PolymerTemplate<GoogleMapTemplateModel> {

	private static final long serialVersionUID = 1L;

	@Id("googlemapid")
	private Div googleMap;

	public GoogleMapComponent() {
		// Empty.
	}

	public void setApiKey(String apiKey) {
		getModel().setApiKey(apiKey);
	}

	public void setClientId(String clientId) {
		getModel().setClientId(clientId);
	}

	public void addPolyLine(JsonObject polyLine) {
		getElement().callFunction("addPolyLine", polyLine); //$NON-NLS-1$
	}

	public void addMarker(JsonObject marker) {
		getElement().callFunction("addMarker", marker); //$NON-NLS-1$
	}

	public void zoomToBounds(JsonObject bounds) {
		getElement().callFunction("zoomToBounds", bounds); //$NON-NLS-1$
	}

    public void setZoomLevel ( int zoomLevel) {
	    getElement().callFunction("internalSetZoomLevel" , JsonConverter.insertZoomLevel(zoomLevel) );
    }

    public void removeAll () {
	    getElement().callFunction("removeAll"  );
    }

}