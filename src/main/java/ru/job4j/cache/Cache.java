package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        if (memory.containsKey(model.getId())) {
            return false;
        }
        memory.put(model.getId(), model);
        return true;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (vol1, vol2) -> {
                    if (model.getVersion() != vol2.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    return new Base(model.getId(), vol1 + 1);
                }
        ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }

    public int getSize() {
        return memory.size();
    }

    public Base findId(int id) {
        return memory.get(id);
    }

}