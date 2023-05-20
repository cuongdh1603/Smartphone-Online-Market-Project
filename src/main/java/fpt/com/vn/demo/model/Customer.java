package fpt.com.vn.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "id_user")
public class Customer extends User{
	@Column(name = "name")
	@NotBlank(message = "Yêu cầu nhập tên")
	private String name;

	@Column(name = "address")
	@NotBlank(message = "Yêu cầu nhập địa chỉ")
	private String address;

	@Column(name = "email")
	@NotBlank(message = "Yêu cầu nhập địa chỉ")
	private String email;

	@Length(min = 9,message = "Số điện thoại không hợp lệ")
	@Column(name = "phone")
	private String phone;

	@OneToMany(mappedBy = "customer")
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "customer")
	private List<CartItem> cartItem;
}
