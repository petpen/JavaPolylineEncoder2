package de.fhb.polyencoder.parser;

import de.fhb.polyencoder.Track;

public interface StringToTrackParser {
  public void parse(String data);
  public Track getTrack();
}
