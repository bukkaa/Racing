package AtapiSoft;

import static java.lang.Thread.sleep;

public class Truck extends Vehicle {
    double stuffWeight;

    public Truck(String name, double speed, double pFail, double stuffWeight){
        this.name = name;
        this.speed = speed;
        this.pFail = pFail;
        this.stuffWeight = stuffWeight;

        finish = false;
        failSec = 0;
        System.out.println("truck " + name + ": " + speed + " m/s; fail probability is " + pFail + "; with " + stuffWeight + " kg of stuff");
    }

    @Override
    public void move(double lapLength) throws InterruptedException {
        double lapDone = 0.00;

        System.out.println("Race for truck " + getName() + " begun!!!");

        while ( lapDone < lapLength ) {
            if (failSec == 0) {
                sleep(1000);
                lapDone += speed;
                if (lapDone <= lapLength) System.out.println("Truck " + getName() + " drove " + lapDone + " meters");
                else System.out.println("Truck " + getName() + " drove " + lapLength + " meters");
                if (Math.random() <= pFail) {
                    failSec = 3;
                    System.out.println("Bad luck for " + getName() + "! Disabled for " + failSec + " seconds");
                }
            }
            else {
                failSec--;
                sleep(1000);
            }
        }
        synchronized (Race.lock) {
            Race.resultTable.put(System.currentTimeMillis(), getName());//заносим финишировавшее ТС в таблицу резулдьтатов
        }

        System.out.println("Truck " + getName() + " finished!");
        finish = true;
    }
}
