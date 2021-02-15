package com.example.projet;


import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupeXMLReader {

    public static List<String> parseXmlByPull(InputStream inputStream, Map<String, String> matiereGroupe) {
        XmlPullParser pullParser = Xml.newPullParser();
        List<String> groupes = null;
        try {
            pullParser.setInput(inputStream, "UTF-8");
            //les noeuds : START_TAG, END_TAG, TEXT etc
            int eventType = pullParser.getEventType();
            String idGroupe = null;
            //tant qu'on est pas à la fin du doc xml
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //commencer à analyser par le root du xml
                    case XmlPullParser.START_DOCUMENT:
                        groupes = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG: //arriver aux tags du xml

                        if ("leaf".equals(pullParser.getName())) {
                            String pathGroupe = pullParser.getAttributeValue(2);
                            if(pathGroupe.contains("E3")){
                                for(String matiere : matiereGroupe.keySet()){
                                    String nomGroupeXML = pullParser.getAttributeValue(1);
                                    String nomUserGroupe = matiereGroupe.get(matiere);
                                    if(pathGroupe.contains(matiere)&&nomGroupeXML.equals(nomUserGroupe)){
                                        idGroupe = pullParser.getAttributeValue(0);
                                    }
                            }

                            }
                        }
                        break;
                    case XmlPullParser.END_TAG: //la fin du tag, ajouter l'objet dans la collection map
                        if ("leaf".equals(pullParser.getName())&& idGroupe != null) {
                            groupes.add(idGroupe);
                            idGroupe = null;
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
        return groupes;
    }
}
