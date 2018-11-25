package ru.petrovvich.study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TestArrayList {

    public static void main(String[] args) {
        List<Integer> listInt = new ArrayList<>();
        listInt.add(1);
        listInt.add(2);
        listInt.add(3);

        List<Integer> list = new ArrayList();

        list.addAll(listInt);

        List<Integer> secondListInt = new ArrayList<>();
        secondListInt.add(1);
        secondListInt.add(453);
        secondListInt.add(3);
        secondListInt.add(2);

        Collections.copy(secondListInt, listInt);

        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        myArrayList.add(1);

        myArrayList.addAll(list);
        Collections.copy(myArrayList, listInt);
    }

    private static void printList(List<?> printedList) {
        for (Object element : printedList){
            if (Objects.isNull(element)) {
                System.out.println("Element is null. Exit.");
                return;
            }
            System.out.println(element.toString());
        }
    }
}
