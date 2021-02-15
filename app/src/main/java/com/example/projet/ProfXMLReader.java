package com.example.projet;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfXMLReader {
    public static List<Prof> parseXmlByPull(InputStream inputStream) {
        XmlPullParser pullParser = Xml.newPullParser();
        List<Prof> profs = null;
        try {
            pullParser.setInput(inputStream, "UTF-8");
            //les noeuds : START_TAG, END_TAG, TEXT etc
            int eventType = pullParser.getEventType();
            Prof prof = null;
            //tant qu'on est pas à la fin du doc xml
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //commencer à analyser par le root du xml
                    case XmlPullParser.START_DOCUMENT:
                        profs = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG: //arriver aux tags du xml
                        if ("leaf".equals(pullParser.getName())) {
                            prof = new Prof();
                            String idProf = pullParser.getAttributeValue(0);
                            prof.setIdProf(idProf);
                            String nomProf = pullParser.getAttributeValue(1);
                            prof.setNomProf(nomProf.split(" ")[0]);
                            String prenomProf = pullParser.getAttributeValue(39);
                            prof.setPrenomProf(prenomProf);
                            String deptProf = pullParser.getAttributeValue(29);
                            prof.setDepartementProf(deptProf);
                        }
                        break;
                    case XmlPullParser.END_TAG: //la fin du tag, ajouter l'objet dans la collection map
                        if ("leaf".equals(pullParser.getName())&& prof != null) {
                            profs.add(prof);
                            prof = null;
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
        return profs;
    }

}
