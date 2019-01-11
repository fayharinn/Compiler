package visitor;

import ast.*;
import ast.Float;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class VariableIntervalVisitor implements ObjVisitor<HashMap<String, Integer>> {

    private HashMap<String, Integer> intervals;
    private int nodeCounter;

    private void update(String var){
        if(!intervals.containsKey(var)) {
            intervals.put(var, nodeCounter);
        }else{
            intervals.replace(var, nodeCounter);
        }
    }

    public VariableIntervalVisitor() {
        intervals = new HashMap<String, Integer>();
        nodeCounter = 0;
    }

    public HashMap<String, Integer> visit(Unit e) {
        return null;
    }

    public HashMap<String, Integer> visit(Bool e) {
        return null;
    }

    public HashMap<String, Integer> visit(Int e) {
        return null;
    }

    public HashMap<String, Integer> visit(Float e) {
        return null;
    }

    public HashMap<String, Integer> visit(Not e) {
        e.e.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(Neg e) {
        e.e.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(Add e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(Sub e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(FNeg e) {
        e.e.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(FAdd e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(FSub e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(FMul e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(FDiv e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(Eq e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(LE e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(If e) {
        e.e1.accept(this);
        e.e2.accept(this);
        e.e3.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(Let e) {
        e.e1.accept(this);
        e.e2.accept(this);
        return intervals;
    }

    public HashMap<String, Integer> visit(Var e) {
        update(e.id.toString());
        nodeCounter++;
        return null;
    }

    public HashMap<String, Integer> visit(LetRec e) {
        HashMap<String, Integer> retInterval = e.fd.e.accept(new VariableIntervalVisitor());
        intervals.putAll(retInterval);
        e.e.accept(this);
        return intervals;
    }

    public HashMap<String, Integer> visit(App e) {
        for(Exp ex: e.es){
            ex.accept(this);
        }
        e.e.accept(this);
        return null;
    }

    public HashMap<String, Integer> visit(Tuple e) {

        for(Exp ex: e.es) {
            ex.accept(this);
        }
        return null;
    }

    //TODO
    public HashMap<String, Integer> visit(LetTuple e) {
        return null;
    }

    public HashMap<String, Integer> visit(Array e) {
        return null;
    }

    public HashMap<String, Integer> visit(Get e) {
        return null;
    }

    public HashMap<String, Integer> visit(Put e) {
        return null;
    }

    public HashMap<String, Integer> visit(Load load) {
        return null;
    }

    public HashMap<String, Integer> visit(Save save) {
        return null;
    }
}
