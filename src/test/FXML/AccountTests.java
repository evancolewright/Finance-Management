package FXML;

import FXML.Account;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTests{

    Account acct1 = new Account(1, "Ryan", 1500.0, 12);
    Account acct2 = new Account(2,"Ryan", 256437.56, 12);
    Account acct3 = new Account(3,"Ryan",1200.0, 12);

    @Test
    public void testAccountGetMonthlyInterest() throws Throwable{

        double result1 = acct1.getMonthlyInterest();
        double result2 = acct2.getMonthlyInterest();
        double result3 = acct3.getMonthlyInterest();
        assertEquals(15.0,result1, 0.0);
        assertEquals(2564.3756,result2,0.0);
        assertEquals(12.0, result3, 0.0);
    }

    @Test
    public void testAccountWithdrawNewBalance() throws Throwable{

        double result1 = acct1.withdraw(12.0, "6-pack of craft beer").getBalance();
        double result2 = acct2.withdraw(15.60, "meal out").getBalance();
        double result3 = acct3.withdraw(49.99, "model B-29 bomber").getBalance();

        assertEquals(1488.0, result1,0.0);
        assertEquals(256421.96, result2, 0.0);
        assertEquals(1150.01, result3, 0.0);

    }

    @Test
    public void testAccountDepositNewBalance() throws Throwable{

        double result1 = acct1.deposit(12.0, "mowing grass").getBalance();
        double result2 = acct2.deposit(0.44, "bubble-gum sale").getBalance();
        double result3 = acct3.deposit(100.0, "sell watch").getBalance();

        assertEquals(1512.0, result1,0.0);
        assertEquals(256438.0, result2, 0.0);
        assertEquals(1300.0, result3, 0.0);

    }

    @After
    public void resetTestAccounts(){
        acct1 = new Account(1, "Ryan", 1500.0, 12);
        acct2 = new Account(2,"Ryan", 256437.56, 12);
        acct3 = new Account(3,"Ryan",1200.0, 12);
    }

}