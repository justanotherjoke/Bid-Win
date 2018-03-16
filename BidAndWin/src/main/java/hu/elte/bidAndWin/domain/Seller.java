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
public class Seller {
	
	@JoinColumn
    //@OneToOne(targetEntity = User.class, optional = false)
	@OneToOne(fetch = FetchType.LAZY)
    //@JsonIgnoreProperties("seller")
    private User user;
	
	//@OneToMany(targetEntity = Item.class)
	@OneToMany(targetEntity = Item.class, mappedBy = "seller")
    @JsonIgnore
    private List<Item> items;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	
	//@Column(nullable = false)
    private long credit;
	
}

