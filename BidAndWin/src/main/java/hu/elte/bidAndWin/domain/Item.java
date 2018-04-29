package hu.elte.bidAndWin.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

//	@OneToMany(targetEntity = Bid.class, mappedBy = "item")
//	@JsonIgnore
//	private List<Bid> bids;
	@OneToMany(targetEntity = Image.class, mappedBy = "item")
	@JsonIgnore
	private List<Bid> bids;
	
//	@JsonBackReference
//	@OneToOne(mappedBy = "item")
//	private Bid bid;

	public Item(List<Image> images, User user, Category category, String name, String description, long startPrice,
		long buyItPrice, Timestamp endTime, long bidIncrement) {
		this.images = images;
		this.user = user;
		this.category = category;
		this.name = name;
		this.description = description;
		this.startPrice = startPrice;
		this.buyItPrice = buyItPrice;
		this.endTime = endTime;
		this.bidIncrement = bidIncrement;
	}

	@OneToMany(targetEntity = Image.class, mappedBy = "item")
	@JsonIgnore
	private List<Image> images;

	@JoinColumn
	@ManyToOne(targetEntity = User.class, optional = false)
	@JsonIgnoreProperties("items")
	private User user;

	@JoinColumn
	@ManyToOne(targetEntity = Category.class, optional = false)
	@JsonIgnoreProperties("items")
	private Category category;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name;

	private String description;

	@Column(nullable = false)
	private long startPrice;

	@Column(nullable = false)
	private long buyItPrice;

	@Column(nullable = false)
	private Timestamp endTime;

	@Column(nullable = false)
	private long bidIncrement;

//	@Column(nullable = true)
//	private long soldToId;
}
