package com.framework.io;

import android.content.Context;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;

public class ResourceFile {
    private BufferedReader in;
    private String Filename;

    public ResourceFile(String filename) {
        Filename = filename;
    }

    public boolean Exists() {
        Context c = ResourceManager.get().getContext();
        return(new File(c.getFilesDir(), Filename).exists());
    }

    public ArrayList<String> ReadFile() {
        Scanner File = ResourceManager.get().getFile(Filename);
        ArrayList<String> lines = new ArrayList<String>();
        while(true) {
            String line = null;
            try {
                line = File.nextLine();
            } catch (NoSuchElementException e) {
                File.close();
                break;
            }

            if(!line.isEmpty() && line.charAt(0) != '#') {
                int last = line.indexOf(' ');
                if(last > 0) {
                    lines.add(line.substring(0, last));
                }
            } else if(!line.isEmpty() && line.charAt(0) != '#' && line.charAt(0) != ' ') {
                lines.add(line);
            } else {
                continue;
            }
        } return lines;
    }

    public void Write(ArrayList<String> objects) {
        // Grab the games context
        Context c = ResourceManager.get().getContext();
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(new File(c.getFilesDir(), Filename)));
            for(String o : objects) {
                if(o != null) {
                    out.write(o);
                    out.newLine();
                }
            } out.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void PrepareToRead() {
        Context c = ResourceManager.get().getContext();
        try {
            in = new BufferedReader(new FileReader(new File(c.getFilesDir(), Filename)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Close() {
        try {
            in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String ReadLine() {
        try {
            return in.readLine();
        } catch (Exception e) {
            System.out.println(e.toString());
        } return "";
    }
}
