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

		if (currentTime.before(item.getEndTime())) {

			return true;
		}
		return false;
	}


	public Bid makeBid(@NonNull Bid bid, @NonNull User user) throws BidNotValidException, UserNotValidException {
			Bid bestBid = bidRepository.findFirstByItemIdOrderByBidOfferDesc(bid.getItem().getId());
			System.out.println(bestBid.getId());
			@NonNull
			Item item = itemRepository.findById(bid.getItem().getId());
			System.out.println(item.getId());
			if(item.getUser().getId() == user.getId()) { // saját tárgyra ne licitáljunk! 
				throw new UserNotValidException();
			}
			
			if (!validateItemTime(item)) {
				System.out.println("vége a licitnek");
				throw new BidNotValidException();
			}
			if(bid.getBidOffer() > bestBid.getBidOffer() ) {
				//item.setBestBidderId(user.getId());
				
				if(bestBid.getUser().getId() == user.getId()) { // ha a saját licitemet akarom megemelni... mondjuk, hogy a villámárat megadjam
					bestBid.setBidOffer(bid.getBidOffer());
					itemRepository.save(item);
					return bidRepository.save(bestBid);
				}
				bid.setItem(bestBid.getItem());
				bid.setUser(user);
				
				
				Bid newBid = bidRepository.save(bid);
				item.setBestBidId(newBid.getId());
				itemRepository.save(item);
				return newBid;
				
			}

			throw new BidNotValidException();
		
	}

}
