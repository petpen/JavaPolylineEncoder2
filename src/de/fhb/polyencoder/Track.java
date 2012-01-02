package de.fhb.polyencoder;

import java.util.ArrayList;

/**
 * Holds a List of {@link Trackpoint}s.
 * 
 * Porting of Mark McClures Javascript PolylineEncoder
 * 
 * @author Mark Rambow (markrambow[at]gmail[dot]com)
 * @author Peter Pensold
 * @version 1
 */
public class Track {
  private ArrayList<Trackpoint> points;



  public Track() {
    points = new ArrayList<Trackpoint>();
  }



  public ArrayList<Trackpoint> getPoints() {
    return this.points;
  }



  public void setPoints(ArrayList<Trackpoint> points) {
    this.points = points;
  }



  public Trackpoint getPoint(int index) {
    return this.points.get(index);
  }



  public void addPoint(Trackpoint point) {
    this.points.add(point);
  }
}
