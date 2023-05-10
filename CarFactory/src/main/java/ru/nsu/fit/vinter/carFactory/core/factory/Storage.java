package ru.nsu.fit.vinter.carFactory.core.factory;

import ru.nsu.fit.vinter.carFactory.core.factory.products.Product;

import java.util.ArrayDeque;
import java.util.Deque;

public class Storage<T extends Product> {
    private final String storageName;
    private final int storageCapacity;
    private final Deque<T> items;
    private final Object monitor = new Object();

    public Storage(String storageName, int storageCapacity) {
        this.storageName = storageName;
        this.storageCapacity = storageCapacity;
        this.items = new ArrayDeque<>();
    }

    public String getStorageName() {
        return storageName;
    }

    public int getStorageCapacity() {
        return storageCapacity;
    }
    public int getItemCount() {
        return items.size();
    }

    public T takeItem() throws InterruptedException {
        synchronized (monitor) {
            while (true) {
                try {
                    if (!items.isEmpty()) {
                        T item = items.getLast();
                        items.removeLast();
                        monitor.notify();
                        return item;
                    } else {
                        monitor.wait();
                    }
                } catch (InterruptedException e) {
                    throw e;
                }
            }
        }
    }

    public void put(T newItem) throws InterruptedException {
        synchronized (monitor) {
            if (items.size() >= storageCapacity) {
                monitor.wait();
            }
            items.add(newItem);
            monitor.notify();
        }
    }
}
