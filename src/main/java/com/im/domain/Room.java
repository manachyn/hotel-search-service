package com.im.domain;

import java.util.ArrayList;
import java.util.List;

public class Room {

    protected int adults;
    protected int children;
    protected List<Integer> childAges;

    public int getAdults() {
        return adults;
    }

    public void setAdults(int value) {
        this.adults = value;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int value) {
        this.children = value;
    }

    public List<Integer> getChildAges() {
        if (childAges == null) {
            childAges = new ArrayList<Integer>();
        }
        return childAges;
    }

    public void setChildAges(List<Integer> childAges) {
        this.childAges = childAges;
    }

}
