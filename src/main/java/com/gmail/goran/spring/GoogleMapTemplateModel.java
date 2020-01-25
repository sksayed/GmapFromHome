
package com.gmail.goran.spring;

import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Google map component template model.
 */
public interface GoogleMapTemplateModel extends TemplateModel {

	void setClientId(String clientId);

	void setApiKey(String apiKey);
}
