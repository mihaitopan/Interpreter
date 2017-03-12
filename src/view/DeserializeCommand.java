package view;

import controller.Controller;
import model.*;
import repository.IRepository;
import repository.Repository;
import utils.*;

public class DeserializeCommand extends Command {
    public DeserializeCommand(String key, String desc){
        super(key, desc);
    }

    public void execute() {
        Statement stmt = new CompStmt(
                new CompStmt(new PrintStmt(new ArithmeticExpr('+', new ConstExpr(3), new ConstExpr(5))),
                        new CompStmt(new AssignStmt(new ConstExpr(11), "b"),
                                new CompStmt(new IfStmt(new ConstExpr(0), new PrintStmt(new ConstExpr(1)),
                                        new PrintStmt(new ConstExpr(2))), new NewStmt(new ConstExpr(41), "heap_1")))),
                new WhileStmt(new BooleanExpr("<", new HeapExpr("heap_1"), new ConstExpr(44)), new CompStmt(new PrintStmt(new ConstExpr(100)),
                        new WriteHeapStmt(new ArithmeticExpr('+', new HeapExpr("heap_1"), new ConstExpr(1)), "heap_1"))));
        PrgState prg = new PrgState(stmt,
                new ExeStack<>(),
                new SymbolTable<>(),
                new Heap<>(),
                new FileTable<>(),
                new Output<>());
        Repository repo = new Repository("logFile.txt", prg);
        try {
            PrgState newprg = repo.deserialize("1stFile.txt");
            IRepository newrepo = new Repository("logFile.txt", newprg);
            Controller ctrl = new Controller(newrepo);
            ctrl.executeAll();
        } catch (InterpreterException e) {
            int a = 4;
        }
    }
}
