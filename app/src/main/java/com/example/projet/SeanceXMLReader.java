package com.example.projet;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SeanceXMLReader {

    //private static List<String> idRegroupements = new ArrayList<String>(Arrays.asList(new String[]{"1017", "949", "597", "598"}));

    private final static String[] jours = {"Lun", "Mar", "Mer", "Jeu", "Ven", "Sam"};

    public static Map<String, List<Seance>> parseXmlByPull(InputStream inputStream, String week) {
        XmlPullParser pullParser = Xml.newPullParser();
        Map<String, List<Seance>> seancesMap = null;

        try {
            pullParser.setInput(inputStream, "UTF-8");
            //les noeuds : START_TAG, END_TAG, TEXT etc
            int eventType = pullParser.getEventType();
            Seance seance = null;
            int jourSeance = -1;
            //tant qu'on est pas à la fin du doc xml
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //commencer à analyser par le root du xml
                    case XmlPullParser.START_DOCUMENT:
                        //seances = new ArrayList<Seance>();
                        seancesMap = new HashMap<>();
                        break;
                    case XmlPullParser.START_TAG: //arriver aux tags du xml
                        if ("event".equals(pullParser.getName())) {
                            if(pullParser.getAttributeValue(11).equals(week)){
                                seance = new Seance();
                                String idSeance = pullParser.getAttributeValue(0);
                                seance.setIdSeance(idSeance);
                                String activityIdSeance = pullParser.getAttributeValue(1);
                                seance.setActivityId(activityIdSeance);
                                String dateSeance = pullParser.getAttributeValue(7);
                                seance.setDateSeance(dateSeance);
                                String heureDebutSeance = pullParser.getAttributeValue(6);
                                seance.setHeureDebutSeance(heureDebutSeance);
                                String heureFinSeance = pullParser.getAttributeValue(5);
                                seance.setHeureFinSeance(heureFinSeance);
                                jourSeance = Integer.parseInt(pullParser.getAttributeValue(10));
                                seance.setJourSeance(jourSeance);
                                int weekSeance = Integer.parseInt(pullParser.getAttributeValue(11));
                                seance.setWeekSeance(weekSeance);
                                int durationSeance = Integer.parseInt(pullParser.getAttributeValue(13));
                                seance.setDurationSeance(durationSeance);
                                String infoSeance = pullParser.getAttributeValue(14);
                                seance.setInfoSeance(infoSeance);
                                String nameSeance = pullParser.getAttributeValue(4);
                                seance.setNameSeance(nameSeance);
                                String couleurSeance = pullParser.getAttributeValue(16);
                                seance.setCouleurSeance(couleurSeance);
                            }

                            /*if(idRegroupements.contains(idRegroupement)){
                                if(nameSeance.contains(":")) {
                                    if(nameSeance.charAt(nameSeance.length()-1)!=':'){
                                        seance.setIdMatiereSeance(nameSeance.split(":")[0]);
                                        seance.setCodeMatiereSeance(nameSeance.split(":")[0]);
                                    }else{
                                        seance.setIdMatiereSeance(nameSeance);
                                    }
                                }else{
                                    seance.setIdMatiereSeance(nameSeance);
                                }
                            }*/
                        }else if("resource".equals(pullParser.getName())&&seance!=null){
                            if("category6".equals(pullParser.getAttributeValue(4))){
                                String idSeance = pullParser.getAttributeValue(6);
                                seance.setIdMatiereSeance(idSeance);
                            }else if("instructor".equals(pullParser.getAttributeValue(4))){
                                String nomProf = pullParser.getAttributeValue(5);
                                seance.addProfs(nomProf);
                            }else if("classroom".equals(pullParser.getAttributeValue(4))){
                                String nomSalle = pullParser.getAttributeValue(5);
                                seance.addSalles(nomSalle);
                            }else if("trainee".equals(pullParser.getAttributeValue(4))){
                                String nomGroupe = pullParser.getAttributeValue(5);
                                seance.addGroupes(nomGroupe);
                            }else if("category5".equals(pullParser.getAttributeValue(4))){
                                seance.setControle(true);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG: //la fin du tag, ajouter l'objet dans la collection map
                        if ("event".equals(pullParser.getName())&& seance != null&&jourSeance != -1) {
                            //seances.add(seance);
                            if(!seancesMap.containsKey(jours[jourSeance])){
                                List<Seance> seances = new ArrayList<>();
                                seancesMap.put(jours[jourSeance], seances);
                            }
                            seancesMap.get(jours[jourSeance]).add(seance);
                            seance = null;
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                }
                //get le prochain tag
                eventType = pullParser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return seancesMap;
    }

}
