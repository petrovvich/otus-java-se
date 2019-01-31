package ru.petrovvich.study;

import java.util.Objects;
import java.util.StringJoiner;

public class CacheElement<K, V> {

    private final K key;
    private final V value;

    public CacheElement(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CacheElement.class.getSimpleName() + "[", "]")
                .add("key=" + key)
                .add("value=" + value)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheElement<?, ?> that = (CacheElement<?, ?>) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}
