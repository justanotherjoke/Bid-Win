package hu.elte.bidAndWin.service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Category;
import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.BidRepository;
import hu.elte.bidAndWin.repository.ItemRepository;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestItemService {

    @Mock
    ItemRepository itemRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;
    @Mock
    BidRepository bidRepositoryMock;

    @InjectMocks
    ItemService itemService;

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

        userNotEmpty = spy(new User(listItemEmpty, listBidNotEmptyTwo, 2, "david", "2222", "2@2", User.Role.USER));
        userNotEmptyAdmin = spy(new User(listItemNotEmpty, listBidNotEmpty, 1, "zoli", "1111", "1@1", User.Role.ADMIN));
        categoryNotEmpty = spy(new Category(listItemNotEmpty, 1, "auto"));
        categoryNotEmptyTwo = spy(new Category(listItemEmpty, 2, "szamitogep"));
        itemNotEmpty = spy(new Item(listImageNotEmpty, userNotEmptyAdmin, categoryNotEmpty, "trabant", "jokocsi", 0, 1000000, timestampFuture, 100));
        imageNotEmpty = spy(new Image("autoitem", "path", itemNotEmpty));
        imageNotEmptyTwo = spy(new Image("autoitem2", "path2", itemNotEmpty));
        bidNotEmpty = spy(new Bid(itemNotEmpty, userNotEmpty, 2, 1000));
        bidNotEmptyOriginal = spy(new Bid(itemNotEmpty, userNotEmptyAdmin, 1, 500));

        listBidNotEmpty.add(bidNotEmptyOriginal);
        listBidNotEmptyTwo.add(bidNotEmpty);
        listImageNotEmpty.add(imageNotEmpty);
        listItemNotEmpty.add(itemNotEmpty);
        listCategoryNotEmpty.add(categoryNotEmpty);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateItem_NullPointerException() throws ItemNotValidException {
        itemService.createItem(itemNull, userNotEmptyAdmin);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateItem_NullPointerException2() throws ItemNotValidException {
        itemService.createItem(itemNotEmpty, userNull);
    }

    @Test(expected = ItemNotValidException.class)
    public void testCreateItem_ItemNotValidException() throws ItemNotValidException {
        itemNotEmpty.setEndTime(new Timestamp(0));
        itemService.createItem(itemNotEmpty, userNotEmptyAdmin);
    }

    @Test(expected = ItemNotValidException.class)
    public void testCreateItem_ItemNotValidException2() throws ItemNotValidException {
        itemNotEmpty.setStartPrice(2000000);
        itemService.createItem(itemNotEmpty, userNotEmptyAdmin);
    }

    @Test
    public void testCreateItem_ReturnItem() throws ItemNotValidException {
        doReturn(itemNotEmpty).when(itemRepositoryMock).save(Mockito.any(Item.class));
        doReturn(bidNotEmptyOriginal).when(bidRepositoryMock).save(Mockito.any(Bid.class));
        assertEquals(itemService.createItem(itemNotEmpty, userNotEmptyAdmin), itemNotEmpty);
    }

    @Test(expected = NullPointerException.class)
    public void testGetItem_NullPointerException() {
        itemService.getItem(1, userNull);
    }

    @Test
    public void testGetItem_ReturnItem() {
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(1);
        assertEquals(itemService.getItem(1, userNotEmpty), itemNotEmpty);
    }

    @Test
    public void testGetAllItems_ReturnItemList() {
        doReturn(listItemNotEmpty).when(itemRepositoryMock).findAll();
        assertEquals(itemService.getAllItems(), listItemNotEmpty);
    }

    @Test(expected = NullPointerException.class)
    public void testGetMyItems_NullPointerException() {
        itemService.getMyItems(userNull);
    }

    @Test
    public void testGetMyItems_ReturnItemList() {
        long id = userNotEmpty.getId();
        doReturn(listItemNotEmpty).when(itemRepositoryMock).findAllByUserId(id);
        assertEquals(itemService.getMyItems(userNotEmpty), listItemNotEmpty);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateItem_NullPointerException() throws ItemNotValidException, UserNotValidException {
        itemService.updateItem(1, itemNull, userNotEmptyAdmin);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateItem_NullPointerException2() throws ItemNotValidException, UserNotValidException {
        itemService.updateItem(1, itemNotEmpty, userNull);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateItem_NullPointerException3() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNull).when(itemRepositoryMock).findById(1);
        itemService.updateItem(1, itemNotEmpty, userNotEmptyAdmin);
    }

    @Test(expected = UserNotValidException.class)
    public void testUpdateItem_UserNotValidException() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(1);
        itemService.updateItem(1, itemNotEmpty, userNotEmpty);
    }

    @Test(expected = ItemNotValidException.class)
    public void testUpdateItem_ItemNotValidException() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(1);
        itemNotEmpty.setEndTime(new Timestamp(0));
        itemService.updateItem(1, itemNotEmpty, userNotEmptyAdmin);
    }

    @Test(expected = ItemNotValidException.class)
    public void testUpdateItem_ItemNotValidException2() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(1);
        itemNotEmpty.setStartPrice(2000000);
        itemService.updateItem(1, itemNotEmpty, userNotEmptyAdmin);
    }

    @Test
    public void testUpdateItem_ReturnItem() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(1);
        doReturn(itemNotEmpty).when(itemRepositoryMock).save(itemNotEmpty);
        assertEquals(itemService.updateItem(1, itemNotEmpty, userNotEmptyAdmin), itemNotEmpty);
    }

}
