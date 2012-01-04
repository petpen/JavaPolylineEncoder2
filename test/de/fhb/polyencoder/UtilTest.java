package de.fhb.polyencoder;

import static org.junit.Assert.*;
import org.junit.*;

public class UtilTest {

  @Test
  public void testReplaceMarker() {
    String text = "Hi {name}";
    String marker = "name";
    String replace = "User";

    assertEquals("The new String must be \"Hi User\"", "Hi User", Util.replaceMarker(text, marker, replace));
  }



  @Test
  public void testReadExistFile() {
    String content = Util.readFile("output/html/maps.html");
    assertFalse("Should return no empty content", content.equals(""));
  }



  @Test
  public void testReadNotExistFile() {
    String content = Util.readFile("output/html/mapssss.html");
    assertTrue("Should return empty content", content.equals(""));
  }



  @Test
  public void testCreateCenter() {
    assertTrue("Center must be 5", 5 == Util.createCenter(-10, 20));
  }



  @Test
  public void testCreateCenterMaxMinFlipped() {
    assertTrue("Center must be 5", 5 == Util.createCenter(20, -10));
  }
}
