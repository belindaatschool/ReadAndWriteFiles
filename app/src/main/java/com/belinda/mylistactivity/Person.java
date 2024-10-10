package com.belinda.mylistactivity;

public class Person
{
    // attributes
    String firstName, lastName, phone;
    Gender gender;

    // Constructors
    public Person(String _firstName, String _lastName,
                  String _phone, Gender _gender)
    {
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.phone = _phone;
        this.gender = _gender;
    }

    public Person(){}

    // Getters and setters
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getPhone()
    {
        return phone;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    public Gender getGender()
    {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "[" + firstName + ", " + lastName + ", " +
                phone +  ", " + gender + "]";
    }
}
