package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterAllocationVisitor implements ObjVisitor<Exp>  {

    private HashMap<Id, Integer> index; // Liste des variables associées aux registres. Si negatif alors offset en memoire.

    public RegisterAllocationVisitor(){
        index = new HashMap<Id, Integer>();
    }

    public Exp visit(Unit e) {
        return e;
    }
    
    public Exp visit(Bool e) {
        return e;
    }
    
    public Exp visit(Int e) {
        return e;
    }
    
    public Exp visit(Float e) {
        return e;
    }
    
    public Exp visit(Not e) {
        return e;
    }
    
    public Exp visit(Neg e) {
        return e;
    }
    
    public Exp visit(Add e) {
        return null;
    }

    
    public Exp visit(Sub e) {
        return null;
    }

    
    public Exp visit(FNeg e) {
        return null;
    }

    
    public Exp visit(FAdd e) {
        return null;
    }

    
    public Exp visit(FSub e) {
        return null;
    }

    
    public Exp visit(FMul e) {
        return null;
    }

    
    public Exp visit(FDiv e) {
        return null;
    }

    
    public Exp visit(Eq e) {
        return null;
    }

    
    public Exp visit(LE e) {
        return null;
    }

    
    public Exp visit(If e) {
        return null;
    }

    
    public Exp visit(Let e) {
        return null;
    }

    
    public Exp visit(Var e) {
        return null;
    }

    
    public Exp visit(LetRec e) {
        return null;
    }

    
    public Exp visit(App e) {
        return null;
    }

    
    public Exp visit(Tuple e) {
        return null;
    }

    
    public Exp visit(LetTuple e) {
        return null;
    }

    
    public Exp visit(Array e) {
        return null;
    }

    
    public Exp visit(Get e) {
        return null;
    }

    
    public Exp visit(Put e) {
        return null;
    }
}
