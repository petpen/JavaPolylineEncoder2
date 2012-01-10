package de.fhb.polyencoder.server.view;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.fhb.polyencoder.server.OutputType;

/**
 * @author Martin Bormeister
 * 
 */
public class GenerateErrorMessageTest {

  @Test
  public void testOutputNOSUPPORT() throws IOException {
    new GenerateErrorMessage();
    assertEquals("error message should be raw typ", "400\nIt is raw.", GenerateErrorMessage.getAs(400, "It is raw.", OutputType.test("java")));
  }
}
