package S2.model;

import java.sql.Timestamp;

public class Customer {
    private int customerId;
    private String customerName;
    private String address;
    private String zipCode;
    private String phoneNumber;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;
    private String state;
    private String country;

    /**
     * Constructor for a customer object
     * @param customerId the customer ID
     * @param customerName the customer name
     * @param address the customer address
     * @param zipCode the customer postal code
     * @param phoneNumber the customer phone number
     * @param createDate the date and time the customer was created
     * @param createdBy the user that created the customer
     * @param lastUpdate the date and the time customer was last updated
     * @param lastUpdatedBy the user the customer was last updated by
     * @param divisionId the division ID of the customer
     * @param state the state of the customer
     * @param country the country of the customer
     */

    public Customer(int customerId, String customerName, String address, String zipCode, String phoneNumber, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId, String state, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId = divisionId;
        this.state = state;
        this.country = country;
    }

    /**
     * @return the customer ID
     */

    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customer ID to set
     */

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customer name
     */

    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customer name to set
     */

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customer address
     */

    public String getAddress() {
        return address;
    }

    /**
     * @param address the customer address to set
     */

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the customer postal code
     */

    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode the customer postal code to set
     */

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the customer phone number
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the customer phone number to set
     */

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the create date and time of the customer
     */

    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the create date and time of the customer
     */

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the creating user of the customer
     */

    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the creating user to set
     */

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     * @return the last update date and time of the customer
     */

    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    /**
     * @param lastUpdate the last update date and time of the customer to set
     */

    public void setLastUpdate(Timestamp lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the last user to update the customer
     */

    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the last user to update the customer to set
     */

    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the customer division ID
     */

    public int getDivisionId(){
        return divisionId;
    }

    /**
     * @param divisionId the customer division ID to set
     */

    public void setDivisionId(int divisionId){
        this.divisionId = divisionId;
    }

    /**
     * @return the customer state
     */

    public String getState(){
        return state;
    }

    /**
     * @param state the customer state to set
     */

    public void setState(String state){
        this.state = state;
    }

    /**
     * @return the customer country
     */

    public String getCountry(){
        return country;
    }

    /**
     * @param country the customer country to set
     */

    public void setCountry(String country){
        this.country = country;
    }
}
