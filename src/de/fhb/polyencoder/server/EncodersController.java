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



  public static String encodeData(String data, String typ, String format) {
    String result = "";
    List<Track> tracks = parseData(data, typ);

    if (tracks.size() > 0) {
      PolylineEncoder polylineEncoder = new PolylineEncoder();
      HashMap<String, String> map = polylineEncoder.dpEncode(tracks.get(0));
      map.putAll(new GeographicBounds(tracks.get(0)).getMinMaxBounds());
      map.putAll(new GeographicBounds(tracks.get(0)).getCenter());
      map.put("pointCount", String.valueOf(tracks.get(0).size()));
      map.put("createdDate", String.valueOf(new Date().getTime()));

      switch (OutputType.test(format)) {
        case HTML:
          result = GenerateHtml.getHtml(map);
          break;
        case JSON:
          map.put("statusCode", "200");
          map.put("statusMessage", "");
          result = GenerateJson.getJson(map);
          break;
        case XML:
          result = GenerateErrorMessage.getAs(200, "Output as XML.", OutputType.XML);
          break;
        case RAW:
          result = GenerateErrorMessage.getAs(200, "Output as Raw.", OutputType.RAW);
          break;
        default:
          result = GenerateErrorMessage.getAs(400, "Outputformat not supported.");
      }
    } else {
      result = GenerateErrorMessage.getAs(400, "No tracks found.");
    }

    return result;
  }



  public static List<Track> parseData(String coords, String typ) {
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
