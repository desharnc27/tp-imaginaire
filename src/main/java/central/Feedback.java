package central;

/**
 * Un objet de cette classe est une rétroaction à une commande
 *
 */
public class Feedback {

    public final String message;
    public final boolean success;

    public Feedback(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

}
