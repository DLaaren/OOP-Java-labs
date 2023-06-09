package ru.nsu.fit.vinter.carFactory.core.factory.tasks;

import ru.nsu.fit.vinter.carFactory.core.factory.products.Car;
import ru.nsu.fit.vinter.carFactory.core.factory.CarFactory;
import ru.nsu.fit.vinter.carFactory.core.factory.Storage;
import ru.nsu.fit.vinter.carFactory.core.threadpool.Task;

import java.util.logging.Logger;

public class SellCar implements Task {
    Logger log = Logger.getLogger(CarFactory.class.getName());

    private int delay;
    private final int carPrice;
    private final CarFactory carFactory;
    private Storage<Car> carStorage;

    public SellCar(CarFactory carFactory, int carPrice, int delay) {
        this.delay = delay;
        this.carPrice = carPrice;
        this.carFactory = carFactory;
        this.carStorage = carFactory.getCarStorage();
    }


    @Override
    public void performTask() throws InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            Thread.sleep(delay);
            carStorage.takeItem();
            carFactory.carSold(carPrice);
            log.info("CAR HAS BEEN SOLD\n");
        }
    }
}
