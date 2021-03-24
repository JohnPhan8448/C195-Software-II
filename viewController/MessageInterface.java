package viewController;

/**
 * this interface Lambda expression is used to display pop up messages takes in Title Header and Content of message and creates pop up.
 * used throughout code to streamline messages displayed to customer.
 */
public interface MessageInterface {

    boolean message(String title, String header, String content);

}
