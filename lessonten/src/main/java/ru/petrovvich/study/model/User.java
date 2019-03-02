package ru.petrovvich.study.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.petrovvich.study.annotations.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    private Integer id;
    private String name;
    private int age;

    public User(String name, int id) {

    }
}
