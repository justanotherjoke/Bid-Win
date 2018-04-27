package hu.elte.bidAndWin.service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.UserRepository;
import hu.elte.bidAndWin.service.UserNotValidException;
import hu.elte.bidAndWin.service.UserService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestUserService {

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UserService userService;

    List<Item> items = new LinkedList<>();
    List<Bid> bids = new LinkedList<>();
    User userSpy = spy(new User());
    User userTwoSpy = spy(new User(items, bids, 2, "a", "b", "c", User.Role.USER));

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testRegister() {
        // to do:nem dob exceptiont ures userre most azt at kene irni itt: userservice.register
        doReturn(userSpy).when(userRepositoryMock).save(userSpy);
        userService.register(userSpy);
        verify(userSpy, times(1)).setRole(User.Role.USER);
        assertEquals(userService.register(userSpy), userSpy);

        doReturn(userTwoSpy).when(userRepositoryMock).save(userTwoSpy);
        userService.register(userTwoSpy);
        verify(userTwoSpy, times(1)).setRole(User.Role.USER);
        assertEquals(userService.register(userTwoSpy), userTwoSpy);
    }

    @Test(expected = UserNotValidException.class)
    public void testlogin_ExceptionThrown() throws UserNotValidException {
        String username = userSpy.getUsername();
        String password = userSpy.getPassword();
        Optional<User> optional = Optional.ofNullable(null);
        doReturn(optional).when(userRepositoryMock).findByUsernameAndPassword(username, password);
        userService.login(userSpy);
    }

    @Test
    public void testlogin_ExceptionNotThrown() throws UserNotValidException {
        String username = userTwoSpy.getUsername();
        String password = userTwoSpy.getPassword();
        Optional<User> optional = Optional.of(userTwoSpy);
        doReturn(optional).when(userRepositoryMock).findByUsernameAndPassword(username, password);
        doReturn(userTwoSpy).when(userRepositoryMock).findByUsername(username);
        assertEquals(userService.login(userTwoSpy), userTwoSpy);
    }

    @Test
    public void testIsLoggedIn_ReturnFalse() throws UserNotValidException {
        UserService userServiceNullUser = new UserService();
        assertEquals(userServiceNullUser.isLoggedIn(), false);

    }

    @Test
    public void testIsLoggedIn_ReturnTrue() throws UserNotValidException {
        UserService userServiceNotNullUser = new UserService(userTwoSpy, userRepositoryMock);
        assertEquals(userServiceNotNullUser.isLoggedIn(), true);
    }

    @Test
    public void testGetLoggedInUser_ReturnNull() {
        UserService userServiceNullUser = new UserService();
        assertEquals(userServiceNullUser.getLoggedInUser(), null);

    }

    @Test
    public void testGetLoggedInUser_ReturnUser() {
        UserService userServiceNotNullUser = new UserService(userTwoSpy, userRepositoryMock);
        assertEquals(userServiceNotNullUser.getLoggedInUser(), userTwoSpy);
    }

    @Test
    public void testLogout_ReturnNull() {
        UserService userServiceNullUser = new UserService();
        userServiceNullUser.logout();
        assertEquals(userServiceNullUser.getUser(),null);

        UserService userServiceNotNullUser = new UserService(userTwoSpy, userRepositoryMock);
        userServiceNotNullUser.logout();
        assertEquals(userServiceNullUser.getUser(),null);
        

    }

}
