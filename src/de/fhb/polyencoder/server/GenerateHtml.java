package de.fhb.polyencoder.server;

import java.util.HashMap;

public class GenerateHtml {

  public static String getHtml(HashMap<String, String> map) {

    return String.format(getTemplate(), map.get("encodedPoints"), map.get("encodedLevels"));
  }



  private static String getTemplate() {
    return "<!DOCTYPE html>"+
"<html lang=\"en\">"+
  "<head>"+
    "<meta charset=\"utf-8\" />"+
    "<title>JavaPolylineEncoder2</title>"+
    "<script src=\"http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAzr2EBOXUKnm_jVnk0OJI7xSosDVG8KKPE1-m51RBrvYughuyMxQ-i1QfUnH94QxWIa6N4U6MouMmBA\""+
            "type=\"text/javascript\"></script>"+
    "<script type=\"text/javascript\">"+

    "function initialize() {"+
      "if (GBrowserIsCompatible()) {"+
        "var map = new GMap2(document.getElementById(\"map_canvas\"));"+
        "map.setCenter(new GLatLng(37.4419, -122.1419), 15);"+
        "map.setUIToDefault();"+
        "map.setMapType(G_NORMAL_MAP);"+

        // Add an encoded polyline.
        "var encodedPoints = \"%s\";"+
        "var encodedLevels = \"%s\";"+
 
        "var encodedPolyline = new GPolyline.fromEncoded({"+
    "color: \"#3333cc\","+
    "weight: 10,"+
    "points: encodedPoints,"+
    "levels: encodedLevels,"+
    "zoomFactor: 32,"+
    "numLevels: 4"+
  "});"+
  "map.addOverlay(encodedPolyline);"+
      "}"+
    "}"+

    "</script>"+
  "</head>"+

  "<body onload=\"initialize()\" onunload=\"GUnload()\">"+
    "<div id=\"map_canvas\" style=\"width: 500px; height: 300px\"></div>"+
  "</body>"+
"</html>";
  }
}
