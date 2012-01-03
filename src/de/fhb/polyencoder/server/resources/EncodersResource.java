package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;

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
  @Produces("text/plain")
  public String post(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link) {
    if (link == null)
      link = "";

    if (typ.equals("gpx")) {

    } else if (typ.equals("kml")) {

    } else if (typ.equals("kmz")) {

    } else if (typ.equals("raw")) {

    } else {
      return "no support for typ";
    }

    if (format.equals("html")) {
      return "gpx/html?link=" + link;
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