package visitor;
import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;

public class AlphaConvVisitor implements ObjVisitor<Exp> {
    private HashMap<Id,Id> hashmap = new HashMap<>();

    public Unit visit(Unit e) {
        return e;
    }

    public Bool visit(Bool e) {
        return e;
    }

    public Int visit(Int e) {
        return e;
    }

    public Float visit(Float e) {
        return e;
    }

    public Not visit(Not e) {
        return new Not(e.e.accept(this));
    }

    public Neg visit(Neg e) {
        return new Neg(e.e.accept(this));
    }

    public Add visit(Add e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new Add(e1,e2);
    }

    public Sub visit(Sub e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new Sub(e1,e2);
    }

    public FNeg visit(FNeg e){
        return new FNeg(e.e.accept(this));
    }

    public FAdd visit(FAdd e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new FAdd(e1,e2);
    }

    public FSub visit(FSub e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new FSub(e1,e2);
    }

    public FMul visit(FMul e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new FMul(e1,e2);
    }

    public FDiv visit(FDiv e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new FDiv(e1,e2);
    }

    public Eq visit(Eq e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new Eq(e1,e2);
    }

    public LE visit(LE e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new LE(e1,e2);
    }

    public If visit(If e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Exp e3 = e.e3.accept(this);
        return new If(e1, e2, e3);
    }


    public Let visit(Let e) {
        Exp e1 = e.e1.accept(this);
        hashmap.put(e.id,e.id);
        Exp e2 = e.e2.accept(this);
        return new Let(e.id, e.t,e1,e2);
    }


    public Var visit(Var e){
        Id new_id = hashmap.get(e.id);
        return new Var(new_id);
    }


    public LetRec visit(LetRec e){
        HashMap<Id,Id> old_values = new HashMap<>();
        ArrayList<Id> new_args = new ArrayList<>();
        for (Id id : e.fd.args) {
            if (hashmap.containsKey(id)) {
                Id new_id = Id.gen();
                Id old_id = hashmap.put(id,new_id);
                old_values.put(id,old_id);
                new_args.add(new_id);
            }
        }
        Exp efd = e.fd.e.accept(this);
        for (Id id : e.fd.args) {
            hashmap.put(id,old_values.get(id));
        }
        FunDef fd = new FunDef(e.fd.id,e.fd.type,new_args,efd);
        Exp e1 = e.e.accept(this);
        return new LetRec(fd,e1);
    }

    public App visit(App e){
        ArrayList<Exp> new_es = new ArrayList<>();
        Exp e1 = e.e.accept(this);
        for (Exp e_current : e.es) {
           Exp elm =  e_current.accept(this);
           new_es.add(elm);
        }
        return new App(e1,new_es);
    }

    public Tuple visit(Tuple e){
        ArrayList<Exp> new_es = new ArrayList<>();
        for (Exp exp : e.es) {
            new_es.add(exp.accept(this));
        }
        return new Tuple(new_es);
    }

    public LetTuple visit(LetTuple e){
        for (Id id : e.ids) {
            hashmap.put(id,id);
        }
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new LetTuple(e.ids,e.ts,e1,e2);
    }

    public Array visit(Array e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new Array(e1,e2);
   }

    public Get visit(Get e){
       Exp e1 = e.e1.accept(this);
       Exp e2 = e.e2.accept(this);
       return new Get(e1,e2);
    }

    public Put visit(Put e){
       Exp e1 = e.e1.accept(this);
       Exp e2 = e.e2.accept(this);
       Exp e3 = e.e3.accept(this);
       return new Put(e1,e2,e3);
    }

}
