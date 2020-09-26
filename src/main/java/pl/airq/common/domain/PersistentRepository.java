package pl.airq.common.domain;

import io.smallrye.mutiny.Uni;

public interface PersistentRepository<T> {

    Uni<Boolean> save(T data);

    default Uni<Boolean> upsert(T data) {
        return save(data);
    }

}
