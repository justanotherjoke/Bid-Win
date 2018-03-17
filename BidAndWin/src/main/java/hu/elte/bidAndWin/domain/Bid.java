package hu.elte.bidAndWin.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
	@JoinColumn
    @ManyToOne(targetEntity = Item.class, optional = false)
    @JsonIgnoreProperties("bids")
    private Item item;
	
	@JoinColumn
    @ManyToOne(targetEntity = Bidder.class, optional = false)
    @JsonIgnoreProperties("bids")
    private Bidder bidder;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	
}
