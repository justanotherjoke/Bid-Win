package hu.elte.bidAndWin.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import hu.elte.bidAndWin.domain.Image2;
 
 
public interface ImageRepository2 extends JpaRepository<Image2, Long>{	
	Image2 findByItemId(long id);
}