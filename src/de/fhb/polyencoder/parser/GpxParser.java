package de.fhb.polyencoder.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.geo.GeographicLocation;

/**
 * @author Peter Pensold
 * @version 1
 */
public class GpxParser extends AbstractStringToTrackParser implements StringToTrackParser {
  
  protected static String TAG_ROOT = "gpx";
  
  protected static String TAG_TRACK = "trk";
  protected static String TAG_TRACKPOINT = "trkpt";
  
  protected static String TAG_ROUTE = "rte";  
  protected static String TAG_ROUTEPOINT = "rtept";

  protected static String TAG_WAYPOINT = "wpt";
  
  protected static String ATTRIBUTE_LAT = "lat";
  protected static String ATTRIBUTE_LNG = "lon";
  protected static String TAG_ALT = "ele";



  public void parse(String data) {
    Document dom = Util.parseXMLToDocument(data);

    if(dom != null) {
      findAndAddTracks(dom);
      findAndAddRoutes(dom);
      findAndAddWaypoints(dom);
    }
  }



  private void findAndAddTracks(Document dom) {
    findAndAddAllTracks(dom, TAG_TRACK, TAG_TRACKPOINT);
  }



  private void findAndAddRoutes(Document dom) {
    findAndAddAllTracks(dom, TAG_ROUTE, TAG_ROUTEPOINT);
  }



  private void findAndAddWaypoints(Document dom) {
    findAndAddAllTracks(dom, TAG_ROOT, TAG_WAYPOINT);
  }



  /**
   * Finds all tracks beginning with tagPath and having a point which is
   * described by tagPoint. If the found track may be empty it will not be
   * added. If a track contains segments, they will be ignored and only the
   * points will be added to the track.
   * 
   * @param dom
   *          Document that should containt the tags tagPath and tagPoint
   * @param tagPath
   *          tag marking a path (e.g. rte, trk)
   * @param tagPoint
   *          tag marking a point
   */
  private void findAndAddAllTracks(Document dom, String tagPath, String tagPoint) {
    NodeList trackElements = dom.getElementsByTagName(tagPath);
  
    for (int i = 0; i < trackElements.getLength(); i++) {
      Track trk = parseTrackElement((Element) trackElements.item(i), tagPoint);

      if (trk.getPoints().size() > 0)
        tracks.add(trk);
    }
  }



  private Track parseTrackElement(Element pointElements, String tag) {
    Track trk = new Track();

    NodeList pointList = pointElements.getElementsByTagName(tag);

    for (int i = 0; i < pointList.getLength(); i++) {
      try {
        trk.addPoint(parseToLocation((Element) pointList.item(i)));
      } catch (NumberFormatException ex) {
        System.out.println("Location is invalid. This point will be ignored.");
      }
    }

    return trk;
  }



  private GeographicLocation parseToLocation(Element element) throws NumberFormatException {
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
