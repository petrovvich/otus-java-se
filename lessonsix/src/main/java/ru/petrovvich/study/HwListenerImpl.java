package ru.petrovvich.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HwListenerImpl<K, V> implements HwListener<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HwListenerImpl.class);
    @Override
    public void notify(K key, V value, String action) {
        LOGGER.info("Try to listen an action: {}, with key {} and value {}", action, key, value);
    }
}
