package de.fhb.polyencoder.server.resources;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.fhb.polyencoder.PolylineEncoder;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.geo.GeographicBounds;
import de.fhb.polyencoder.parser.ParserFactory;
import de.fhb.polyencoder.parser.StringToTrackParser;
import de.fhb.polyencoder.server.GenerateErrorMessage;
import de.fhb.polyencoder.server.GenerateHtml;
import de.fhb.polyencoder.server.InputType;
import de.fhb.polyencoder.server.OutputType;

@Path("/encoder/{typ}/{format}")
public class EncodersResource {

  @GET
  public String get(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link) {
    if (isValidLink(link) == false)
      link = "";
    return "/encoder/" + typ + "/" + format;
  }



  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  public String post(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link, @FormParam("coords") String coords) {
    String result = "";
    String errorMessage = "";

    boolean isInputValid = isInputValid(typ, link, coords);
    boolean isOutputValid = isOutputValid(format);

    if (isInputValid && isOutputValid) {

      if (link.length() > 0) {
        result = GenerateErrorMessage.getAs(200, "Load data from link.");
      }

      List<Track> tracks = parseData(coords, typ);

      if (tracks.size() > 0) {
        PolylineEncoder polylineEncoder = new PolylineEncoder();
        HashMap<String, String> map = polylineEncoder.dpEncode(tracks.get(0));
        map.putAll(new GeographicBounds(tracks.get(0)).getMinMaxBounds());

        switch (OutputType.test(format)) {
          case HTML:
            result = GenerateHtml.getHtml(map);
            break;
          case JSON:
            result = GenerateErrorMessage.getAs(200, "Output as JSON.", OutputType.JSON);
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
    } else {
      if (isInputValid == false) {
        errorMessage += " No inputformat specified or not supported.";
      }

      if (isOutputValid == false) {
        errorMessage += " Wrong outputformat specified or not supported.";
      }

      OutputType outputType = (isOutputValid) ? OutputType.test(format) : OutputType.RAW;
      result = GenerateErrorMessage.getAs(400, errorMessage, outputType);
    }

    return result;
  }



  private List<Track> parseData(String coords, String typ) {
    StringToTrackParser trackParser;

    trackParser = ParserFactory.buildParser(InputType.test(typ));
    
    trackParser.parse(coords);
    
    return trackParser.getTracks();
  }



  private boolean isOutputValid(String format) {
    OutputType input = OutputType.test(format);
    return (input != OutputType.NOSUPPORT);
  }



  private boolean isInputValid(String typ, String link, String coords) {
    return isValidTyp(typ) && isValidLink(link) && hasValidCoords(coords);
  }



  private boolean hasValidCoords(String coords) {
    return (coords != null && coords.equals("") == false);
  }



  private boolean isValidLink(String link) {
    // TODO This has to be checked later for a valid url
    return (link != null);
  }



  private boolean isValidTyp(String typ) {
    InputType input = InputType.test(typ);
    return (input != InputType.NOSUPPORT);
  }

}