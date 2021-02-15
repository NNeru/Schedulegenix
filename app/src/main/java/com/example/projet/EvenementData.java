package com.example.projet;


public class EvenementData {
    private int id;
    private String evenement;
    private String description;
    private String date_;
    private byte[] image;

    public EvenementData(int id, String evenement, String description, String date_, byte[] image) {
        this.id = id;
        this.evenement = evenement;
        this.description = description;
        this.date_ = date_;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvenement() {
        return evenement;
    }

    public void setEvenement(String evenement) {
        this.evenement = evenement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDate_(){return date_;}
    public void setDate_(String date){this.date_=date_;}

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
