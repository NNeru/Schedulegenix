package com.example.projet;


import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class ActivityXMLReader {

    public static ActivitySeance parseXmlByPull(InputStream inputStream) {
        XmlPullParser pullParser = Xml.newPullParser();
        ActivitySeance activityS = null;
        try {
            pullParser.setInput(inputStream, "UTF-8");
            //les noeuds : START_TAG, END_TAG, TEXT etc
            int eventType = pullParser.getEventType();
            //tant qu'on est pas à la fin du doc xml
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //commencer à analyser par le root du xml
                    case XmlPullParser.START_DOCUMENT:
                        activityS = new ActivitySeance();
                        break;
                    case XmlPullParser.START_TAG: //arriver aux tags du xml
                        if ("activity".equals(pullParser.getName())) {
                            String typeSeance = pullParser.getAttributeValue(2);
                            activityS.setTypeSeance(typeSeance);
                            String nomMatiereSeance = pullParser.getAttributeValue(29);
                            activityS.setNomMatiereSeance(nomMatiereSeance);
                            String timeZoneSeance = pullParser.getAttributeValue(28);
                            activityS.setTimeZoneSeance(timeZoneSeance);
                        }
                        break;
                    case XmlPullParser.END_TAG: //la fin du tag, ajouter l'objet dans la collection
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
        return activityS;
    }

}
