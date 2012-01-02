package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;

@Path("/encoder/{typ}/{format}")
public class EncodersResource {

  @GET
  @Produces("text/plain")
  public String get(@PathParam ("typ") String typ, @PathParam ("format") String format, @QueryParam("link") String link) {
    return "/encoder/"+typ+"/"+format;
  }



  @POST
  @Produces("text/plain")
  public String post(@PathParam ("typ") String typ, @PathParam ("format") String format, @QueryParam("link") String link) {
    if(link == null) link = "";
    
    if(typ.equals("gpx") && format.equals("html")) return "gpx/html?link="+link;
    if(typ.equals("kml") && format.equals("json")) return "kml/json?link="+link;
    if(typ.equals("kmz") && format.equals("json")) return "kmz/json?link="+link;
    if(typ.equals("raw") && format.equals("raw")) return "raw/raw?link="+link;
    
    return "no support for typ/format";
  }
}