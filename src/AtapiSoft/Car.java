package AtapiSoft;

import static java.lang.Thread.sleep;

public class Car extends Vehicle {

    int persons = 0; //количчество человек на борту

    public Car(String name, double speed, double pFail, int persons){
        this.name = name;
        this.speed = speed;
        this.pFail = pFail;
        this.persons = persons;

        finish = false;
        failSec = 0;
        System.out.println("car " + name + ": " + speed + " m/s; fail probability is " + pFail + "; with " + persons + " persons on board");
    }

    @Override
    public void move(double lapLength) throws InterruptedException {
        double lapDone = 0.00; //сколько м проехало ТС. Инициализируем нулем для нескольких заездов

        System.out.println("Race for car " + getName() + " begun!!!");

        while ( lapDone < lapLength ) { //пока ТС не проехало весь круг
            if (failSec == 0) {//если колесо не пробито
                sleep(1000);//ждем 1 секунду перед ходом
                lapDone += speed; //инкремент расстояния, которое проехало ТС
                if (lapDone <= lapLength) System.out.println("Car " + getName() + " drove " + lapDone + " meters"); //вывод пройденного расстояния
                else System.out.println("Car " + getName() + " drove " + lapLength + " meters"); //если пройденное расстояние превысит длину круга, напечатается длина круга
                if (Math.random() <= pFail) {// испытываем удачу на прокол колеса
                    failSec = 3;//время простоя
                    System.out.println("Bad luck for " + getName() + "! Disabled for " + failSec + " seconds");
                }
            }
            else {
                failSec--;//если колеса пробито, уменьшаем время простоя на секунду
                sleep(1000);//ждем секунду до следующего хода
            }
        }
        //входим в блок синхронизации для записи ТС в таблицу результатов
        synchronized (Race.lock) {
            Race.resultTable.put(System.currentTimeMillis(), getName());//заносим финишировавшее ТС в таблицу резулдьтатов
        }

        System.out.println("Car " + getName() + " finished!");
        finish = true; //устанавливаем флаг финиширования
    }
}
