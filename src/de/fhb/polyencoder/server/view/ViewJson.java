package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.server.OutputType;

/**
 * @author Martin Bormeister
 * 
 */
public class ViewJson extends ViewGenerator {

  public ViewJson(HashMap<String, String> map, OutputType format) {
    super(map, format);
  }



  @Override
  public String getView() {
    if (tracks.length() > 0) {
      tracks.deleteCharAt(tracks.length() - 1);
    }
    return super.getView();
  }
}
