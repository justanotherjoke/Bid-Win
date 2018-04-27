package hu.elte.bidAndWin.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.BidRepository;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;

@Service
public class ItemService {

	private ItemRepository itemRepository;
	private UserRepository userRepository;
	private BidRepository bidRepository;

	@Autowired
	public ItemService(ItemRepository itemRepository, UserRepository userRepository, BidRepository bidRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
		this.bidRepository = bidRepository;
	}

	public Item createItem(Item item, User user) throws ItemNotValidException {
		boolean valid = validateItem(item);
		if (valid) {
			item.setUser(user);
			return itemRepository.save(item);
		} else {
			throw new ItemNotValidException();
		}

	}

	private boolean validateItem(Item item) {
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());

		if (item.getStartPrice() <= item.getBuyItPrice() && currentTime.before(item.getEndTime())) {
			return true;
		}
		return false;
	}

	public Item getItem(long id, User loggedInUser){
		return itemRepository.findById(id);
	}

	public List<Item> getAllItems(){
		return (List<Item>) itemRepository.findAll();
	}
	
	public List<Item> getMyItems(User loggedInUser){
		return itemRepository.findAllByUserId(loggedInUser.getId());
	}
	
	public Item updateItem(long id, Item item, User user) throws ItemNotValidException, UserNotValidException {
		Bid currentBid = bidRepository.findByItemId(id);
		Item currentItem = itemRepository.findById(id);
		if(currentItem == null) {
			return null;
		}
		if(currentItem.getUser().getId() != user.getId()) {
			throw new UserNotValidException();
		}
		if (currentBid == null && currentItem !=null) { // ha van ilyen item, de nincs még rá licit
			boolean valid = validateItem(item);
			if (valid) {
				item.setUser(user);
				return itemRepository.save(item);
			} else {
				throw new ItemNotValidException();
			}	
		} else {
			throw new ItemNotValidException();
		}
	}
			

}

