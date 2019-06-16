package ru.petrovich.study.backend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class EmptyDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
