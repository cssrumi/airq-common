package pl.airq.common.infrastructure.persistance;

import java.util.function.Supplier;

public class SqlLike {

    private SqlLike() {
    }

    public static String inAnyPosition(String param) {
        return "'%" + param + "%'";
    }

    public static String inAnyPosition(Supplier<String> supplier) {
        return "'%" + supplier.get() + "%'";
    }
}
