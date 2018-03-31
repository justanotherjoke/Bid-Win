package hu.elte.bidAndWin.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Item;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.ItemRepository;
import hu.elte.bidAndWin.repository.UserRepository;

@Service
public class ItemService {

	private ItemRepository itemRepository;
	private UserRepository userRepository;
	
	@Autowired
	public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
	}
	
	
	public Item createItem(Item item, User user) throws ItemNotValidException {
		boolean valid= validateItem(item);
		if(valid) {
			item.setUser(user);
			return itemRepository.save(item);
		} else {
			throw new ItemNotValidException();
		}
        
    }
	
	private boolean validateItem(Item item) {
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());

		if(item.getStartPrice() <= item.getBuyItPrice() && currentTime.before(item.getEndTime()) ) {
			return true;
		}
		return false;
	}


	public Item getItem(long id, User loggedInUser) {
		return itemRepository.findById(id);
	}
}
