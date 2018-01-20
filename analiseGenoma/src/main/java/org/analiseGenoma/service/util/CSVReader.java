package org.analiseGenoma.service.util;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements Serializable {

    private List<Line> file;

    public CSVReader(byte[] contents) {
        file = new ArrayList<>();
        String[] lines = new String(contents, StandardCharsets.UTF_8).split("\n");
        for (String ln : lines) {
            file.add(new Line(ln));
        }
    }

    public CSVReader(String contents) {
        file = new ArrayList<>();
        String[] lines = contents.split("\n");
        for (String ln : lines) {
            file.add(new Line(ln));
        }
    }

    public List<Line> getFile() {
        return file;
    }

    public Line getFile(int index) {
        return file.get(index);
    }

    public void setFile(List<Line> file) {
        this.file = file;
    }

    public int size() {
        return file.size();
    }

    @Override
    public String toString() {
        return "CSVReader{" + "file=" + file + '}';
    }

}
