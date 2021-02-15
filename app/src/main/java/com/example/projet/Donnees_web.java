package com.example.projet;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Donnees_web {

    private String sessionId;
    private Map<String, String> mapRegroupement;

    public Donnees_web(){
        this.mapRegroupement = new HashMap<>();
        this.manageException();
        this.initaliserFonctionnementWeb();
        this.initialiserTableReGroupement();

    }

    /**
     * Initialisation fonctionnement des requêtes web
     */
    private void initaliserFonctionnementWeb(){
        //path initial qui retourne sessionId
        String pathInitial = "https://planif.esiee.fr/jsp/webapi?function=connect&login=lecteur1&password=";
        //Obtenir sessionId
        this.sessionId = posturl(pathInitial).substring(52, 63);
        //path pour retourner idProjet qui identifie l'année
        String pathIdProjet = "https://planif.esiee.fr/jsp/webapi?sessionId="+this.sessionId+"&function=getProjects";
        String contenuXMLIdprojet = posturl(pathIdProjet);
        //Obtenir idProjet
        String regEx="[^0-9]+";
        Pattern pattern = Pattern.compile(regEx);
        String[] chiffre = pattern.split(contenuXMLIdprojet);
        String idProjet = chiffre[chiffre.length - 1];
        //textView.setText(this.idProjet);
        //path pour initialiser API adesoft et activer les fonctions get
        String pathSetProjet = "https://planif.esiee.fr/jsp/webapi?sessionId="+this.sessionId+"&function=setProject&projectId="+ idProjet;
        posturl(pathSetProjet);
    }

    public List<Prof> getListeProfs(String dept){

        //path pour obtenir les noms et  des profs
        String pathProfs = "https://planif.esiee.fr/jsp/webapi?sessionId="+sessionId+"&function=getResources&tree=true&category=instructor&jobCategory="+dept+"&detail=11";
        //création de la liste des profs
        List<Prof> listeProf= ProfXMLReader.parseXmlByPull(new ByteArrayInputStream(posturl(pathProfs).getBytes()));
        return listeProf;
    }


    /**
     * Initialisation de GROUPE_TABLE, en fonction de regroupement
     * @throws Exception
     */
    public List<String> getUserGroupeID(Map<String, String> matiereGroupe) throws Exception {

        //path pour obtenir les groupes
        String pathGroupe = "https://planif.esiee.fr/jsp/webapi?sessionId="+sessionId+"&function=getResources&tree=true&category=trainee&detail=3";
        //création de la liste des groupes
        List<String> listeGroupe = GroupeXMLReader.parseXmlByPull(new ByteArrayInputStream(posturl(pathGroupe).getBytes()), matiereGroupe);
        return listeGroupe;
    }

    /**
     * Initialisation de REGROUPEMENT_TABLE
     */
    private void initialiserTableReGroupement() {
        /*
        String pathRegroupement = "https://planif.esiee.fr/jsp/webapi?sessionId="+sessionId+"&function=getResources&tree=true&category=category6&detail=2";
        this.listeRegroupement= RegroupementXMLReader.parseXmlByPull(new ByteArrayInputStream(posturl(pathRegroupement).getBytes()));
        listeRegroupement.add(new Regroupement("1017", "Mastères"));
        listeRegroupement.add(new Regroupement("949", "ISBS"));
        listeRegroupement.add(new Regroupement("597", "Autres Enseignements"));
        listeRegroupement.add(new Regroupement("598", "Réuions-Réservations-Manifestations"));*/
        mapRegroupement.put("E1","655");
        mapRegroupement.put("E2", "522");
        mapRegroupement.put("E3", "524");
        mapRegroupement.put("E4", "518");
        mapRegroupement.put("E5", "978");
        mapRegroupement.put("Apprenti 3ème année", "215");
        mapRegroupement.put("Apprenti 4ème année", "216");
        mapRegroupement.put("Apprenti 5ème année", "255");
        mapRegroupement.put("Apprenti Cergy 3ème année", "5598");
        mapRegroupement.put("Apprenti Cergy 4ème année", "5976");
        mapRegroupement.put("Apprenti Cergy 5ème année", "4418");
        mapRegroupement.put("Mastères", "1017");
        mapRegroupement.put("ISBS", "949");
        mapRegroupement.put("Autres Enseignements", "597");
        mapRegroupement.put("Réunions-Réservations-Manifestations", "598");
    }

    /**
     * Initialisation de SEANCE_TABLE
     */
    public Map<String, List<Seance>> getUserSeances(List<String> userGroupeID, String week){
        //String pathSeances = "https://planif.esiee.fr/jsp/webapi?sessionId="+sessionId+"&function=getEvents&resources=3782|3736|3688|3566|2615|3336|2063|1169|3625|3600|6197|1567|4665&detail=8";
        StringBuilder pathSeances = new StringBuilder("https://planif.esiee.fr/jsp/webapi?sessionId="+sessionId+"&function=getEvents&resources=");
        for(String id : userGroupeID){
            pathSeances.append(id).append("|");
        }
        pathSeances.append("&detail=8");
        Map<String, List<Seance>> seancesMap = SeanceXMLReader.parseXmlByPull(new ByteArrayInputStream(posturl(pathSeances.toString()).getBytes()), week);
        for(List<Seance> listeSeances : seancesMap.values()){
            for(Seance seance : listeSeances){
                String pathActivitySeance = "https://planif.esiee.fr/jsp/webapi?sessionId="+sessionId+"&function=getActivities&id="+seance.getActivityId()+"&detail=9";
                ActivitySeance activityS = ActivityXMLReader.parseXmlByPull(new ByteArrayInputStream(posturl(pathActivitySeance).getBytes()));
                seance.setNomMatiereSeance(activityS.getNomMatiereSeance());
            }
        }

        return seancesMap;
    }

    /**
     * Initialisation de SALLE_TABLE, il faut stocker toutes les salles
     * @throws Exception
     */
    public List<String> getDispoSalles(String typeSalles) throws Exception {
        //path pour obtenir les noms et les types des salles
        String pathSalles = "https://planif.esiee.fr/jsp/webapi??sessionId="+sessionId+"&function=getResources&tree=true&category=classroom&detail=5";
        //création de la liste des salles
        Map<String, String> sallesListe = SalleXMLReader.parseXmlByPull(new ByteArrayInputStream(posturl(pathSalles).getBytes()), typeSalles);
        String pathDispoSalles = "https://planif.esiee.fr/jsp/webapi?sessionId="+sessionId+"&function=getEvents&date=03/11/2020&detail=8";
        List<String> sallesDispos = DispoSalleXMLReader.parseXmlByPull(new ByteArrayInputStream(posturl(pathDispoSalles).getBytes()), "14:00", sallesListe);

        return sallesDispos;
    }



    /**
     * Obtenir le code source d'une page web et le retourner pour que le contenu soit acquis en String
     */
    private String posturl(String url) {
        InputStream is = null;
        String result = "";

        try {
            URL u = new URL(url);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                    .permitDiskWrites()
                    .build());
            StrictMode.setThreadPolicy(old);
            is = c.getInputStream();
        } catch (Exception e) {
            return "Fail to establish http connection!" + e.toString();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

            result = sb.toString();
        } catch (Exception e) {
            return "Fail to convert net stream!";
        }

        return result;
    }

    private void manageException() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
                .build());
    }
}
