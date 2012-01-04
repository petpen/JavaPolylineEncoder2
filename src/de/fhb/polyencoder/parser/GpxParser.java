package de.fhb.polyencoder.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.geo.GeographicLocation;

public class GpxParser extends AbstractStringToTrackParser implements StringToTrackParser {
  
  private static String TAG_ROOT = "gpx";
  
  private static String TAG_TRACK = "trk";
  private static String TAG_TRACKPOINT = "trkpt";
  
  private static String TAG_ROUTE = "rte";  
  private static String TAG_ROUTEPOINT = "rtept";

  private static String TAG_WAYPOINT = "wpt";
  
  private static String ATTRIBUTE_LAT = "lat";
  private static String ATTRIBUTE_LNG = "lon";
  private static String TAG_ALT = "ele";



  public void parse(String data) {
    Document dom = Util.parseXMLToDocument(data);

    addTracksToTracks(dom);
    addRoutesToTracks(dom);
    addWaypointsToTracks(dom);
  }



  private void addTracksToTracks(Document dom) {
    addPathsToTracks(dom, TAG_TRACK, TAG_TRACKPOINT);
  }



  private void addRoutesToTracks(Document dom) {
    addPathsToTracks(dom, TAG_ROUTE, TAG_ROUTEPOINT);
  }



  private void addWaypointsToTracks(Document dom) {
    addPathsToTracks(dom, TAG_ROOT, TAG_WAYPOINT);
  }



  private void addPathsToTracks(Document dom, String tagPath, String tagPoint) {
    NodeList trackElements = dom.getElementsByTagName(tagPath);
  
    for (int i = 0; i < trackElements.getLength(); i++) {
      tracks.add(parseTrackElement((Element) trackElements.item(i), tagPoint));
    }
  }



  private Track parseTrackElement(Element pointElements, String tag) {
    Track trk = new Track();

    NodeList pointList = pointElements.getElementsByTagName(tag);

    for (int i = 0; i < pointList.getLength(); i++) {
      trk.addPoint(parseToLocation((Element) pointList.item(i)));
    }

    return trk;
  }



  private GeographicLocation parseToLocation(Element element) {
    double latitude = Double.parseDouble(element.getAttribute(ATTRIBUTE_LAT));
    double longitude = Double.parseDouble(element.getAttribute(ATTRIBUTE_LNG));
    double altitude = 0.0;

    if (hasElementTag(element, TAG_ALT)) {
      NodeList tags = element.getElementsByTagName(TAG_ALT);
      altitude = Double.parseDouble(tags.item(0).getTextContent());
    }

    return new GeographicLocation(latitude, longitude, altitude);
  }



  private boolean hasElementTag(Element element, String tagName) {
    NodeList tags = element.getElementsByTagName(tagName);

    return (tags.getLength() == 1);
  }
}
