package com.example.aplikasibookingruangan_fp_pam.Model;

public class ModelData {
    String ruangan, id;

    public ModelData(){

    }

    public ModelData(String ruangan, String id) {
        this.ruangan = ruangan;
        this.id = id;
    }

    public String getRuangan() {
        return ruangan;
    }

    public void setRuangan(String ruangan) {
        this.ruangan = ruangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
