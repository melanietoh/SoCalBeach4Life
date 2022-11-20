package com.example.socalbeach4life;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

public class UserModelTester {
    /** Setup method executed before each test */
    DatabaseModeler db;

    @Before
    public void setup() {
        db = new DatabaseModeler();
    }
    /** Test Case #1 */
    @Test
    public void testUserRegistration() {
        UserModel test = new UserModel("testid", "testemail@gmail.com", "testname", new HashMap<>(), new HashMap<>());

        assertTrue("Registering User Successful", db.testRegisterUser(test));
    }

    /** Test Case #2 */
    @Test
    public void testUserLogin() {
        UserModel test = new UserModel("testid", "testemail@gmail.com", "testname", new HashMap<>(), new HashMap<>());

        db.testRegisterUser(test);
        assertTrue("Registering User Successful", db.signInValidate(test));
    }
}
