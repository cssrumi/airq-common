package pl.airq.common.domain;

import io.smallrye.mutiny.Uni;

public interface PersistentRepository<T> {

    Uni<Result> save(T data);

    default Uni<Result> upsert(T data) {
        return save(data);
    }

    enum Result {
        SAVED, UPSERTED, FAILED;

        public boolean isSuccess() {
            return this == SAVED || this == UPSERTED;
        }
    }
}
