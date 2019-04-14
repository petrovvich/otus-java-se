package ru.petrovvich.study.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private PhoneDataSet phone;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    public UserDataSet() {
    }

    public UserDataSet(String name, PhoneDataSet phone, AddressDataSet address) {
        this.setId(-1);
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public PhoneDataSet getPhone() {
        return phone;
    }

    private void setPhone(PhoneDataSet phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id'" + getId() + '\'' +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
