package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterAllocationVisitor implements ObjVisitor<Exp>  {

    private static final int MIN_REG = 4;
    private static final int MAX_REG = 10;
    private HashMap<String, Integer> indexRegistres; // Liste des variables associées aux registres.
    private HashMap<String, Integer> indexStack; // Liste des variables associées à leur offset dans la pile.
    private int nextReg;
    private int stackOffset;

    private String getIdFromReg(int reg){
        for (HashMap.Entry<String, Integer> entry : indexRegistres.entrySet()) {
            if(entry.getValue() == reg){
                return entry.getKey();
            }
        }
        return null;
    }

    private int getRegister(){
        nextReg++;
        if(nextReg > MAX_REG)
            nextReg = MIN_REG - 1;
        return nextReg;
    }

    public RegisterAllocationVisitor(){
        indexRegistres = new HashMap<String, Integer>();
        indexStack = new HashMap<String, Integer>();
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
        return new Add(e.e1.accept(this), e.e2.accept(this));
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
        if(indexRegistres.containsKey(e.id.toString())){
            return new Let(new Id("r" + indexRegistres.get(e.id.toString())), e.t, e.e1.accept(this), e.e2.accept(this));
        }else{
            int reg = getRegister();
            Id idReg = new Id("r" + reg);
            boolean load = false;
            String idSave = null;
            if(indexRegistres.containsValue(reg)){
                idSave = getIdFromReg(reg);
                if(!indexStack.containsKey(idSave)){
                    indexStack.put(idSave, stackOffset);
                    stackOffset -= 4;
                }
                indexRegistres.remove(idSave);
                if(indexStack.containsKey(e.id.toString()))
                    load = true;
            }
            indexRegistres.put(e.id.toString(), reg);

            Exp temp = new Let(idReg, e.t, e.e1.accept(this), e.e2.accept(this));
            if(load){
                temp = new Load(idReg, indexStack.get(e.id.toString()), temp);
            }else if(idSave != null){
                temp = new Save(idReg, indexStack.get(idSave), temp);
            }

            return temp;
        }
    }
    
    public Exp visit(Var e) {
        return new Var(new Id("r" + indexRegistres.get(e.id.toString())));
    }

    public Exp visit(LetRec e) { //TODO replace args with regs
        return new LetRec(new FunDef(e.fd.id, e.fd.type, e.fd.args, e.fd.e.accept(this)), e.e.accept(this));
    }
    
    public Exp visit(App e) {
        ArrayList<Exp> es = new ArrayList<Exp>();
        for(Exp ex: e.es){
            es.add(ex.accept(this));
        }
        return new App(e.e.accept(this), es);
    }
    
    public Exp visit(Tuple e) {
        ArrayList<Exp> es = new ArrayList<Exp>();
        for(Exp ex: e.es){
            es.add(ex.accept(this));
        }
        return new Tuple(es);
    }
    
    public Exp visit(LetTuple e) { //TODO ids
        return new LetTuple(e.ids, e.ts, e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(Array e) {
        return new Array(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(Get e) {
        return new Get(e.e1.accept(this), e.e2.accept(this));
    }
    
    public Exp visit(Put e) {
        return new Put(e.e1.accept(this), e.e2.accept(this), e.e3.accept(this));
    }

    public Exp visit(Load load) {
        return null;
    }

    public Exp visit(Save save) {
        return null;
    }
}
