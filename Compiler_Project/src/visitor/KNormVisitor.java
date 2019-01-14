package visitor;

import ast.*;
import ast.Float;

import java.util.ArrayList;
import java.util.List;
import type.Type;
import utils.Id;

public class KNormVisitor implements ObjVisitor<Exp> {

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

    public Let visit(Not e) {
        Exp e1 = e.e.accept(this);
        Id new_var = Id.gen();
        Type new_type = Type.gen();
        return new Let(new_var, new_type, e1, new Not(new Var(new_var))) ;
    }

    public Let visit(Neg e) {
        Exp e1 = e.e.accept(this);
        Id new_var = Id.gen();
        Type new_type = Type.gen();
        return new Let(new_var, new_type, e1, new Neg(new Var(new_var))) ;
    }

    public Let visit(Add e) {
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                    new Let(new_var2, new_type2, e2,
                        new Add(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(Sub e) {
		//TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new Sub(new Var(new_var1), new Var(new_var2))));
        return res;
    }
    
    public Exp visit(FNeg e){
       //TO DO ?????????????????????????
       Exp e1 = e.e.accept(this);
       Id new_var = Id.gen();
       Type new_type = Type.gen();
       return new Let(new_var, new_type, e1, new FNeg(new Var(new_var))) ;
    }

    public Let visit(FAdd e){
       //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new FAdd(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(FSub e){
        //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new FSub(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(FMul e) {
       //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new FMul(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(FDiv e){
        //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new FDiv(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(Eq e){
        //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new Eq(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(LE e){
        //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                        new Let(new_var2, new_type2, e2,
                            new LE(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public If visit(If e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Exp e3 = e.e3.accept(this);
        return new If(e1, e2, e3);
    }

    public Let visit(Let e) {
      Exp e1 = e.e1.accept(this);
      Exp e2 = e.e2.accept(this);
      Id new_var1 = e.id;
      Type new_type1 = e.t;
      Let res = new Let(new_var1, new_type1, e1, e2);
       return res;
    }

    public Var visit(Var e){
        return e;
    }

    public LetRec visit(LetRec e){
       //TO DO
       Exp e2 = e.e.accept(this);//suite du code
       Exp e1 = e.fd.e.accept(this);//code de la fct
       Id idF = e.fd.id;
       Type typeF = Type.gen();
       LetRec res = new LetRec(new FunDef(idF,typeF,e.fd.args,e1),e2);
       return res;
    }

    public Exp visit(App e){
        //TO DO
        ArrayList<Exp> vars = new ArrayList<Exp>();
        ArrayList<Id> ids = new ArrayList<Id>();
        for(int i = 0; i < e.es.size(); i++){
            Id id = Id.gen();
            ids.add(id);
            vars.add(new Var(id));
        }

        Exp temp = new App(e.e.accept(this), vars);
        for(int i = e.es.size() - 1; i >= 0 ; i--){
            Let let = new Let(ids.get(i), Type.gen(), e.es.get(i).accept(this), temp);
            temp = let;
        }
        
        return temp;
    }

    public Exp visit(Tuple e){
        ArrayList<Exp> vars = new ArrayList<Exp>();
        ArrayList<Id> ids = new ArrayList<Id>();
        for(int i = 0; i < e.es.size(); i++){
            Id id = Id.gen();
            ids.add(id);
            vars.add(new Var(id));
        }

        Exp temp = new Tuple(vars);
        for(int i = e.es.size() - 1; i >= 0 ; i--){
            Let let = new Let(ids.get(i), Type.gen(), e.es.get(i).accept(this), temp);
            temp = let;
        }

        return temp;
    }

    public LetTuple visit(LetTuple e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);

        ArrayList<Type> ts = new ArrayList<Type>();
        ArrayList<Id> ids = new ArrayList<Id>();
        for(Id id : e.ids){
            ids.add(Id.gen());
            ts.add(Type.gen());
        }

        return new LetTuple(ids, ts, e1, e2);
    }

    public Let visit(Array e){
        //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new Array(new Var(new_var1), new Var(new_var2))));
        return res;
   }

    public Let visit(Get e){
        //TO DO
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Let res = new Let(new_var1, new_type1, e1,
                  new Let(new_var2, new_type2, e2,
                  new Get(new Var(new_var1), new Var(new_var2))));
        return res;
    }

    public Let visit(Put e){
        Exp e1 = e.e1.accept(this);
        Exp e2 = e.e2.accept(this);
        Exp e3 = e.e3.accept(this);

        Id new_var1 = Id.gen();
        Type new_type1 = Type.gen();
        Id new_var2 = Id.gen();
        Type new_type2 = Type.gen();
        Id new_var3 = Id.gen();
        Type new_type3 = Type.gen();

        Let res = 
        new Let(new_var1, new_type1, e1,
          new Let(new_var2, new_type2, e2,
              new Let(new_var3, new_type3, e3,
                new Put(new Var(new_var1), new Var(new_var2), new Var(new_var3)))));
        return res;
    }
}


