package de.fhb.polyencoder.server.view;

import java.util.HashMap;

import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.server.OutputType;

public abstract class ViewGenerator {

  protected String frame;
  protected StringBuilder tracks;
  protected String outputTyp;



  public ViewGenerator(HashMap<String, String> map, OutputType format) {
    outputTyp = format.toString().toLowerCase();
    frame = getFrameTemplate();
    frame = Util.replaceMarker(frame, map);
    tracks = new StringBuilder("");
  }



  public void addTrack(HashMap<String, String> map) {
    String track = getTrackTemplate();
    track = Util.replaceMarker(track, map);
    tracks.append(track+"\n");
  }



  public String getView() {
    if (tracks.length() > 0) {
      tracks.deleteCharAt(tracks.length() - 1);
    }
    return Util.replaceMarker(frame, "tracks", tracks.toString());
  }



  private String getFrameTemplate() {
    return Util.readFile("templates/"+outputTyp+"/frame."+outputTyp);
  }



  private String getTrackTemplate() {
    return Util.readFile("templates/"+outputTyp+"/track."+outputTyp);
  }
}
