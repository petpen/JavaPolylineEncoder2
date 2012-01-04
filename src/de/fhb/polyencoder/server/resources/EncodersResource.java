package de.fhb.polyencoder.server.resources;

import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.fhb.polyencoder.PolylineEncoder;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.geo.GeographicBounds;
import de.fhb.polyencoder.geo.GeographicPositionParser;
import de.fhb.polyencoder.server.GenerateHtml;
import de.fhb.polyencoder.server.InputType;
import de.fhb.polyencoder.server.OutputType;

@Path("/encoder/{typ}/{format}")
public class EncodersResource {

  @GET
  public String get(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link) {
    if (link == null)
      link = "";
    return "/encoder/" + typ + "/" + format;
  }



  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  public String post(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link, @FormParam("coords") String coords) {
    Track track = null;
    if (link == null) {
      link = "";
    }
    if (coords == null) {
      coords = "";
    }
    if (link.equals("") & coords.equals("")) {
      return "no input no output";
    }

    if (!link.equals("")) {
      return "load form link";
    }

    switch (InputType.test(typ.toUpperCase())) {
    case GPX:
      track = GeographicPositionParser.pointsToTrack(coords);
      break;
    case KML:
      break;
    case KMZ:
      break;
    case RAW:
      break;
    default:
      return "no support for typ";
    }

    PolylineEncoder polylineEncoder = new PolylineEncoder();
    HashMap<String, String> map = polylineEncoder.dpEncode(track);
    map.putAll(new GeographicBounds(track).getCenter());

    switch (OutputType.test(format.toUpperCase())) {
    case HTML:
      return GenerateHtml.getHtml(map);
    case JSON:
      return "json output";
    case RAW:
      return "raw output";
    default:
      return "no support for format";
    }
  }
}