package com.example.capstone;

public class ReplyData {

    private int imgView, rno;
    private String reply_writer, reply_write_date, reply_content, reply_heartbtn1, id;

    public ReplyData(int imgView, String reply_writer, String reply_write_date, String reply_content, String reply_heartbtn1, int rno, String id) {
        this.imgView = imgView;
        this.reply_writer = reply_writer;
        this.reply_write_date = reply_write_date;
        this.reply_content = reply_content;
        this.reply_heartbtn1 = reply_heartbtn1;
        this.rno = rno;
        this.id = id;
    }

    public int getImgView() {
        return imgView;
    }

    public void setImgView(int imgView) {
        this.imgView = imgView;
    }

    public int getRno() {
        return rno;
    }

    public void setRno(int rno) {
        this.rno = rno;
    }

    public String getReply_writer() {
        return reply_writer;
    }

    public void setReply_writer(String reply_writer) {
        this.reply_writer = reply_writer;
    }

    public String getReply_write_date() {
        return reply_write_date;
    }

    public void setReply_write_date(String reply_write_date) {
        this.reply_write_date = reply_write_date;
    }

    public String getReply_content() {
        return reply_content;
    }

    public void setReply_content(String reply_content) {
        this.reply_content = reply_content;
    }

    public String getReply_heartbtn1() {
        return reply_heartbtn1;
    }

    public void setReply_heartbtn1(String reply_heartbtn1) {
        this.reply_heartbtn1 = reply_heartbtn1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

