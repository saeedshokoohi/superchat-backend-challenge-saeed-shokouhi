package de.superchat.crm.entity.enums;

public enum ContactStatus {
    /* for contact which are added automatically */
    TEMP,

    /* for contact which are added by user or confirmed temp user */
    CONFIRMED,

    /* if contact is blocked the system getting message from them */
    BLOCKED
}
