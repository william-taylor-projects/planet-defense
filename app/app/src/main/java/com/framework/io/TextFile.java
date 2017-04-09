package com.framework.io;

import java.util.*;

public class TextFile {
    private ArrayList<String> lines = new ArrayList<String>();

    public TextFile(String filename) {
        Scanner scanner = ResourceManager.get().getFile(filename);
        String line = null;

        while(true) {
            try {
                line = scanner.nextLine();
            } catch (NoSuchElementException e) {
                scanner.close();
                break;
            }

            if(line.length() > 0 && line.charAt(0) != '#') {
                lines.add(line);
            }
        }
    }

    public ArrayList<String> GetLines() {
        ArrayList<String> ReversedLines = new ArrayList<String>();
        for(int i = lines.size() - 1; i >= 0; --i) {
            ReversedLines.add(lines.get(i));
        } return ReversedLines;
    }
}
