package com.dudu.domain;

/**
 * Created by tengj on 2017/3/29.
 */
public class JytClaimMain {

    //描述
    private String FFaultDesc ;

    //最小里程
    private String mix;

    //最大里程
    private String max;

    //车型
    private String FProModel;

    //车系
    private String FProSerie;


    //标签
    private String FKGFlag;


    //相似度
    private String bfb;

    public String getFFaultDesc() {
        return FFaultDesc;
    }

    public void setFFaultDesc(String FFaultDesc) {
        this.FFaultDesc = FFaultDesc;
    }

    public String getMix() {
        return mix;
    }

    public void setMix(String mix) {
        this.mix = mix;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getFProModel() {
        return FProModel;
    }

    public void setFProModel(String FProModel) {
        this.FProModel = FProModel;
    }

    public String getFKGFlag() {
        return FKGFlag;
    }

    public void setFKGFlag(String FKGFlag) {
        this.FKGFlag = FKGFlag;
    }

    public String getFProSerie() {
        return FProSerie;
    }

    public void setFProSerie(String FProSerie) {
        this.FProSerie = FProSerie;
    }

    public String getBfb() {
        return bfb;
    }

    public void setBfb(String bfb) {
        this.bfb = bfb;
    }
}
