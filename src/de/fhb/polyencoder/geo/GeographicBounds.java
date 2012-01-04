package de.fhb.polyencoder.geo;

import java.util.ArrayList;

import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.Util;

/**
 * @author Peter Pensold
 * @version 0.5
 */
public class GeographicBounds {
  private double maxLat = 0.0;
  private double minLat = 0.0;
  private double maxLng = 0.0;
  private double minLng = 0.0;
  private double maxAlt = 0.0;
  private double minAlt = 0.0;
  
  private double centerLat = Double.NaN;
  private double centerLng = Double.NaN;
  private double centerAlt = Double.NaN;



  public GeographicBounds(Track trk) {
    this(trk.getPoints());
  }



  public GeographicBounds(ArrayList<GeographicLocation> points) {
    createBounds(points);
  }



  /**
   * This method creates the bounds to a list of {@link GeographicLocation}s.
   * This algorithm is very simple. It searches only for the minimum and maximum
   * of latitude, longitude and altitude value. Therefore this algorithm only
   * works correctly as long as the points do not pass the 180° meridian and/or
   * go completely round the earth.
   * 
   * @param points
   *          which are inside the bounds
   */
  public void createBounds(ArrayList<GeographicLocation> points) {
    double maxLat = 0, minLat = 0, maxLng = 0, minLng = 0, maxAlt = 0, minAlt = 0;
    double curPointLat, curPointLng, curPointAlt;
    GeographicLocation point;
    
    for (int i = 0; i < points.size(); i++) {
      point = points.get(i);
      curPointLat = point.lat();
      curPointLng = point.lng();
      curPointAlt = point.alt();

      if (i > 0) {
        minLat = Math.min(curPointLat, minLat);
        maxLat = Math.max(curPointLat, maxLat);
        
        minLng = Math.min(curPointLng, minLng);
        maxLng = Math.max(curPointLng, maxLng);
        
        minAlt = Math.min(curPointAlt, minAlt);
        maxAlt = Math.max(curPointAlt, maxAlt);
      } else {
        maxLat = minLat = curPointLat;
        maxLng = minLng = curPointLng;
        maxAlt = minAlt = curPointAlt;
      }
    }
    
    this.maxLat = maxLat;
    this.minLat = minLat;
    this.maxLng = maxLng;
    this.minLng = minLng;
    this.maxAlt = maxAlt;
    this.minAlt = minAlt;
  }



  private void createCenter() {
    this.centerLat = Util.createCenter(minLat, maxLat);
    this.centerLng = Util.createCenter(minLng, maxLng);
    this.centerAlt = Util.createCenter(minAlt, maxAlt);
  }



  public double getMaxLat() {
    return maxLat;
  }



  public double getMinLat() {
    return minLat;
  }



  public double getMaxLng() {
    return maxLng;
  }



  public double getMinLng() {
    return minLng;
  }



  public double getMaxAlt() {
    return maxAlt;
  }



  public double getMinAlt() {
    return minAlt;
  }



  public double getCenterLat() {
    if (Double.isNaN(centerLat)) {
      createCenter();
    }

    return centerLat;
  }



  public double getCenterLng() {
    if (Double.isNaN(centerLng)) {
      createCenter();
    }

    return centerLng;
  }



  public double getCenterAlt() {
    if (Double.isNaN(centerAlt)) {
      createCenter();
    }

    return centerAlt;
  }
}
