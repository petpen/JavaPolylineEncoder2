package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;

import de.fhb.polyencoder.Util;

/**
 * @author Martin Bormeister
 * 
 */
@Path("/")
public class FormularResource {

  private final static String FORMULAR = Util.readFile("templates/view/formular.html");



  @GET
  @Produces("text/html")
  public String get() {
    return FORMULAR;
  }
}