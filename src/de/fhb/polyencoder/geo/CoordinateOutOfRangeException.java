package de.fhb.polyencoder.geo;

/**
 * Will be thrown if a geographic coordinate is out of its range.
 * 
 * @author Peter Pensold
 * 
 */
public class CoordinateOutOfRangeException extends Exception {
  private static final long serialVersionUID = -5715456466362141682L;



  public CoordinateOutOfRangeException() {
    super();
  }



  public CoordinateOutOfRangeException(String string) {
    super(string);
  }
}
