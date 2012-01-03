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
}
