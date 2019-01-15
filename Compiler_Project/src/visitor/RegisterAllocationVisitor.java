package visitor;

import ast.*;
import ast.Float;
import type.Type;
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
    private HashMap<String, String> indexRegisters; // Liste des variables associées aux registres.
    private HashMap<String, Integer> indexStack; // Liste des variables associées à leur offset dans la pile.
    private HashMap<String, Integer> intervals;
    private ArrayList<String> availableRegisters;
    private int stackOffset;
    private int nodeCounter;

    /***
     * Initialisation commune aux deux constructeurs
     */
    private void init(){
        indexStack = new HashMap<String, Integer>();
        availableRegisters = new ArrayList<String>();
        for(int i = MIN_REG; i <= MAX_REG; i++) {
            availableRegisters.add("r" + i);
        }
        nodeCounter = 0;
    }

    /***
     * Constructeur appelé au tout début d'allocation
     * @param intervals
     */
    public RegisterAllocationVisitor(HashMap<String, Integer> intervals){
        init();
        this.intervals = intervals;
        indexRegisters = new HashMap<String, String>();
        stackOffset = -4;
    }

    /***
     * Constructeur appelé lorsqu'on rentre dans un nouveau contexte
     * @param intervals
     * @param regs
     * @param stackOffset
     */
    public RegisterAllocationVisitor(HashMap<String, Integer> intervals, HashMap<String, String> regs, int stackOffset){
        init();
        this.intervals = intervals;
        indexRegisters = regs;
        this.stackOffset = stackOffset;
    }

    /***
     * Permet d'obtenir l'idée d'un registre donné, car HashMap n'offre pas de méthode adequate
     * @param reg
     * @return l'Id du registre donné, ou null si il ne le trouve pas
     */
    private String getIdFromReg(String reg){
        for (HashMap.Entry<String, String> entry : indexRegisters.entrySet()) {
            if(entry.getValue().equals(reg)){
                return entry.getKey();
            }
        }
        return null;
    }

    /***
     * Regarde si il faut spill une variable, il enrichie indexStack si il le faut
     * @param reg
     * @return l'id de la variable si il faut spill, null sinon
     */
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

    /***
     * Renvoie le nom de registre à utiliser, le spill n'est pas géré ici
     * @return le registre à utililiser
     */
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
        }else{ //sinon on prend le registre qui a été libéré en premier
            reg = availableRegisters.get(0);
            availableRegisters.remove(0);
        }
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
        return new If(e.e1.accept(this), new Let(new Id("r0"), Type.gen(), e.e2.accept(this), new Unit()), new Let(new Id("r0"), Type.gen(), e.e3.accept(this), new Unit()));
    }
    
    public Exp visit(Let e) {
        nodeCounter++;
        if(indexRegisters.containsKey(e.id.toString())){
            return new Let(new Id(indexRegisters.get(e.id.toString())), e.t, e.e1.accept(this), e.e2.accept(this));
        }else{
            String reg = getRegister();
            Id idReg = new Id(reg);
            int size = indexStack.size();
            String idSave = checkoutReg(reg);
            boolean moveStackPointer = size < indexStack.size();
            indexRegisters.put(e.id.toString(), reg);
            Exp temp = new Let(idReg, e.t, e.e1.accept(this), e.e2.accept(this));
            if(moveStackPointer) {
                Id sp = new Id("sp");
                temp = new Let(sp, Type.gen(), new Sub(new Var(sp), new Int(4)), temp);
            }
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
            int size = indexStack.size();
            String idSave = checkoutReg(reg);
            boolean moveStackPointer = size < indexStack.size();
            indexRegisters.put(e.id.toString(), reg);
            Exp temp = new Load(new Id(reg), indexStack.get(e.id.toString()), new Unit());
            if(moveStackPointer) {
                Id sp = new Id("sp");
                temp = new Let(sp, Type.gen(), new Sub(new Var(sp), new Int(4)), temp);
            }
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
        //Allocation des arguments
        for(int i = 0; i < e.fd.args.size(); i++){
            if(i < 4){
                args.add(new Id("r" + i));
                regs.put(e.fd.args.get(i).toString(), "r" + i);
            }else{
                args.add(e.fd.args.get(i));
            }
        }
        Exp temp = e.fd.e.accept(new RegisterAllocationVisitor(intervals, regs, -9 * 4)); //Les registres sont sauvegardés donc on deplace le haut de la pile
        FunDef fd = new FunDef(e.fd.id, e.fd.type, args, temp);
        return new LetRec(fd, temp);
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
