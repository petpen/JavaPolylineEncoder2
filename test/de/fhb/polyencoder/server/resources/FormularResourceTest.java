package de.fhb.polyencoder.server.resources;

import static org.junit.Assert.*;
import org.junit.*;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

/**
 * @author Martin Bormeister
 * 
 */
public class FormularResourceTest extends JerseyTest {

  WebResource webResource;



  public FormularResourceTest() throws Exception {
    super("de.fhb.polyencoder.server.resources");
    webResource = resource();
  }



  @Test
  public void testGet() {
    String responseMsg = webResource.path("/").get(String.class);
    assertNotNull("Should return not null", responseMsg);
    assertFalse("Should return not empty string", responseMsg.equals(""));
  }
}
