package Other2;

import java.util.concurrent.Semaphore;

public class Main {
    public static final int CARS_COUNT = 4;
    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++) {
            if (cars[i] != null){
                new SimpleSemaphore<>();
            }
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

class SimpleSemaphore<Car>{
    Car car;
    final Semaphore semaphore = new Semaphore(4);
    Runnable limit =
            new Runnable() {

                int count = 0;

                public void run() {
                    int time = 3 +(int) (Math.random()* 4.0);
                    int num = count++;
                    try {
                        semaphore.acquire();
                        System.out.println(car + "Начала гонку" + num);
                        Thread.sleep(500);
                        System.out.println(car + "Завершила гонку" +num);
                        semaphore.release();

                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }

            };
     // for (int i= 0; i<10; i++)
     //new Thread(limit).start();
    // забыл дописать счетчик, и время работы.
    // Но почему то 49 и 50 у меня не работают, не понял почему
}


abstract class Stage {
    protected int length;
    protected String description;
    public String getDescription() {
        return description;
    }
    public abstract void go(Car c);
}
class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Tunnel extends Stage {
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
