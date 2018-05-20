package hu.elte.bidAndWin.controller;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestTimeCheckController {

    @InjectMocks
    TimeCheckController timeCheckController;

    @Test
    public void testGetTime_ReturnDate() {
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
        Date currentDate = (Date) currentTime;
        System.out.println(timeCheckController.getTime().compareTo(currentDate));
        assertTrue((timeCheckController.getTime().compareTo(currentDate)) >= 0);
        assertTrue((timeCheckController.getTime().compareTo(currentDate)) <= 1000);
    }
}
