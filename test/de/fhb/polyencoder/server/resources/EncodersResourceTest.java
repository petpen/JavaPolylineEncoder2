package de.fhb.polyencoder.server.resources;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;

import org.junit.*;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;

import de.fhb.polyencoder.Util;

public class EncodersResourceTest extends JerseyTest {

  public static final String PACKAGE_NAME = "de.fhb.polyencoder.server.resources";
  private static final String DEFAULT_VALID_LINK = "http://www.google.de/robots.txt";
  private static final String DEFAULT_COORDS_GPX = Util.readFile("testfiles/gpx/threeWaypoints_GPX_1.0.gpx");
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



  // @Test
  // public void testGetGpxValidLink() {
  // WebResource webRes = webResource.path("gpx/bar").queryParam("link",
  // DEFAULT_VALID_LINK);
  // String responseMsg = webRes.get(String.class);
  // assertEquals("Should return <p>Invalid link.</p>", responseMsg);
  // }

  @Test
  public void testGetGpxWithOutLink() {
    String responseMsg = webResource.path("gpx/html").get(String.class);
    assertTrue("Should return <p>Invalid link.</p>", responseMsg.indexOf("<p>Invalid link.</p>") >= 0);
    responseMsg = webResource.path("gpx/json").get(String.class);
    assertTrue("Should return \"message\": \"Invalid link.\"", responseMsg.indexOf("\"message\": \"Invalid link.\"") >= 0);
    responseMsg = webResource.path("gpx/xml").get(String.class);
    assertTrue("Should return <status code=\"400\">Invalid link.</status>", responseMsg.indexOf("<status code=\"400\">Invalid link.</status>") >= 0);
    responseMsg = webResource.path("gpx/raw").get(String.class);
    assertEquals("Should return \"Invalid link.\"", "400\nInvalid link.", responseMsg);
  }



  @Test
  public void testGetKmlWithOutLink() {
    String responseMsg = webResource.path("kml/html").get(String.class);
    assertTrue("Should return <p>Invalid link.</p>", responseMsg.indexOf("<p>Invalid link.</p>") >= 0);
    responseMsg = webResource.path("kml/json").get(String.class);
    assertTrue("Should return \"message\": \"Invalid link.\"", responseMsg.indexOf("\"message\": \"Invalid link.\"") >= 0);
    responseMsg = webResource.path("kml/xml").get(String.class);
    assertTrue("Should return <status code=\"400\">Invalid link.</status>", responseMsg.indexOf("<status code=\"400\">Invalid link.</status>") >= 0);
    responseMsg = webResource.path("kml/raw").get(String.class);
    assertEquals("Should return \"Invalid link.\"", "400\nInvalid link.", responseMsg);
  }



  @Test
  public void testGetKmzWithOutLink() {
    String responseMsg = webResource.path("kmz/html").get(String.class);
    assertTrue("Should return <p>Invalid link.</p>", responseMsg.indexOf("<p>Invalid link.</p>") >= 0);
    responseMsg = webResource.path("kmz/json").get(String.class);
    assertTrue("Should return \"message\": \"Invalid link.\"", responseMsg.indexOf("\"message\": \"Invalid link.\"") >= 0);
    responseMsg = webResource.path("kmz/xml").get(String.class);
    assertTrue("Should return <status code=\"400\">Invalid link.</status>", responseMsg.indexOf("<status code=\"400\">Invalid link.</status>") >= 0);
    responseMsg = webResource.path("kmz/raw").get(String.class);
    assertEquals("Should return \"Invalid link.\"", "400\nInvalid link.", responseMsg);
  }



  @Test
  public void testGetRawWithOutLink() {
    String responseMsg = webResource.path("raw/html").get(String.class);
    assertTrue("Should return <p>Invalid link.</p>", responseMsg.indexOf("<p>Invalid link.</p>") >= 0);
    responseMsg = webResource.path("raw/json").get(String.class);
    assertTrue("Should return \"message\": \"Invalid link.\"", responseMsg.indexOf("\"message\": \"Invalid link.\"") >= 0);
    responseMsg = webResource.path("raw/xml").get(String.class);
    assertTrue("Should return <status code=\"400\">Invalid link.</status>", responseMsg.indexOf("<status code=\"400\">Invalid link.</status>") >= 0);
    responseMsg = webResource.path("raw/raw").get(String.class);
    assertEquals("Should return \"Invalid link.\"", "400\nInvalid link.", responseMsg);
  }



  @Test
  public void testPostNoLinkNoCoords() {
    String responseMsg = webResource.path("gpx/raw").post(String.class);
    assertEquals("Return with empty link", "400\nNo data found.", responseMsg);
  }



  @Test
  public void testPostWithLink() {
    String responseMsg = webResource.path("gpx/raw").queryParam("link", "foo").post(String.class);
    assertEquals("Return with link \"foo\"", "400\nNo data found.", responseMsg);
  }



  @Test
  public void testPostWrongTyp() {
    WebResource webRes = webResource.path("foo/raw").queryParam("coords", DEFAULT_COORDS_GPX);
    String responseMsg = webRes.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class);
    assertEquals("Should return error", "400\n No inputformat specified or not supported.", responseMsg);
  }



  @Test
  public void testPostWrongFormat() {
    WebResource webRes = webResource.path("gpx/bar").queryParam("coords", DEFAULT_COORDS_GPX);
    String responseMsg = webRes.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class);
    assertEquals("Should return error", "400\n Wrong outputformat specified or not supported.", responseMsg);
  }



  @Test
  public void testPostGpxHtml() {
    WebResource webRes = webResource.path("gpx/html").queryParam("coords", DEFAULT_COORDS_GPX);
    String responseMsg = webRes.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class);
    assertTrue("Should include encoded points information", responseMsg.indexOf(DEFAULT_COORDS_GPX_ENCODED_POINTS) >= 0);
    assertTrue("Should include encoded levels information", responseMsg.indexOf(DEFAULT_COORDS_GPX_ENCODED_LEVELS) >= 0);
  }



  @Test
  public void testPostGpxJson() {
    WebResource webRes = webResource.path("gpx/json").queryParam("coords", DEFAULT_COORDS_GPX);
    String responseMsg = webRes.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(String.class);
    assertTrue("Should include encoded points information", responseMsg.indexOf(DEFAULT_COORDS_GPX_ENCODED_POINTS) >= 0);
    assertTrue("Should include encoded levels information", responseMsg.indexOf(DEFAULT_COORDS_GPX_ENCODED_LEVELS) >= 0);
  }
}
