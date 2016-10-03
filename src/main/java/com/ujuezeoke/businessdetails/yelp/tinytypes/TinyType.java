package com.ujuezeoke.businessdetails.yelp.tinytypes;

/**
 * Created by Obianuju Ezeoke on 25/09/2016.
 */
public abstract class TinyType<T> {
    private final T value;

    protected TinyType(T value) {
        this.value = value;
    }

    public T getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TinyType<?> tinyType = (TinyType<?>) o;

        return value.equals(tinyType.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "value=" + value +
                '}';
    }
}
