package co.gov.metropol.area247.repository.domain.abstracts;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;
//@GeneratedValue(strategy = GenerationType.AUTO)
@MappedSuperclass
public abstract class Entities extends Auditable<String> implements Persistable<Long> {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_MOVILIDAD_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	@Column(name = "ID")
	protected Long id;

	@Column(name = "B_ENABLED")
	protected boolean enabled = true;

	public abstract <T extends Entities> T withId(Long id);

	public abstract <T extends Entities> T withEnabled(boolean enabled);

	@Override
	public Long getId() {
		return id;
	}

	public Entities setId(Long id) {
		this.id = id;
		return this;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Entities setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	@JsonIgnore
	@Override
	public boolean isNew() {
		return (this.id == null);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
