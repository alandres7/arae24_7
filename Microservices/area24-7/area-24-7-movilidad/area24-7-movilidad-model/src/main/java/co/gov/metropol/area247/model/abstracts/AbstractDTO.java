package co.gov.metropol.area247.model.abstracts;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractDTO {

    protected Long id;

    @Value(value = "true")
    protected boolean enabled = true;

    public abstract <T extends AbstractDTO> T withId(Long id);

    public abstract <T extends AbstractDTO> T withEnabled(boolean enabled);

    public Long getId() {
        return id;
    }

    public AbstractDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public AbstractDTO setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isNew() {
        return (this.id == null);
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
