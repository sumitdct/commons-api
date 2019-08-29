package org.billing.api.util;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseConstant {
    NAME_ERROR("Please provide name"),

    EMAIL_ERROR("Please provide email address"),
    EMAIL_ID_ALREAY_EXISTS("Email address already exists"),
    INVALID_EMAIL("Invalid email pattern"),
    CONTACT_NO_ERROR("Please provide contact no"),
    PRODUCT_NAME_ERROR("Product Name is required"),
    PASSWORD_ERROR("Please provide login password"),
    DEVICE_ID_ERROR("deviceId is not given"),
    IMPROPER_PASSWORD_PATTERN("Improper password pattern -> Password must be 3 Capital Alphabets,1 Special character , 3 Small alphabet and 3 digits"),

    NEW_PASSWORD_ERROR("Please provide new password"),
    NEW_IMPROPER_PASSWORD_PATTERN("Improper password pattern -> Password must be 3 Capital Alphabets,1 Special character , 3 Small alphabet and 3 digits"),

    EMAIL_ID_NOT_EXISTS("Provided email id does not exists"),
    EMAIL_ID_INACTIVE("Provided email id is inactive ,Please active it"),
    ATTEMPT_LIMIT_EXCEEDED("Attempt to login limit exceed"),
    INVALID_PASSWORD("Invalid password"),
    PASSWORDS_SHOULD_NOT_MATCH("Old password and new password must not match"),

    ACCOUNT_TYPE_ERROR("Please select account type"),
    ORGANIZATION_ERROR("Please provide organization name"),
    ORGANIZATION_ALREADY_EXISTS("Organization already exists"),

    SUCCESS("success"),
    ERROR("error")
    ;
    private String status;
    public String getStatus(){
        return this.status;
    }

}
