package Model;

/**
 * this class creates/updates customer information
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String state;
    private String postalCode;
    private String phoneNumber;
    private String country;


    /**
     * creates Customer object
     * @param id Customer_ID
     * @param name Customer_Name
     * @param address Address
     * @param state state/province
     * @param postalCode postalcode
     * @param country Country
     * @param phoneNumber PhoneNumber
     */
    public Customer(int id, String name, String address, String state, String postalCode, String country, String phoneNumber){
        this.id = id;
        this.name = name;
        this.address = address;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    /**
     * gets ID
     * @return id
     */
    public int getId(){ return id; }

    /**
     * gets Customer_Name
     * @return name
     */
    public String getName(){ return name; }

    /**
     * gets Address
     * @return address
     */
    public String getAddress(){ return address; }

    /**
     * gets First Level Division
     * @return state
     */
    public String getState(){ return state;}

    /**
     * gets Postal Code
     * @return postalCode
     */
    public String getPostalCode(){ return postalCode; }

    /**
     * gets Country
     * @return country
     */
    public String getCountry(){ return country; }

    /**
     * gets PhoneNumber
     * @return phoneNumber
     */
    public String getPhoneNumber(){ return phoneNumber; }


    /**
     * sets Customer_ID
     * @param id
     */
    public void setId(int id){ this.id = id; }

    /**
     * sets Customer_Name
     * @param name
     */
    public void setName(String name){ this.name = name; }

    /**
     * sets Address
     * @param address
     */
    public void setAddress(String address){ this.address = address; }

    /**
     * sets state
     * @param state
     */
    public void setState(String state){ this.state = state; }

    /**
     * sets postalCode
     * @param postalCode
     */
    public void setPostalCode(String postalCode){ this.postalCode = postalCode; }

    /**
     * sets country
     * @param country
     */
    public void setCountry(String country) { this.country = country; }

    /**
     * sets phone number
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber = phoneNumber; }

}
