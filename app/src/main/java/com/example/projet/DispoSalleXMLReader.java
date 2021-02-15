package com.example.projet;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DispoSalleXMLReader {

    public static List<String> parseXmlByPull(InputStream inputStream, String heureActuel, Map<String, String> salles) {
        XmlPullParser pullParser = Xml.newPullParser();
        String startHour = "";
        String endHour = "";
        try {
            pullParser.setInput(inputStream, "UTF-8");
            //les noeuds : START_TAG, END_TAG, TEXT etc
            int eventType = pullParser.getEventType();
            //tant qu'on est pas à la fin du doc xml
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    //commencer à analyser par le root du xml
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG: //arriver aux tags du xml

                        if ("event".equals(pullParser.getName())) {
                            startHour = pullParser.getAttributeValue(6);
                            endHour = pullParser.getAttributeValue(5);

                        }else if ("resource".equals(pullParser.getName())){
                            if("classroom".equals(pullParser.getAttributeValue(4))){
                                if(salles.containsKey(pullParser.getAttributeValue(6))){
                                    if(belongPlageHoraire(heureActuel, startHour, endHour)){
                                        salles.remove(pullParser.getAttributeValue(6));
                                    }
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG: //la fin du tag, ajouter l'objet dans la collection map
                       /* if ("leaf".equals(pullParser.getName())&& prof != null) {
                            profs.add(prof);
                            prof = null;
                        }*/
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
        Collection<String> nomsSalles = salles.values();
        List<String> nomSallesList = new ArrayList<>(nomsSalles);

        return nomSallesList;
    }

    private static boolean belongPlageHoraire(String nowTime, String beginTime, String endTime){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date nowT =null;
        Date beginT = null;
        Date endT = null;
        try{
            nowT = df.parse(nowTime);
            beginT = df.parse(beginTime);
            endT = df.parse(endTime);
        }catch (ParseException e){
            e.printStackTrace();
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowT);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginT);

        Calendar end = Calendar.getInstance();
        end.setTime(endT);

        return date.after(begin) && date.before(end);

    }
}
