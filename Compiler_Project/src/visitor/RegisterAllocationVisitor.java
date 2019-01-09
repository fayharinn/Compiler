package visitor;

import ast.*;
import ast.Float;
import type.Type;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterAllocationVisitor implements ObjVisitor<Exp>  {

    private static final int MIN_REG = 4;
    private static final int MAX_REG = 10;
    private HashMap<Id, Integer> indexRegistres; // Liste des variables associées aux registres.
    private HashMap<Id, Integer> indexStack; // Liste des variables associées à leur offset dans la pile.
    private int nextReg;
    private int stackOffset;

    private Id getIdFromReg(int reg){
        for (HashMap.Entry<Id, Integer> entry : indexRegistres.entrySet()) {
            if(entry.getValue() == reg){
                return entry.getKey();
            }
        }
        return null;
    }

    private int getRegistre(){
        nextReg++;
        if(nextReg > MAX_REG)
            nextReg = MIN_REG - 1;
        return nextReg;
    }

    public RegisterAllocationVisitor(){
        indexRegistres = new HashMap<Id, Integer>();
        indexStack = new HashMap<Id, Integer>();
        stackOffset = -4;
        nextReg = MIN_REG - 1;
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
        return new Add(e.e1.accept(this), e.e1.accept(this));
    }
    
    public Exp visit(Sub e) {
        return new Sub(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(FNeg e) {
        return new FNeg(e.e.accept(this));
    }
    
    public Exp visit(FAdd e) {
        return new FAdd(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(FSub e) {
        return new FSub(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(FMul e) {
        return new FMul(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(FDiv e) {
        return new FDiv(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(Eq e) {
        return new Eq(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(LE e) {
        return new LE(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(If e) {
        return new If(e.e1.accept(this), e.e2.accept(this), e.e3.accept(this));
    }
    
    public Exp visit(Let e) {
        if(indexRegistres.containsKey(e.id)){
            return new Let(new Id("r" + indexRegistres.get(e.id)), e.t, e.e1.accept(this), e.e2.accept(this));
        }else{
            int reg = getRegistre();
            Id idReg = new Id("r" + reg);
            Exp temp = new Let(idReg, e.t, e.e1.accept(this), e.e2.accept(this));

            if(indexRegistres.containsValue(reg) && indexStack.containsKey(e.id)){
                temp = new Load(idReg, indexStack.get(e.id), temp);
            }else if(indexRegistres.containsValue(reg)){
                if(!indexStack.containsKey(e.id)){
                    indexStack.put(e.id, stackOffset);
                    stackOffset -= 4;
                }
                Id id = getIdFromReg(reg);
                temp = new Save(idReg, indexStack.get(id), temp);
                indexRegistres.remove(id);
            }
            indexRegistres.put(e.id, reg);

            return temp;
        }
    }
    
    public Exp visit(Var e) {
        return new Var(new Id("r" + indexRegistres.get(e.id)));
    }

    public Exp visit(LetRec e) {
        return new LetRec(e.fd, e.e.accept(this));
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

    public Exp visit(Load load) {
        return null;
    }

    public Exp visit(Save save) {
        return null;
    }
}
