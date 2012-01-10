package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.server.OutputType;

/**
 * @author Martin Bormeister
 * 
 */
public class ViewRaw extends ViewGenerator {

  public ViewRaw(HashMap<String, String> map, OutputType format) {
    super(map, format);
  }
}
