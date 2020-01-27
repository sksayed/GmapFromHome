package com.infoworks.lab.gmap;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("map-page")
@HtmlImport("html/good-map.html")
public class GoogleMap extends PolymerTemplate<TemplateModel> {

	private static final long serialVersionUID = 1L;

	@Id("good-map")
	private Div googleMap;

}