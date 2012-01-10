package de.fhb.polyencoder;

import static org.junit.Assert.*;

import java.util.HashMap;

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
  public void testReplaceMarkerOneBackslash() {
    String text = "Hi {name}";
    String marker = "name";
    String replace = "\\User";
    assertEquals("The new String must be \"Hi User\"", "Hi \\User", Util.replaceMarker(text, marker, replace));
  }



  @Test
  public void testReplaceMarkerHashMap() {
    String text = "Hi {name} {nachname}";
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("name", "foo");
    map.put("nachname", "bar");

    assertEquals("The new String must be \"Hi foo bar\"", "Hi foo bar", Util.replaceMarker(text, map));
  }



  @Test
  public void testReadExistFile() {
    String content = Util.readFile("templates/html/maps.html");
    assertFalse("Should return no empty content", content.equals(""));
  }



  @Test
  public void testReadNotExistFile() {
    String content = Util.readFile("templates/html/mapssss.html");
    assertTrue("Should return empty content", content.equals(""));
  }



  @Test
  public void testReadFile() {
    String content = Util.readFile("testfiles/readFile.test");
    assertEquals("Should return right content", "hello world\n!\"d$%&/\nok!", content);
  }



  @Test
  public void testCreateCenter() {
    assertTrue("Center must be 5", 5 == Util.createCenter(-10, 20));
  }



  @Test
  public void testCreateCenterMaxMinFlipped() {
    assertTrue("Center must be 5", 5 == Util.createCenter(20, -10));
  }



  @Test
  public void testIsString() {
    assertTrue("String should be a String", Util.isStringNotEmpty("Test"));
  }



  @Test
  public void testIsEmptyString() {
    assertFalse("empty string should act as empty", Util.isStringNotEmpty(""));
  }



  @Test
  public void testIsNotString() {
    assertFalse("null should act as empty", Util.isStringNotEmpty(null));
  }



  @Test
  public void testEncodeFalseBackslash() {
    String in = "\n";
    String out = "\n";
    assertEquals("A backslash which acts as an control character should not be encoded.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeOneBackslashOnly() {
    String in = "\\";
    String out = "\\\\";
    assertEquals("One backslash must be encoded to two backslashes.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeOneBackslashOnBeginning() {
    String in = "\\Test";
    String out = "\\\\Test";
    assertEquals("One backslash must be encoded to two backslashes.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeOneBackslashOnEnd() {
    String in = "Test\\";
    String out = "Test\\\\";
    assertEquals("One backslash must be encoded to two backslashes.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeOneBackslashMiddle() {
    String in = "Te\\st";
    String out = "Te\\\\st";
    assertEquals("One backslash must be encoded to two backslashes.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeTwoBackslashes() {
    String in = "\\\\";
    String out = "\\\\\\\\";
    assertEquals("Should be four backslashes after encoding.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeTwoBackslashesOnBeginning() {
    String in = "\\\\Test";
    String out = "\\\\\\\\Test";
    assertEquals("Should be four backslashes after encoding.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeTwoBackslashesOnEnd() {
    String in = "Test\\\\";
    String out = "Test\\\\\\\\";
    assertEquals("Should be four backslashes after encoding.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeTwoBackslashesMiddle() {
    String in = "Te\\\\st";
    String out = "Te\\\\\\\\st";
    assertEquals("Should be four backslashes after encoding.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeTwoBackslashesDiverted() {
    String in = "Te\\st\\";
    String out = "Te\\\\st\\\\";
    assertEquals("Should be four backslashes after encoding.", out, Util.protectBackslashes(in));
  }



  @Test
  public void testEncodeBackslashesUneven() {
    String in = "Te\\\nst";
    String out = "Te\\\\\nst";
    assertEquals("Should be two backslashes and a linewrap after encoding.", out, Util.protectBackslashes(in));
  }
}
