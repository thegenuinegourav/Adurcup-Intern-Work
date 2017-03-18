package com.adurcup.adurcupseller.misc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kshivang on 22/12/16.
 *
 */

public class RegistrationFormModel {
    private String businessName;
    private String businessPrimaryNumber;
    private String businessSecondaryNumber;
    private String email;
    private String panNumber;
    private String vatNumber;
    private String faxNumber;
    private String serviceTaxRsgtNumber;
    private String serviceTaxCodeNumber;
    private String legalStatus;
    private String concernPersonFirstName;
    private String concernPersonSecondName;
    private String concernPersonEmail;
    private String concernPersonAge;
    private String concernPersonGender;
    private String concernPersonNationality;
    private String concernPersonPrimaryNumber;
    private String concernPersonSecondaryNumber;
    private String bankName;
    private String bankAccountNo;
    private String bankAddress;
    private String bankCity;
    private String bankState;
    private String bankPinCode;
    private String bankChequeInFavourOf;
    private String ifscCode;
    private String micrCode;
    private String bankSortCode;
    private String authorizedBankPersonFirstName;
    private String authorizedBankPersonSecondName;
    private String authorizedBankPersonDesignation;

    private String RegisterAddress;
    private String RegisterAddress2;
    private List<String> businessType;
    private List<String> addressName = new ArrayList<>();
    private List<String> addressLine1 = new ArrayList<>();
    private List<String> addressCity = new ArrayList<>();
    private List<String> addressState = new ArrayList<>();
    private List<String> addressPinCode = new ArrayList<>();
    private List<String> addressPhone= new ArrayList<>();
    private List<Boolean> registeredAddress = new ArrayList<>();

    private String Bank_Address;
    private String Business_Id;

    public String getBusiness_Id() {
        return Business_Id;
    }

    public void setBusiness_Id(String business_Id) {
        Business_Id = business_Id;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public void setBank_Address(String address) {
        Bank_Address = address;
    }

    public String getBank_Address()
    {
        return Bank_Address;
    }

    public void setRegisterAddress(String registeredAddress) {
        RegisterAddress = registeredAddress;
    }

    public String getRegisterAddress()
    {
        return RegisterAddress;
    }

    public void setRegisterAddress2(String registeredAddress) {
        RegisterAddress2 = registeredAddress;
    }

    public String getRegisterAddress2()
    {
        return RegisterAddress2;
    }


    private static RegistrationFormModel registrationFormModel;

    public static RegistrationFormModel getInstance()
    {

            if(registrationFormModel == null)
                registrationFormModel = new RegistrationFormModel();


        return registrationFormModel;
    }

    public void destroy()
    {
        this.registrationFormModel = null;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessPrimaryNumber() {
        return businessPrimaryNumber;
    }

    public void setBusinessPrimaryNumber(String businessPrimaryNumber) {
        this.businessPrimaryNumber = businessPrimaryNumber;
    }

    public String getBusinessSecondaryNumber() {
        return businessSecondaryNumber;
    }

    public void setBusinessSecondaryNumber(String businessSecondaryNumber) {
        this.businessSecondaryNumber = businessSecondaryNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getServiceTaxRsgtNumber() {
        return serviceTaxRsgtNumber;
    }

    public void setServiceTaxRsgtNumber(String serviceTaxRsgtNumber) {
        this.serviceTaxRsgtNumber = serviceTaxRsgtNumber;
    }

    public String getServiceTaxCodeNumber() {
        return serviceTaxCodeNumber;
    }

    public void setServiceTaxCodeNumber(String serviceTaxCodeNumber) {
        this.serviceTaxCodeNumber = serviceTaxCodeNumber;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(String legalStatus) {
        this.legalStatus = legalStatus;
    }

    public String getConcernPersonFirstName() {
        return concernPersonFirstName;
    }

    public void setConcernPersonFirstName(String concernPersonFirstName) {
        this.concernPersonFirstName = concernPersonFirstName;
    }

    public String getConcernPersonSecondName() {
        return concernPersonSecondName;
    }

    public void setConcernPersonSecondName(String concernPersonSecondName) {
        this.concernPersonSecondName = concernPersonSecondName;
    }

    public String getConcernPersonEmail() {
        return concernPersonEmail;
    }

    public void setConcernPersonEmail(String concernPersonEmail) {
        this.concernPersonEmail = concernPersonEmail;
    }

    public String getConcernPersonAge() {
        return concernPersonAge;
    }

    public void setConcernPersonAge(String concernPersonAge) {
        this.concernPersonAge = concernPersonAge;
    }

    public String getConcernPersonGender() {
        return concernPersonGender;
    }

    public void setConcernPersonGender(String concernPersonGender) {
        this.concernPersonGender = concernPersonGender;
    }

    public String getConcernPersonNationality() {
        return concernPersonNationality;
    }

    public void setConcernPersonNationality(String concernPersonNationality) {
        this.concernPersonNationality = concernPersonNationality;
    }

    public String getConcernPersonPrimaryNumber() {
        return concernPersonPrimaryNumber;
    }

    public void setConcernPersonPrimaryNumber(String concernPersonPrimaryNumber) {
        this.concernPersonPrimaryNumber = concernPersonPrimaryNumber;
    }

    public String getConcernPersonSecondaryNumber() {
        return concernPersonSecondaryNumber;
    }

    public void setConcernPersonSecondaryNumber(String concernPersonSecondaryNumber) {
        this.concernPersonSecondaryNumber = concernPersonSecondaryNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankState() {
        return bankState;
    }

    public void setBankState(String bankState) {
        this.bankState = bankState;
    }

    public String getBankPinCode() {
        return bankPinCode;
    }

    public void setBankPinCode(String bankPinCode) {
        this.bankPinCode = bankPinCode;
    }

    public String getBankChequeInFavourOf() {
        return bankChequeInFavourOf;
    }

    public void setBankChequeInFavourOf(String bankChequeInFavourOf) {
        this.bankChequeInFavourOf = bankChequeInFavourOf;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String getBankSortCode() {
        return bankSortCode;
    }

    public void setBankSortCode(String bankSortCode) {
        this.bankSortCode = bankSortCode;
    }

    public String getAuthorizedBankPersonFirstName() {
        return authorizedBankPersonFirstName;
    }

    public void setAuthorizedBankPersonFirstName(String authorizedBankPersonFirstName) {
        this.authorizedBankPersonFirstName = authorizedBankPersonFirstName;
    }

    public String getAuthorizedBankPersonSecondName() {
        return authorizedBankPersonSecondName;
    }

    public void setAuthorizedBankPersonSecondName(String authorizedBankPersonSecondName) {
        this.authorizedBankPersonSecondName = authorizedBankPersonSecondName;
    }

    public String getAuthorizedBankPersonDesignation() {
        return authorizedBankPersonDesignation;
    }

    public void setAuthorizedBankPersonDesignation(String authorizedBankPersonDesignation) {
        this.authorizedBankPersonDesignation = authorizedBankPersonDesignation;
    }

    public List<String> getBusinessType() {
        return businessType;
    }

    public void setBusinessType(List<String> businessType) {
        this.businessType = businessType;
    }

    public List<String> getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName, int i) {
        this.addressName.add(i, addressName);
    }

    public List<String> getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1, int i) {
        this.addressLine1.add(i, addressLine1);
    }

    public List<String> getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity, int i) {
        this.addressCity.add(i, addressCity);
    }

    public List<String> getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState, int i) {
        this.addressState.add(i, addressState);
    }

    public List<String> getAddressPinCode() {
        return addressPinCode;
    }

    public void setAddressPinCode(String addressPinCode, int i) {
        this.addressPinCode.add(i, addressPinCode);
    }

    public List<String> getAddressPhone() {
        return addressPhone;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone.add(addressPhone);
    }

    public List<Boolean> getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(Boolean registeredAddress, int i) {
        this.registeredAddress.add(i, registeredAddress);
    }
}
