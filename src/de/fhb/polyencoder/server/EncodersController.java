package de.fhb.polyencoder.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.fhb.polyencoder.PolylineEncoder;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.geo.GeographicBounds;
import de.fhb.polyencoder.parser.ParserFactory;
import de.fhb.polyencoder.parser.StringToTrackParser;
import de.fhb.polyencoder.server.view.*;

public class EncodersController {

  public static boolean isAcceptedInput(InputType in) {
    return isAcceptedInput(in, new InputType[] {InputType.NOSUPPORT});
  }
  


  public static boolean isAcceptedInput(InputType in, InputType[] excludes) {
    Set<InputType> list = new TreeSet<InputType>();
    list.addAll(Arrays.asList(excludes));
    list.add(InputType.NOSUPPORT);
    
    return Util.isValidEnum(in, list);
  }



  public static boolean isAcceptedOutput(OutputType out) {
    return isAcceptedOutput(out, new OutputType[] { OutputType.NOSUPPORT});
  }



  public static boolean isAcceptedOutput(OutputType out, OutputType[] excludes) {
    Set<OutputType> list = new TreeSet<OutputType>();
    list.addAll(Arrays.asList(excludes));
    list.add(OutputType.NOSUPPORT);
    
    return Util.isValidEnum(out, list);
  }



  public static String encodeFile(String path, InputType typ, OutputType format) {
    List<Track> tracks = parseFile(path, typ);

    return encode(tracks, format);
  }



  public static String encodeData(String data, InputType typ, OutputType format) {
    List<Track> tracks = parseData(data, typ);

    return encode(tracks, format);
  }



  private static String encode(List<Track> tracks, OutputType format) {
    String result = "";
    HashMap<String, String> map;

    if (tracks.size() > 0) {
      PolylineEncoder polylineEncoder = new PolylineEncoder();
      map = new GeographicBounds(tracks.get(0)).getMinMaxBounds();
      map.putAll(new GeographicBounds(tracks.get(0)).getCenter());
      map.put("createdDate", String.valueOf(new Date().getTime()));
      map.put("statusCode", "200");
      map.put("statusMessage", "");

      ViewGenerator vg = ViewFactory.buildViewGenerator(map, format);
      if (vg != null) {
        for (int i = 0; i < tracks.size(); i++) {
          map.clear();
          map.put("pointCount", String.valueOf(tracks.get(i).size()));
          map.putAll(polylineEncoder.dpEncode(tracks.get(i)));
          vg.addTrack(map);
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



  private static List<Track> parseFile(String path, InputType typ) {
    StringToTrackParser trackParser;

    trackParser = ParserFactory.buildParser(typ);
    if (trackParser != null) {
      trackParser.parseFile(path);
    } else {
      return new ArrayList<Track>();
    }

    return trackParser.getTracks();
  }



  private static List<Track> parseData(String coords, InputType typ) {
    StringToTrackParser trackParser;

    trackParser = ParserFactory.buildParser(typ);
    if (trackParser != null) {
      trackParser.parse(coords);
    } else {
      return new ArrayList<Track>();
    }

    return trackParser.getTracks();
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



  public static String createErrorMessage(boolean isAcceptedInput, boolean isAcceptedOutput, OutputType out) {
    String errorMessage = getErrorMsg(isAcceptedInput, isAcceptedOutput);
    out = isAcceptedOutput ? out : OutputType.RAW;

    return GenerateErrorMessage.getAs(400, errorMessage, out);
  }



  public static String encodeFromLink(InputType in, OutputType out, String link) {
    String result = "";
    String errorMsg = "";
    
    if (Util.isStringNotEmpty(link)) {
      String fileName = Util.createTempFileName(in.toString().toLowerCase());
      
      try {
        Util.downloadFile(link, fileName);
      } catch (IllegalStateException e) {
        errorMsg = "No file found.";
      } catch (IOException e) {
        errorMsg = "Internal error can't write file.";
        fileName = "";
      }
      
      if(errorMsg.isEmpty()) {
        result = encodeFile(fileName, in, out);
      } else {
        result = GenerateErrorMessage.getAs(400, errorMsg, out);
      }
      
      if(fileName.length() > 0) {
        Util.deleteFile(fileName);
      }
    } else {
      result = GenerateErrorMessage.getAs(400, "Invalid link.", out);
    }
    
    return result;
  }



  public static String encodeFromData(InputType in, OutputType out, String data) {
    String result = "";
    
    if (Util.isStringNotEmpty(data)) {
      result = encodeData(data, in, out);
    } else {
      result = GenerateErrorMessage.getAs(400, "No data found.", out);
    }
    
    return result;
  }



  public static String encodeFromFile(InputType in, OutputType out, InputStream dataStream) {
    String result = "";
    
    String fileName = Util.createTempFileName(in.toString().toLowerCase());
    
    try {
      Util.writeInputStreamToFile(dataStream, fileName);
    } catch (Exception e) {
      fileName = "";
    }

    if (fileName.isEmpty()) {
      result = GenerateErrorMessage.getAs(400, "No data found.", out);
    } else {
      result = encodeFile(fileName, in, out);
      Util.deleteFile(fileName);
    }
    
    return result;
  }
}
