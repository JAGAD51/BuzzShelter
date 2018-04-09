package com.buzzshelter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

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


// doesn't test toasts
// addUser doesn't check for null accounts, accounts with partially filled info, etc.
//implement: checking that the new user is in the database after adduser is called successfully

public class AddUserTests {

    private Model testModel;

    private DatabaseHelper db;
    private Context context;
    private Toast toast;
    private User user1;
    private String existingUsername = "Bob";

    @Before
    public void setUp() {
        testModel = Model.getInstance();
        db = mock(DatabaseHelper.class);
        context = mock(Context.class);
        toast = mock(Toast.class);


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

        assertTrue(db.fetchSpecificUserByID(user1.getId()) != null);
    }




}
