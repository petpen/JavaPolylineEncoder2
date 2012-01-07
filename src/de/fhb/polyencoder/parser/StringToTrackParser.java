package de.fhb.polyencoder.parser;

import java.util.List;

import de.fhb.polyencoder.Track;

public interface StringToTrackParser {
  public void parse(String data);
  public void parseFile(String fileName);
  public List<Track> getTracks();
}
