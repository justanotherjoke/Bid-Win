package hu.elte.bidAndWin.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	@OneToMany(targetEntity = Bid.class, mappedBy = "item")
	@JsonIgnore
	private List<Bid> bids;

	@OneToMany(targetEntity = Image.class, mappedBy = "item")
	@JsonIgnore
	private List<Image> images;

	@JoinColumn
	@ManyToOne(targetEntity = Seller.class, optional = false)
	@JsonIgnoreProperties("items")
	private Seller seller;

	@JoinColumn
	@ManyToOne(targetEntity = Category.class, optional = false)
	@JsonIgnoreProperties("items")
	private Category category;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String description;

	@Column(nullable = false)
	private long startPrice;
	
	@Column(nullable = false)
	private long soldPrice;
	
	@Column(nullable = true)
	private long soldToId;
	
}
