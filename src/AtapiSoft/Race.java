package AtapiSoft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Race {
    static double lapLength = 0.00; // Длина круга в м
    static Collection<Vehicle> vehicles = new ArrayList<>();//Коллекция Транспортных Средств, считанных из конфиг-файла
    static Collection<Thread> threads = new LinkedList<>(); //Коллекция потоков для заезда каждого Транспортного Средства
    static Map<Long, String> resultTable = new TreeMap<>(); //Сортированная мапа с результатами заезда. Ключ - время прибытия на финиш каждого ТС. Значение - имя ТС
    static final Object lock = new Object();//замок для блока синхронизации

    public static void race(Collection<Vehicle> vehicles){

        //каждому ТС создаем собственный поток для заезда
        for (Vehicle vehicle : vehicles) {
            threads.add(new Thread(() -> {
                try {
                    vehicle.move(lapLength);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }

        //Старт всех участников
        threads.forEach(Thread::start);

        //Главный поток ждет завершения всех заездов
        threads.forEach(thread -> {//проверяем каждый гоночный поток из коллекции потоков
            try {
                thread.join();//если поток еще жив, ждем его завершения
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threads.forEach(Thread::interrupt);//прерываем все гоночные потоки
        threads.clear();//сбрасываем список гоночных потоков

        int place = 1;//место в таблице
        //Печатаем таблицу результатов
        for (String name : resultTable.values()) {
            System.out.println(name + " is on " + place + " place");
            place++;
        }
        resultTable.clear();//сбрасываем мапу с результатами заезда
    }

    public static void main(String[] args) throws IOException {

        ConfigReader configReader = new ConfigReader();
        configReader.readConfig("config.txt");
        configReader.makeByConfig();

        Collection<Vehicle> vehicles = Arrays.asList(
                new Car("Lada", 25.00, 0.34, 4),
                new Car("BMW", 30.00, 0.28, 2),
                new Truck("GAZ", 14.50, 0.10, 50),
                new Motorcycle("Yamaha", 35.15, 0.32, false)
        );

        //начинаем гонку
        race(vehicles);

        //threads.forEach(Thread::interrupt);//прерываем все гоночные потоки

        System.out.println("Do want one more lap? Type y");//запрос на повторные круги
        String str = new BufferedReader(new InputStreamReader(System.in)).readLine();//считываем введенную строку
        while (str.equals("y")){
            race(vehicles);//запуск гонки
            System.out.println("Do want one more lap? Type y");//запрос на повторные круги
            str = new BufferedReader(new InputStreamReader(System.in)).readLine();//считываем введенную строку
        }
    }
}
