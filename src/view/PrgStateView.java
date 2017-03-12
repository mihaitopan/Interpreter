package view;

import java.util.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control. TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;

import utils.*;
import model.*;
import controller.*;

public class PrgStateView {
	private BorderPane pane;
	private ListView<Integer> pids;
	private TextField nrPrgState;
    private static int selectedID = -1;

    private Controller ctrl;
    private ListView<String> exeStack;
    private ListView<String> output;
	private TableView<Map.Entry<Integer, Integer>> heap;
	private TableView<Map.Entry<Integer, FileData>> fileTable;
	private TableView<Map.Entry<String,Integer>> symbolTable;

	public PrgStateView(Controller ctrl) {
		this.ctrl = ctrl;
		initView();
		populatePrgList();
	}

	private void initView() {
        this.pane = new BorderPane();
        this.pane.setCenter(createList());
        FlowPane fp = new FlowPane();
        this.nrPrgState = new TextField();
        fp.getChildren().addAll(new Label("Number of PrgStates:"), nrPrgState);
        this.pane.setTop(fp);
        GridPane grid = new GridPane();

        initHeap();
        initFileTable();
        initSymbolTable();

        Button butt = new Button("Execute One Step");
        butt.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                populatePrgList();
                populateExeStack();
                populateHeap();
                populateSymbolTable();
                populateFileTable();
                populateOutput();
                updateNrPrgStates();
                try {
                    ctrl.executeAll();
                } catch (InterpreterException ex) {
                    ex.printStackTrace();
                }
            }
        });

        grid.add(this.heap, 0, 0);
        grid.add(this.symbolTable, 1, 0);
        grid.add(this.fileTable, 0, 1);
        this.pane.setLeft(grid);
        this.pane.setBottom(butt);
        populatePrgList();
    }

	private void initSymbolTable() {
		this.symbolTable = new TableView<Map.Entry<String, Integer>>();
		
		TableColumn<Map.Entry<String, Integer>, String> keys = new TableColumn<>("Name");
        keys.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Map.Entry<String, Integer>, String> param) {
				return new SimpleStringProperty(param.getValue().getKey());
			}
		});

		TableColumn<Map.Entry<String, Integer>, Integer> vals = new TableColumn<>("Value");
        vals.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, Integer>, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Map.Entry<String, Integer>, Integer> param) {
				return new SimpleIntegerProperty(param.getValue().getValue()).asObject();
			}
		});

		this.symbolTable.getColumns().addAll(keys, vals);
	}

	private void initFileTable() {
		this.fileTable = new TableView<Map.Entry<Integer, FileData>>();

		TableColumn<Map.Entry<Integer, FileData>, Integer> keys = new TableColumn<>("Identifier");
        keys.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, FileData>, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Map.Entry<Integer, FileData>, Integer> param) {
				return new SimpleIntegerProperty(param.getValue().getKey()).asObject();
			}
		});

		TableColumn<Map.Entry<Integer, FileData>, String> vals = new TableColumn<>("File Name");
        vals.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, FileData>, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Map.Entry<Integer, FileData>, String> param) {
				return new SimpleStringProperty(param.getValue().getValue().getFileName());
			}
		});

		this.fileTable.getColumns().addAll(keys, vals);
	}

	private void initHeap(){
		this.heap = new TableView<Map.Entry<Integer, Integer>>();

        TableColumn<Map.Entry<Integer, Integer>,Integer> keys = new TableColumn<>("Address");
        keys.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, Integer>, ObservableValue<Integer>>() {
			
			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Map.Entry<Integer, Integer>, Integer> param) {
				return new SimpleIntegerProperty(param.getValue().getKey()).asObject();
			}
		});
		
		TableColumn<Map.Entry<Integer, Integer>,Integer> vals = new TableColumn<>("Value");
		vals.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, Integer>, ObservableValue<Integer>>() {

			@Override
			public ObservableValue<Integer> call(CellDataFeatures<Map.Entry<Integer, Integer>, Integer> param) {
				return new SimpleIntegerProperty(param.getValue().getValue()).asObject();
			}
		});
		
		this.heap.getColumns().addAll(keys, vals);
		this.heap.setEditable(true);
	}
	
	private FlowPane createList(){
		FlowPane sp = new FlowPane();
		this.exeStack = new ListView<>();
        this.exeStack.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.exeStack.setMaxWidth(300);
        this.exeStack.setMaxHeight(300);
		
		this.output = new ListView<String>();
        this.output.setMaxHeight(40);
		this.pids = new ListView<Integer>();
        this.pids.setMaxHeight(40);
        this.pids.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.pids.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				if(newValue != null){
                    selectedID = newValue;
					populateExeStack();
					populateSymbolTable();
				}
			}
		});

		sp.getChildren().addAll(this.exeStack, this.output, this.pids);
		return sp;
	}

	private void populatePrgList(){
		pids.getItems().clear();
		for(PrgState prg : this.ctrl.getRepo().getPrgStates()){
			pids.getItems().add(prg.getId());
		}
	}

	private void populateExeStack() {
		exeStack.getItems().clear();
		IExeStack<Statement> stk;
		for(PrgState prg : ctrl.getRepo().getPrgStates()){
			if(prg.getId() == selectedID){
				stk = prg.getExeStack();
				for(Statement stm : stk.getAll()){
					exeStack.getItems().add(stm.toString());
				}
				break;
			}
		}
	}

	public void populateHeap() {
        this.heap.getItems().clear();
        if(ctrl.getRepo().getPrgStates().size()>0){
            PrgState myPrg = ctrl.getRepo().getPrgStates().get(0);
            ObservableList<Map.Entry<Integer, Integer>> list = FXCollections.observableArrayList();
            for(Map.Entry<Integer, Integer> elem : myPrg.getHeap().getAll()){
                list.add(elem);
            }
            this.heap.setItems(list);
        }
	}

    public void populateSymbolTable() {
        this.symbolTable.getItems().clear();
        for(PrgState prg : ctrl.getRepo().getPrgStates()){
            if(prg.getId() == selectedID){
                ObservableList<Map.Entry<String, Integer>> list = FXCollections.observableArrayList();
                for(Map.Entry<String, Integer> elem : prg.getSymbolTable().getAll()){
                    list.add(elem);
                }
                this.symbolTable.setItems(list);
                break;
            }
        }
    }

	public void populateFileTable() {
		this.fileTable.getItems().clear();
		if(this.ctrl.getRepo().getPrgStates().size()>0){
			PrgState myPrg = this.ctrl.getRepo().getPrgStates().get(0);
			ObservableList<Map.Entry<Integer, FileData>> list = FXCollections.observableArrayList();
			for(Map.Entry<Integer, FileData> elem : myPrg.getFileTable().getAll()){
				list.add(elem);
			}
            this.fileTable.setItems(list);
		}
	}

    public void populateOutput() {
        this.output.getItems().clear();
        if(ctrl.getRepo().getPrgStates().size()>0){
            this.output.getItems().add(this.ctrl.getRepo().getPrgStates().get(0).getOutput().getAll().toString());
        }
    }

    public void updateNrPrgStates() {
        this.nrPrgState.setText("" + ctrl.getRepo().getPrgStates().size());
    }

	public BorderPane getView() {
        return this.pane;
	}

	public void populateAll(){
        populatePrgList();
        populateExeStack();
        populateHeap();
        populateSymbolTable();
        populateFileTable();
        populateOutput();
        updateNrPrgStates();
	}
}
