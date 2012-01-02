package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;

@Path("/encoder/{typ}/{format}")
public class EncodersResource {

  @GET
  @Produces("text/plain")
  public String get(@PathParam ("typ") String typ, @PathParam ("format") String format, @QueryParam("link") String link) {
    // Return some cliched textual content
    return "/encoder/"+typ+"/"+format;
  }



  @POST
  @Produces("text/plain")
  public String post(@PathParam ("typ") String typ, @PathParam ("format") String format, @QueryParam("link") String link) {
    if (link != null)
      return link;
    return "";
  }
}