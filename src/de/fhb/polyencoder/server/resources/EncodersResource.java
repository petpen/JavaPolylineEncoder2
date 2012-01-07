package de.fhb.polyencoder.server.resources;

import java.io.InputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

import de.fhb.polyencoder.server.EncodersController;
import de.fhb.polyencoder.server.OutputType;
import de.fhb.polyencoder.server.view.GenerateErrorMessage;

@Path("/encoder/{"+EncodersResource.INPUT+"}/{"+EncodersResource.OUTPUT+"}")
public class EncodersResource {
  protected static final String INPUT = "typ";
  protected static final String OUTPUT = "format";
  private static final String LINK = "link";
  private static final String POSTDATA = "coords";



  @GET
  public String get(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @QueryParam(LINK) String link) {
    String result;

    if (!EncodersController.isValidLink(link)) {
      result = GenerateErrorMessage.getAs(400, "Invalid link.", OutputType.test(format));
    } else {
      // TODO load data from link, need interface to stub download
      String data = "";
      result = EncodersController.encodeData(data, typ, format);
    }
    return result;
  }



  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  public String post(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @FormParam(POSTDATA) String data) {
    String result = "";
    String errorMessage = "";

    boolean isInputValid = EncodersController.isValidTyp(typ);
    boolean isOutputValid = EncodersController.isOutputValid(format);

    if (isInputValid && isOutputValid) {

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



  @POST
  @Consumes({ MediaType.MULTIPART_FORM_DATA })
  public String post(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @FormDataParam(POSTDATA) InputStream dataStream) {
    return null;
  }
}