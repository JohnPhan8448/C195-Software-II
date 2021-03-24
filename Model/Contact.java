package Model;

/**
 * this class creates contacts for reporting from database information
 */
public class Contact {
    private int contactID;
    private String name;
    private String email;

    /**
     * creates the contact from database for usage
     * @param contactID Contact_ID
     * @param name Contact_Name
     * @param email Contact_email
     */
    public Contact(int contactID, String name, String email){
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    //getters

    /**
     * gets Contact_ID
     * @return contactID
     */
    public int getContactID(){ return contactID;}

    /**
     * gets Contact_Name
     * @return name
     */
    public String getName(){return name;}

    /**
     * gets contact Email
     * @return email
     */
    public String getEmail(){return email;}

    //setters

    /**
     * sets Contact ID
     * @param contactID
     */
    public void setContactID(int contactID){ this.contactID = contactID;}

    /**
     * sets contact Name
     * @param name
     */
    public void setName(String name){ this.name = name;}

    /**
     * sets contact email
     * @param email
     */
    public void setEmail(String email){ this.email = email;}

}
