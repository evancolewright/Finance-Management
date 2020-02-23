package FXML;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TransactionTests {

    @Test
    public void getDate() {
        Date d = new Date();
        Transaction t = new Transaction('W',100.0,1500.0,"1");
        Date testDate = t.getDate();
        assertEquals(d.getClass().getName(), testDate.getClass().getName());
    }

    @Test
    public void setDate() {
    }

    @Test
    public void getType() {
    }

    @Test
    public void setType() {
    }

    @Test
    public void getAmount() {
    }

    @Test
    public void setAmount() {
    }

    @Test
    public void getBalance() {
    }

    @Test
    public void setBalance() {
    }

    @Test
    public void getDescription() {
    }

    @Test
    public void setDescription() {
    }
}