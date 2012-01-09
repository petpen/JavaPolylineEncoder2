package de.fhb.polyencoder.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.fhb.polyencoder.PolylineEncoder;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.geo.GeographicBounds;
import de.fhb.polyencoder.parser.ParserFactory;
import de.fhb.polyencoder.parser.StringToTrackParser;
import de.fhb.polyencoder.server.view.*;

public class EncodersController {

  public static boolean isValidTyp(String typ) {
    InputType input = InputType.test(typ);
    return (input != InputType.NOSUPPORT);
  }



  public static boolean isOutputValid(String format) {
    OutputType input = OutputType.test(format);
    return (input != OutputType.NOSUPPORT);
  }



  public static String encodeFile(String path, String typ, String format) {
    List<Track> tracks = parseFile(path, typ);

    return encode(tracks, format);
  }



  public static String encodeData(String data, String typ, String format) {
    List<Track> tracks = parseData(data, typ);

    return encode(tracks, format);
  }



  private static String encode(List<Track> tracks, String format) {
    String result = "";
    HashMap<String, String> map;

    if (tracks.size() > 0) {
      PolylineEncoder polylineEncoder = new PolylineEncoder();
      map = new GeographicBounds(tracks.get(0)).getMinMaxBounds();
      map.putAll(new GeographicBounds(tracks.get(0)).getCenter());
      map.put("pointCount", String.valueOf(tracks.get(0).size()));
      map.put("createdDate", String.valueOf(new Date().getTime()));
      map.put("statusCode", "200");
      map.put("statusMessage", "");

      ViewGenerator vg = ViewFactory.buildViewGenerator(map, OutputType.test(format));
      if (vg != null) {
        for (int i=0;i<tracks.size();i++) {
          vg.addTrack(polylineEncoder.dpEncode(tracks.get(i)));
        }
        result = vg.getView();
      } else {
        result = GenerateErrorMessage.getAs(400, "Outputformat not supported.");
      }
    } else {
      result = GenerateErrorMessage.getAs(400, "No tracks found.");
    }

    return result;
  }



  private static List<Track> parseFile(String path, String typ) {
    StringToTrackParser trackParser;

    trackParser = ParserFactory.buildParser(InputType.test(typ));
    if (trackParser != null) {
      trackParser.parseFile(path);
    } else {
      return new ArrayList<Track>();
    }

    return trackParser.getTracks();
  }



  private static List<Track> parseData(String coords, String typ) {
    StringToTrackParser trackParser;

    trackParser = ParserFactory.buildParser(InputType.test(typ));
    if (trackParser != null) {
      trackParser.parse(coords);
    } else {
      return new ArrayList<Track>();
    }

    return trackParser.getTracks();
  }



  public static boolean hasValidData(String data) {
    return (data != null && data.length() > 0);
  }



  public static boolean isValidLink(String link) {
    // TODO This has to be checked later for a valid url
    return (link != null && link.length() > 0);
  }



  public static String getErrorMsg(boolean isInputValid, boolean isOutputValid) {
    String errorMessage = "";
    if (isInputValid == false) {
      errorMessage += " No inputformat specified or not supported.";
    }
    if (isOutputValid == false) {
      errorMessage += " Wrong outputformat specified or not supported.";
    }
    return errorMessage;
  }
}
