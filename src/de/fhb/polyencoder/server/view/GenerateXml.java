package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.Util;

public class GenerateXml {

  public static String getXml(HashMap<String, String> map) {
    String xml = getTemplate();
    xml = Util.replaceMarker(xml, map);
    return xml;
  }



  private static String getTemplate() {
    return Util.readFile("templates/xml/data.xml");
  }
}
