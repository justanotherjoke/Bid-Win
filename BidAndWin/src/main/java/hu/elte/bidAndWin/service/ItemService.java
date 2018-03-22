package hu.elte.bidAndWin.service;

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
	
	
	public Item createItem(Item item, User user) {
		item.setUser(user);
        return itemRepository.save(item);
    }
	
	public Item getItem(long id, User loggedInUser) {
		return itemRepository.findById(id);
	}
}
