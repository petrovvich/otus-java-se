package ru.petrovich.study;

import lombok.NoArgsConstructor;
import ru.petrovich.study.source.TestEntity;
import ru.petrovich.study.test.interfaces.After;
import ru.petrovich.study.test.interfaces.Before;
import ru.petrovich.study.test.interfaces.Test;

@NoArgsConstructor
public class TestingClass {

    private static final String RESULT_OK = "OK";
    private static final String RESULT_FAIL = "FAIL";

    private TestEntity testEntity = new TestEntity();

    @Before
    public void setUp() throws Exception {
        System.out.println("Running setting before");
        testEntity.setAge(11L);
        testEntity.setFirstName("Danny");
        testEntity.setLastName("Krakovitz");
        testEntity.setMiddleName("Second");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Running setting after");
        testEntity.setAge(null);
        testEntity.setFirstName(null);
        testEntity.setLastName(null);
        testEntity.setMiddleName(null);
    }

    @Test
    public String testGetFirstName(){
        System.out.println("Running test");
        String firstName = "Danny";

        return "Test result is: " + (firstName.equals(testEntity.getFirstName()) ? RESULT_OK : RESULT_FAIL);
    }
}
