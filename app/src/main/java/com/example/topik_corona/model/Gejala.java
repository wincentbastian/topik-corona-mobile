package com.example.topik_corona.model;

public class Gejala {
    String id;
    String gejala;
    String kategori_gejala;
    Boolean setSelected;
//    public Gejala(int id, String gejala, int kategori_gejala) {
//        this.id = id;
//        this.gejala = gejala;
//        this.kategori_gejala = kategori_gejala;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getKategori_gejala() {
        return kategori_gejala;
    }

    public void setKategori_gejala(String kategori_gejala) {
        this.kategori_gejala = kategori_gejala;
    }

    public Boolean getSetSelected() {
        return setSelected;
    }

    public void setSetSelected(Boolean setSelected) {
        this.setSelected = setSelected;
    }
}
