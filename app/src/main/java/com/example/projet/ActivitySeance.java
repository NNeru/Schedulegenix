package com.example.projet;


public class ActivitySeance {

    private String nomMatiereSeance;

    private String typeSeance;

    private String timeZoneSeance;

    public String getNomMatiereSeance() {
        return nomMatiereSeance;
    }

    public void setNomMatiereSeance(String nomSeance) {
        this.nomMatiereSeance = nomSeance;
    }

    public String getTypeSeance() {
        return typeSeance;
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }

    public String getTimeZoneSeance() {
        return timeZoneSeance;
    }

    public void setTimeZoneSeance(String timeZoneSeance) {
        this.timeZoneSeance = timeZoneSeance;
    }

    @Override
    public String toString() {
        return "ActivitySeance{" +
                "nomMatiereSeance='" + nomMatiereSeance + '\'' +
                ", typeSeance='" + typeSeance + '\'' +
                ", timeZoneSeance='" + timeZoneSeance + '\'' +
                '}';
    }
}
