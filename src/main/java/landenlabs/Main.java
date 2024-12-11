/*
    Dennis Lang  (landenlabs.com)
    Dec-2024
 */
package landenlabs;

import java.util.ArrayList;

/**
 * Demonstrate and test parsing and extracting fields form Json.
 */
public class Main {
    static String json1 =
          "{ "
        + "'seriesInfo': "
        + "  {"
        +     "'temp': "
        + "    {"
        + "      'word': '\\\"world\\\"' "
        + "      'boolTrue': true, "
        + "      'posInt': 12345, "
        + "      'negFloat': -12.345, "
        + "      'nullValue': null, "
        + "      'bb': { "
        + "        'tl': { 'lat': 90.05, 'lng': -180.05 }, "
        + "        'br': { 'latStr': '-90.05', 'lngstr': '180.05' } "
        + "       }, "
        + "      'series': [{ 'ts': 111}, { 'ts': 222}, { 'ts': 333}] "
        + "   },"
        + "    'emptyMap': {}, "
        + "    'emptyArray': [], "
        + "    'numArray': [ 1,2,3.1,4.2,'5','6',7,8,9 ] "
        + "  }"
        + "}";

    public static void main(String[] args) {
        System.out.println("[Start]");
        JsonReader reader = new JsonReader(json1.replace('\'', '"'), false);
        // JsonDump.dump(reader.data);

        JsonData data = reader.data;
        System.out.println("    posInt = " + data.getInt("seriesInfo.temp.posInt", -1));
        System.out.println("  negFloat = " + data.getFloat("seriesInfo.temp.negFloat", -1));
        System.out.println("  boolTrue = " + data.get("seriesInfo.temp.boolTrue", false));
        System.out.println(" bb.br.lat = " + data.get("seriesInfo.temp.bb.br.latStr", -1));
        ArrayList<Object> array = data.get("seriesInfo.temp.series", null);
        System.out.println("series size= " + (array != null ? array.size() : 0));
        if (array != null) {
            for (int idx = 0; idx < array.size(); idx++) {
                Integer value = JsonData.get(array.get(idx), "ts", -1);
                System.out.println("  series[" + idx + "]= " + value);
            }
        }
        Object emptyMap = data.get("seriesInfo.emptyMap", "error");
        System.out.println(" emptyMap= " +  emptyMap);

        array = data.get("seriesInfo.emptyArray", new ArrayList<>(10));
        System.out.println(" emptyArray= " + ((array != null) ? array.size() : 0) + " (size)");

        array = data.get("seriesInfo.numArray", null);
        JsonDump.dump(array, 0);
    }
}