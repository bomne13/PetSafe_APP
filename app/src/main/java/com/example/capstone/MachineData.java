package com.example.capstone;

public class MachineData {

    private int imgView;
    private String machine_nickname, machine_name;
    private boolean powerBtn;

    public MachineData(int imgView, String machine_nickname, boolean powerBtn) {
        this.imgView = imgView;
        this.machine_nickname = machine_nickname;
        this.powerBtn = powerBtn;
    }

    public int getImgView() {
        return imgView;
    }

    public void setImgView(int imgView) {
        this.imgView = imgView;
    }

    public String getMachine_nickname() {
        return machine_nickname;
    }

    public void setMachine_nickname(String machine_nickname) {
        this.machine_nickname = machine_nickname;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public void setMachine_name(String machine_name) {
        this.machine_name = machine_name;
    }


    public boolean isPowerBtn() {
        return powerBtn;
    }

    public void setPowerBtn(boolean powerBtn) {
        this.powerBtn = powerBtn;
    }

}
