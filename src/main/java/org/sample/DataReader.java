package org.sample;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DataReader {
    List<Map<String, String>> readData(String path, String sheet) throws IOException;
}
