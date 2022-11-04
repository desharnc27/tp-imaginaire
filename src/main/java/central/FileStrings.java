package central;

import java.io.File;

/**
 * Cette classe contient des méthodes permettant d'obtenir aisément des noms
 * complets de fichiers (incluant le chemin du répertoire)
 *
 */
public class FileStrings {

    private final static String SEP = File.separator;
    private final static String ROOT = findRoot();
    private final static String PROFILES = "profilesets";
    private final static String CMDF = "commandfiles";
    private final static String DEFAULT_DATA = "default.txt";

    private static String findRoot() {
        String res = System.getProperty("user.dir");
        //Au cas où le programme serait lancé par ligne de commande en ayant au préalable navigué au dossier "classes"
        //au lieu de l'avoir lancé par l'IDE.
        String removableEnd = SEP + "target" + SEP + "classes";
        if (res.endsWith(removableEnd)) {
            res = res.substring(0, res.length() - removableEnd.length());
        }
        return res;

    }

    private static String getProfilesPath() {
        return ROOT + SEP + PROFILES;
    }

    private static String getCommandFolder() {
        return ROOT + SEP + CMDF;
    }

    //public
    //Pas de javadoc, à vous de figurer comment utiliser ces fonctions (c'est assez simple)
    public static String getDefaultProfilesFile() {
        return getProfilesPath() + SEP + DEFAULT_DATA;
    }

    public static String getCommandFilePath(String name) {
        return getCommandFolder() + SEP + name;
    }

    public static String getProfilesFilePath(String name) {
        return getProfilesPath() + SEP + name;
    }

}
