package MultiThreadRace;

abstract public class Vehicle {

    double speed, pFail; //скорость в м/с, вероятность прокола колеса в диапазоне от 0.00 до 1.00
    String name; //имя ТС
    boolean finish; //флаг финиширования
    int failSec; //время простоя при проколе колеса

    public abstract void move(double lapLength) throws InterruptedException; //процесс движения

    public String getName() {return name;} //возвращает имя ТС

    public boolean isFinished() {
        return finish;
    }
}
