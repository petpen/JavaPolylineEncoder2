package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.Util;

public class GenerateHtml {

  public static String getHtml(HashMap<String, String> map) {
    String html = getTemplate();
    html = Util.replaceMarker(html, map);
    return html;
  }



  private static String getTemplate() {
    return Util.readFile("templates/html/maps.html");
  }
}
