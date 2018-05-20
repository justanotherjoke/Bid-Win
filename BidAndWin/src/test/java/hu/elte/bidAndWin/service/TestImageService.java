package hu.elte.bidAndWin.service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Category;
import hu.elte.bidAndWin.domain.Image;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ImageRepository;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;
import java.io.IOException;
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
public class TestImageService {

    @Mock
    ImageRepository imageRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;
    @Mock
    ItemRepository itemRepositoryMock;

    @InjectMocks
    ImageService imageService;

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
    public void testgetImageByItemId_NullPointerException_UserNull() throws UserNotValidException {
        imageService.getImageByItemId(1, userNull);
    }

    @Test(expected = NullPointerException.class)
    public void testgetImageByItemId_NullPointerException_ImageDoesNotExists() throws UserNotValidException {
        doReturn(imageNull).when(imageRepositoryMock).findByItemId(1);
        imageService.getImageByItemId(1, userNotEmpty);
    }

    @Test(expected = UserNotValidException.class)
    public void testgetImageByItemId_UserNotValidExceptionThrown_ImageDoesNotBelongToUser() throws UserNotValidException {
        doReturn(imageNotEmpty).when(imageRepositoryMock).findByItemId(1);
        imageService.getImageByItemId(1, userNotEmpty);
    }

    @Test
    public void testgetImageByItemId_ReturnImage() throws UserNotValidException {
        doReturn(imageNotEmpty).when(imageRepositoryMock).findByItemId(1);
        assertEquals(imageService.getImageByItemId(1, userNotEmptyAdmin), imageNotEmpty);
    }

    @Test
    public void testGetAllImages_ReturnImageList() {
        doReturn(listImageEmpty).when(imageRepositoryMock).findAll();
        assertEquals(imageService.getAllImages(), listImageEmpty);
    }

    @Test(expected = NullPointerException.class)
    public void testUploadImage_NullPointerException_ImageNull() throws IOException, UserNotValidException {
        imageService.uploadImage(imageNull, userNotEmptyAdmin);
    }

    @Test(expected = NullPointerException.class)
    public void testUploadImage_NullPointerException_UserNull() throws IOException, UserNotValidException {
        imageService.uploadImage(imageNotEmpty, userNull);
    }

    @Test(expected = NullPointerException.class)
    public void testUploadImage_NullPointerException_ItemDoesNotExists() throws IOException, UserNotValidException {
        long id = imageNotEmpty.getItem().getId();
        doReturn(itemNull).when(itemRepositoryMock).findById(id);
        imageService.uploadImage(imageNotEmpty, userNotEmptyAdmin);
    }

    @Test(expected = UserNotValidException.class)
    public void testUploadImage_UserNotValidException_ItemDoesNotBelongToUser() throws IOException, UserNotValidException {
        long id = imageNotEmpty.getItem().getId();
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(id);
        imageService.uploadImage(imageNotEmpty, userNotEmpty);
    }

    @Test
    public void testUploadImage_ReturnImage_OverWriteImage() throws IOException, UserNotValidException {
        long itemid = imageNotEmpty.getItem().getId();
        long imageid = imageNotEmpty.getItem().getId();
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(itemid);
        doReturn(imageNotEmptyTwo).when(imageRepositoryMock).findByItemId(imageid);
        doReturn(imageNotEmptyTwo).when(imageRepositoryMock).save(imageNotEmptyTwo);
        assertEquals(imageService.uploadImage(imageNotEmpty, userNotEmptyAdmin), imageNotEmptyTwo);
    }

    @Test
    public void testUploadImage_ReturnImage() throws IOException, UserNotValidException {
        long itemid = imageNotEmpty.getItem().getId();
        long imageid = imageNotEmpty.getItem().getId();
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(itemid);
        doReturn(imageNull).when(imageRepositoryMock).findByItemId(imageid);
        doReturn(imageNotEmpty).when(imageRepositoryMock).save(Mockito.any(Image.class));
        assertEquals(imageService.uploadImage(imageNotEmpty, userNotEmptyAdmin), imageNotEmpty);
    }

}
