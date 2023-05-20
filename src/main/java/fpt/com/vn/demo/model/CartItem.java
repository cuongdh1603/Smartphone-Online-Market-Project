package fpt.com.vn.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cartitem")
public class CartItem {

	@Id
	@Column(name = "id_cartitem")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "code")
	private String code;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "address")
	private String address;

	@Column(name = "create_time")
	// @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.ss")
	private Date createTime;

	@Column(name = "pay_time")
	private Date payTime;

	@Column(name = "status")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "id_item")
	private Item item;

	
	
}
/*
Table: cartitem
Columns:
id_cartitem int AI PK 
code varchar(10) 
quantity int 
address varchar(100) 
create_time datetime 
pay_time datetime 
status tinyint 
id_user int 
id_item int
 */