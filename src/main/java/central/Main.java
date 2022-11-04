package central;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import yourcode.Manager;

public class Main {

    public static void main(String[] args) {
        System.out.println();

        System.out.println("");
        String dataName = null;
        if (args.length > 0) {
            dataName = args[0];
        }

        Manager manager = new Manager(dataName);

        Scanner scanner = new Scanner(System.in);

        boolean exitRequired = false;

        ConcurrentLinkedQueue<String> fileCommands = new ConcurrentLinkedQueue<>();

        while (!exitRequired) {

            String userInput;
            boolean commandFromFile = true;
            if (fileCommands.isEmpty()) {
                commandFromFile = false;
                System.out.println("Veuillez entrer une commande:");
                userInput = scanner.next();

            } else {
                //Des commandes provenant d'un fichier sont en attente d'exécution alors, pas besoin d'input de la part de l'utilisateur
                userInput = fileCommands.poll();
            }
            String[] arrayInput = userInput.split(",");
            String[] extraInput = Arrays.copyOfRange(arrayInput, 1, arrayInput.length);

            Feedback feedback;
            try {
                switch (arrayInput[0]) {
                    case "exit-without-save":
                        exitRequired = true;
                        feedback = new Feedback("Fermeture immédiate...", true);
                        break;
                    case "exit":
                        exitRequired = true;
                        feedback = manager.saveData(extraInput);
                        break;
                    case "lcff":
                        if (commandFromFile) {
                            feedback = new Feedback("Dans un fichier de commandes spécifiquement, lcff est interdite pour éviter tout risque de récursion.", false);
                        } else {
                            feedback = loadCommandsFromFile(extraInput[0], fileCommands);
                        }
                        break;
                    case "save-profiles":
                        feedback = manager.saveData(extraInput);
                        break;

                    case "new-profile":
                        feedback = manager.newProfile(extraInput);
                        break;
                    case "result":
                        feedback = manager.resultUpdate(extraInput);
                        break;
                    case "undo":
                        feedback = manager.undo(extraInput);
                        break;
                    case "redo":
                        feedback = manager.redo(extraInput);
                        break;
                    case "suggest-opponents":
                        feedback = manager.suggestOpponents(extraInput);
                        break;
                    case "disable-profile":
                        feedback = manager.disableProfile(extraInput);
                        break;
                    case "enable-profile":
                        feedback = manager.enableProfile(extraInput);
                        break;
                    case "list-active":
                        feedback = manager.listActive();
                        break;
                    default:
                        feedback = new Feedback("\"" + arrayInput[0] + "\" n'est pas une commande connue", false);
                        break;

                }
            } catch (ArrayIndexOutOfBoundsException e) {
                feedback = new Feedback("Nombre insuffisant d'arguments.", false);

            } catch (NumberFormatException e) {
                feedback = new Feedback("Un argument ne respecte pas le format numérique attendu.", false);
            }

            if (!feedback.success) {
                System.out.println("Attention: la commande \"" + userInput + "\" n'a pas été exécutée.");
                System.out.println("Raison: " + feedback.message);
            } else if (!commandFromFile) {
                //En cas de commande valide, on n'affiche pas le feedback si la commande vient d'un fichier
                // afin d'éviter d'imprimer une quantité démesurée d'information
                System.out.println(feedback.message);
            }
            System.out.println("-----");

        }

    }

    /**
     * Remplit une queue de toutes les commandes du fichier, dans le même ordre
     *
     * @param name nom du fichier (sans le chemin du répertoire)
     * @param queue liste de commandes, initialement vide, qui sera remplie avec
     * toutes les commandes du fichiers
     * @return feedback
     */
    private static Feedback loadCommandsFromFile(String name, ConcurrentLinkedQueue<String> queue) {
        String strPath = FileStrings.getCommandFilePath(name);
        Path path = Paths.get(strPath);

        List<String> content;
        try {
            content = Files.readAllLines(path);
        } catch (IOException ex) {
            return new Feedback("Fichier inexistant: " + strPath, false);
        }
        queue.clear();
        while (!content.isEmpty()) {
            queue.add(content.remove(0));
        }
        return new Feedback("Les commandes du fichier " + name + " seront exécutées", true);

    }

}
