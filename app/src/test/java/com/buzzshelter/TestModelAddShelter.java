package com.buzzshelter;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.Shelter;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class TestModelAddShelter {

    private Shelter shelter;
    private Shelter shelterDupe;
    private Shelter shelter2;
    private Shelter shelterNull;

    @Before
    public void setUp() {
        shelter = new Shelter("Paradise,", "50", "Male",
                "-51", "30", "564 Church Ave,", "456 - 654 - 4444");
        shelterDupe = new Shelter("Paradise,", "50", "Male",
                "-51", "30", "564 Church Ave,", "456 - 654 - 4444");
        shelter2 = new Shelter("Mexico Hotel", "89", "Women", "-89",
                "40", "456 Taco Street", "456 - 987 - 2341");
        Model.getInstance().getShelterList().clear();
    }

    @Test
    public void TestAdd() {
        assertEquals(true, Model.getInstance().addShelter(shelter));
    }

    @Test
    public void TestDupe() {
        assertEquals(true, Model.getInstance().addShelter(shelter));
        assertEquals(false, Model.getInstance().addShelter(shelterDupe));
    }

    @Test
    public void TestNull() {
        assertEquals(false, Model.getInstance().addShelter(null));
    }

    @Test
    public void TestAddMultiple() {
        assertEquals(true, Model.getInstance().addShelter(shelter));
        assertEquals(true, Model.getInstance().addShelter(shelter2));
    }
}