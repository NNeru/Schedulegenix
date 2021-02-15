package com.example.projet;

public class Salle {

    private String idSalle;

    private String nomSalle;

    private String typeSalle;

    public String getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(String idSalle) {
        this.idSalle = idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }
    public String getTypeSalle() {
        return typeSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public void setTypeSalle(String typeSalle) {
        this.typeSalle = typeSalle;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "idSalle='" + idSalle + '\'' +
                ", nomSalle='" + nomSalle + '\'' +
                ", typeSalle='" + typeSalle + '\'' +
                '}';
    }
}
