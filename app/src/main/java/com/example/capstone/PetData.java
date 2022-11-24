package com.example.capstone;

public class PetData {

    private int pet_photo, pet_pno;
    private String pet_name, pet_date;

    public PetData(int pet_photo, String pet_name, String pet_date, int pet_pno) {
        this.pet_photo = pet_photo;
        this.pet_name = pet_name;
        this.pet_date = pet_date;
        this.pet_pno = pet_pno;
    }

    public int getPet_photo() {
        return pet_photo;
    }

    public void setPet_photo(int pet_photo) {
        this.pet_photo = pet_photo;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_date = pet_name;
    }

    public String getPet_date() {
        return pet_date;
    }

    public void setPet_date(String pet_date) {
        this.pet_date = pet_date;
    }

    public int getPet_pno() {
        return pet_pno;
    }

    public void setPet_pno(int pet_pno) {
        this.pet_pno = pet_pno;
    }
}



