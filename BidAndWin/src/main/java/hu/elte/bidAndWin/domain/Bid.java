package hu.elte.bidAndWin.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
//	@JoinColumn
//    @ManyToOne(targetEntity = Item.class, optional = false)
//    @JsonIgnoreProperties("bids")
//    private Item item;
	
    @OneToOne
	@JoinColumn(name="item_id")
	private Item item;
	
	@JoinColumn
    @ManyToOne(targetEntity = User.class, optional = false)
    @JsonIgnoreProperties("bids")
    private User user;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(nullable = false)
	private long bidOffer;
	
	public Bid(Item item, long bidOffer, User user) {
		this.item = item;
		this.bidOffer =bidOffer;
		this.user = user;
		
	}
	
}
