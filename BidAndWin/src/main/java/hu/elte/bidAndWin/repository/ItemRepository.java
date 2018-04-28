package hu.elte.bidAndWin.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.elte.bidAndWin.domain.Bid;
import hu.elte.bidAndWin.domain.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long>{

	Item findById(long id);

	List<Item> findAllByUserId(long id);
	
	Item findByImageId(long id);
}
