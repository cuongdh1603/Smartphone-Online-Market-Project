package fpt.com.vn.demo.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "username")
	@NotBlank(message = "Không được để trống")
	@Size(min=5,max=15,message = "Độ dài tên đăng nhập tối thiếu từ 5 kí tự trở lên")
	private String username;
	@Column(name = "password")
	@NotBlank(message = "Không được để trống")
	@Size(min=5,max=15,message = "Độ dài tên đăng nhập tối thiếu từ 5 kí tự trở lên")
	private String password;
	
//	@ManyToOne
//	@JoinColumn(name = "id_role")
//	private Role role;
	

}
