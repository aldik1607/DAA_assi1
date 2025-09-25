package util;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {
    private final String filePath;

    public CSVWriter(String filePath) {
        this.filePath = filePath;
    }

    public void write(String header, String data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(header + "\n");
            writer.write(data + "\n");
        }
    }
}
