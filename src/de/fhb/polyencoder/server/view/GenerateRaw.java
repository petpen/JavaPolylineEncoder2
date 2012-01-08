package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.Util;

public class GenerateRaw {

  public static String getRaw(HashMap<String, String> map) {
    String raw = getTemplate();
    raw = Util.replaceMarker(raw, map);
    return raw;
  }



  private static String getTemplate() {
    return Util.readFile("templates/raw/data.raw");
  }
}
