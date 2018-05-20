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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

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

    @Test(expected = NullPointerException.class)
    public void testCreateItem_NullPointerException_ItemNull() throws ItemNotValidException {
        itemService.createItem(itemNull, userNotEmptyAdmin);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateItem_NullPointerException_UserNull() throws ItemNotValidException {
        itemService.createItem(itemNotEmpty, userNull);
    }

    @Test(expected = ItemNotValidException.class)
    public void testCreateItem_ItemNotValidException_CurrentTimeIsBeforeEndTime() throws ItemNotValidException {
        itemNotEmpty.setEndTime(new Timestamp(0));
        itemService.createItem(itemNotEmpty, userNotEmptyAdmin);
    }

    @Test(expected = ItemNotValidException.class)
    public void testCreateItem_ItemNotValidException_StartPriceIsHigherThenBuyItPrice() throws ItemNotValidException {
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
    public void testGetItem_NullPointerException_UserNull() {
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
    public void testGetMyItems_NullPointerException_UserNull() {
        itemService.getMyItems(userNull);
    }

    @Test
    public void testGetMyItems_ReturnItemList() {
        long id = userNotEmpty.getId();
        doReturn(listItemNotEmpty).when(itemRepositoryMock).findAllByUserId(id);
        assertEquals(itemService.getMyItems(userNotEmpty), listItemNotEmpty);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateItem_NullPointerException_ItemNull() throws ItemNotValidException, UserNotValidException {
        itemService.updateItem(1, itemNull, userNotEmptyAdmin);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateItem_NullPointerException_UserNull() throws ItemNotValidException, UserNotValidException {
        itemService.updateItem(1, itemNotEmpty, userNull);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateItem_NullPointerException_ItemDoesNotExists() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNull).when(itemRepositoryMock).findById(1);
        itemService.updateItem(1, itemNotEmpty, userNotEmptyAdmin);
    }

    @Test(expected = UserNotValidException.class)
    public void testUpdateItem_UserNotValidException_ItemDoesNotBelongToUser() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(1);
        itemService.updateItem(1, itemNotEmpty, userNotEmpty);
    }

    @Test(expected = ItemNotValidException.class)
    public void testUpdateItem_ItemNotValidException_CurrentTimeIsBeforeEndTime() throws ItemNotValidException, UserNotValidException {
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(1);
        itemNotEmpty.setEndTime(new Timestamp(0));
        itemService.updateItem(1, itemNotEmpty, userNotEmptyAdmin);
    }

    @Test(expected = ItemNotValidException.class)
    public void testUpdateItem_ItemNotValidException_StartPriceIsHigherThenBuyItPrice() throws ItemNotValidException, UserNotValidException {
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
