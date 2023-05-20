package fpt.com.vn.demo.model;

import java.text.NumberFormat;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {
	@Id
	@Column(name = "id_item")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private Double price;

	@Column(name = "des_screen")
	private String descScreen;

	@Column(name = "size_screen")
	private Double sizeScreen;

	@Column(name = "battery")
	private Integer battery;

	@Column(name = "size_ram")
	private Integer sizeRam;

	@Column(name = "xresol")
	private Integer xResolution;

	@Column(name = "yresol")
	private Integer yResolution;

	@Column(name = "photo")
	private String photo;

	@Column(name = "note")
	private String note;

	@OneToMany(mappedBy = "item")
	private List<Comment> comments;
	@OneToMany(mappedBy = "item")
	private List<CartItem> cartItem;
	@OneToMany(mappedBy = "item")
	private List<ColorItem> colorItems;
	
	@ManyToOne
	@JoinColumn(name = "id_brand")
	private Brand brand;

	public String getPhotoPath(){
		if(photo == null) return null;
		return "/img/item/"+id+"/"+photo;
	}
	public String getFormatPrice(){
		NumberFormat myFormat = NumberFormat.getInstance();
		myFormat.setGroupingUsed(true);
		return myFormat.format(getPrice());
	}
}
/*
id_item int AI PK 
name varchar(45) 
id_brand int 
des_screen varchar(45) 
size_screen double 
size_ram int 
battery int 
xresol int 
yresol int 
note varchar(45)
 */
