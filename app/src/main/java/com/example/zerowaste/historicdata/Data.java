package com.example.zerowaste.historicdata;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table")
public class Data {
    @PrimaryKey(autoGenerate = true)
    private int pk;
    private int id;
    private int BID;
    private int PID;
    private double LAT;
    private double LON;
    private int ITERATOR;
    private String BF_TIMESTAMP;
    private String BF_URL;
    private String AF_TIMESTAMP;
    private String AF_URL;
    private String TIMESTAMP;

    public Data(int id, int BID, int PID, double LAT, double LON, int ITERATOR, String BF_TIMESTAMP, String BF_URL, String AF_TIMESTAMP, String AF_URL, String TIMESTAMP) {
        this.id = id;
        this.BID = BID;
        this.PID = PID;
        this.LAT = LAT;
        this.LON = LON;
        this.ITERATOR = ITERATOR;
        this.BF_TIMESTAMP = BF_TIMESTAMP;
        this.BF_URL = BF_URL;
        this.AF_TIMESTAMP = AF_TIMESTAMP;
        this.AF_URL = AF_URL;
        this.TIMESTAMP = TIMESTAMP;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getPk() {
        return pk;
    }

    public int getId() {
        return id;
    }

    public int getBID() {
        return BID;
    }

    public int getPID() {
        return PID;
    }

    public double getLAT() {
        return LAT;
    }

    public double getLON() {
        return LON;
    }

    public int getITERATOR() {
        return ITERATOR;
    }

    public String getBF_TIMESTAMP() {
        return BF_TIMESTAMP;
    }

    public String getBF_URL() {
        return BF_URL;
    }

    public String getAF_TIMESTAMP() {
        return AF_TIMESTAMP;
    }

    public String getAF_URL() {
        return AF_URL;
    }

    public String getTIMESTAMP() {
        return TIMESTAMP;
    }
}