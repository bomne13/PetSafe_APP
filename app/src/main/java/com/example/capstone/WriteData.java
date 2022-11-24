package com.example.capstone;

public class WriteData {

    private int imgView, bno;
    private String title, write_date, writer, heart, reply;
    //private boolean powerBtn;

    public WriteData(int imgView, String title, String write_date, String writer, String heart, String reply,int bno) {
        this.imgView = imgView;
        this.title = title;
        this.write_date = write_date;
        this.writer = writer;
        this.heart = heart;
        this.reply = reply;
        this.bno = bno;
    }

    public int getImgView() {
        return imgView;
    }

    public void setImgView(int imgView) {
        this.imgView = imgView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWrite_date() {
        return write_date;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getHeart() {
        return heart;
    }

    public void setHeart(String heart) {
        this.heart = heart;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }
}

