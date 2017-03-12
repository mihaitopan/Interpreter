package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> map;

    public  TextMenu(){
        this.map = new HashMap<>();
    }

    public void addCommand(Command c){
        this.map.put(c.getKey(),c);
    }

    private void printMenu() {
    	System.out.println("Menu");
        for(Command c: this.map.values()){
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            printMenu();
            System.out.printf("Option: ");
            String key = scanner.nextLine();
            Command c = this.map.get(key);
            if (c == null) {
                System.out.println("Invalid option");
                continue;
            }
            c.execute();
        }
    }
}
