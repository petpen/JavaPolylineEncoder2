package de.fhb.polyencoder.view;

import java.util.HashMap;

import de.fhb.polyencoder.Util;

public class GenerateJson {

  public static String getJson(HashMap<String, String> map) {
    String json = getTemplate();
    json = Util.replaceMarker(json, map);
    return json;
  }



  private static String getTemplate() {
    return Util.readFile("templates/json/data.json");
  }
}
