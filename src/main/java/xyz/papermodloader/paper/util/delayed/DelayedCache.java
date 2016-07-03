package xyz.papermodloader.paper.util.delayed;

import java.util.function.Supplier;

public class DelayedCache<T> implements Delayed<T> {
    private T t;
    private Supplier<T> supplier;

    public DelayedCache(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (this.t == null) {
            this.t = this.supplier.get();
        }
        return this.t;
    }
}