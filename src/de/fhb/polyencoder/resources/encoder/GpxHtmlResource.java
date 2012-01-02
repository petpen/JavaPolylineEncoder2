package de.fhb.polyencoder.resources.encoder;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/encoder/gpx/html")
public class GpxHtmlResource {

  // The Java method will process HTTP GET requests
  @GET
  // The Java method will produce content identified by the MIME Media
  // type "text/plain"
  @Produces("text/plain")
  public String getClichedMessage() {
    // Return some cliched textual content
    return "/encoder/gpx/html";
  }
  @POST
  @Produces("text/plain")
  public String post(@QueryParam("link") String link) {
    if (link != null) return link;
    return "";
  }
}