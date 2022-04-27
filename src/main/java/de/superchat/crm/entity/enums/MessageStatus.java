package de.superchat.crm.entity.enums;

public enum MessageStatus {
    /**The message is sending but no confirmation on sending**/
    PENDING,

    /**The message has been confirmed as sent **/
    SENT,

    /**The message has been confirmed as delivered by the receiver**/
    DELIVERED,

    /** The message has be confirmed as seen by the receiver **/
    SEEN
}
