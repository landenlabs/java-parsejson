/*
    Dennis Lang  (landenlabs.com)
    Dec-2024
 */
package landenlabs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Demonstrate and test parsing and extracting fields form Json.
 */
public class Main {


    public static void main(String[] args) {
      if (args.length == 0) {
          Test1.test();
      } else {
          try {
              String json = Files.readString(Paths.get(args[0]));
              JsonReader reader = new JsonReader(json, true);
              JsonDump.dump(reader.data);
          } catch (Exception ex) {
              System.err.println(ex);
          }
      }
    }
}