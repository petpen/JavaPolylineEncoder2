package de.fhb.polyencoder.server.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.*;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;

public class EncodersResourceTest extends JerseyTest {

  public static final String PACKAGE_NAME = "de.fhb.polyencoder.server.resources";
  private static final String DEFAULT_COORDS_GPX = "5, 5\n5.1, 4.3\n5.3, 3.9";
  private static final String DEFAULT_COORDS_GPX_ENCODED_POINTS = "_qo]_qo]}oR~ugCaaf@~bmA";
  private static final String DEFAULT_COORDS_GPX_ENCODED_LEVELS = "PLP";
  private WebResource webResource;



  public EncodersResourceTest() throws Exception {
    super(new WebAppDescriptor.Builder(PACKAGE_NAME).build());
    webResource = resource().path("encoder");
  }



  @Override
  protected TestContainerFactory getTestContainerFactory() {
    return new GrizzlyWebTestContainerFactory();
  }



  @Test
  public void testGetGpxHtml() {
    String responseMsg = webResource.path("gpx/html").get(String.class);
    assertEquals("Should return /encoder/gpx/html", "/encoder/gpx/html", responseMsg);
  }



  @Test
  public void testGetKmlJson() {
    String responseMsg = webResource.path("kml/json").get(String.class);
    assertEquals("Should return /encoder/kml/json", "/encoder/kml/json", responseMsg);
  }



  @Test
  public void testGetKmzJson() {
    String responseMsg = webResource.path("kmz/json").get(String.class);
    assertEquals("Should return /encoder/kmz/json", "/encoder/kmz/json", responseMsg);
  }



  @Test
  public void testGetRawRaw() {
    String responseMsg = webResource.path("raw/raw").get(String.class);
    assertEquals("Should return /encoder/raw/raw", "/encoder/raw/raw", responseMsg);
  }



  @Test
  public void testPostNoLinkNoCoords() {
    String responseMsg = webResource.path("gpx/html").post(String.class);
    assertEquals("Return with empty link", "no input no output", responseMsg);
  }



  @Test
  public void testPostWithLink() {
    String responseMsg = webResource.path("gpx/html").queryParam("link", "foo").post(String.class);
    assertEquals("Return with link \"foo\"", "load form link", responseMsg);
  }



  @Test
  public void testPostWrongTyp() {
    WebResource webRes = webResource.path("foo/html").queryParam("coords", DEFAULT_COORDS_GPX);
    String responseMsg = webRes.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class);
    assertEquals("Should return error", "no support for typ", responseMsg);
  }



  @Test
  public void testPostWrongFormat() {
    WebResource webRes = webResource.path("gpx/bar").queryParam("coords", DEFAULT_COORDS_GPX);
    String responseMsg = webRes.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class);
    assertEquals("Should return error", "no support for format", responseMsg);
  }



  @Test
  public void testPostGpxHtml() {
    WebResource webRes = webResource.path("gpx/html").queryParam("coords", DEFAULT_COORDS_GPX);
    String responseMsg = webRes.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class);
    System.out.println(responseMsg);
    assertTrue("Should include encoded points information", responseMsg.indexOf(DEFAULT_COORDS_GPX_ENCODED_POINTS) >= 0);
    assertTrue("Should include encoded levels information", responseMsg.indexOf(DEFAULT_COORDS_GPX_ENCODED_LEVELS) >= 0);
  }
}
