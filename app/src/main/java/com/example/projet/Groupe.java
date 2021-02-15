package com.example.projet;


public class Groupe {

    private String idGroupe;

    private String lieblleGroupe;

    private String idMatiereGroupe;

    private String pathGroupe;

    public String getIdGroupe() {
        return idGroupe;
    }

    public void setIdGroupe(String idGroupe) {
        this.idGroupe = idGroupe;
    }

    public String getLieblleGroupe() {
        return lieblleGroupe;
    }

    public void setLieblleGroupe(String lieblleGroupe) {
        this.lieblleGroupe = lieblleGroupe;
    }

    public String getIdMatiereGroupe() {
        return idMatiereGroupe;
    }

    public void setIdMatiereGroupe(String idMatiereGroupe) {
        this.idMatiereGroupe = idMatiereGroupe;
    }

    public String getPathGroupe() {
        return pathGroupe;
    }

    public void setPathGroupe(String pathGroupe) {
        this.pathGroupe = pathGroupe;
    }

    @Override
    public String toString() {
        return "Groupe{" +
                "idGroupe='" + idGroupe + '\'' +
                ", lieblleGroupe='" + lieblleGroupe + '\'' +
                ", idMatiereGroupe='" + idMatiereGroupe + '\'' +
                ", pathGroupe='" + pathGroupe + '\'' +
                '}';
    }
}
