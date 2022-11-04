package central;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Autres fonctions utiles
 */
public class SomeFunctions {

    /**
     * Lis toutes les lignes d'un fichier et les retourne sous forme de liste
     *
     * @param filename nom complet du fichier
     * @return liste contenant toutes les lignes du fichier en ordre
     * @throws IOException si le fichier n'existe pas
     */
    public static List<String> readAllLines(String filename) throws IOException {
        Path path = Paths.get(filename);
        List<String> content;
        content = Files.readAllLines(path);
        return content;
    }

    /**
     * Vérifie si un string contient uniquement des chiffres
     *
     * @param word le mot à vérifier
     * @return true si le mot ne contient que des chiffres, faux sinon
     */
    public static boolean containsOnlyDigits(String word) {
        return containsOnlyType(word, "[0-9]");
    }

    /**
     * Vérifie si un string contient uniquement des lettres
     *
     * @param word le mot à vérifier
     * @return true si le mot ne contient que des lettres, faux sinon
     */
    public static boolean containsOnlyLetters(String word) {
        return containsOnlyType(word, "[A-Za-z]");
    }

    private static boolean containsOnlyType(String word, String type) {
        return word.matches(type + "*");
    }
}
