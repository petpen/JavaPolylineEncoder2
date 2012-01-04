package de.fhb.polyencoder.server;

import de.fhb.polyencoder.Util;

public class GenerateErrorMessage {

  public static String getAs(int status, String message) {
    return getAs(status, message, OutputType.RAW);
  }


  public static String getAs(int status, String message, OutputType out) {
    String html = getTemplate(out);
    html = Util.replaceMarker(html, "statusCode", String.valueOf(status));
    html = Util.replaceMarker(html, "statusMessage", message);
    return html;
  }

  
  private static String getTemplate(OutputType out) {
    String fileName = "";
    
    switch (out) {
      case HTML:
        fileName = "html/error.html";
        break;
      case JSON:
        fileName = "json/error.json";
        break;
      case XML:
        fileName = "xml/error.xml";
        break;
      default:
        fileName = "raw/error.raw";
    }

    return Util.readFile("templates/" + fileName);
  }
}