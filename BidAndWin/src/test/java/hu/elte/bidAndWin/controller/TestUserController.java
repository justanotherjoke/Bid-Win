package hu.elte.bidAndWin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Category;
import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.service.UserNotValidException;
import hu.elte.bidAndWin.service.UserService;
import java.sql.Timestamp;

import java.util.LinkedList;
import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class TestUserController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    List<Bid> listBidEmpty;
    List<Image> listImageEmpty;
    List<Category> listCategoryEmpty;
    List<Item> listItemEmpty;
    List<Bid> listBidNotEmpty;
    List<Bid> listBidNotEmptyTwo;
    List<Image> listImageNotEmpty;
    List<Category> listCategoryNotEmpty;
    List<Item> listItemNotEmpty;

    Timestamp timestampFuture;
    byte[] byteEmpty;

    Bid bidNull;
    Category categoryNull;
    Item itemNull;
    Image imageNull;
    User userNull;

    Bid bidNotEmpty;
    Bid bidNotEmptyOriginal;
    User userNotEmpty;
    User userNotEmptyAdmin;
    Category categoryNotEmpty;
    Category categoryNotEmptyTwo;
    Item itemNotEmpty;
    Image imageNotEmpty;
    Image imageNotEmptyTwo;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        listBidEmpty = new LinkedList<>();
        listImageEmpty = new LinkedList<>();
        listCategoryEmpty = new LinkedList<>();
        listItemEmpty = new LinkedList<>();
        listBidNotEmpty = new LinkedList<>();
        listBidNotEmptyTwo = new LinkedList<>();
        listImageNotEmpty = new LinkedList<>();
        listCategoryNotEmpty = new LinkedList<>();
        listItemNotEmpty = new LinkedList<>();

        timestampFuture = new Timestamp(1000000000000000000L);

        byteEmpty = "".getBytes();

        userNotEmpty = new User(listItemEmpty, listBidNotEmptyTwo, 2, "david", "2222", "2@2", User.Role.USER);
        userNotEmptyAdmin = new User(listItemNotEmpty, listBidNotEmpty, 1, "zoli", "1111", "1@1", User.Role.ADMIN);
        categoryNotEmpty = new Category(listItemNotEmpty, 1, "auto");
        categoryNotEmptyTwo = new Category(listItemEmpty, 2, "szamitogep");
        itemNotEmpty = new Item(listImageNotEmpty, userNotEmptyAdmin, categoryNotEmpty, "trabant", "jokocsi", 0, 1000000, timestampFuture, 100);
        imageNotEmpty = new Image("autoitem", "path", itemNotEmpty);
        imageNotEmptyTwo = new Image("autoitem2", "path2", itemNotEmpty);
        bidNotEmpty = new Bid(itemNotEmpty, userNotEmpty, 2, 1000);
        bidNotEmptyOriginal = new Bid(itemNotEmpty, userNotEmptyAdmin, 1, 500);

        listBidNotEmpty.add(bidNotEmptyOriginal);
        listBidNotEmptyTwo.add(bidNotEmpty);
        listImageNotEmpty.add(imageNotEmpty);
        listItemNotEmpty.add(itemNotEmpty);
        listCategoryNotEmpty.add(categoryNotEmpty);
    }

    @Test
    public void testUser_ReturnOk() throws Exception {
        doReturn(true).when(userService).isLoggedIn();
        doReturn(userNotEmptyAdmin).when(userService).getLoggedInUser();
        mvc.perform(get("/api/user")).andExpect(status().isOk());
    }

    @Test
    public void testUser_ReturnBadRequest() throws Exception {
        doReturn(false).when(userService).isLoggedIn();
        mvc.perform(get("/api/user")).andExpect(status().isBadRequest());
    }

    @Test
    public void testRegister_ReturnOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        doReturn(userNotEmpty).when(userService).register(Mockito.any(User.class));
        mvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testRegister_ReturnBadRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        doThrow(NullPointerException.class).when(userService).register(Mockito.any(User.class));
        mvc.perform(MockMvcRequestBuilders.post("/api/user/register").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_ReturnOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        doReturn(userNotEmpty).when(userService).login(Mockito.any(User.class));
        mvc.perform(MockMvcRequestBuilders.post("/api/user/login").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testLogin_ReturnBadRequest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        doThrow(UserNotValidException.class).when(userService).login(Mockito.any(User.class));
        mvc.perform(MockMvcRequestBuilders.post("/api/user/login").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testLogin_ReturnBadRequest2() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        doThrow(NullPointerException.class).when(userService).login(Mockito.any(User.class));
        mvc.perform(MockMvcRequestBuilders.post("/api/user/login").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void testLogout_ReturnOk() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(userNotEmpty);
        doReturn(userNotEmpty).when(userService).logout();
        mvc.perform(MockMvcRequestBuilders.post("/api/user/logout").content(jsonInString).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
