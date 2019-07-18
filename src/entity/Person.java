package entity;

import Annotation.XmlAttr;

public class Person {
    @XmlAttr("xm")
    private String name;

    @XmlAttr("zjlx")
    private String idCardType;

    @XmlAttr("sfzmh")
    private String idCardNum;

    @XmlAttr("lxdh")
    private String telephone;

    @XmlAttr("lxdz")
    private String address;

    @XmlAttr("dzyx")
    private String email;

    @XmlAttr("yzbm")
    private String postCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
