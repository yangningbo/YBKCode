package com.puxtech.ybk.hangqing.jsondata.getpriceforcommodity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by fanshuo on 16/4/27.
 */
public class FenBi implements Serializable{

    @Expose
    @SerializedName("IDX")
    private int idx;//索引
    @Expose
    @SerializedName("TIME")
    private long time;//时间long
    @Expose
    @SerializedName("PRI")
    private float pri;//价格
    @Expose
    @SerializedName("TVOL")
    private int tvol;//成交量
    @Expose
    @SerializedName("TFLA")
    private int tfla;//买卖标识

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getPri() {
        return pri;
    }

    public void setPri(float pri) {
        this.pri = pri;
    }

    public int getTvol() {
        return tvol;
    }

    public void setTvol(int tvol) {
        this.tvol = tvol;
    }

    public int getTfla() {
        return tfla;
    }

    public void setTfla(int tfla) {
        this.tfla = tfla;
    }
}
