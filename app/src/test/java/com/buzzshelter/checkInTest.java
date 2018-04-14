package com.buzzshelter;

import android.content.Context;
import android.support.v4.app.NavUtils;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.buzzshelter.Model.Shelter;
/**
 * Created by Grace Harper on 4/13/18.
 */

public class checkInTest {
    private Model testModel;
    private DatabaseHelper db;
    private Context context;
    private User user = mock(User.class);
    private String realShelterName = "Recovery Place";
    private String fakeShelterName = "Not a Real Shelter";
    private Shelter realShelter = new Shelter(realShelterName, "100", "Men", "11", "11", "105 Ln ","34566799686");
    private Shelter fakeShelter = new Shelter(fakeShelterName, "100", "Men", "11", "11", "105 Ln ","34566799686");
    private static final int TIMEOUT = 200;


    @Before
    public void setUp() {
        testModel = Model.getInstance();
        db = mock(DatabaseHelper.class);
        context = mock(Context.class);
        DatabaseHelper.setInstance(db);

    }

    /**
     * Tests addShelter
     */

    /*
    Tests null context
     */
    @Test(timeout = TIMEOUT)
    public void testNullContext() {
        boolean worked = testModel.addShelter(realShelter, null);
        assertEquals(false, worked);
    }

    /*
    Tests null shelter
     */

    @Test(timeout = TIMEOUT)
    public void testNullShelter() {
        boolean worked = testModel.addShelter(null, context);
        assertEquals(false, worked);
    }
    /*
    Test when the Shelter is already in the database
     */
    @Test(timeout = TIMEOUT)
    public void testPreexistingShelter() {
        when (db.fetchSpecificShelterByName(realShelterName)).thenReturn(realShelter);
        boolean worked = testModel.addShelter(realShelter,context);
        assertEquals(false, worked);
    }
    /*
    Tests when the Shelter is not already n the database
     */

    @Test(timeout = TIMEOUT)
    public void testNewShelter() {
        when (db.fetchSpecificShelterByName(fakeShelterName)).thenReturn(null);
        boolean worked = testModel.addShelter(fakeShelter, context);
        assertEquals(true, worked);
    }

    /**
     * Testing when the db is null
     * Should throw an excepion but our addShelter function
     * wasn't writtent to handle it.
     */

    @Test(timeout = TIMEOUT, expected = NullPointerException.class)
    void testCustomException() {
        final NullPointerException expectEx = new NullPointerException();
        when(db.fetchSpecificShelterByName(fakeShelterName)).thenThrow(new NullPointerException());
        testModel.addShelter(fakeShelter, context);

    }

}


