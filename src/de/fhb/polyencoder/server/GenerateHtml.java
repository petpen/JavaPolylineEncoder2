package de.fhb.polyencoder.server;

import java.util.HashMap;

import de.fhb.polyencoder.Util;

public class GenerateHtml {

  public static String getHtml(HashMap<String, String> map) {
    String html = getTemplate();
    html = html.replaceAll("\\{encPoints\\}", map.get("encodedPoints"));
    html = html.replaceAll("\\{encLevels\\}", map.get("encodedLevels"));
    return html;
  }



  private static String getTemplate() {
    return Util.readFile("output/maps.html");
  }
}
