package hu.elte.bidAndWin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.User;
import hu.elte.bidAndWin.repository.BidRepository;
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
		// egyelore rossz (nem egyezik a user.id a bidder.id-val... csak teszt miatt probalom)
        return bidRepository.findByBidderId(user.getId());
    }

}
