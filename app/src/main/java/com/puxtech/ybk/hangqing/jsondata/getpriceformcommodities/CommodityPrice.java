package com.puxtech.ybk.hangqing.jsondata.getpriceformcommodities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.puxtech.ybk.hangqing.util.PriceUtil;

import java.util.Calendar;

/**
 * 盘口信息，商品一口价
 */
public class CommodityPrice {
    @Expose
    @SerializedName("CID")
    private int cid;//商品编号
    @Expose
    @SerializedName("CTYP")
    private int ctyp;//商品类型
    @Expose
    @SerializedName("MCU")
    private int mcu;//最小变动价位
    @Expose
    @SerializedName("HANN")
    private int hann;//盘口序号
    @Expose
    @SerializedName("LATP")
    private float latp;//最新价
    @Expose
    @SerializedName("BUYP")
    private float buyp;//买价
    @Expose
    @SerializedName("SELP")
    private float selp;//卖价
    @Expose
    @SerializedName("HIGP")
    private float higp;//最高
    @Expose
    @SerializedName("LOWP")
    private float lowp;//最低
    @Expose
    @SerializedName("YCLO")
    private float yclo;//昨收
    @Expose
    @SerializedName("YCLE")
    private float ycle;//昨结
    @Expose
    @SerializedName("OPEP")
    private float opep;//开盘价
    @Expose
    @SerializedName("TIME")
    private long time;//时间
    @Expose
    @SerializedName("BUYA")
    private int buya;//买挂牌数量
    @Expose
    @SerializedName("SELA")
    private int sela;//卖挂牌数量
    @Expose
    @SerializedName("CURA")
    private int cura;//现量
    @Expose
    @SerializedName("TVOL")
    private int tvol;//成交量
    @Expose
    @SerializedName("TAMT")
    private float tamt;//成交金额
    @Expose
    @SerializedName("HOLA")
    private int hola;//总持仓量


    //public methods
    public String getChengJiaoE(){
        return PriceUtil.keepPrecision(tamt,2);
    }

    public String getZuiXinJia(){
        return PriceUtil.keepPrecision(latp, mcu);
    }

    public String getZhangDieText(){
        return PriceUtil.keepPrecision(latp - yclo, mcu);
    }
    public float getZhangDie(){
        return (latp - yclo);
    }

    public String getZhangFu(){
        if(yclo == 0){
            return "--";
        }
        float zhangDie = latp-yclo;
        float zhangFu = zhangDie/yclo;
        return PriceUtil.formatPercentage(zhangFu);
    }

    public String getZhenFu(){
        if(yclo == 0){
            return "--";
        }
        float zhenFu = (higp - lowp)/yclo;
        return PriceUtil.formatPercentage(zhenFu);
    }

    public String getZuiDi(){
        if(lowp == 0){
            return "--";
        }
        return PriceUtil.keepPrecision(lowp, mcu);
    }

    public String getZuiGao(){
        if(higp == 0){
            return "--";
        }
        return PriceUtil.keepPrecision(higp,mcu);
    }

    public String getZuoShou(){
        if(yclo == 0){
            return "--";
        }
        return PriceUtil.keepPrecision(yclo,mcu);
    }

    //getters and setters
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getCtyp() {
        return ctyp;
    }

    public void setCtyp(int ctyp) {
        this.ctyp = ctyp;
    }

    public int getMcu() {
        return mcu;
    }

    public void setMcu(int mcu) {
        this.mcu = mcu;
    }

    public int getHann() {
        return hann;
    }

    public void setHann(int hann) {
        this.hann = hann;
    }

    public float getLatp() {
        return latp;
    }

    public void setLatp(int latp) {
        this.latp = latp;
    }

    public float getBuyp() {
        return buyp;
    }

    public void setBuyp(int buyp) {
        this.buyp = buyp;
    }

    public float getSelp() {
        return selp;
    }

    public void setSelp(int selp) {
        this.selp = selp;
    }

    public float getHigp() {
        return higp;
    }

    public void setHigp(int higp) {
        this.higp = higp;
    }

    public float getLowp() {
        return lowp;
    }

    public void setLowp(int lowp) {
        this.lowp = lowp;
    }

    public float getYclo() {
        return yclo;
    }

    public void setYclo(int yclo) {
        this.yclo = yclo;
    }

    public float getYcle() {
        return ycle;
    }

    public void setYcle(int ycle) {
        this.ycle = ycle;
    }

    public float getOpep() {
        return opep;
    }

    public void setOpep(int opep) {
        this.opep = opep;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getBuya() {
        return buya;
    }

    public void setBuya(int buya) {
        this.buya = buya;
    }

    public int getSela() {
        return sela;
    }

    public void setSela(int sela) {
        this.sela = sela;
    }

    public int getCura() {
        return cura;
    }

    public void setCura(int cura) {
        this.cura = cura;
    }

    public int getTvol() {
        return tvol;
    }

    public void setTvol(int tvol) {
        this.tvol = tvol;
    }

    public float getTamt() {
        return tamt;
    }

    public void setTamt(int tamt) {
        this.tamt = tamt;
    }

    public int getHola() {
        return hola;
    }

    public void setHola(int hola) {
        this.hola = hola;
    }
}
