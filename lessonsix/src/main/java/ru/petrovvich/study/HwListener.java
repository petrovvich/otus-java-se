package ru.petrovvich.study;

public interface HwListener<K, V> {
    void notify(K key, V value, String action);
}
