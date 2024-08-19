package ui;


import java.io.FileNotFoundException;

public class Main {
    private static boolean choose = false;

    public static void main(String[] args) {
        if (choose) {
            try {
                new DungeonsAndDragonsApp();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to run application: file not found");
            }
        } else {
            try {
                new GUI();
            }  catch (FileNotFoundException e) {
                System.out.println("Unable to run application: file not found");
            }
        }

    }
}
