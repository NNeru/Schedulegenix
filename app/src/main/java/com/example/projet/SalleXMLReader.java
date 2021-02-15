package com.example.projet;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SalleXMLReader {

    public static Map<String, String> parseXmlByPull(InputStream inputStream, String typeSalles) {
        XmlPullParser pullParser = Xml.newPullParser();
        Map<String, String> salles = null;
        try {
            pullParser.setInput(inputStream, "UTF-8");
            //les noeuds : START_TAG, END_TAG, TEXT etc
            int eventType = pullParser.getEventType();
            String idSalle = null;
            String nomSalle = null;
            //tant qu'on est pas à la fin du doc xml
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //commencer à analyser par le root du xml
                    case XmlPullParser.START_DOCUMENT:
                        salles = new HashMap<>();
                        break;
                    case XmlPullParser.START_TAG: //arriver aux tags du xml
                        if ("leaf".equals(pullParser.getName())) {
                            if(pullParser.getAttributeValue(5).equals(typeSalles)){
                                idSalle = pullParser.getAttributeValue(0);
                                nomSalle = pullParser.getAttributeValue(1);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG: //la fin du tag, ajouter l'objet dans la collection map
                        if ("leaf".equals(pullParser.getName())&& idSalle != null && nomSalle != null) {
                            salles.put(idSalle, nomSalle);
                            idSalle = null;
                            nomSalle = null;
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
        return salles;
    }
}
