package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;

@Path("/")
public class FormularResource {

  @GET
  @Produces("text/html")
  public String get() {
    StringBuilder form = new StringBuilder();
    form.append("<!DOCTYPE html>");
    form.append("<html>");
    form.append("<head></head>");
    form.append("<body>");
    form.append("<form method=\"post\" action=\"/encoder/raw/html\">");
    form.append("<textarea name=\"coords\"></textarea ><br>");
    form.append("<input type=\"submit\">");
    form.append("</form>");
    form.append("</body>");
    form.append("</html>");
    return form.toString();
  }
}