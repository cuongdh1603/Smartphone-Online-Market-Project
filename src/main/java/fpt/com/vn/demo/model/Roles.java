package fpt.com.vn.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {
	@Id
	@Column(name = "id_role")
	private Integer id;
	@Column(name = "code_role")
	private String code;
	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "role")
	private List<StaffRole> staffRoleList;
}
