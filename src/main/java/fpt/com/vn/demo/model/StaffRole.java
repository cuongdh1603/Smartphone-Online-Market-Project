package fpt.com.vn.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;




@Entity
@Table(name = "staff_role")
public class StaffRole {
	@Id
	@Column(name = "id_staff_role")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idStaffRole;
	
	@ManyToOne
	@JoinColumn(name = "id_staff")
	private Staff staff;
//	
	@ManyToOne
	@JoinColumn(name = "id_role")
	private Roles role;
}
