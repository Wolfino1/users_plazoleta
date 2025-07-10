package com.plazoleta.usuarios.domain.models;

import java.time.LocalDate;

import com.plazoleta.usuarios.domain.exceptions.*;
import com.plazoleta.usuarios.domain.util.constants.DomainConstants;

import java.time.Period;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class UserModel {

        private long id;
        private String name;
        private String lastname;
        private String document;
        private String phoneNumber;
        private LocalDate dateOfBirth;
        private String email;
        private String password;
        private Long idRole;
        private Long idRestaurant;

        public UserModel(long id, String name, String lastname, String document, String phoneNumber,
                         LocalDate dateOfBirth, String email, String password, Long idRole, Long idRestaurant) {
            this.id = id;
            setName(name);
            setLastname(lastname);
            setDocument(document);
            setPhoneNumber(phoneNumber);
            setDateOfBirth(dateOfBirth);
            setEmail(email);
            setPassword(password,password);
            this.idRole = idRole;
            this.idRestaurant = idRestaurant;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if (name == null) throw new NullException(DomainConstants.FIELD_NAME_NULL_MESSAGE);
            if (name.trim().isEmpty()) throw new EmptyException(DomainConstants.FIELD_NAME_EMPTY_MESSAGE);
            this.name = name;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            if (lastname == null) throw new NullException(DomainConstants.FIELD_LASTNAME_NULL_MESSAGE);
            if (lastname.trim().isEmpty()) throw new EmptyException(DomainConstants.FIELD_LASTNAME_EMPTY_MESSAGE);
            this.lastname = lastname;
        }

        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            if (document == null) throw new NullException(DomainConstants.FIELD_DOCUMENT_NULL_MESSAGE);
            if (document.trim().isEmpty()) throw new EmptyException(DomainConstants.FIELD_DOCUMENT_EMPTY_MESSAGE);
            if (!document.matches("\\d+")) throw new WrongArgumentException(DomainConstants.WRONG_ARGUMENT_DOCUMENT_MESSAGE);
            this.document = document;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            if (phoneNumber == null) throw new NullException(DomainConstants.FIELD_PHONE_NULL_MESSAGE);
            if (phoneNumber.trim().isEmpty()) throw new EmptyException(DomainConstants.FIELD_PHONE_EMPTY_MESSAGE);
            if (phoneNumber.length() > 13) throw new MaxSizeExceededException(DomainConstants.MAX_SIZE_EXCEEDED_PHONE_NUMBER);
            String phoneRegex = "^\\+?[0-9]{2}[0-9]{10}$";
            if (!phoneNumber.matches(phoneRegex)) {
                throw new WrongArgumentException(DomainConstants.WRONG_ARGUMENT_PHONE_MESSAGE);
            }

            this.phoneNumber = phoneNumber;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(LocalDate dateOfBirth) {
            if (dateOfBirth == null) {
                throw new NullException(DomainConstants.FIELD_DOB_NULL_MESSAGE);
            }

            if (!esMayorDeEdad(dateOfBirth)) {
                throw new UnderAgeException(DomainConstants.UNDER_AGE_MESSAGE);
            }
            this.dateOfBirth = dateOfBirth;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            if (email == null) throw new NullException(DomainConstants.FIELD_EMAIL_NULL_MESSAGE);
            if (email.trim().isEmpty()) throw new EmptyException(DomainConstants.FIELD_EMAIL_EMPTY_MESSAGE);

            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                throw new WrongArgumentException(DomainConstants.WRONG_ARGUMENT_EMAIL_MESSAGE);
            }

            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String passwordEncode,String password) {

            if (password == null) throw new NullException(DomainConstants.FIELD_PASSWORD_NULL_MESSAGE);
            if (password.trim().isEmpty()) throw new EmptyException(DomainConstants.FIELD_PASSWORD_EMPTY_MESSAGE);
            this.password = passwordEncode;
        }

        public Long getIdRole() {
            return idRole;
        }

        public void setIdRole(Long idRole) {
            this.idRole = idRole;
        }

        private boolean esMayorDeEdad(LocalDate fechaNacimiento) {
            LocalDate hoy = LocalDate.now();
            return Period.between(fechaNacimiento, hoy).getYears() >= 18;
        }

        public Long getIdRestaurant() {
            return idRestaurant;
        }

        public void setIdRestaurant(Long idRestaurant) {
            this.idRestaurant = idRestaurant;
        }
    }
