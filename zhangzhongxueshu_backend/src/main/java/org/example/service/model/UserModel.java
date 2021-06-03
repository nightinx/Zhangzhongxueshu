package org.example.service.model;

public class UserModel {
    private Integer id;

    private String name;

    private String mailBox;
    private String encrptPassword;

    private String school;
    private String interest_1;
    private String interest_2;
    private String interest_3;

    public void setSchool(String school) {
        this.school = school;
    }

    public void setInterest_1(String interest_1) {
        this.interest_1 = interest_1;
    }

    public void setInterest_2(String interest_2) {
        this.interest_2 = interest_2;
    }

    public void setInterest_3(String interest_3) {
        this.interest_3 = interest_3;
    }


    public String getSchool() {
        return school;
    }

    public String getInterest_1() {
        return interest_1;
    }

    public String getInterest_2() {
        return interest_2;
    }

    public String getInterest_3() {
        return interest_3;
    }







    public Integer getId() {
        return id;
    }

    public String getMailBox() {
        return mailBox;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }
}
