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
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor(onConstructor = @__(
	@Autowired))
public class ItemService {

	private ItemRepository itemRepository;
	private UserRepository userRepository;
	private BidRepository bidRepository;

	public List<Item> createItem(@NonNull Item item, @NonNull User user) throws ItemNotValidException, UserNotValidException {
		boolean valid = validateItem(item);
		if (valid) {
			Item it = new Item();
			it.setUser(user);
			it.setBestBidderId(-1);
			
			it.setBidIncrement(item.getBidIncrement());
			it.setBuyItPrice(item.getBuyItPrice());
			it.setDescription(item.getDescription());
			it.setEndTime(item.getEndTime());
			it.setName(item.getName());
			it.setStartPrice(item.getStartPrice());
			it.setCategory(item.getCategory());
		
			itemRepository.save(it);

			Bid bid = new Bid(it, -1, user); 
			bidRepository.save(bid);

			return getAllItems();
		} else {
			throw new ItemNotValidException();
		}
	}

	private boolean validateItem(Item item) {
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());

		return item.getStartPrice() <= item.getBuyItPrice() && currentTime.before(item.getEndTime());
	}

	public Item getItem(long id, @NonNull User loggedInUser) {
		return itemRepository.findById(id);
	}

	public List<Item> getAllItems() {
		return (List<Item>) itemRepository.findAll();
	}

	public List<Item> getMyItems(@NonNull User loggedInUser) {
		return itemRepository.findAllByUserId(loggedInUser.getId());
	}

	public Item updateItem(long id, @NonNull Item item, @NonNull User user) throws ItemNotValidException, UserNotValidException {

		@NonNull
		Item currentItem = itemRepository.findById(id);

		if (currentItem.getUser().getId() != user.getId()) {
			throw new UserNotValidException();
		}
		if (validateItem(item)) {
			item.setUser(user);
			return itemRepository.save(item);
		} else {
			throw new ItemNotValidException();
		}

	}
}
