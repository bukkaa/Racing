package AtapiSoft;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ConfigReader {
    private ArrayList<String> list = new ArrayList<>();

    public void readConfig(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ( (line = reader.readLine()) != null) list.add(line);
    }

    public void makeByConfig() {
        Race.lapLength = Double.parseDouble(list.get(0));
        list.remove(0);
        list.trimToSize();

        list.forEach(line -> {
            String[] words = line.split(" ");
            switch(words[0]) {
                case "Car" :
                    Race.vehicles.add( new Car(words[1], Double.parseDouble( words[2] ), Double.parseDouble( words[3] ), Integer.parseInt( words[4] ) ) ); break;
                case "Truck" :
                    Race.vehicles.add( new Truck(words[1], Double.parseDouble( words[2] ), Double.parseDouble( words[3] ), Double.parseDouble( words[4] ) ) ); break;
                case "Motorcycle" :
                    Race.vehicles.add( new Motorcycle(words[1], Double.parseDouble( words[2] ), Double.parseDouble( words[3] ), Boolean.parseBoolean( words[4] ) ) ); break;
                default:
                    System.out.println("Failed creating a vehicle"); break;
            }
                });
    }
}
