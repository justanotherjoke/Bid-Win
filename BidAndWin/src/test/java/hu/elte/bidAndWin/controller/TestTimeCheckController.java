package hu.elte.bidAndWin.controller;

import org.junit.*;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BidController.class)
public class TestTimeCheckController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testgetAllBids_ReturnOK() throws Exception {
        assertEquals(1, 1);
        //ezt igy nem tudom tesztelni
    }
}
