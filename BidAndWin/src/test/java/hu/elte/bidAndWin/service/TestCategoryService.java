package hu.elte.bidAndWin.service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Category;
import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.CategoryRepository;
import hu.elte.bidAndWin.repository.UserRepository;
import java.sql.Timestamp;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestCategoryService {

	@Mock
	CategoryRepository categoryRepositoryMock;
	@Mock
	UserRepository userRepositoryMock;

	@InjectMocks
	CategoryService categoryService;

	List<Bid> listBidEmpty = new LinkedList<>();
	List<Bid> listBidNotEmpty = new LinkedList<>();
	List<Item> listItemEmpty = new LinkedList<>();
	List<Image> listImageEmpty = new LinkedList<>();
	Item itemEmpty = new Item();
	Bid bidEmpty = new Bid();
	Category categoryEmpty = new Category();

	Timestamp timestampEmpty = new Timestamp(0);
	User userEmptySpy = spy(new User());
	User userAdminSpy = spy(new User(listItemEmpty, listBidEmpty, 1, "zoli", "1111", "1@1", User.Role.ADMIN));
	User userNotEmptySpy = spy(new User(listItemEmpty, listBidEmpty, 2, "david", "2222", "2@2", User.Role.USER));
	Bid bidNotEmptyOriginalSpy = spy(new Bid(itemEmpty, userEmptySpy, 1, 500));
	Item itemNotEmptySpy = spy(new Item(bidEmpty, listImageEmpty, userEmptySpy, categoryEmpty, 1, "trabant", "jokocsi", 0, 1000000, timestampEmpty, 100));
	Bid bidNotEmptySpy = spy(new Bid(itemEmpty, userEmptySpy, 2, 1000));
	Category categoryNotEmpty = new Category(listItemEmpty, 1, "auto");
	Category categoryNotEmptyTwo = new Category(listItemEmpty, 1, "szamitogep");
	List<Category> listCategoryEmpty = new LinkedList<>();
	List<Category> listCategoryNotEmpty = new LinkedList<>();

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);
		listItemEmpty.add(itemNotEmptySpy);
		listCategoryNotEmpty.add(categoryNotEmpty);

	}

	@Test
	public void testFindAll_ReturnEmptyCategoryList() {
		doReturn(listCategoryEmpty).when(categoryRepositoryMock).findAll();
		assertEquals(categoryService.findAll(), listCategoryEmpty);
	}

	@Test
	public void testFindAll_ReturnCategoryList() {
		doReturn(listCategoryNotEmpty).when(categoryRepositoryMock).findAll();
		assertEquals(categoryService.findAll(), listCategoryNotEmpty);
	}

	@Test(expected = UserNotValidException.class)
	public void testCreateCategory_UserNotValidExceptionThrown() throws UserNotValidException, CategoryNotValidException {
		categoryService.createCategory(categoryNotEmpty, userNotEmptySpy);
	}

	@Test(expected = CategoryNotValidException.class)
	public void testCreateCategory_CategoryNotValidExceptionThrown() throws UserNotValidException, CategoryNotValidException {
		doReturn(listCategoryNotEmpty).when(categoryRepositoryMock).findAll();
		categoryService.createCategory(categoryNotEmpty, userAdminSpy);
	}

	@Test
	public void testCreateCategory_RetrunCategoryList() throws UserNotValidException, CategoryNotValidException {
		doReturn(listCategoryNotEmpty).when(categoryRepositoryMock).findAll();
		categoryService.createCategory(categoryNotEmptyTwo, userAdminSpy);
		doReturn(categoryNotEmptyTwo).when(categoryRepositoryMock).save(categoryNotEmptyTwo);
		assertEquals(categoryService.createCategory(categoryNotEmptyTwo, userAdminSpy), listCategoryNotEmpty);
	}

	//to do: test nulls
	//to do: cleanup unnecesary shit
}
