package com.example.projet;

import java.util.HashMap;
import java.util.Map;

public class EtudiantsMatieresGroupes {

    private String idEtudiant;

    private String promoEtudiant;

    private String nomEtudiant;

    private String prenomEtudiant;

    private Map<String, String> matiereGroupe;

    public EtudiantsMatieresGroupes(){
        this.matiereGroupe = new HashMap<String, String>();
    }

    public String getIdEtudiant() {
        return idEtudiant;
    }

    public void setIdEtudiant(String idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public String getPromoEtudiant() {
        return promoEtudiant;
    }

    public void setPromoEtudiant(String promoEtudiant) {
        this.promoEtudiant = promoEtudiant;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public void setPrenomEtudiant(String prenomEtudiant) {
        this.prenomEtudiant = prenomEtudiant;
    }

    public Map<String, String> getMatiereGroupe() {
        return matiereGroupe;
    }

    public void addMatiereGroupe(String matiere, String groupe) {
        this.matiereGroupe.put(matiere, groupe);
    }

    @Override
    public String toString() {
        return "EtudiantsMatieresGroupes{" +
                "idEtudiant='" + idEtudiant + '\'' +
                ", promoEtudiant='" + promoEtudiant + '\'' +
                ", nomEtudiant='" + nomEtudiant + '\'' +
                ", prenomEtudiant='" + prenomEtudiant + '\'' +
                ", matiereGroupe=" + matiereGroupe +
                '}'+"\n";
    }
}
