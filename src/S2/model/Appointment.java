package S2.model;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;
    private String contact;

    /**
     * Constructor of an appointment object
     * @param appointmentId Appointment ID
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param start Appointment start time and date
     * @param end Appointment end time and date
     * @param createDate Time and date the appointment was created
     * @param createdBy User that created the appointment
     * @param lastUpdate Time and date the the appointment was last updated
     * @param lastUpdatedBy User that last updated the appointment
     * @param customerId Customer ID associated with the appointment
     * @param userId User ID that created the appointment
     * @param contactId Contact ID associated with the appointment
     * @param contact Contact name associated with the appointment
     */
    
    public Appointment(int appointmentId, String title, String description, String location, String type, Timestamp start, Timestamp end, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId, String contact){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contact = contact;
    }

    /**
     * @param appointmentId the appointment id to set
     */

    public void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId;
    }

    /**
     * @return the appointment id
     */

    public int getAppointmentId(){
        return appointmentId;
    }

    /**
     * @param title the title to set
     */

    public void setTitle(String title){
        this.title = title;
    }

    /**
     * @return the title
     */

    public String getTitle(){
        return title;
    }

    /**
     * @param description the description to set
     */

    public void setDescription(String description){
        this.description = description;
    }

    /**
     * @return the description
     */

    public String getDescription(){
        return description;
    }

    /**
     * @param location the location to set
     */

    public void setLocation(String location){
        this.location = location;
    }

    /**
     * @return the location
     */

    public String getLocation(){
        return location;
    }

    /**
     * @param type the type to set
     */

    public void setType(String type){
        this.type = type;
    }

    /**
     * @return the type
     */

    public String getType(){
        return type;
    }

    /**
     * @param start the start time to set
     */

    public void setStart(Timestamp start){
        this.start = start;
    }

    /**
     * @return the start time
     */

    public Timestamp getStart(){
        return start;
    }

    /**
     * @param end the end time to set
     */

    public void setEnd(Timestamp end){
        this.end = end;
    }

    /**
     * @return the end time
     */

    public Timestamp getEnd(){
        return end;
    }

    /**
     * @param createDate the create date to set
     */

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the create date
     */

    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createdBy the creating user to set
     */

    public void setCreatedBy(String createdBy){
        this.createdBy = createdBy;
    }

    /**
     * @return the creating user
     */

    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param lastUpdate the last update time to set
     */

    public void setLastUpdate(Timestamp lastUpdate){
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the last update time
     */

    public Timestamp getLastUpdate(){
        return lastUpdate;
    }

    /**
     * @param lastUpdatedBy the last updating user to set
     */

    public void setLastUpdatedBy(String lastUpdatedBy){
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the last updating user
     */

    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }

    /**
     * @param customerId the customer ID to set
     */

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customer ID
     */

    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param userId the user ID to set
     */

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the user ID
     */

    public int getUserId() {
        return userId;
    }

    /**
     * @param contactId the contact ID to set
     */

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the contact ID
     */

    public int getContactId() {
        return contactId;
    }

    /**
     * @param contact the contact to set
     */

    public void setContact(String contact){
        this.contact = contact;
    }

    /**
     * @return the contact
     */

    public String getContact(){
        return contact;
    }

}
