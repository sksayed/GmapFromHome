package com.infoworks.lab.app;

import com.infoworks.lab.AbstractDashBoard.SujonDashBoard;
import com.vaadin.flow.component.Component;

public abstract class ExtendedDiv extends SujonDashBoard {

   void addToMain (Component... components){
      this.mainContent.add(components);
  }
}
