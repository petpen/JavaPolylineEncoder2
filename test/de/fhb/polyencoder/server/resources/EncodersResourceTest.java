package de.fhb.polyencoder.server.resources;

import static org.junit.Assert.*;
import org.junit.*;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class EncodersResourceTest extends JerseyTest {

  WebResource webResource;



  public EncodersResourceTest() throws Exception {
    super("de.fhb.polyencoder.server.resources");
    webResource = resource();
  }



  @Test
  public void testGetGpxHtml() {
    String responseMsg = webResource.path("encoder").path("gpx/html").get(String.class);
    assertEquals("Should return /encoder/gpx/html", "/encoder/gpx/html", responseMsg);
  }



  @Test
  public void testGetKmlJson() {
    String responseMsg = webResource.path("encoder").path("kml/json").get(String.class);
    assertEquals("Should return /encoder/kml/json", "/encoder/kml/json", responseMsg);
  }



  @Test
  public void testGetKmzJson() {
    String responseMsg = webResource.path("encoder").path("kmz/json").get(String.class);
    assertEquals("Should return /encoder/kmz/json", "/encoder/kmz/json", responseMsg);
  }



  @Test
  public void testGetRawRaw() {
    String responseMsg = webResource.path("encoder").path("raw/raw").get(String.class);
    assertEquals("Should return /encoder/raw/raw", "/encoder/raw/raw", responseMsg);
  }



  @Test
  public void testPostNoLink() {
    String responseMsg = webResource.path("encoder").path("gpx/html").post(String.class);
    assertEquals("Return with empty link", "gpx/html?link=", responseMsg);
  }



  @Test
  public void testPostWithLink() {
    String responseMsg = webResource.path("encoder").path("gpx/html").queryParam("link", "foo").post(String.class);
    assertEquals("Return with link \"foo\"", "gpx/html?link=foo", responseMsg);
  }



  @Test
  public void testPostWrongTyp() {
    String responseMsg = webResource.path("encoder").path("foo/html").queryParam("link", "foo").post(String.class);
    assertEquals("Should return error", "no support for typ/format", responseMsg);
  }



  @Test
  public void testPostWrongFormat() {
    String responseMsg = webResource.path("encoder").path("gpx/bar").queryParam("link", "foo").post(String.class);
    assertEquals("Should return error", "no support for typ/format", responseMsg);
  }
}
