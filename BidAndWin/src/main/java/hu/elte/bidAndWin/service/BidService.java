package hu.elte.bidAndWin.service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
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
public class BidService {

	private BidRepository bidRepository;
	private UserRepository userRepository;
	private ItemRepository itemRepository;

	public List<Bid> getAllBids() {
		return (List<Bid>) bidRepository.findAll();
	}

	public List<Bid> getMyBids(@NonNull User user) {
		return bidRepository.findByUserId(user.getId());
	}

	public Bid getBid(long id, @NonNull User loggedInUser) throws UserNotValidException {

		@NonNull
		Bid bid = bidRepository.findById(id);

		if (loggedInUser.getId() != bid.getItem().getUser().getId()) {
			throw new UserNotValidException();
		}
		return bidRepository.findById(id);
		// mit dobjunk ha nincs ilyen id?
	}

	private boolean validateItemTime(Item item) {
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		System.out.println(item.getEndTime());
		System.out.println(currentTime);

		if (currentTime.before(item.getEndTime())) {

			return true;
		}
		return false;
	}

	public Bid makeBid(long itemId, @NonNull Bid bid, @NonNull User user) throws BidNotValidException {
		
		@NonNull
		Bid currentBid = bidRepository.findByItemId(itemId);
		
		@NonNull
		Item item = itemRepository.findById(currentBid.getItem().getId());
		
		if (!validateItemTime(item)) {
			throw new BidNotValidException();
		}
		if (currentBid.getBidOffer() != -1) {
			if (bid.getBidOffer() >= (currentBid.getBidOffer() + currentBid.getItem().getBidIncrement())) {
				currentBid.setUser(user);

				return bidRepository.save(currentBid);
			} else {
				throw new BidNotValidException();
			}
		} else {
			if (bid.getBidOffer() >= currentBid.getItem().getStartPrice() && bid.getUser().getId() != user.getId()) {
				currentBid.setUser(user);
				currentBid.setBidOffer(bid.getBidOffer());
				return bidRepository.save(currentBid);
			} else {
				throw new BidNotValidException();
			}
		}

	}

}
