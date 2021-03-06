package encapsulation.exercises.classBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        double length = Double.parseDouble(reader.readLine());
        double width = Double.parseDouble(reader.readLine());
        double height = Double.parseDouble(reader.readLine());

        try {
            Box box = new Box(length, width, height);
            System.out.printf("%.2f%n",box.calculateSurfaceArea());
            System.out.printf("%.2f%n",box.calculateLateralSurfaceArea());
            System.out.printf("%.2f%n",box.calculateVolume());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

    }
}
