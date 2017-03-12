package view;

import controller.Controller;
import model.Expression;
import utils.InterpreterException;

public class RunExample extends Command {
    private Controller ctrl;

    public RunExample(String key, String desc, Controller ctrl){
        super(key, desc);
        this.ctrl = ctrl;
    }

    @Override
    public void execute() {
        try {
            this.ctrl.executeAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
