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

	@Test
	public void TestUser() {
		assertTrue(true);
	}

//	@Mock
//	BidRepository bidRepositoryMock;
//	@Mock
//	UserRepository userRepositoryMock;
//	@Mock
//	ItemRepository itemRepositoryMock;
//
//	@InjectMocks
//	BidService bidService;
//
//	List<Bid> listBidEmpty = new LinkedList<>();
//	List<Bid> listBidNotEmpty = new LinkedList<>();
//	List<Category> listCategoryEmpty = new LinkedList<>();
//	List<Category> listCategoryNotEmpty = new LinkedList<>();
//	List<Image> listImageEmpty = new LinkedList<>();
//	List<Item> listItemEmpty = new LinkedList<>();
//
//	Bid bidEmpty = spy(new Bid());
//	Category categoryEmpty = spy(new Category());
//	Item itemEmpty = spy(new Item());
//	Image imageEmpty = spy(new Image());
//	User userEmptySpy = spy(new User());
//
//	Timestamp timestampEmpty = new Timestamp(0);
//	byte[] byteEmpty = "".getBytes();
//
//	User userNotEmptySpy = spy(new User(listItemEmpty, listBidEmpty, 2, "david", "2222", "2@2", User.Role.USER));
//	User userAdminSpy = spy(new User(listItemEmpty, listBidEmpty, 1, "zoli", "1111", "1@1", User.Role.ADMIN));
//	Bid bidNotEmptyOriginalSpy;
//	Bid bidNotEmptySpy;
//	Category categoryNotEmpty = spy(new Category(listItemEmpty, 1, "auto"));
//	Category categoryNotEmptyTwo = spy(new Category(listItemEmpty, 2, "szamitogep"));
//	Item itemNotEmptySpy = spy(new Item(bidNotEmptySpy, listImageEmpty, userNotEmptySpy, categoryNotEmpty, 1, "trabant", "jokocsi", 0, 1000000, timestampEmpty, 100));
//	Image imageNotEmpty = spy(new Image(itemNotEmptySpy, 1, byteEmpty));
//
//	@Before
//	public void setUp() throws Exception {
//
//		MockitoAnnotations.initMocks(this);
//		listBidNotEmpty.add(bidEmpty);
//		bidNotEmptyOriginalSpy = spy(new Bid(itemNotEmptySpy, userEmptySpy, 1, 500));
//		bidNotEmptySpy = spy(new Bid(itemNotEmptySpy, userEmptySpy, 2, 1000));
//	}
//
//	@Test
//	public void testGetAllBids_ReturnEmptyBidList() {
//		doReturn(listBidEmpty).when(bidRepositoryMock).findAll();
//		assertEquals(bidService.getAllBids(), listBidEmpty);
//	}
//
//	@Test
//	public void testGetAllBids_ReturnBidList() {
//		doReturn(listBidNotEmpty).when(bidRepositoryMock).findAll();
//		assertEquals(bidService.getAllBids(), listBidNotEmpty);
//	}
//
//	@Test
//	public void testGetMyBids_ReturnEmptyBidList() throws UserNotValidException {
//		long userId = userNotEmptySpy.getId();
//		doReturn(listBidEmpty).when(bidRepositoryMock).findByUserId(userId);
//		assertEquals(bidService.getMyBids(userNotEmptySpy), listBidEmpty);
//	}
//
//	@Test
//	public void testGetMyBids_ReturnBidList() throws UserNotValidException {
//		long userId = userNotEmptySpy.getId();
//		doReturn(listBidNotEmpty).when(bidRepositoryMock).findByUserId(userId);
//		assertEquals(bidService.getMyBids(userNotEmptySpy), listBidNotEmpty);
//	}
//
//	@Test
//	public void testGetBid_ReturnBid() throws UserNotValidException {
//		doReturn(bidNotEmptySpy).when(bidRepositoryMock).findById(1);
//		assertEquals(bidService.getBid(1, userNotEmptySpy), bidNotEmptySpy);
//	}
	//to do: test bidservice.makebid
	//to do: test nulls
	//to do: cleanup unnecesary shit
	//to do: testgetbid,testgetmybif redo.
	//to do: getbid?
}
