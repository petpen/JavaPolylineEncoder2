package de.fhb.polyencoder.server;

import static org.junit.Assert.*;


import org.junit.*;

public class EncodersControllerTest {
  @Test
  public void testIsValidTyp() {
    assertTrue("gpx should be valid input typ", EncodersController.isAcceptedInput(InputType.test("gpx")));
    assertTrue("kml should be valid input typ", EncodersController.isAcceptedInput(InputType.test("kml")));
    assertTrue("kmz should be valid input typ", EncodersController.isAcceptedInput(InputType.test("kmz")));
    assertTrue("raw should be valid input typ", EncodersController.isAcceptedInput(InputType.test("raw")));
  }



  @Test
  public void testIsValidFormat() {
    assertTrue("html should be valid output format", EncodersController.isAcceptedOutput(OutputType.test("html")));
    assertTrue("json should be valid output format", EncodersController.isAcceptedOutput(OutputType.test("json")));
    assertTrue("raw should be valid output format", EncodersController.isAcceptedOutput(OutputType.test("raw")));
    assertTrue("xml should be valid output format", EncodersController.isAcceptedOutput(OutputType.test("xml")));
  }



  @Test
  public void testIsInvalidTyp() {
    assertFalse("foo should be invalid input typ", EncodersController.isAcceptedInput(InputType.test("foo")));
  }



  @Test
  public void testIsInvalidFormat() {
    assertFalse("bar should be invalid output format", EncodersController.isAcceptedOutput(OutputType.test("bar")));
  }
}
