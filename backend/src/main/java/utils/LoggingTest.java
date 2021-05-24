/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
/**
 *
 * @author marcg
 */
public class LoggingTest {

  private static Logger LOGGER = LoggerFactory.getLogger(LoggingTest.class);
  private static final Marker IMPORTANT = MarkerFactory.getMarker("IMPORTANT");
  public static void main(String[] args) {

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);

        LOGGER.info("Hello from Logback {}", data);

  }

}
