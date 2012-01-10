package de.fhb.polyencoder.server.view;

import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.server.OutputType;

/**
 * @author Martin Bormeister
 * 
 */
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
    if (out == OutputType.NOSUPPORT) {
      out = OutputType.RAW;
    }

    String fileName = String.format("templates/%1$s/error.%1$s", out.toString().toLowerCase());

    return Util.readFile(fileName);
  }
}