package visitor;
import ast.*;
import ast.Float;
import utils.Id;

import java.util.ArrayList;
import java.util.HashMap;


public class ClosureConversion implements ObjVisitor<Exp> {

    private ArrayList<LetRec> declarationFun = new ArrayList<>();
    private ArrayList<String> nameFun = new ArrayList<>();

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
        return e;
    }

    public Neg visit(Neg e) {
        return e;
    }

    public Add visit(Add e) {
        return e ;
    }

    public Sub visit(Sub e) {
        return e;
    }

    public FNeg visit(FNeg e){
        return e;
    }

    public FAdd visit(FAdd e){
        return e;
    }

    public FSub visit(FSub e){
        return e;
    }

    public FMul visit(FMul e) {
        return e;
    }

    public FDiv visit(FDiv e){
        return e;
    }

    public Eq visit(Eq e){
        return e;
    }

    public LE visit(LE e){
        return e;
    }

    public If visit(If e){
        return e;
    }

    public Let visit(Let e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new Let(e.id,e.t,e1,e2);
    }

    public Var visit(Var e){
        return e;
    }

    public Exp visit(LetRec e){
        //CAS SIMPLE
        nameFun.add(e.fd.id.toString());
        Exp e1 = e.fd.e.accept(this);
        FunDef fd = new FunDef(e.fd.id,e.fd.type,e.fd.args,e1);
        LetRec new_e = new LetRec(fd,e.e);
        declarationFun.add(new_e);
        //CAS COMPLEXE

        return e.e.accept(this);
    }

    public App visit(App e){
        if ( nameFun.contains(((Var) e.e).id.toString()) ) {
            Id app = new Id("apply_direct");
            ArrayList<Exp> args = new ArrayList<>(e.es);
            args.add(0, e.e);
            return new App(new Var(app), args);
        } else {
            return e;
        }
    }

    public Tuple visit(Tuple e){
        return e;
    }

    public LetTuple visit(LetTuple e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        return new LetTuple(e.ids,e.ts,e.e1,e2);
    }

    public Array visit(Array e){
        return e;
   }

    public Get visit(Get e){
       return e;
    }

    public Put visit(Put e){
       return e;
    }

}

