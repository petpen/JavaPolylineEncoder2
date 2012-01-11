package de.fhb.polyencoder.parser;

import java.util.List;

import de.fhb.polyencoder.Track;

/**
 * @author Peter Pensold
 * @version 1
 */
public interface StringToTrackParser {
  public void parse(String data);
  public void parseFile(String fileName);
  public List<Track> getTracks();
}
