package com.example.projet;

public class AbsenceData {
    private int id;
    private String heureabs;
    private String dateabs;
    private String justifabs;

    public AbsenceData(int id, String heureabs, String dateabs, String justifabs) {
        this.id = id;
        this.heureabs = heureabs;
        this.dateabs = dateabs;
        this.justifabs =justifabs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeureabs() {
        return heureabs;
    }

    public void setHeureabs(String heureabs) {
        this.heureabs = heureabs;
    }

    public String getDateabs() {
        return dateabs;
    }

    public void setDateabs(String dateabs) {
        this.dateabs = dateabs;
    }
    public String getJustifabs() {
        return justifabs;
    }
    public void setJustifabs(String justifabs) {
        this.dateabs = justifabs;
    }
}
