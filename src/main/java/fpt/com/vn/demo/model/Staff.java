package fpt.com.vn.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "staff")
@PrimaryKeyJoinColumn(name = "id_user")
public class Staff extends User{
	@Column(name = "name")
	private String name;
	
//	@ManyToOne
//	@JoinColumn(name = "id_role")
//	private Role role;
	@OneToMany(mappedBy = "staff")
	private List<StaffRole> staffRoleList;
}
