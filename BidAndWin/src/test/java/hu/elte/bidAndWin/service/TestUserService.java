package hu.elte.bidAndWin.service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.UserRepository;

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

	List<Item> listItemEmpty = new LinkedList<>();
	List<Bid> listBidEmpty = new LinkedList<>();
	User userEmptySpy = spy(new User());
	User userNotEmptySpy = spy(new User(listItemEmpty, listBidEmpty, 2, "david", "2222", "2@2", User.Role.USER));

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testRegister() {
		doReturn(userEmptySpy).when(userRepositoryMock).save(userEmptySpy);
		userService.register(userEmptySpy);
		verify(userEmptySpy, times(1)).setRole(User.Role.USER);
		assertEquals(userService.register(userEmptySpy), userEmptySpy);

		doReturn(userNotEmptySpy).when(userRepositoryMock).save(userNotEmptySpy);
		userService.register(userNotEmptySpy);
		verify(userNotEmptySpy, times(1)).setRole(User.Role.USER);
		assertEquals(userService.register(userNotEmptySpy), userNotEmptySpy);
	}

	@Test(expected = UserNotValidException.class)
	public void testLogin_UserNotValidExceptionThrown() throws UserNotValidException {
		String username = userEmptySpy.getUsername();
		String password = userEmptySpy.getPassword();
		Optional<User> optionalEmpty = Optional.ofNullable(null);
		doReturn(optionalEmpty).when(userRepositoryMock).findByUsernameAndPassword(username, password);
		userService.login(userEmptySpy);
	}

	@Test
	public void testLogin_RetrunUser() throws UserNotValidException {
		String username = userNotEmptySpy.getUsername();
		String password = userNotEmptySpy.getPassword();
		Optional<User> optionalNoEmpty = Optional.of(userNotEmptySpy);
		doReturn(optionalNoEmpty).when(userRepositoryMock).findByUsernameAndPassword(username, password);
		doReturn(userNotEmptySpy).when(userRepositoryMock).findByUsername(username);
		assertEquals(userService.login(userNotEmptySpy), userNotEmptySpy);
	}

	@Test
	public void testIsLoggedIn_ReturnFalse() throws UserNotValidException {
		UserService userServiceEmptyUser = new UserService();
		assertEquals(userServiceEmptyUser.isLoggedIn(), false);

	}

	@Test
	public void testIsLoggedIn_ReturnTrue() throws UserNotValidException {
		UserService userServiceNotEmptyUser = new UserService(userNotEmptySpy, userRepositoryMock);
		assertEquals(userServiceNotEmptyUser.isLoggedIn(), true);
	}

	@Test
	public void testGetLoggedInUser_ReturnNull() {
		UserService userServiceEmptyUser = new UserService();
		assertEquals(userServiceEmptyUser.getLoggedInUser(), null);

	}

	@Test
	public void testGetLoggedInUser_ReturnUser() {
		UserService userServiceNotEmptyUser = new UserService(userNotEmptySpy, userRepositoryMock);
		assertEquals(userServiceNotEmptyUser.getLoggedInUser(), userNotEmptySpy);
	}

	@Test
	public void testLogout_ReturnNull() {
		UserService userServiceEmptyUser = new UserService();
		userServiceEmptyUser.logout();
		assertEquals(userServiceEmptyUser.getUser(), null);

		UserService userServiceNotEmptyUser = new UserService(userNotEmptySpy, userRepositoryMock);
		userServiceNotEmptyUser.logout();
		assertEquals(userServiceEmptyUser.getUser(), null);

	}
	//to do: test nulls
	//to do:nem dob exceptiont ures userre most azt at kene irni itt: userservice.register
	//to do: cleanup unnecesary shit

}
