package com.example.capstone;

public class AlarmData {

    private int imgView;
    private String alarm_name, alarm_date;

    public AlarmData(int imgView, String alarm_name, String alarm_date) {
        this.imgView = imgView;
        this.alarm_name = alarm_name;
        this.alarm_date = alarm_date;
    }

    public int getImgView() {
        return imgView;
    }

    public void setImgView(int imgView) {
        this.imgView = imgView;
    }

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public String getAlarm_date() {
        return alarm_date;
    }

    public void setAlarm_date(String alarm_date) {
        this.alarm_date = alarm_date;
    }


}
