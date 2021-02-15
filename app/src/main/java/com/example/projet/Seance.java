package com.example.projet;


import java.util.ArrayList;
import java.util.List;

public class Seance {

    private String idSeance;

    private String activityId;

    private String dateSeance;

    private String heureDebutSeance;

    private String heureFinSeance;

    private String nameSeance;

    private int jourSeance;

    private int weekSeance;

    private int durationSeance;

    private String infoSeance;

    private String idMatiereSeance;

    private String nomMatiereSeance;

    private List<String> listeSalles;

    private List<String> listeProfs;

    private List<String> listeGroupes;

    private boolean isControle;

    private String couleurSeance;

    public Seance(){
        this.listeProfs = new ArrayList<>();
        this.listeSalles = new ArrayList<>();
        this.listeGroupes = new ArrayList<>();
        this.isControle = false;
    }
/*
    public Seance(String idSeance, String activityId, String dateSeance, String heureDebutSeance, String heureFinSeance, String nameSeance, int jourSeance, int weekSeance, String infoSeance, String idMatiereSeance, String nomMatiereSeance, String couleurSeance) {
        this.idSeance = idSeance;
        this.activityId = activityId;
        this.dateSeance = dateSeance;
        this.heureDebutSeance = heureDebutSeance;
        this.heureFinSeance = heureFinSeance;
        this.nameSeance = nameSeance;
        this.jourSeance = jourSeance;
        this.weekSeance = weekSeance;
        this.infoSeance = infoSeance;
        this.idMatiereSeance = idMatiereSeance;
        this.nomMatiereSeance = nomMatiereSeance;
        this.couleurSeance = couleurSeance;
    }*/

    public String getIdSeance() {
        return idSeance;
    }

    public void setIdSeance(String idSeance) {
        this.idSeance = idSeance;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getDateSeance() {
        return dateSeance;
    }

    public void setDateSeance(String dateSeance) {
        this.dateSeance = dateSeance;
    }

    public String getHeureDebutSeance() {
        return heureDebutSeance;
    }

    public void setHeureDebutSeance(String heureDebutSeance) {
        this.heureDebutSeance = heureDebutSeance;
    }

    public int getTransitionYSeance(){
        String[] heureMin = this.heureDebutSeance.split(":");
        return 3*(60*(Integer.parseInt(heureMin[0])-7) + Integer.parseInt(heureMin[1]));
    }

    public int getHeightSeance(){
        return this.durationSeance*15*3;
    }

    public String getHeureFinSeance() {
        return heureFinSeance;
    }

    public void setHeureFinSeance(String heureFinSeance) {
        this.heureFinSeance = heureFinSeance;
    }

    public String getNameSeance() {
        return nameSeance;
    }

    public void setNameSeance(String nameSeance) {
        this.nameSeance = nameSeance;
    }

    public int getJourSeance() {
        return jourSeance;
    }

    public void setJourSeance(int jourSeance) {
        this.jourSeance = jourSeance;
    }

    public int getWeekSeance() {
        return weekSeance;
    }

    public void setWeekSeance(int weekSeance) {
        this.weekSeance = weekSeance;
    }

    public int getDurationSeance() {
        return durationSeance;
    }

    public void setDurationSeance(int durationSeance) {
        this.durationSeance = durationSeance;
    }

    public String getInfoSeance() {
        return infoSeance;
    }

    public void setInfoSeance(String infoSeance) {
        this.infoSeance = infoSeance;
    }

    public String getIdMatiereSeance() {
        return idMatiereSeance;
    }

    public void setIdMatiereSeance(String codeMatiereSeance) {
        this.idMatiereSeance = codeMatiereSeance;
    }

    public String getNomMatiereSeance() {
        return nomMatiereSeance;
    }

    public void setNomMatiereSeance(String nomMatiereSeance) {
        this.nomMatiereSeance = nomMatiereSeance;
    }


    public List<String> getListeSalles() {
        return listeSalles;
    }

    public void addSalles(String idSalle) {
        this.listeSalles.add(idSalle);
    }

    public List<String> getListeProfs() {
        return listeProfs;
    }

    public void addProfs(String idProf) {
        this.listeProfs.add(idProf);
    }

    public List<String> getListeGroupes() {
        return listeGroupes;
    }

    public void addGroupes(String idGroupe) {
        this.listeGroupes.add(idGroupe);
    }

    public boolean isControle() {
        return isControle;
    }

    public void setControle(boolean controle) {
        isControle = controle;
    }

    public String getCouleurSeance() {
        return couleurSeance;
    }

    public void setCouleurSeance(String coleurSeance) {
        this.couleurSeance = coleurSeance;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "idSeance='" + idSeance + '\'' +
                ", activityId='" + activityId + '\'' +
                ", dateSeance='" + dateSeance + '\'' +
                ", heureDebutSeance='" + heureDebutSeance + '\'' +
                ", heureFinSeance='" + heureFinSeance + '\'' +
                ", nameSeance='" + nameSeance + '\'' +
                ", jourSeance=" + jourSeance +
                ", weekSeance=" + weekSeance +
                ", durationSeance=" + durationSeance +
                ", infoSeance='" + infoSeance + '\'' +
                ", idMatiereSeance='" + idMatiereSeance + '\'' +
                ", nomMatiereSeance='" + nomMatiereSeance + '\'' +
                ", listeSalles=" + listeSalles +
                ", listeProfs=" + listeProfs +
                ", listeGroupes=" + listeGroupes +
                ", isControle=" + isControle +
                ", couleurSeance='" + couleurSeance + '\'' +
                '}';
    }
}
