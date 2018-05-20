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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

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

    List<Bid> listBidEmpty;
    List<Image> listImageEmpty;
    List<Category> listCategoryEmpty;
    List<Item> listItemEmpty;
    List<Bid> listBidNotEmpty;
    List<Bid> listBidNotEmptyTwo;
    List<Image> listImageNotEmpty;
    List<Category> listCategoryNotEmpty;
    List<Item> listItemNotEmpty;
    List<Item> listItemNotEmptyForCategory;
    List<Bid> listBidNotEmptyForItem;

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
        listItemNotEmptyForCategory = new LinkedList<>();
        listBidNotEmptyForItem = new LinkedList<>();

        timestampFuture = new Timestamp(1000000000000000000L);

        byteEmpty = "".getBytes();

        userNotEmpty = spy(new User(listItemEmpty, listBidNotEmptyTwo, 2, "david", "2222", "2@2", User.Role.USER));
        userNotEmptyAdmin = spy(new User(listItemNotEmpty, listBidNotEmpty, 1, "zoli", "1111", "1@1", User.Role.ADMIN));
        categoryNotEmpty = spy(new Category(listItemNotEmptyForCategory, 1, "auto"));
        categoryNotEmptyTwo = spy(new Category(listItemEmpty, 2, "szamitogep"));
        itemNotEmpty = new Item(listBidNotEmptyForItem, listImageNotEmpty, userNotEmptyAdmin, categoryNotEmpty, 1, "trabant", "jokocsi", 0, 1000000, timestampFuture, 100, 1);
        imageNotEmpty = spy(new Image(itemNotEmpty, 1, "autoitem"));
        imageNotEmptyTwo = spy(new Image(itemNotEmpty, 2, "autoitem2"));
        bidNotEmpty = spy(new Bid(itemNotEmpty, userNotEmpty, 2, 1000));
        bidNotEmptyOriginal = spy(new Bid(itemNotEmpty, userNotEmptyAdmin, 1, 500));

        listBidNotEmpty.add(bidNotEmptyOriginal);
        listBidNotEmptyTwo.add(bidNotEmpty);
        listImageNotEmpty.add(imageNotEmpty);
        listItemNotEmpty.add(itemNotEmpty);
        listCategoryNotEmpty.add(categoryNotEmpty);
        listItemNotEmptyForCategory.add(itemNotEmpty);
        listBidNotEmptyForItem.add(bidNotEmptyOriginal);

        userNotEmptyAdmin.setBids(listBidNotEmpty);
        userNotEmpty.setBids(listBidNotEmptyTwo);
        itemNotEmpty.setImages(listImageNotEmpty);
        userNotEmptyAdmin.setItems(listItemNotEmpty);
        categoryNotEmpty.setItems(listItemNotEmptyForCategory);
        itemNotEmpty.setBids(listBidNotEmptyForItem);

        listBidNotEmpty = userNotEmptyAdmin.getBids();
        listBidNotEmptyTwo = userNotEmpty.getBids();
        listImageNotEmpty = itemNotEmpty.getImages();
        listItemNotEmpty = userNotEmptyAdmin.getItems();
        listItemNotEmptyForCategory = categoryNotEmpty.getItems();
        listBidNotEmptyForItem = itemNotEmpty.getBids();
    }

    @Test
    public void testFindAll_ReturnCategoryList() {
        doReturn(listCategoryNotEmpty).when(categoryRepositoryMock).findAll();
        assertEquals(categoryService.findAll(), listCategoryNotEmpty);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateCategory_NullPointerException_CategoryNull() throws UserNotValidException, CategoryNotValidException {
        categoryService.createCategory(categoryNull, userNotEmptyAdmin);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateCategory_NullPointerException_UserNull() throws UserNotValidException, CategoryNotValidException {
        categoryService.createCategory(categoryNull, userNull);
    }

    @Test(expected = UserNotValidException.class)
    public void testCreateCategory_UserNotValidException_UserNotAdmin() throws UserNotValidException, CategoryNotValidException {
        categoryService.createCategory(categoryNotEmptyTwo, userNotEmpty);
    }

    @Test(expected = CategoryNotValidException.class)
    public void testCreateCategory_CategoryNotValidException_CategoryAlreadyExists() throws UserNotValidException, CategoryNotValidException {
        doReturn(listCategoryNotEmpty).when(categoryRepositoryMock).findAll();
        categoryService.createCategory(categoryNotEmpty, userNotEmptyAdmin);
    }

    @Test
    public void testCreateCategory_ReturnCategoryList() throws UserNotValidException, CategoryNotValidException {
        doReturn(listCategoryNotEmpty).when(categoryRepositoryMock).findAll();
        doReturn(categoryNotEmptyTwo).when(categoryRepositoryMock).save(categoryNotEmptyTwo);
        assertEquals(categoryService.createCategory(categoryNotEmptyTwo, userNotEmptyAdmin), listCategoryNotEmpty);
    }
}
