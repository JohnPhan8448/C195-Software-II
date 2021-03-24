package Model;

/**
 * this class creates the appointment object
 */
public class Appointment {

    //variable declaration
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;

    /**
     *  when called creates an appointment from database information
     * @param id Appointment_ID
     * @param title Title information
     * @param description description information
     * @param location location information
     * @param type type information
     * @param start start date and time formatted
     * @param end end date and time formatted
     * @param customerId Customer_ID
     * @param userId User_ID
     * @param contactId Contact_ID
     * @param contactName Contact_Name
     */
    public Appointment(int id, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId, String contactName){
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * gets ID
     * @return id
     */
    public int getId(){return id;}

    /**
     * gets Title
     * @return title
     */
    public String getTitle(){ return title;}

    /**
     * gets Description
     * @return description
     */
    public String getDescription(){ return description;}

    /**
     * gets Location
     * @return location
     */
    public String getLocation(){ return location;}

    /**
     * gets Type
     * @return type
     */
    public String getType(){return type;}

    /**
     * gets Start
     * @return start
     */
    public String getStart(){return start;}

    /**
     * gets End
     * @return end
     */
    public String getEnd(){return end;}

    /**
     * gets Customer_ID
     * @return customerId
     */
    public int getCustomerId(){return customerId;}

    /**
     * gets User_ID
     * @return userId
     */
    public int getUserId(){return userId;}

    /**
     * gets ContactId
     * @return ContactId
     */
    public int getContactId(){return contactId;}

    /**
     * gets ContactName
     * @return contactName
     */
    public String getContactName(){return contactName;}


    /**
     * sets Appointment_ID
     * @param id appointment id
     */
    public void setId(int id){ this.id = id;}

    /**
     * sets Title
     * @param title title
     */
    public void setTitle(String title){ this.title = title;}

    /**
     * sets Description
     * @param description description
     */
    public void setDescription(String description){ this.description = description;}

    /**
     * sets Location
     * @param location
     */
    public void setLocation(String location){ this.location = location;}

    /**
     * sets Type
     * @param type
     */
    public void setType(String type){ this.type = type;}

    /**
     * sets Start Date Time
     * @param start
     */
    public void setStart(String start){ this.start = start;}

    /**
     * sets End Date Time
     * @param end String end time
     */
    public void setEnd(String end){ this.end = end;}

    /**
     * sets Customer_ID
     * @param customerId
     */
    public void setCustomerId(int customerId){ this.customerId = customerId;}

    /**
     * sets User_ID
     * @param userId
     */
    public void setUserId(int userId){this.userId = userId;}

    /**
     * sets Contact_ID
     * @param contactId
     */
    public void setContactId(int contactId){ this.contactId = contactId;}

    /**
     * sets contact name
     * @param contactName String
     */
    public void setContactName(String contactName){ this.contactName = contactName;}

}
