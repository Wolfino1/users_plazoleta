package com.plazoleta.usuarios.domain.util.constants;

public class DomainConstants {
    public static final String FIELD_NAME_NULL_MESSAGE = "Field name cannot be null";
    public static final String FIELD_NAME_EMPTY_MESSAGE = "Field name cannot be empty";
    public static final String FIELD_LASTNAME_NULL_MESSAGE = "Field lastname cannot be null";
    public static final String FIELD_LASTNAME_EMPTY_MESSAGE = "Field lastname cannot be empty";
    public static final String FIELD_EMAIL_NULL_MESSAGE = "Field Email cannot be null";
    public static final String FIELD_EMAIL_EMPTY_MESSAGE = "Field Email cannot be empty";
    public static final String FIELD_DOCUMENT_NULL_MESSAGE = "Field document cannot be null";
    public static final String FIELD_DOCUMENT_EMPTY_MESSAGE = "Field document cannot be empty";
    public static final String FIELD_PHONE_NULL_MESSAGE = "Field phone number cannot be null";
    public static final String FIELD_PHONE_EMPTY_MESSAGE = "Field phone number cannot be empty";
    public static final String FIELD_DOB_NULL_MESSAGE = "Field date of birth cannot be null";
    public static final String FIELD_PASSWORD_NULL_MESSAGE = "Field date of password cannot be null";
    public static final String FIELD_PASSWORD_EMPTY_MESSAGE = "Field date of password cannot be null";
    public static final String WRONG_ARGUMENT_EMAIL_MESSAGE = "Email has not a valid format";
    public static final String MAX_SIZE_EXCEEDED_PHONE_NUMBER = "Phone number can only contain 13 characters";
    public static final String WRONG_ARGUMENT_PHONE_MESSAGE = "phone number must begin with "+" followed by 12 numbers";
    public static final String WRONG_ARGUMENT_DOCUMENT_MESSAGE = "Document can only contain numbers";
    public static final String UNDER_AGE_MESSAGE = "User must be 18 years old or more";
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_CANT_NULL = "User can't be null";
    public static final String INVALID_CREDENTIALS = "Invalid Credentials";
    public static final Long ADMIN_ID = 1L;
    public static final Long OWNER_ID = 2L;
    public static final Long EMPLOYEE_ID = 3L;
    public static final Long CLIENT_ID = 4L;
    public static final String DONT_OWN_THE_RESTAURANT= "This restaurant doesn't belong to you";
    public static final String RESTAURANTID_MANDATORY_FOR_EMPLOYEES = "Restaurant ID is mandatory " +
            "for creating employees";
}
