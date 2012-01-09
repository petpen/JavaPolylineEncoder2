package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.server.OutputType;

public class ViewFactory {
  public static ViewGenerator buildViewGenerator(HashMap<String, String> map, OutputType format) {
    switch (format) {
      case HTML:
        return buildViewHtml(map, format);
      case XML:
        return buildViewXml(map, format);
      case JSON:
        return buildViewJson(map, format);
      case RAW:
        return buildViewRaw(map, format);
      default:
        return null;
    }
  }



  public static ViewGenerator buildViewHtml(HashMap<String, String> map, OutputType format) {
    return new ViewHtml(map, format);
  }



  public static ViewGenerator buildViewXml(HashMap<String, String> map, OutputType format) {
    return new ViewXml(map, format);
  }



  public static ViewGenerator buildViewJson(HashMap<String, String> map, OutputType format) {
    return new ViewJson(map, format);
  }



  public static ViewGenerator buildViewRaw(HashMap<String, String> map, OutputType format) {
    return new ViewRaw(map, format);
  }
}
