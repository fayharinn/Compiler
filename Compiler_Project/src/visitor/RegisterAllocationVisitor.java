package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;

/*TODO:
    -Load
    -Valeur de retour
    -Sauvegarde registres
    -Contexte (intervals...)
    -Heap
 */

public class RegisterAllocationVisitor implements ObjVisitor<Exp>  {

    private static final int MIN_REG = 4;
    private static final int MAX_REG = 10;
    private HashMap<String, String> indexRegisters; // Liste des variables associées aux registres.
    private HashMap<String, Integer> indexStack; // Liste des variables associées à leur offset dans la pile.
    private HashMap<String, Integer> intervals;
    private ArrayList<String> availableRegisters;
    private int stackOffset;
    private int nodeCounter;
//    private int nextReg;

    private String getIdFromReg(String reg){
        for (HashMap.Entry<String, String> entry : indexRegisters.entrySet()) {
            if(entry.getValue().equals(reg)){
                return entry.getKey();
            }
        }
        return null;
    }

    private String checkoutReg(String reg){
        if(indexRegisters.containsValue(reg)){
            String idSave = getIdFromReg(reg);
            if(!indexStack.containsKey(idSave) && intervals.get(idSave) >= nodeCounter){
                stackOffset -= 4;
                indexStack.put(idSave, stackOffset);
            }
            indexRegisters.remove(idSave);
            return idSave;
        }else{
            return null;
        }
    }

    private String getRegister(){
        ArrayList<String> keys = new ArrayList<>(); //On ne peut pas remove directement dans le for (CurrentModificationException)
        for(HashMap.Entry<String, String> entry : indexRegisters.entrySet()){
            if(Integer.parseInt(entry.getValue().substring(1)) >= MIN_REG && intervals.get(entry.getKey()) < nodeCounter){ //Variable plus utilisée
                availableRegisters.add(entry.getValue());
                keys.add(entry.getKey());
            }
        }
        for(String s: keys){
            indexRegisters.remove(s);
        }
        String reg = null;
        if(availableRegisters.isEmpty()){ //Si aucun registre est disponible, on spill la variable utilisée en dernier (/!\ pas forcement la variable utilisée la plus tardivement)
            int max = 0;
            for(HashMap.Entry<String, String> entry : indexRegisters.entrySet()){
                if(intervals.get(entry.getKey()) > max) {
                    reg = entry.getValue();
                    max = intervals.get(entry.getKey());
                }
            }
        }else{
            reg = availableRegisters.get(0);
            availableRegisters.remove(0);
        }


//        String reg = "r" + nextReg;
//        nextReg++;
//        if(nextReg > MAX_REG)
//            nextReg = MIN_REG;
        return reg;
    }

    private void init(){
        indexStack = new HashMap<String, Integer>();
        availableRegisters = new ArrayList<String>();
        for(int i = MIN_REG; i <= MAX_REG; i++){
            availableRegisters.add("r" + i);
        }
        stackOffset = 0;
        nodeCounter = 0;
        //nextReg = MIN_REG;
    }

    public RegisterAllocationVisitor(HashMap<String, Integer> intervals){
        this.intervals = intervals;
        indexRegisters = new HashMap<String, String>();
        init();
    }

    public RegisterAllocationVisitor(HashMap<String, Integer> intervals, HashMap<String, String> regs){
        this.intervals = intervals;
        indexRegisters = regs;
        init();
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
        if(indexRegisters.containsKey(e.id.toString())){
            return new Let(new Id(indexRegisters.get(e.id.toString())), e.t, e.e1.accept(this), e.e2.accept(this));
        }else{
            String reg = getRegister();
            Id idReg = new Id(reg);
            String idSave = checkoutReg(reg);
            indexRegisters.put(e.id.toString(), reg);
            Exp temp = new Let(idReg, e.t, e.e1.accept(this), e.e2.accept(this));
            if(idSave != null){
                temp = new Save(idReg, indexStack.get(idSave), temp);
            }
            return temp;
        }
    }
    
    public Exp visit(Var e) {
        nodeCounter++;
        if(indexRegisters.containsKey(e.id.toString())){
            return new Var(new Id(indexRegisters.get(e.id.toString())));
        }else if(indexStack.containsKey(e.id.toString())) {
            String reg = getRegister();
            String idSave = checkoutReg(reg);
            Exp temp = new Load(new Id(reg), indexStack.get(e.id.toString()), new Var(new Id(indexRegisters.get(e.id.toString()))));
            if(idSave != null){
                temp = new Save(new Id(reg), indexStack.get(idSave), temp);
            }
            return temp;
        }else{
            return e;
        }
    }

    public Exp visit(LetRec e) {
        ArrayList<Id> args = new ArrayList<>();
        HashMap<String, String> regs = new HashMap<String, String>();
        for(int i = 0; i < e.fd.args.size(); i++){
            if(i < 4){
                args.add(new Id("r" + i));
                regs.put(e.fd.args.get(i).toString(), "r" + i);
            }else{
                args.add(e.fd.args.get(i));
            }
        }
        return new LetRec(new FunDef(e.fd.id, e.fd.type, args, e.fd.e.accept(new RegisterAllocationVisitor(intervals, regs))), e.e.accept(this));
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
