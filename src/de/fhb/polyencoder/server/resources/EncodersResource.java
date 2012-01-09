package de.fhb.polyencoder.server.resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.multipart.FormDataParam;

import de.fhb.polyencoder.Util;
import de.fhb.polyencoder.server.EncodersController;
import de.fhb.polyencoder.server.InputType;
import de.fhb.polyencoder.server.OutputType;
import de.fhb.polyencoder.server.view.GenerateErrorMessage;
import de.n00b.downloader.Downloader;

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
    String errorMsg = "";

    boolean isInputValid = EncodersController.isValidTyp(typ);
    boolean isOutputValid = EncodersController.isOutputValid(format);

    if (isInputValid && isOutputValid) {
      if (!EncodersController.isValidLink(link)) {
        result = GenerateErrorMessage.getAs(400, "Invalid link.", OutputType.test(format));
      } else {
        String fileName = String.format("%s/%s.%s", "temp", (new Date()).getTime(), typ.toLowerCase());
        FileOutputStream fos;
        try {
          fos = new FileOutputStream(fileName);
          try {
            Downloader.downloadFile(link, fos);
            System.out.println("geladen");
          } catch (IllegalStateException e) {
            errorMsg = "No file found.";
          }
          fos.close();
          System.out.println("closed");
        } catch (IOException e) {
          fileName = "";
          errorMsg = "Internal error can't write file.";
        }
        if (errorMsg.equals("")) {
          System.out.println("encodeFile");
          result = EncodersController.encodeFile(fileName, typ, format);
        } else {
          result = GenerateErrorMessage.getAs(400, errorMsg);
        }
        if (!fileName.equals("")) {
          Util.deleteFile(fileName);
        }
      }
    } else {
      String errorMessage = EncodersController.getErrorMsg(isInputValid, isOutputValid);
      OutputType outputType = isOutputValid ? OutputType.test(format) : OutputType.RAW;
      result = GenerateErrorMessage.getAs(400, errorMessage, outputType);
    }
    return result;
  }



  @POST
  @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
  public String post(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @FormParam(POSTDATA) String data) {
    String result = "";

    boolean isInputValid = EncodersController.isValidTyp(typ) && InputType.test(typ) != InputType.KMZ;
    boolean isOutputValid = EncodersController.isOutputValid(format);

    OutputType outputType = isOutputValid ? OutputType.test(format) : OutputType.RAW;

    if (isInputValid && isOutputValid) {

      if (EncodersController.hasValidData(data)) {
        result = EncodersController.encodeData(data, typ, format);
      } else {
        result = GenerateErrorMessage.getAs(400, "No data found.");
      }
    } else if (InputType.test(typ) == InputType.KMZ) {
      result = GenerateErrorMessage.getAs(400, "KMZ isn't supported for this method.", outputType);
    } else {
      String errorMessage = EncodersController.getErrorMsg(isInputValid, isOutputValid);
      result = GenerateErrorMessage.getAs(400, errorMessage, outputType);
    }

    return result;
  }



  @POST
  @Consumes({ MediaType.MULTIPART_FORM_DATA })
  public String post(@PathParam(INPUT) String typ, @PathParam(OUTPUT) String format, @FormDataParam(FILEDATA) InputStream dataStream) {
    boolean isInputValid = EncodersController.isValidTyp(typ);
    boolean isOutputValid = EncodersController.isOutputValid(format);
    String result = "";

    if (isInputValid && isOutputValid) {
      String fileName = String.format("%s/%s.%s", "temp", (new Date()).getTime(), typ.toLowerCase());
      try {
        Util.writeInputStreamToFile(dataStream, fileName);
      } catch (Exception e) {
        fileName = "";
      }

      if (!fileName.equals("")) {
        result = EncodersController.encodeFile(fileName, typ, format);
        Util.deleteFile(fileName);
      } else {
        result = GenerateErrorMessage.getAs(400, "No data found.");
      }
    } else {
      String errorMessage = EncodersController.getErrorMsg(isInputValid, isOutputValid);
      OutputType outputType = isOutputValid ? OutputType.test(format) : OutputType.RAW;
      result = GenerateErrorMessage.getAs(400, errorMessage, outputType);
    }

    return result;
  }
}