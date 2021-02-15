package com.example.projet;

public class Prof {

    private String idProf;

    private String nomProf;

    private String prenomProf;

    private String departementProf;


    public String getIdProf() {
        return idProf;
    }

    public void setIdProf(String idProf) {
        this.idProf = idProf;
    }

    public String getNomProf() {
        return nomProf;
    }

    public void setNomProf(String nomProf) {
        this.nomProf = nomProf;
    }

    public String getPrenomProf() {
        return prenomProf;
    }

    public void setPrenomProf(String prenomProf) {
        this.prenomProf = prenomProf;
    }

    public String getDepartementProf() {
        return departementProf;
    }

    public void setDepartementProf(String departementProf) {
        this.departementProf = departementProf;
    }

    @Override
    public String toString() {
        return "Prof{" +
                "idProf='" + idProf + '\'' +
                ", nomProf='" + nomProf + '\'' +
                ", prenomProf='" + prenomProf + '\'' +
                ", departementProf='" + departementProf + '\'' +
                '}';
    }
}
