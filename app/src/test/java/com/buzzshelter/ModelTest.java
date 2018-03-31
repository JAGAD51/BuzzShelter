package com.buzzshelter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by dhuynh38 on 3/30/18.
 */

public class ModelTest {

    private Model testModel;

    private DatabaseHelper db;
    private SQLiteDatabase sqlDb;
    private Context context;

    private String testUsername;
    private String testPassword;
    private User testUser;

    @Before
    public void setUp() {
        testModel = Model.getInstance();
        context = mock(Context.class);
        db = mock(DatabaseHelper.class);
        DatabaseHelper.setInstance(db);
        sqlDb = mock(SQLiteDatabase.class);
        testUsername = "Bob";
        testPassword = "123";
        testUser = mock(User.class);
    }

    /**
     * Tests validateUser method when all parameters are null.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_NullParams() {
        boolean validated = testModel.validateUser(null, null, null);
        assertEquals(validated, false);
    }

    /**
     * Tests validateUser method when the specified user cannot be found.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_NoUserFound() {
        when(db.fetchSpecificUserByID(testUsername)).thenReturn(null);
        boolean validated = testModel.validateUser(testUsername, testPassword, context);
        assertEquals(validated, false);
    }

    /**
     * Tests validateUser method when the password entered is incorrect.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_IncorrectPassword() {
        when(db.fetchSpecificUserByID(testUsername)).thenReturn(testUser);
        when(testUser.getPassword()).thenReturn("");

        boolean validated = testModel.validateUser(testUsername, testPassword, context);
        assertEquals(validated, false);
    }

    /**
     * Tests validateUser method when the password entered is correct.
     * Duy Huynh
     */
    @Test
    public void testValidateUser_CorrectPassword() {
        when(db.fetchSpecificUserByID(testUsername)).thenReturn(testUser);
        when(testUser.getPassword()).thenReturn(testPassword);

        boolean validated = testModel.validateUser(testUsername, testPassword, context);
        assertEquals(validated, true);
    }

}
