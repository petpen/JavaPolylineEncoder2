package de.fhb.polyencoder.server.resources;

import java.io.InputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

import de.fhb.polyencoder.server.EncodersController;
import de.fhb.polyencoder.server.InputType;
import de.fhb.polyencoder.server.OutputType;

@Path("/encoder/{" + EncodersResource.INPUT + "}/{" + EncodersResource.OUTPUT + "}")
public class EncodersResource {
  protected static final String INPUT = "typ";
  protected static final String OUTPUT = "format";
  private static final String LINK = "link";
  private static final String POSTDATA = "coords";
  private static final String FILEDATA = "fileData";



  @GET
  public String get(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @QueryParam(LINK) String link) {
    String result = "";

    OutputType out = OutputType.test(format);
    InputType in = InputType.test(typ);

    boolean isAcceptedInput = EncodersController.isAcceptedInput(in);
    boolean isAcceptedOutput = EncodersController.isAcceptedOutput(out);
    
    if (isAcceptedInput && isAcceptedOutput) {
      result = EncodersController.encodeFromLink(in, out, link);
    } else {
      result = EncodersController.createErrorMessage(isAcceptedInput, isAcceptedOutput, out);
    }
    
    return result;
  }



  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  public String post(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @FormParam(POSTDATA) String data) {
    String result = "";

    OutputType out = OutputType.test(format);
    InputType in = InputType.test(typ);

    boolean isAcceptedInput = EncodersController.isAcceptedInput(in, new InputType[] {InputType.KMZ});
    boolean isAcceptedOutput = EncodersController.isAcceptedOutput(out);

    if (isAcceptedInput && isAcceptedOutput) {
      result = EncodersController.encodeFromData(in, out, data);
    } else {
      result = EncodersController.createErrorMessage(isAcceptedInput, isAcceptedOutput, out);
    }

    return result;
  }



  @POST
  @Consumes({ MediaType.MULTIPART_FORM_DATA })
  public String post(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @FormDataParam(FILEDATA) InputStream dataStream) {
    String result = "";

    OutputType out = OutputType.test(format);
    InputType in = InputType.test(typ);

    boolean isAcceptedInput = EncodersController.isAcceptedInput(in);
    boolean isAcceptedOutput = EncodersController.isAcceptedOutput(out);

    if (isAcceptedInput && isAcceptedOutput) {
      result = EncodersController.encodeFromFile(in, out, dataStream);
    } else {
      result = EncodersController.createErrorMessage(isAcceptedInput, isAcceptedOutput, out);
    }

    return result;
  }
}