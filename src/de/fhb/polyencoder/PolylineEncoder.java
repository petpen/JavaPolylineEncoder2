package de.fhb.polyencoder;

/**
 * Porting of Mark McClures Javascript PolylineEncoder
 * All the mathematical logic is more or less copied from McClure
 *  
 * @author Mark Rambow (markrambow[at]gmail[dot]com)
 * @author Peter Pensold
 * @version 0.5
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class PolylineEncoder {
  private boolean forceEndpoints = true;
  private int numLevels = 18;
  private int zoomFactor = 2;
  private double verySmall = 0.00001;
  private double[] zoomLevelBreaks;

  private HashMap<String, Double> bounds;



  /**
   * Creates a PolylinEncoder with the following default values:
   * <ul>
   * <li>numLevels = 18</li>
   * <li>zoomFactor = 2</li>
   * <li>verySmall = 0.00001</li>
   * <li>forceEndpoints = true</li>
   * </ul>
   * 
   * @see #PolylineEncoder(int, int, double, boolean)
   */
  public PolylineEncoder() {
    createZoomLevelBreaks();
  }



  /**
   * 
   * @param numLevels
   *          indicates how many different levels of magnification the polyline
   *          has
   * @param zoomFactor
   *          the change in magnification between numLevels
   * @param verySmall
   *          indicates the length of a barely visible object at the highest
   *          zoom level
   * @param forceEndpoints
   *          indicates whether or not the endpoints should be visible at all
   *          zoom levels
   */
  public PolylineEncoder(int numLevels, int zoomFactor, double verySmall, boolean forceEndpoints) {
    this.numLevels = numLevels;
    this.zoomFactor = zoomFactor;
    this.verySmall = verySmall;
    this.forceEndpoints = forceEndpoints;

    createZoomLevelBreaks();
  }



  private void createZoomLevelBreaks() {
    this.zoomLevelBreaks = new double[numLevels];

    for (int i = 0; i < numLevels; i++) {
      this.zoomLevelBreaks[i] = verySmall*Math.pow(this.zoomFactor, numLevels - i - 1);
    }
  }



  /**
   * This computes the appropriate zoom level of a point in terms of it's
   * distance from the relevant segment in the DP algorithm. Could be done in
   * terms of a logarithm, but this approach makes it a bit easier to ensure
   * that the level is not too large.
   */
  public int computeLevel(double absMaxDist) {
    int lev = 0;
  
    if (absMaxDist > this.verySmall) {
      lev = 0;
  
      while (absMaxDist < this.zoomLevelBreaks[lev]) {
        lev++;
      }
    }
  
    return lev;
  }



  public String createEncodings(ArrayList<Trackpoint> points, double[] dists) {
    Double curPointLat, curPointLng;
    StringBuffer encodedPoints = new StringBuffer();
  
    int pLat = 0, pLng = 0;
    double maxLat = 0, minLat = 0, maxLng = 0, minLng = 0;
  
    for (int i = 0; i < points.size(); i++) {
      curPointLat = points.get(i).lat();
      curPointLng = points.get(i).lng();
  
      // determine bounds (max/min lat/lon)
      if (i == 0) {
        maxLat = minLat = curPointLat;
        maxLng = minLng = curPointLng;
      } else {
        if (curPointLat > maxLat) {
          maxLat = curPointLat;
        } else if (curPointLat < minLat) {
          minLat = curPointLat;
        } else if (curPointLng > maxLng) {
          maxLng = curPointLng;
        } else if (curPointLng < minLng) {
          minLng = curPointLng;
        }
      }
  
      if (dists[i] != 0 || i == 0 || i == points.size() - 1) {
  
        int late5 = Util.floor1e5(curPointLat);
        int lnge5 = Util.floor1e5(curPointLng);
  
        int dlat = late5 - pLat;
        int dlng = lnge5 - pLng;
  
        pLat = late5;
        pLng = lnge5;
  
        encodedPoints.append(encodeSignedNumber(dlat));
        encodedPoints.append(encodeSignedNumber(dlng));
      }
    }
  
    HashMap<String, Double> bounds = new HashMap<String, Double>();
    bounds.put("maxlat", new Double(maxLat));
    bounds.put("minlat", new Double(minLat));
    bounds.put("maxlon", new Double(maxLng));
    bounds.put("minlon", new Double(minLng));
  
    this.setBounds(bounds);
  
    return encodedPoints.toString();
  }



  /**
   * Ramer-Douglas-Peucker algorithm, adapted for encoding. This algorithm is
   * used to reduce the number of points in a curve.
   * 
   * @return HashMap [encodedPoints; encodedPointsLiteral; encodedLevels]
   * @see <a
   *      href="http://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm">Ramer�Douglas�Peucker algorithm (Wikipedia)</a>
   * 
   */
  public HashMap<String, String> dpEncode(Track track) {
    ArrayList<Trackpoint> points = track.getPoints();
    Stack<int[]> stack = new Stack<int[]>();
    String encodedPoints, encodedLevels;
    Trackpoint segStart, segEnd;

    int maxLoc = 0;
    int segStackStart, segStackEnd;
    int[] current;
    double maxDist, absMaxDist = 0.0, temp = 0.0;
    double segmentLength = 0.0;
    double[] dists = new double[points.size()];

    if (points.size() > 2) {
      int[] stackVal = new int[] { 0, (points.size() - 1) };
      stack.push(stackVal);

      while (stack.size() > 0) {
        current = stack.pop();
        segStackStart = current[0];
        segStackEnd = current[1];
        segStart = points.get(segStackStart);
        segEnd = points.get(segStackEnd);

        maxDist = 0;

        segmentLength = Math.pow(segEnd.lat() - segStart.lat(), 2) + Math.pow(segEnd.lng() - segStart.lng(), 2);

        for (int segLoc = segStackStart + 1; segLoc < segStackEnd; segLoc++) {
          temp = distance(points.get(segLoc), segStart, segEnd, segmentLength);

          if (temp > maxDist) {
            maxDist = temp;
            maxLoc = segLoc;

            if (maxDist > absMaxDist) {
              absMaxDist = maxDist;
            }
          }
        }

        if (maxDist > this.verySmall) {
          dists[maxLoc] = maxDist;

          int[] stackValCurMax = { segStackStart, maxLoc };
          int[] stackValMaxCur = { maxLoc, segStackEnd };

          stack.push(stackValCurMax);
          stack.push(stackValMaxCur);
        }
      }
    }

    encodedPoints = createEncodings(points, dists);
    String encodedPointsLiteral = encodeDoubleBackslash(encodedPoints);

    encodedLevels = encodeLevels(points, dists, absMaxDist);

    HashMap<String, String> hm = new HashMap<String, String>();
    hm.put("encodedPoints", encodedPoints);
    hm.put("encodedPointsLiteral", encodedPointsLiteral);
    hm.put("encodedLevels", encodedLevels);
    return hm;
  }



  /**
   * Now we can use the previous function to march down the list of points and
   * encode the levels. Like createEncodings, we ignore points whose distance
   * (in dists) is undefined.
   */
  public String encodeLevels(ArrayList<Trackpoint> points, double[] dists, double absMaxDist) {
    String edge;
    StringBuffer encoded_levels = new StringBuffer();

    if (this.forceEndpoints) {
      edge = encodeNumber(this.numLevels - 1);
    } else {
      edge = encodeNumber(this.numLevels - computeLevel(absMaxDist) - 1);
    }

    encoded_levels.append(edge);

    for (int i = 1; i < points.size() - 1; i++) {
      if (dists[i] != 0) {
        encoded_levels.append(encodeNumber(this.numLevels - computeLevel(dists[i]) - 1));
      }
    }

    encoded_levels.append(edge);

    return encoded_levels.toString();
  }



  public HashMap<String, Double> getBounds() {
    return bounds;
  }



  public void setBounds(HashMap<String, Double> bounds) {
    this.bounds = bounds;
  }



  /**
   * Computes the distance between the point pt and the segment
   * [seqStart,seqEnd]. This could probably be replaced with something that is a
   * bit more numerically stable.
   * 
   * @param pt
   * @param segStart
   * @param segEnd
   * @return
   */
  public static double distance(Trackpoint pt, Trackpoint segStart, Trackpoint segEnd, double segLength) {
    double u, out = 0.0;
    double ptLat = pt.lat();
    double ptLon = pt.lng();
    double segStartLat = segStart.lat();
    double segStartLon = segStart.lng();
    double segEndLat = segEnd.lat();
    double segEndLon = segEnd.lng();
  
    if (segStart.equals(segEnd)) {
      out = Util.sqrtOfSquared((segEndLat - ptLat), (segEndLon - ptLon));
    } else {
      u = ((ptLat - segStartLat)*(segEndLat - segStartLat) + (ptLon - segStartLon)*(segEndLon - segStartLon))/segLength;
  
      if (u <= 0) {
        out = Util.sqrtOfSquared((ptLat - segStartLat), (ptLon - segStartLon));
      }
  
      if (0 < u && u < 1) {
        out = Util.sqrtOfSquared((ptLat - segStartLat - u*(segEndLat - segStartLat)), (ptLon - segStartLon - u*(segEndLon - segStartLon)));
      }
  
      if (1 <= u) {
        out = Util.sqrtOfSquared((ptLat - segEndLat), (ptLon - segEndLon));
      }
    }
  
    return out;
  }



  /**
   * Replaces all double backslashes inside a String. It uses a regular
   * expression.
   * 
   * @param s
   *          String that may have double backslashes
   * 
   * @return the String with all double backslashes replaced
   * 
   * @see <a href="http://facstaff.unca.edu/mcmcclur/GoogleMaps/EncodePolyline/pitfalls.html">Potential encoding pitfalls</a>
   */
  public static String encodeDoubleBackslash(String s) {
    return s.replaceAll("\\\\", "\\\\\\\\");
  }



  public static String encodeNumber(int num) {
    int unitSeparator = 0x1f;
    int whitespace = 0x20;
  
    StringBuffer encodeString = new StringBuffer();
  
    while (num >= whitespace) {
      int nextValue = (whitespace | (num & unitSeparator)) + 63;
      encodeString.append((char) (nextValue));
      num >>= 5;
    }
  
    num += 63;
    encodeString.append((char) (num));
  
    return encodeString.toString();
  }



  public static String encodeSignedNumber(int num) {
    int signed = num << 1;
  
    if (num < 0) {
      signed = ~(signed);
    }
  
    return (encodeNumber(signed));
  }



  /**
   * Parses a String containing points in a form as described in param points.
   * Altitude will be ignored here so far.
   * 
   * @param points
   *          set the points that should be encoded all points have to be in the
   *          following form: {@code Longitude,Latitude,Altitude'_' ...}
   * 
   * @return the parsed track
   * 
   * @see #parseStringToTrack(String, TrackSeparator, PointArrayPositions)
   */
  public static Track kmlLineStringToTrack(String points) {
    TrackSeparator sep = new TrackSeparator(" ", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0);
  
    return parseStringToTrack(points, sep, pos);
  }



  /**
   * Parses a String containing points in a form as described in param points.
   * Google can't show Altitude, but its in some GPS/GPX Files.
   * 
   * @param points
   *          set the points that should be encoded all points have to be in the
   *          following form: {@code Longitude,Latitude,Altitude\n}
   * 
   * @return the parsed track
   * 
   * @see #parseStringToTrack(String, TrackSeparator, PointArrayPositions)
   */
  public static Track pointsAndAltitudeToTrack(String points) {
    System.out.println("pointsAndAltitudeToTrack");
    TrackSeparator sep = new TrackSeparator("\n", ",");
    PointArrayPositions pos = new PointArrayPositions(1, 0, 2);
  
    return parseStringToTrack(points, sep, pos);
  }



  /**
   * Parses a String containing points in a form as described in param points.
   * 
   * @param points
   *          set the points that should be encoded all points have to be in the
   *          following form: {@code Latitude, Longitude\n}
   * 
   * @return the parsed track
   * 
   * @see #parseStringToTrack(String, TrackSeparator, PointArrayPositions)
   */
  public static Track pointsToTrack(String points) {
    TrackSeparator sep = new TrackSeparator("\n", ", ");
    PointArrayPositions pos = new PointArrayPositions(0, 1);
  
    return parseStringToTrack(points, sep, pos);
  }



  /**
   * Parses a String to a Track. If one point is defined by two coordinates the
   * altitude will be set to 0.0
   * 
   * @param points
   * @param separator
   * @param positions
   * 
   * @return the parsed track
   * 
   * @see TrackSeparator
   * @see PointArrayPositions
   */
  public static Track parseStringToTrack(String points, TrackSeparator separator, PointArrayPositions positions) {
    Track trk = new Track();
    StringTokenizer st = new StringTokenizer(points, separator.forLines());
  
    while (st.hasMoreTokens()) {
      String[] pointStrings = st.nextToken().split(separator.forPoints());
  
      switch (pointStrings.length) {
        case 3:
          trk.addPoint(new Trackpoint(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), new Double(pointStrings[positions.alt()])));
          break;
  
        case 2:
          trk.addPoint(new Trackpoint(new Double(pointStrings[positions.lat()]), new Double(pointStrings[positions.lng()]), 0.0));
          break;
      }
    }
  
    return trk;
  }



  public static HashMap<String, String> createEncodings(Track track, int level, int step) {
    StringBuffer encodedPoints = new StringBuffer();
    StringBuffer encodedLevels = new StringBuffer();

    ArrayList<Trackpoint> trackpointList = track.getPoints();

    int plat = 0;
    int plng = 0;
    int counter = 0;

    int listSize = trackpointList.size();

    Trackpoint trackpoint;

    for (int i = 0; i < listSize; i += step) {
      counter++;
      trackpoint = trackpointList.get(i);

      int late5 = Util.floor1e5(trackpoint.getLatitude());
      int lnge5 = Util.floor1e5(trackpoint.getLongitude());

      int dlat = late5 - plat;
      int dlng = lnge5 - plng;

      plat = late5;
      plng = lnge5;

      encodedPoints.append(encodeSignedNumber(dlat)).append(encodeSignedNumber(dlng));
      encodedLevels.append(encodeNumber(level));
    }

    System.out.println("listSize: " + listSize + " step: " + step + " counter: " + counter);

    HashMap<String, String> resultMap = new HashMap<String, String>();
    resultMap.put("encodedPoints", encodedPoints.toString());
    resultMap.put("encodedPointsLiteral", encodeDoubleBackslash(encodedPoints.toString()));
    resultMap.put("encodedLevels", encodedLevels.toString());

    return resultMap;
  }
}
