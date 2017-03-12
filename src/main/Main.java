package main;
import model.*;
import repository.*;
import controller.*;
import view.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

	public static List<Statement> getStatements(){
        ArrayList<Statement> list = new ArrayList<>();

        // Example 1
        Statement stmt1 = new CompStmt(
                            new OpenFileStmt("1stFile.txt", "1stFile"),
                            new CompStmt(new PrintStmt(new ArithmeticExpr('-', new ConstExpr(10), new ConstExpr(5))),
                                    new CompStmt(new AssignStmt(new ConstExpr(11), "b"),
                                    new CompStmt(new SleepStmt(5),
                                    new CompStmt(new IfStmt(new ConstExpr(0),
                                            new PrintStmt(new ConstExpr(1)),
                                            new PrintStmt(new ConstExpr(2))),
                                            new AssignStmt(new ConstExpr(10), "x"))))));
        list.add(stmt1);

        // Example 2
        Statement stmt2 = new AssignStmt(new ArithmeticExpr('/', new ConstExpr(3), new ConstExpr(0)), "var");
        list.add(stmt2);

        // Example 3
        Statement stmt3 = new CompStmt(
                            new OpenFileStmt("2ndFile.txt", "2ndFile"),
                            new CompStmt(new ReadFileStmt("2ndFile.txt", "h1"),
                                    new CompStmt(new ReadFileStmt("2ndFile.txt", "h2"), new CloseFileStmt("2ndFile.txt"))));
        list.add(stmt3);

        // Example 4
        Statement stmt4 = new CompStmt(
                new CompStmt( new CompStmt(new NewStmt(new ConstExpr(81), "heap_1"), new CompStmt(
                        new NewStmt(new ConstExpr(82), "heap_2"),
                        new CompStmt(new PrintStmt(new HeapExpr("heap_1")), new WriteHeapStmt(new ConstExpr(181), "heap_1")))),
                    new PrintStmt(new ArithmeticExpr('-', new ConstExpr(10), new ConstExpr(5)))),
                new CompStmt(new PrintStmt(new ArithmeticExpr('+', new ConstExpr(3), new ConstExpr(5))),
                    new CompStmt(new AssignStmt(new ConstExpr(11), "b"),
                        new CompStmt(new IfStmt(new ConstExpr(0), new PrintStmt(new ConstExpr(1)),
                            new PrintStmt(new ConstExpr(2))), new AssignStmt(new ConstExpr(0), "heap_1")))));
        list.add(stmt4);

        // Example 5
        Statement stmt5 = new CompStmt(
                new CompStmt(new PrintStmt(new ArithmeticExpr('+', new ConstExpr(3), new ConstExpr(5))),
                        new CompStmt(new AssignStmt(new ConstExpr(11), "b"),
                                new CompStmt(new IfStmt(new ConstExpr(0), new PrintStmt(new ConstExpr(1)),
                                        new PrintStmt(new ConstExpr(2))), new NewStmt(new ConstExpr(41), "heap_1")))),
                new WhileStmt(new BooleanExpr("<", new HeapExpr("heap_1"), new ConstExpr(44)), new CompStmt(new PrintStmt(new ConstExpr(100)),
                        new WriteHeapStmt(new ArithmeticExpr('+', new HeapExpr("heap_1"), new ConstExpr(1)), "heap_1"))));
        list.add(stmt5);

        // Example 6
        Statement stmt6 = new CompStmt(
            new CompStmt(new AssignStmt(new ConstExpr(10), "v"), new NewStmt(new ConstExpr(22), "a")),
            new CompStmt(
                    new ForkStmt(new ForkStmt(new AssignStmt(new ConstExpr(42), "v"))),
                    new CompStmt(new ForkStmt(new CompStmt( new CompStmt(new WriteHeapStmt(new ConstExpr(30), "a"),
                                                                            new AssignStmt(new ConstExpr(32), "v")),
                new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new HeapExpr("a"))))),
                new CompStmt(new PrintStmt(new VarExpr("v")), new PrintStmt(new HeapExpr("a"))))));
        list.add(stmt6);

        return list;
	}

    public void start(Stage primaryStage){
        IRepository repo = new Repository("logFile.txt");
        Controller ctrl = new Controller(repo);
        primaryStage.setTitle("Programs");
        primaryStage.setScene(new Scene(new MainWindow(ctrl, getStatements()).getView(), 700, 500));
        primaryStage.show();
    }
}
