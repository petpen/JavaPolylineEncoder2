package de.fhb.polyencoder.parser;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.fhb.polyencoder.Util;

public class KmzParser extends AbstractStringToTrackParser  implements StringToTrackParser {
  public final static String DEFAULT_KML_NAME = "doc.kml";
  private final static String TEMP_FOLDER = "temp";



  /**
   * Reads a KMZ file from the server and extracts the main KML file inside this
   * file. The KML must be named {@code doc.kml}. This is the default if this
   * file was created by Google Earth. If no {@code doc.kml} was found no track
   * will be parsed.
   * 
   * This method creates a temporary kml file. At the end this temporary file
   * will be deleted. The name of this file will be {@link Date#getTime()}.kml
   * and it is stored inside a temporary folder.
   * 
   * @param data
   *          Filename of the KMZ file on the server
   */
  public void parse(String data) {
    String kmlFileName = unzipMainKMLFromKMZ(data);
    String kmlContent = Util.readFile(kmlFileName);
    
    parseKMLData(kmlContent);

    
    Util.deleteFile(kmlFileName);
  }



  private void parseKMLData(String data) {
    StringToTrackParser kmlParser = ParserFactory.buildKmlParser();
    kmlParser.parse(data);
    
    tracks = kmlParser.getTracks();
  }



  private static String unzipMainKMLFromKMZ(String fileName) {
    String kmlFileName = "";

    try {
      FileInputStream fis = new FileInputStream(fileName);
      ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
      ZipEntry entry;
      boolean foundKML = false;

      while ((entry = zis.getNextEntry()) != null && foundKML == false) {
        if (entry.getName().equals(DEFAULT_KML_NAME)) {
          kmlFileName = extractAndGetKMLFileName(zis);
          foundKML = true;
        }
      }

      zis.close();
    } catch (Exception e) {
      System.out.println("Problem with KMZ Data");
    }

    return kmlFileName;
  }



  private static String extractAndGetKMLFileName(InputStream is) {
    String tempName = String.format("%s/%s.kml", TEMP_FOLDER, (new Date()).getTime());
    
    try {
      Util.writeInputStreamToFile(is, tempName);
    } catch (Exception e) {
      //e.printStackTrace();
    }
    
    return tempName;
  }
}
