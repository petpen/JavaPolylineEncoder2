package de.fhb.polyencoder.server.resources;

import java.util.HashMap;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.fhb.polyencoder.PolylineEncoder;
import de.fhb.polyencoder.Track;
import de.fhb.polyencoder.geo.GeographicPositionParser;
import de.fhb.polyencoder.server.GenerateHtml;

@Path("/encoder/{typ}/{format}")
public class EncodersResource {

  @GET
  @Produces("text/html")
  public String get(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link) {
    if (link == null)
      link = "";
    return "/encoder/" + typ + "/" + format;
  }



  @POST
  @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
  // @Produces("text/html")
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

    if (typ.equals("gpx")) {
      track = GeographicPositionParser.pointsToTrack(coords);
    } else if (typ.equals("kml")) {

    } else if (typ.equals("kmz")) {

    } else if (typ.equals("raw")) {

    } else {
      return "no support for typ";
    }

    PolylineEncoder polylineEncoder = new PolylineEncoder();
    HashMap<String, String> map = polylineEncoder.dpEncode(track);

    if (format.equals("html")) {
      return GenerateHtml.getHtml(map);
      // return "gpx/html?link=" + link;
    } else if (format.equals("json")) {
      return "kml/json?link=" + link;
    } else if (format.equals("json")) {
      return "kmz/json?link=" + link;
    } else if (format.equals("raw")) {
      return "raw/raw?link=" + link;
    } else {
      return "no support for format";
    }
  }
}