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
    }

    private boolean validateItemTime(Item item) {
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        return currentTime.before(item.getEndTime());
    }

    public Bid makeBid(@NonNull Bid bid, @NonNull User user) throws BidNotValidException, UserNotValidException {
        @NonNull
        Item item = itemRepository.findById(bid.getItem().getId());

        Bid bestBid = bidRepository.findFirstByItemIdOrderByBidOfferDesc(bid.getItem().getId());

        if (item.getUser().getId() == user.getId() && bestBid.getUser().getId() != user.getId()) {
            throw new UserNotValidException();
        }

        if (!validateItemTime(item)) {
            throw new BidNotValidException();
        }

        if (bid.getBidOffer() > bestBid.getBidOffer()) {
            if (bestBid.getUser().getId() == user.getId()) {
                bestBid.setBidOffer(bid.getBidOffer());
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
