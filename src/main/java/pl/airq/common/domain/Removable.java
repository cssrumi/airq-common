package pl.airq.common.domain;

import io.smallrye.mutiny.Uni;
import pl.airq.common.store.key.Key;

public interface Removable<T, K extends Key> {

    Uni<Boolean> remove(T data);

    Uni<Boolean> removeByKey(K key);

}
