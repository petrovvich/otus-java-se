package ru.petrovich.study.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.StringJoiner;

@Data
@Entity
@Table(name = "user")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private PhoneDataSet phone;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressDataSet address;

    @Column
    private String password;

    public UserDataSet() {
    }

    public UserDataSet(String name, String password, PhoneDataSet phone, AddressDataSet address) {
        this.setId(-1);
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = password;
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
        return new StringJoiner(", ", UserDataSet.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("phone=" + phone)
                .add("address=" + address)
                .add("password='" + password + "'")
                .toString();
    }
}
