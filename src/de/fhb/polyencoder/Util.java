package de.fhb.polyencoder;

/**
 * Collection of useful functions.
 * 
 * @author Mark Rambow (markrambow[at]gmail[dot]com)
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
}
