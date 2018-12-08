package ru.petrovich.study.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TestEntity {

    private String firstName;

    private String lastName;

    private String middleName;

    private Long age;

    private boolean isEmployed;

    private String postition;
}
