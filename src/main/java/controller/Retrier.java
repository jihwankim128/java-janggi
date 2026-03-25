package controller;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Retrier {
    private Retrier() {
    }

    public static <T> T retry(Supplier<T> task, Consumer<IllegalArgumentException> consumer) {
        while (true) {
            try {
                return task.get();
            } catch (IllegalArgumentException e) {
                consumer.accept(e);
            }
        }
    }
}
