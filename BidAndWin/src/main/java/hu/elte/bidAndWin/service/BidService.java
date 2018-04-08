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

@Service
public class BidService {

	private BidRepository bidRepository;
	private UserRepository userRepository;

	@Autowired
	public BidService(BidRepository bidRepository, UserRepository userRepository) {
		this.bidRepository = bidRepository;
		this.userRepository = userRepository;
	}

	public List<Bid> getAllBids() {
            return (List<Bid>)bidRepository.findAll();
    }

	public List<Bid> getMyBids(User user) {
        return bidRepository.findByUserId(user.getId());
    }

	public Bid getBid(long id, User loggedInUser) {
		return bidRepository.findById(id);
		// mit dobjunk ha nincs ilyen id?
	}
	
	public Bid makeBid(Bid bid, User user) throws BidNotValidException {
		Bid currentBid = bidRepository.findById(bid.getItem().getId());
		if(currentBid != null) {
			if(bid.getBidOffer() >= (currentBid.getBidOffer() + currentBid.getItem().getBidIncrement()) ) {
				currentBid.setUser(user);
				
				return bidRepository.save(currentBid);
			} else {
				throw new BidNotValidException();
			}
		} else {
			if(bid.getBidOffer() >= bid.getItem().getStartPrice()) {
				currentBid.setUser(user);
				return bidRepository.save(currentBid);
			} else {
				throw new BidNotValidException();
			}
		}
	}

	/*
	public Bid updateBid(long id, Bid bid, User user) throws UserNotValidException {
        Bid currentBid = bidRepository.findById(id);
        //
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());
//        System.out.println("currentTime: " +  currentTime);
//        System.out.println("ez: " + currentBid.getItem().getEndTime());
//        Timestamp s = currentBid.getItem().getEndTime();
//        System.out.println(currentTime.after(s));

        if (currentBid!= null && currentTime.before(currentBid.getItem().getEndTime())) {
            currentBid.setUser(user);
            return bidRepository.save(currentBid);
        } else {
            throw new UserNotValidException();
        }
    }*/
	
	
}
