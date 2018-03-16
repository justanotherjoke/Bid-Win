package hu.elte.bidAndWin.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bidder {
	
	@JoinColumn
    //@OneToOne(targetEntity = User.class, optional = false)
	@OneToOne(fetch = FetchType.LAZY)
    //@JsonIgnoreProperties("bidder")
    private User user;
	
	//@OneToMany(targetEntity = Bid.class)
	@OneToMany(targetEntity = Bid.class, mappedBy = "bidder")
    @JsonIgnore
    private List<Bid> bids;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	//@Column(nullable = false)
    private long credit;
	
}

