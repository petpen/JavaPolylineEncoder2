package de.fhb.polyencoder.server;

import static org.junit.Assert.*;


import org.junit.*;

import de.fhb.polyencoder.Util;

public class EncodersControllerTest {
  private static final String DEFAULT_COORDS_GPX = Util.readFile("testfiles/gpx/threeWaypoints_GPX_1.0.gpx");



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



  @Test
  public void testIsValidData() {
    assertTrue("gpx data should be valid data", EncodersController.hasValidData(DEFAULT_COORDS_GPX));
  }



  @Test
  public void testIsInvalidData() {
    assertFalse("empty should be invalid data", EncodersController.hasValidData(""));
    assertFalse("null should be invalid data", EncodersController.hasValidData(null));
  }



  @Test
  public void testIsValidLink() {
    assertTrue("should be valid link", EncodersController.isValidLink("http://google.de/robots.txt"));
  }



  @Test
  public void testIsInvalidLink() {
    assertFalse("should be invalid link", EncodersController.isValidLink(""));
  }
}
