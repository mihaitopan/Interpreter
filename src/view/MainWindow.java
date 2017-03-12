package view;

import java.util.*;
import java.awt.Toolkit;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import utils.*;
import model.*;
import controller.*;

public class MainWindow {
	private Controller ctrl;
	private ListView<String> prgs;
    private List<Statement> list;
	private Button execute;

	public MainWindow(Controller ctrl, List<Statement> list) {
		this.ctrl = ctrl;
		this.list = list;

        prgs = new ListView<>();
        prgs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		execute = new Button("Execute program");
		execute.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e){
				int index = prgs.getSelectionModel().getSelectedIndex();
				if (index <0){
					Toolkit.getDefaultToolkit().beep();
				} else {
                    initPrgState(list.get(index));
					prgs.getItems().remove(index);
					Stage prgView = new Stage();
					PrgStateView view=new PrgStateView(ctrl);
					prgView.setTitle("PrgState");
					prgView.setScene(new Scene(view.getView(), 700, 500));
					view.populateAll();
					prgView.show();
				}
			}
		});
	}

	public void initPrgState(Statement stmt) {
        PrgState initPrgState = new PrgState(stmt,
                                new ExeStack<>(),
                                new SymbolTable<>(),
                                new Heap<>(),
                                new FileTable<>(),
                                new Output<>());
        this.ctrl.addPrgState(initPrgState);
	}

	public HBox getView() {
		HBox view = new HBox();
		Label title = new Label("Runnable programs");
		populateList();
		view.getChildren().addAll(title, this.prgs, this.execute);
		return view;
	}

	public void populateList() {
		this.prgs.getItems().clear();
		int index = 1;
		for(Statement prg : this.list){
            this.prgs.getItems().add("Run example " + index + ": "+ prg.toString());
			index++;
		}
	}
}
