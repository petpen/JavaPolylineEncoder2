package de.fhb.polyencoder;

import java.util.ArrayList;

/**
 * Holds a List of {@link GeographicLocation}s.
 * 
 * Porting of Mark McClures Javascript PolylineEncoder
 * 
 * @author Mark Rambow (markrambow[at]gmail[dot]com)
 * @author Peter Pensold
 * @version 1
 */
public class Track {
  private ArrayList<GeographicLocation> points;



  public Track() {
    points = new ArrayList<GeographicLocation>();
  }



  public ArrayList<GeographicLocation> getPoints() {
    return this.points;
  }



  public void setPoints(ArrayList<GeographicLocation> points) {
    this.points = points;
  }



  public GeographicLocation getPoint(int index) {
    return this.points.get(index);
  }



  public void addPoint(GeographicLocation point) {
    this.points.add(point);
  }
}
