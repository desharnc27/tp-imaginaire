package central;

/**
 * Classe contentant des fonctions calculant des cotes ELO.
 */
public class EloUtils {

    /**
     * Valeur ELO de départ pour un nouveau profil
     */
    public static final short STARTING_ELO = 1500;
    /**
     * Valeur ELO maximale d'un profil (le minimum est 0)
     */
    public static final short MAX_ELO = 4000;

    /**
     * Calcule les nouvelles cotes ELO d'un joueur 0 et d'un joueur 1 après
     * qu'ils se soient affrontés.
     *
     * @param elo0 cote ELO initiale du joueur 0
     * @param elo1 cote ELO initiale du joueur 1
     * @param ng0 nombre de parties jouées par le joueur 0 (avant ce match)
     * @param ng1 nombre de parties jouées par le joueur 1 (avant ce match)
     * @param outcome 1 si le joueur 0 gagne, -1 si le joueur 1 gagne, 0 en cas
     * de match nul
     * @return un tableau de taille 2, contenant les nouvelles valeurs ELO du
     * joueur 0 et du joueur 1, dans cet ordre
     */
    public static short[] getBothNewElos(short elo0, short elo1, int ng0, int ng1, byte outcome) {
        short[] elos = new short[]{elo0, elo1};
        int[] ngs = new int[]{ng0, ng1};
        short[] newElos = new short[2];
        double outcome01 = (outcome + 1) / 2.0;
        double difference = outcome01 - expectedResult(elo0, elo1);

        for (int i = 0; i < 2; i++) {

            int sign = i == 0 ? 1 : -1;
            short newElo = (short) (0.5 + elos[i] + sign * difference * getKFactor(ngs[i]));

            if (newElo < 0) {
                newElo = 0;
            } else if (newElo > MAX_ELO) {
                newElo = MAX_ELO;
            }
            newElos[i] = newElo;
        }
        return newElos;

    }

    //private
    private static double expectedResult(short elo0, short elo1) {
        double expected = 1 / (1 + Math.pow(10, (elo1 - elo0) / 400.0));
        return expected;
    }

    private static double getKFactor(int ng) {
        if (ng > 40) {
            return 10;
        }
        return 10 * Math.pow(4, 1 - ng / 40.0);

    }

}
