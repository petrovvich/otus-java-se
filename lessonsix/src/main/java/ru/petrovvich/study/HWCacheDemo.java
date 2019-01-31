package ru.petrovvich.study;

public class HWCacheDemo {

    public static void main(String[] args) {
        new HWCacheDemo().demo();
    }

    private void demo() {
        HwCache<Integer, Integer> cache = new HwCacheImpl<>();
        HwListener<Integer, Integer> listener = (key, value, action) -> System.out.println("Listen action: " + action + ", with key " + key + " and value " + value);
        cache.addListener(listener);
        cache.put(1, 1);
        System.out.println(cache.get(1));
        cache.remove(1);
        cache.removeListener(listener);

    }
}
