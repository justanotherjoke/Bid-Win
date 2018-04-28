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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestBidService {

    @Mock
    BidRepository bidRepositoryMock;
    @Mock
    UserRepository userRepositoryMock;
    @Mock
    ItemRepository itemRepositoryMock;

    @InjectMocks
    BidService bidService;

    List<Bid> listBidEmpty = new LinkedList<>();
    List<Bid> listBidNotEmpty = new LinkedList<>();
    List<Item> listItemEmpty = new LinkedList<>();
    List<Image> listImageEmpty = new LinkedList<>();
    Item itemEmpty = new Item();
    Bid bidEmpty = new Bid();
    Category categoryEmpty = new Category();
    Timestamp timestampEmpty = new Timestamp(0);
    User userEmptySpy = spy(new User());
    User userNotEmptySpy = spy(new User(listItemEmpty, listBidEmpty, 2, "david", "2222", "2@2", User.Role.USER));
    Bid bidNotEmptySpy = spy(new Bid(itemEmpty, userEmptySpy, 1, 1000));
    Bid bidNotEmptyOriginalSpy = spy(new Bid(itemEmpty, userEmptySpy, 1, 500));
    Item itemNotEmptySpy = spy(new Item(bidEmpty, listImageEmpty, userEmptySpy, categoryEmpty, 1, "trabant", "jokocsi", 0, 1000000, timestampEmpty, 100));

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        listBidNotEmpty.add(bidEmpty);

    }

    @Test
    public void testGetAllBids_ReturnEmptyBidList() {
        doReturn(listBidEmpty).when(bidRepositoryMock).findAll();
        assertEquals(bidService.getAllBids(), listBidEmpty);
    }

    @Test
    public void testGetAllBids_ReturnBidList() {
        doReturn(listBidNotEmpty).when(bidRepositoryMock).findAll();
        assertEquals(bidService.getAllBids(), listBidNotEmpty);
    }

    @Test
    public void testGetMyBids_ReturnEmptyBidList() {
        long userId = userNotEmptySpy.getId();
        doReturn(listBidEmpty).when(bidRepositoryMock).findByUserId(userId);
        assertEquals(bidService.getMyBids(userNotEmptySpy), listBidEmpty);
    }

    @Test
    public void testGetMyBids_ReturnBidList() {
        long userId = userNotEmptySpy.getId();
        doReturn(listBidNotEmpty).when(bidRepositoryMock).findByUserId(userId);
        assertEquals(bidService.getMyBids(userNotEmptySpy), listBidNotEmpty);
    }

    @Test
    public void testGetBid_ReturnEmptyBid() {
        doReturn(bidEmpty).when(bidRepositoryMock).findById(1);
        assertEquals(bidService.getBid(1, userNotEmptySpy), bidEmpty);
    }

    @Test
    public void testGetBid_ReturnBid() {
        doReturn(bidNotEmptySpy).when(bidRepositoryMock).findById(1);
        assertEquals(bidService.getBid(1, userNotEmptySpy), bidNotEmptySpy);
    }
    //to do: test bidservice.makebid
    //to do: test nulls
    //to do: cleanup unnecesary shit
}
