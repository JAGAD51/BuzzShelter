package com.buzzshelter;

import android.content.Context;

import com.buzzshelter.Model.AccountType;
import com.buzzshelter.Model.Model;
import com.buzzshelter.Model.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Created by tonyzhang on 4/4/18.
 */

public class AddUserTests {

    private Model testModel;

    private DatabaseHelper db;
    private Context context;
    private User user1;
    private String existingUsername = "Bob";

    @Before
    public void setUp() {
        testModel = Model.getInstance();
        db = mock(DatabaseHelper.class);
        context = mock(Context.class);

        DatabaseHelper.setInstance(db);
        user1 = mock(User.class);

    }

    /**
     * Tests addUser method when the user is null
     * Tony Zhang
     */
    @Test
    public void testAddUser_NullUser() {
        boolean validated = testModel.addUser(null, context);
        assertEquals(validated, false);
    }

    /**
     * Tests addUser method when the user already exists
     * Tony Zhang
     */
    @Test
    public void testAddUser_ExistingUser() {
        user1 = db.fetchSpecificUserByID(existingUsername);
        boolean validated = testModel.addUser(user1, context);
        assertEquals(false, validated);
    }

    /**
     * Tests addUser method when adding a new user
     * Tony Zhang
     */
    @Test
    public void testAddUser() {
        user1 = new User("testId", "TestUser", "123456", AccountType.USER);
        boolean validated = testModel.addUser(user1, context);
        assertEquals(validated, true);
    }

    /**
     * Tests addUser method when context is null
     * Tony Zhang
     */
    @Test
    public void testAddUser_nullContext() {
        user1 = new User("testId", "TestUser", "123456", AccountType.USER);
        boolean validated = testModel.addUser(user1, null);
        assertEquals(validated, false);
    }

    /**
     * Tests addUser method when db is null
     * Tony Zhang
     */
    @Test
    public void testAddUser_nullDatabase() {
        user1 = new User("testId", "TestUser", "123456", AccountType.USER);
        boolean validated = testModel.addUser(user1, context);
        assertEquals(validated, false);
    }




}
