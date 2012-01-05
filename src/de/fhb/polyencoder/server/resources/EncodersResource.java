package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.fhb.polyencoder.server.EncodersController;
import de.fhb.polyencoder.server.OutputType;
import de.fhb.polyencoder.server.view.GenerateErrorMessage;

@Path("/encoder/{typ}/{format}")
public class EncodersResource {

  @GET
  public String get(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link) {
    String result;

    if (!EncodersController.isValidLink(link)) {
      result = GenerateErrorMessage.getAs(400, "Invalid link.", OutputType.test(format));
    } else {
      //TODO load data from link, need interface to stub download
      String data = "";
      result = EncodersController.encodeData(data, typ, format);
    }
    return result;
  }



  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  public String post(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link, @FormParam("coords") String data) {
    String result = "";
    String errorMessage = "";

    boolean isInputValid = EncodersController.isValidTyp(typ);
    boolean isOutputValid = EncodersController.isOutputValid(format);

    if (isInputValid && isOutputValid) {

      if (EncodersController.isValidLink(link)) {
        // TODO see TODO at get()
        data = "";
      }

      if (EncodersController.hasValidData(data)) {
        result = EncodersController.encodeData(data, typ, format);
      } else {
        result = GenerateErrorMessage.getAs(400, "No data found.");
      }
    } else {
      OutputType outputType = OutputType.test(format);

      if (isInputValid == false) {
        errorMessage += " No inputformat specified or not supported.";
      }

      if (isOutputValid == false) {
        errorMessage += " Wrong outputformat specified or not supported.";
        outputType = OutputType.RAW;
      }

      result = GenerateErrorMessage.getAs(400, errorMessage, outputType);
    }

    return result;
  }

}