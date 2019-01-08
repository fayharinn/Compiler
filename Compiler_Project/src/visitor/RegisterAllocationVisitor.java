package visitor;

import ast.*;
import ast.Float;
import type.Type;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterAllocationVisitor implements ObjVisitor<Exp>  {

    private HashMap<Id, Integer> indexRegistres; // Liste des variables associées aux registres.
    private HashMap<Id, Integer> indexStack; // Liste des variables associées à leur offset dans la pile.

    private int getRegistre(){
        return 0;
    }

    public RegisterAllocationVisitor(){
        indexRegistres = new HashMap<Id, Integer>();
        indexStack = new HashMap<Id, Integer>();
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
        return new Not(e.e.accept(this));
    }
    
    public Exp visit(Neg e) {
        return new Neg(e.e.accept(this));
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
        int registre = indexRegistres.get(e.id);
        if(registre >= 0){ //La variable est dans un registre
            return new Var(new Id("r" + registre));
        }else{
            return null;
        }
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
