package hu.elte.bidAndWin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class Image {

	public Image(byte[] pic, String path, Item item) {
		this.pic = pic;
		this.item = item;
	}

//	@JoinColumn
//    @ManyToOne(targetEntity = Item.class, optional = false)
//    @JsonIgnoreProperties("images")
//    private Item item;
	@OneToOne
	@JoinColumn(name = "item_id")
	private Item item;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Lob
	@Column(name = "pic")
	private byte[] pic;
}
