package de.fhb.polyencoder;

/**
 * Collection of useful functions.
 * 
 * @author Peter Pensold
 * @version 0.5
 * 
 */
public class Util {

  /**
   * Calculates the following phrase with a and b:<br/>
   * {@code Math.sqrt(Math.pow(a,2) + Math.pow(b,2))}
   * 
   * @param a
   * @param b
   * @return the result of this phrase
   */
  public static double sqrtOfSquared(double a, double b) {
    return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
  }



  public static int floor1e5(double coordinate) {
    // Flooring Result, seams not to round, so its done with math.round
    // should just cut the last digits, maybe with strings?
    // McClure takes floor to, but its different to Maps explanation...
    // further testing needed

    return (int) Math.floor(coordinate*1e5);
  }
}
