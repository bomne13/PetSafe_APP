package com.example.capstone;

public class AlumData {

    private int cnoTxt;
    private String imgView, alum_time, alum_name, alum_date;
    private boolean alum_checked;

    public AlumData(int cnoTxt, String alum_date, String imgView, String alum_time, String alum_name, boolean alum_checked) {
        this.cnoTxt = cnoTxt;
        this.alum_date = alum_date;
        this.imgView = imgView;
        this.alum_time = alum_time;
        this.alum_name = alum_name;
        this.alum_checked = alum_checked;

    }

    public String getImgView() {
        return imgView;
    }

    public void setImgView(String imgView) {
        this.imgView = imgView;
    }

    public String getAlum_time() {
        return alum_time;
    }

    public void setAlum_time(String alum_time) {
        this.alum_time = alum_time;
    }

    public String getAlum_name() {
        return alum_name;
    }

    public void setAlum_name(String alum_name) {
        this.alum_name = alum_name;
    }

    public String getAlum_date() {
        return alum_date;
    }

    public void setAlum_date(String alum_date) {
        this.alum_date = alum_date;
    }

    public int getCnoTxt() {
        return cnoTxt;
    }

    public void setCnoTxt(int cnoTxt) {
        this.cnoTxt = cnoTxt;
    }

    public boolean isAlum_checked() {
        return alum_checked;
    }

    public void setAlum_checked(boolean alum_checked) {
        this.alum_checked = alum_checked;
    }
}

