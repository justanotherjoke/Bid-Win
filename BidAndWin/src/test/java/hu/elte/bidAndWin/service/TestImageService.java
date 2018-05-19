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
    public void testgetImageByItemId_NullPointerException() throws UserNotValidException {
        imageService.getImageByItemId(1, userNull);
    }

    @Test(expected = NullPointerException.class)
    public void testgetImageByItemId_NullPointerException2() throws UserNotValidException {
        doReturn(imageNull).when(imageRepositoryMock).findByItemId(1);
        imageService.getImageByItemId(1, userNotEmpty);
    }

    @Test(expected = UserNotValidException.class)
    public void testgetImageByItemId_UserNotValidExceptionThrown() throws UserNotValidException {
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
    public void testUploadImage_NullPointerException() throws IOException, UserNotValidException {
        imageService.uploadImage(imageNull, userNotEmptyAdmin);
    }

    @Test(expected = NullPointerException.class)
    public void testUploadImage_NullPointerException2() throws IOException, UserNotValidException {
        imageService.uploadImage(imageNotEmpty, userNull);
    }

    @Test(expected = NullPointerException.class)
    public void testUploadImage_NullPointerException3() throws IOException, UserNotValidException {
        long id = imageNotEmpty.getItem().getId();
        doReturn(itemNull).when(itemRepositoryMock).findById(id);
        imageService.uploadImage(imageNotEmpty, userNotEmptyAdmin);
    }
    
    @Test(expected = UserNotValidException.class)
    public void testUploadImage_UserNotValidException() throws IOException, UserNotValidException {
        long id = imageNotEmpty.getItem().getId();
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(id);
        imageService.uploadImage(imageNotEmpty, userNotEmpty);
    }

    @Test
    public void testUploadImage_ReturnImage() throws IOException, UserNotValidException {
        long itemid = imageNotEmpty.getItem().getId();
        long imageid = imageNotEmpty.getItem().getId();
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(itemid);
        doReturn(imageNotEmptyTwo).when(imageRepositoryMock).findByItemId(imageid);
        doReturn(imageNotEmptyTwo).when(imageRepositoryMock).save(imageNotEmptyTwo);
        assertEquals(imageService.uploadImage(imageNotEmpty, userNotEmptyAdmin), imageNotEmptyTwo);
    }
    @Test
    public void testUploadImage_ReturnImage2() throws IOException, UserNotValidException {
        long itemid = imageNotEmpty.getItem().getId();
        long imageid = imageNotEmpty.getItem().getId();
        doReturn(itemNotEmpty).when(itemRepositoryMock).findById(itemid);
        doReturn(imageNull).when(imageRepositoryMock).findByItemId(imageid);
        doReturn(imageNotEmpty).when(imageRepositoryMock).save(Mockito.any(Image.class));
        assertEquals(imageService.uploadImage(imageNotEmpty, userNotEmptyAdmin), imageNotEmpty);
    }

}
