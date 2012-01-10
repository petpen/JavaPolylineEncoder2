package de.fhb.polyencoder.server.view;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.fhb.polyencoder.server.OutputType;

/**
 * @author Martin Bormeister
 * 
 */
public class ViewFactoryTest {

  @Test
  public void testWrongOutput() throws IOException {
    new ViewFactory();
    assertEquals("expect null", null, ViewFactory.buildViewGenerator(null, OutputType.test("java")));
  }
}
