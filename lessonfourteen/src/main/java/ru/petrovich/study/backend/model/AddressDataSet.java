package ru.petrovich.study.backend.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Data
@Entity
@Table(name = "address")
public class AddressDataSet extends DataSet {

    public AddressDataSet() {

    }

    public AddressDataSet(String city, String street, String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @Column
    private String city;

    @Column
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Override
    public String toString() {
        return new StringJoiner(", ", AddressDataSet.class.getSimpleName() + "[", "]")
                .add("city='" + city + "'")
                .add("street='" + street + "'")
                .add("houseNumber='" + houseNumber + "'")
                .toString();
    }
}
