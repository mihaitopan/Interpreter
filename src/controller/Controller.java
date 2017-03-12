package controller;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import model.*;
import repository.*;
import utils.*;

public class Controller {
    private ExecutorService executor;
    private IRepository repo;
    private Observable obs;

	public Controller(IRepository repo) {
		this.repo = repo;
        this.obs = new Observable();
	}

    public void addObserver(Observer o){
        this.obs.addObserver(o);
    }

    public IRepository getRepo() {
        return this.repo;
    }

    public void addPrgState(PrgState ps) {
        this.repo.addPrgState(ps);
    }

	public List<PrgState> removeCompleted(List<PrgState> those) {
		return those.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }

    public Map<Integer,Integer> conservativeGarbageCollector(Collection<Integer> symTableValues,
                                                             Map<Integer,Integer> heap){
        return heap.entrySet().stream().filter(e->symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(x->x.getKey(), x->x.getValue()));
    }

    void oneStepForAll(List<PrgState> prgList) throws InterpreterException {
        prgList.forEach(prg -> { try { repo.logPrgState(prg); } catch (Exception e) {
            e.printStackTrace(); }
        });

        List<Callable<PrgState>> callList = prgList.stream().map((PrgState p) ->
                (Callable<PrgState>)(() -> {return p.oneStep();})).collect(Collectors.toList());

        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream().map(future -> {
                try {
                    return future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(p -> p != null).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InterpreterException(e.getMessage());
        }

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgState(prg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        repo.setPrgStates(prgList);
    }

	public void executeAll() throws InterpreterException {
        // executeOneStep - since GUI

        try {
            repo.clearFile();
        } catch(Exception e){
            throw new InterpreterException(e.getMessage());
        }

        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompleted(repo.getPrgStates());
        if (prgList.size() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("End of execution.");
            alert.showAndWait();
        } else {
            oneStepForAll(prgList);
        }

        executor.shutdownNow();
	}
}
