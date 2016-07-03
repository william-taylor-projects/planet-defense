package com.planetDefense.common;

import com.framework.dialogs.MessageBox;
import com.framework.io.ResourceFile;

import java.util.ArrayList;

public class Statistics {
    public class Statistic {
        public Integer totalWaves;
        public Integer totalGames;
        public Integer killCount;
        public Integer levels;
    }

    private static Statistics instance;
    private ResourceFile saveFile;
    private Statistic stats;

    private Boolean first;

    private Statistics() {
        this.saveFile = new ResourceFile("save.xt");
        this.stats = new Statistic();
        this.first = false;

        if(!saveFile.Exists()) {
            ArrayList<String> integer = new ArrayList<String>();

            integer.add(String.valueOf(0));
            integer.add(String.valueOf(0));
            integer.add(String.valueOf(0));
            integer.add(String.valueOf(0));

            saveFile.Write(integer);
            first = true;
        }

        stats = getStatistics();
    }

    public static Statistics get() {
        if(instance == null) {
            instance = new Statistics();
        } return instance;
    }

    public void release() {
        if(saveFile.Exists()) {
            ArrayList<String> integer = new ArrayList<String>();

            integer.add(String.valueOf(stats.totalWaves));
            integer.add(String.valueOf(stats.totalGames));
            integer.add(String.valueOf(stats.killCount));
            integer.add(String.valueOf(stats.levels));

            // save it to the file
            saveFile.Write(integer);
        }
    }

    public Statistics newRecord(Integer wave) {
        if(stats.levels < wave) {
            stats.levels = wave;
        } return this;
    }

    public Statistic readReadStats() {
        stats = getStatistics();
        return(stats);
    }

    public Statistics resetStats() {
        ArrayList<String> integer = new ArrayList<String>();
        integer.add(String.valueOf(0));
        integer.add(String.valueOf(0));
        integer.add(String.valueOf(0));
        integer.add(String.valueOf(0));
        saveFile.Write(integer);
        return this;
    }

    private Statistic getStatistics() {
        try {
            saveFile.PrepareToRead();

            stats.totalWaves = Integer.valueOf(saveFile.ReadLine());
            stats.totalGames = Integer.valueOf(saveFile.ReadLine());
            stats.killCount = Integer.valueOf(saveFile.ReadLine());
            stats.levels = Integer.valueOf(saveFile.ReadLine());

            saveFile.Close();
        } catch(Exception e) {
            MessageBox error = new MessageBox();
            error.setMessage(e.getMessage());
            error.setTitle(e.toString());
            error.show(true);
        }

        return(stats);
    }

    public Statistics waveDone() {
        if(stats != null) {
            stats.totalWaves++;
        } return this;
    }

    public Statistics gamePlayed() {
        if(stats != null) {
            stats.totalGames++;
        } return this;
    }

    public Statistics enemyDown() {
        if(stats != null) {
            stats.killCount++;
        } return this;
    }

    public Boolean isFirstTimePlaying() {
        return(first);
    }

    public Statistic getStats() {
        return stats;
    }
}