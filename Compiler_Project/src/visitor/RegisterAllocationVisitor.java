package visitor;

import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;

/*TODO:
    -Load DONE
    -Valeur de retour
    -Sauvegarde registres DONE?
    -Contexte (intervals...) DONE
    -Gestion FP/SP
    -Heap
 */

public class RegisterAllocationVisitor implements ObjVisitor<Exp>  {

    private static final int MIN_REG = 4;
    private static final int MAX_REG = 10;
    private static final String[] calleeSave = {"r4", "r5", "r6", "r7", "r8", "r9", "r10", "r11", "r13"};
    private static final String[] callerSave = {"r0", "r1", "r2", "r3", "r12", "r14", "r15" };
    private HashMap<String, String> indexRegisters; // Liste des variables associées aux registres.
    private HashMap<String, Integer> indexStack; // Liste des variables associées à leur offset dans la pile.
    private HashMap<String, Integer> intervals;
    private ArrayList<String> availableRegisters;
    private int stackOffset;
    private int nodeCounter;
//    private int nextReg;

    private void init(){
        indexStack = new HashMap<String, Integer>();
        availableRegisters = new ArrayList<String>();
        for(int i = MIN_REG; i <= MAX_REG; i++) {
            availableRegisters.add("r" + i);
        }
        nodeCounter = 0;
        //nextReg = MIN_REG;
    }

    public RegisterAllocationVisitor(HashMap<String, Integer> intervals){
        this.intervals = intervals;
        indexRegisters = new HashMap<String, String>();
        stackOffset = -4;
        init();
    }

    public RegisterAllocationVisitor(HashMap<String, Integer> intervals, HashMap<String, String> regs, int stackOffset){
        this.intervals = intervals;
        indexRegisters = regs;
        stackOffset = stackOffset;
        init();
    }

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
                indexStack.put(idSave, stackOffset);
                stackOffset -= 4;
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
        if(indexRegisters.containsKey(e.id.toString())){ //Variable a deja un registre
            return new Var(new Id(indexRegisters.get(e.id.toString())));
        }else if(indexStack.containsKey(e.id.toString())) { //La variable est sur la pile, on la load
            String reg = getRegister();
            String idSave = checkoutReg(reg);
            indexRegisters.put(e.id.toString(), reg);
            Exp temp = new Load(new Id(reg), indexStack.get(e.id.toString()), new Var(new Id(reg)));
            if(idSave != null){
                temp = new Save(new Id(reg), indexStack.get(idSave), temp);
            }
            return temp;
        }else{ //Nom de fonction...
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
        int stack = -4 * calleeSave.length;
        Exp temp = e.fd.e.accept(new RegisterAllocationVisitor(intervals, regs, stack - 4));
        for(String reg : calleeSave) {
            //if(!availableRegisters.contains(reg)){ //TODO Sauvegarder seulement les registres utilisés puis gerer load dans les bons registres
            temp = new Save(new Id(reg), stack, temp);
            stack += 4;
            //}
        }
        FunDef fd = new FunDef(e.fd.id, e.fd.type, args, temp);
        temp = e.e.accept(this);
        stack = -4 * calleeSave.length;
        for(String reg : calleeSave) {
            temp = new Load(new Id(reg), stack, temp);
            stack += 4;
        }
        return new LetRec(fd, temp);
    }
    
    public Exp visit(App e) {
        ArrayList<Exp> es = new ArrayList<Exp>();
        for(Exp ex: e.es){
            es.add(ex.accept(this));
        }
        Exp temp = e.e.accept(this);
        int stack = stackOffset - (callerSave.length * 4);
        for(String reg : callerSave){
            temp = new Load(new Id(reg), stack, temp);
            stack += 4;
        }
        temp = new App(temp, es);
        stack = stackOffset - (callerSave.length * 4);
        for(String reg : callerSave){
            temp = new Save(new Id(reg), stack, temp);
            stack += 4;
        }
        return temp;
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
