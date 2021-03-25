package pl.airq.common.infrastructure.store.key;

import com.google.common.base.Preconditions;
import java.util.Objects;
import pl.airq.common.domain.vo.StationId;

public class SKey implements Key {

    private final StationId id;

    public SKey(StationId id) {
        Preconditions.checkNotNull(id);
        this.id = id;
    }

    @Override
    public String value() {
        return id.value();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SKey sKey = (SKey) o;
        return Objects.equals(id, sKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "SKey{" +
                "id=" + id +
                '}';
    }
}
