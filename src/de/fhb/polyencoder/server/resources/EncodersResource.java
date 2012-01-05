package de.fhb.polyencoder.server.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static de.fhb.polyencoder.server.EncodersController.*;
import de.fhb.polyencoder.server.OutputType;
import de.fhb.polyencoder.view.GenerateErrorMessage;

@Path("/encoder/{typ}/{format}")
public class EncodersResource {

  @GET
  public String get(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link) {
    if (isValidLink(link) == false)
      link = "";
    return "/encoder/" + typ + "/" + format;
  }



  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  public String post(@PathParam("typ") String typ, @PathParam("format") String format, @QueryParam("link") String link, @FormParam("coords") String data) {
    String result = "";
    String errorMessage = "";

    boolean isInputValid = isValidTyp(typ);
    boolean isOutputValid = isOutputValid(format);

    if (isInputValid && isOutputValid) {

      if (isValidLink(link)) {
        // TODO load data from link
        data = "";
      }

      if (hasValidData(data)) {
        result = encodeData(data, typ, format);
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