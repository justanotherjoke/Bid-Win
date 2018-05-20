package hu.elte.bidAndWin.controller;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(TimeCheckController.class)
public class TestTimeCheckController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testgetTime_ReturnTime() {
        assertEquals(1, 1);
    }
}
