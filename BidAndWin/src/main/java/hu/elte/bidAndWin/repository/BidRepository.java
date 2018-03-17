package hu.elte.bidAndWin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.bidAndWin.domain.Bid;

@Repository
public interface BidRepository extends CrudRepository<Bid, Long>{

	List<Bid> findByBidderId(long id);

}
