package de.fhb.polyencoder.resources.encoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/encoder/raw/raw")
public class RawRawResource {

  // The Java method will process HTTP GET requests
  @GET
  // The Java method will produce content identified by the MIME Media
  // type "text/plain"
  @Produces("text/plain")
  public String getClichedMessage() {
    // Return some cliched textual content
    return "/encoder/raw/raw";
  }
}