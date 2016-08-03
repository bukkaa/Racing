package AtapiSoft;

import static java.lang.Thread.sleep;

public class Motorcycle extends Vehicle {

    boolean carriage;

    public Motorcycle(String name, double speed, double pFail, boolean carriage){
        this.name = name;
        this.speed = speed;
        this.pFail = pFail;
        this.carriage = carriage;

        finish = false;
        failSec = 0;
        System.out.println("motorcycle " + name + ": " + speed + " m/s; fail probability is " + pFail + "; carriage is " + carriage);
    }

    @Override
    public void move(double lapLength) throws InterruptedException {
        double lapDone = 0.00;

        System.out.println("Race for motorcycle " + getName() + " begun!!!");

        while ( lapDone < lapLength ) {
            if (failSec == 0) {
                sleep(1000);
                lapDone += speed;
                if (lapDone <= lapLength) System.out.println("Motorcycle " + getName() + " drove " + lapDone + " meters");
                else System.out.println("Motorcycle " + getName() + " drove " + lapLength + " meters");
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

        System.out.println("Motorcycle " + getName() + " finished!");
        finish = true;
    }
}
