package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;

@Path("/")
public class FormularResource {

  @GET
  @Produces("text/html")
  public String get() {
    StringBuilder form = new StringBuilder();
    form.append("<!DOCTYPE html>\n");
    form.append("<html>\n");
    form.append("<head></head>\n");
    form.append("<body>\n");
    form.append("<form method=\"post\" action=\"/encoder/gpx/html\">\n");
    form.append("<textarea name=\"coords\"></textarea ><br>\n");
    form.append("<input type=\"submit\">\n");
    form.append("</form>\n");
    form.append("</body>\n");
    form.append("</html>");
    return form.toString();
  }
}