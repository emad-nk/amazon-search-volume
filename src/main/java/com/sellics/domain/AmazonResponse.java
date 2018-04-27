package com.sellics.domain;

import java.util.ArrayList;

public class AmazonResponse {

    private String first;
    private ArrayList<String> list1;
    private ArrayList<String> list2;
    private ArrayList<String> list3;
    private String last;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public ArrayList<String> getList1() {
        return list1;
    }

    public void setList1(ArrayList<String> list1) {
        this.list1 = list1;
    }

    public ArrayList<String> getList2() {
        return list2;
    }

    public void setList2(ArrayList<String> list2) {
        this.list2 = list2;
    }

    public ArrayList<String> getList3() {
        return list3;
    }

    public void setList3(ArrayList<String> list3) {
        this.list3 = list3;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
