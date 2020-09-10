package pl.airq.common.domain;

import io.smallrye.mutiny.Uni;

public interface PersistentRepository<T> {

    Uni<Boolean> save(T data);

    Uni<Boolean> upsert(T data);

}
