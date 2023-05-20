package fpt.com.vn.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand")
public class Brand {
	@Id
	@Column(name = "id_brand")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "namebrand")
	private String name;
	@Column(name = "logo")
	private String logo;
	
	public String getLogoImagePath(){
		if(logo == null) return null;
		return "/img/brand/"+id+"/"+logo;
	}
	@OneToMany(mappedBy = "brand")
	private List<Item> items;
}
