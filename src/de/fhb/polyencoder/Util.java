package de.fhb.polyencoder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Collection of useful functions.
 * 
 * @author Mark Rambow (markrambow[at]gmail[dot]com)
 * @author Peter Pensold
 * @version 0.5
 * 
 */
public class Util {

  private final static int BUFFER = 2048;



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



  public static double createCenter(double min, double max) {
    return min + (max - min)/2;
  }



  public static String readFile(String fileName) {
    String loadingFailedOutput = "";
    FileReader fr;
    StringBuilder sb = new StringBuilder();
    String line;

    try {
      fr = new FileReader(fileName);
      BufferedReader br = new BufferedReader(fr);

      while ((line = br.readLine()) != null) {
        sb.append(line + "\n");
      }
      sb.deleteCharAt(sb.length()-1);

      fr.close();
    } catch (FileNotFoundException e) {
      sb = new StringBuilder(loadingFailedOutput);
    } catch (IOException e) {
      sb = new StringBuilder(loadingFailedOutput);
    } catch (StringIndexOutOfBoundsException e) {
      sb = new StringBuilder(loadingFailedOutput);
    }

    return sb.toString();
  }



  /**
   * Deletes a file if it exists, is writable and is not a directory.
   * 
   * @param fileName
   * @return if deletion succeeded or not.
   * @see File#delete()
   */
  public static boolean deleteFile(String fileName) {
    boolean deletionSucceeded = false;

    File file = new File(fileName);

    if (file.exists() && file.canWrite() && file.isDirectory() == false) {
      deletionSucceeded = file.delete();
    }

    return deletionSucceeded;
  }



  /**
   * Writes a InputStream to a file.
   * 
   * @param is
   *          InputStream containing the data to write to {@code fileName}
   * @param fileName
   *          Name of the new file
   * @throws IOException
   */
  public static void writeInputStreamToFile(InputStream is, String fileName) throws Exception {
    int count;
    byte data[] = new byte[BUFFER];

    FileOutputStream fos = new FileOutputStream(fileName);
    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

    while ((count = is.read(data, 0, BUFFER)) != -1) {
      dest.write(data, 0, count);
    }

    dest.flush();
    dest.close();
  }



  /**
   * Replaces all possible appearances of marker inside a text. The marker is a
   * regular expression. The marker will be surrounded with braces. E. g. if you
   * use the marker {@code name} the method searches for {{@code name} inside
   * the text.
   * 
   * @param text
   *          the string to search for marker
   * @param marker
   *          the marker for the place where the new text from parameter replace
   *          should be
   * @param replace
   *          text that replaces the marker
   * @return the replaced content of the text
   */
  public static String replaceMarker(String text, String marker, String replace) {
    return text.replaceAll("\\{" + marker + "\\}",replace);
  }



  public static String replaceMarker(String text, HashMap<String, String> map) {
    for (Entry<String, String> entry : map.entrySet()) {
      text = replaceMarker(text, entry.getKey(), entry.getValue());
    }
    return text;
  }



  /**
   * Parses an String containing a xml structure to a document. If an exception
   * will be thrown the method will return null.
   * 
   * @param data
   *          String containing a valid xml structure
   * @return Document or null if exception was thrown.
   */
  public static Document parseXMLToDocument(String data) {
    Document doc = null;

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder;
      builder = factory.newDocumentBuilder();

      InputSource is = new InputSource();
      is.setCharacterStream(new StringReader(data));
      doc = builder.parse(is);
    } catch (Exception e) {
      System.out.println("Returned null. Invalid or currupted XML file.");
    }

    return doc;
  }
}
