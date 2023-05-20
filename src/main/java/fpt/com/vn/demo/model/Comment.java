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

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@Column(name = "id_comment")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "content")
	private String content;
	@Column(name = "history")
	private Date history;
	
	@ManyToOne
	@JoinColumn(name = "id_user")
	private Customer customer;
	@ManyToOne
	@JoinColumn(name = "id_item")
	private Item item;
}
