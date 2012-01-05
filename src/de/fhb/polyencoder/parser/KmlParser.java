package de.fhb.polyencoder.parser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import de.fhb.polyencoder.PointArrayPositions;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.TrackSeparator;
import de.fhb.polyencoder.Util;

public class KmlParser extends AbstractStringToTrackParser implements StringToTrackParser {

  protected static String TAG_DATA = "coordinates";



  /**
   * Parses a String containing an KML file content. The points must be stored
   * inside tags named {@code coordinates}. All points have to be in the
   * following form: {@code Longitude,Latitude,Altitude'_' ...}<br/>
   * If altitude is missing it will be replaced with a value as described in
   * {@link AbstractStringToTrackParser#parseStringToTrack(String, TrackSeparator, PointArrayPositions)}
   * 
   * @param data
   * 
   * @see AbstractStringToTrackParser#parseStringToTrack(String, TrackSeparator,
   *      PointArrayPositions)
   */
  public void parse(String data) {
    Document dom = Util.parseXMLToDocument(data);

    if (dom != null) {
      findAndAddTracks(dom);
    }
  }



  private void findAndAddTracks(Document dom) {
    Track trk = null;
    TrackSeparator sep = new TrackSeparator(" ", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0, 2);

    NodeList trackElements = dom.getElementsByTagName(TAG_DATA);
    for (int i = 0; i < trackElements.getLength(); i++) {
      trk = parseStringToTrack(trackElements.item(i).getTextContent(), sep, pos);

      if (trk.getPoints().size() > 0)
        tracks.add(trk);
    }
  }
}
