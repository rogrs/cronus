package br.com.rogrs.cronus.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.rogrs.framework.domain.EntityWithUUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends EntityWithUUID {

	private String name;
	private int workload;
	private short rate;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_course_teacher"))
	private Teacher teacher;
}