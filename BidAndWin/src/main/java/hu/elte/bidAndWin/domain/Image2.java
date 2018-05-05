package hu.elte.bidAndWin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image2 {

	public Image2(String imageStr, String path, Item item) {
		this.pic = imageStr;
		this.item = item;
	}

	@OneToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(columnDefinition="TEXT")
	private String pic;
}

//to do:noargsconstruktor hasznalva van imageserviceben-- nem kene
