package de.fhb.polyencoder.resources.encoder;

import static org.junit.Assert.*;
import org.junit.*;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class GpxHtmlResourceTest extends JerseyTest {

  WebResource webResource;



  public GpxHtmlResourceTest() throws Exception {
    super("de.fhb.polyencoder.resources");
    webResource = resource();
  }



  @Test
  public void testGet() {
    String responseMsg = webResource.path("encoder/gpx/html").get(String.class);
    assertEquals("Should return /encoder/gpx/html", "/encoder/gpx/html", responseMsg);
  }



  @Test
  public void testPostEmptyInput() {
    String responseMsg = webResource.path("encoder/gpx/html").post(String.class);
    assertEquals("Return should be empty", "", responseMsg);
  }



  @Test
  public void testPostWithInput() {
    String responseMsg = webResource.path("encoder/gpx/html").queryParam("link", "foo").post(String.class);
    assertEquals("Return should be \"foo\"", "foo", responseMsg);
  }
}
