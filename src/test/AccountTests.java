package test;

import main.FXML.Account;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTests{

    @Test
    public void testAccountGetMonthlyInterest() throws Throwable{
        Account acct = new Account(1, "Ryan", 1500.0, 12);

        double result = acct.getMonthlyInterest();
        assertEquals(15.0,result, 0.0);
    }

}