package de.fhb.polyencoder;

import static org.junit.Assert.assertEquals;
import de.fhb.polyencoder.PolyEncoder;

import org.junit.Test;

public class PolyEncoderTest {

  @Test
  public void work() {
    assertEquals("Der gelesene Token ist nicht gleich dem geschriebenen Token.", "hello world", PolyEncoder.work("hello world"));
  }
}
