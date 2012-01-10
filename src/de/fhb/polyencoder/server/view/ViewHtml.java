package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.server.OutputType;

/**
 * @author Martin Bormeister
 * 
 */
public class ViewHtml extends ViewGenerator {

  public ViewHtml(HashMap<String, String> map, OutputType format) {
    super(map, format);
  }
}
