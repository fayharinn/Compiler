package visitor;

import ast.*;
import ast.Float;
import utils.Id;
import utils.findType;
import type.*;
import java.util.*;


public class TypeCheckVisitor implements TypeVisitor<Type> {
	private HashMap<Id, Type> env ;
	private HashMap<Type,Type> equations;

    public TypeCheckVisitor() {
		this.env = new HashMap<>();
	}

    public HashMap<Id, Type> getEnv() {
        return env;
    }

	public Type visit(Unit e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TUnit();
    }

    public Type visit(Bool e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TBool();
    }

    public Type visit(Int e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	
        return new TInt();
    }

    public Type visit(Float e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TFloat();
    }

    public Type visit(Not e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e.accept(this,env,new TBool(), genEqs);
    	genEqs.put(new TBool(), exptype);
    	this.equations = genEqs;
    	return new TBool();
    }

    public Type visit(Neg e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
        this.equations = genEqs;
    	return new TInt();
    }

    public Type visit(Add e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
        this.equations = genEqs;
    	return new TInt();
    }

    public Type visit(Sub e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
        this.equations = genEqs;
    	return new TInt();
    }

    public Type visit(FNeg e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        e.e.accept(this,env,new TFloat(),genEqs);
        return new TFloat();
    }

    public Type visit(FAdd e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
        this.equations = genEqs;
    	return new TFloat();
    }

    public Type visit(FSub e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
        this.equations = genEqs;
    	return new TFloat();
    }

    public Type visit(FMul e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
        this.equations = genEqs;
    	return new TFloat();
    }

    public Type visit(FDiv e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TFloat(), genEqs);
    	e.e2.accept(this,env,new TFloat(), genEqs);
    	genEqs.put(new TFloat(), exptype);
        this.equations = genEqs;
    	return new TFloat();
    }

    public Type visit(Eq e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TBool(), exptype);
        this.equations = genEqs;
    	return new TBool();
    }

    public Type visit(LE e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TInt(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TBool(), exptype);
        this.equations = genEqs;
    	return new TBool();
    }

    public Type visit(If e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TBool(), genEqs);
    	e.e2.accept(this,env,new TInt(), genEqs);
    	e.e3.accept(this,env,new TInt(), genEqs);
    	genEqs.put(new TInt(), exptype);
        this.equations = genEqs;
        return new TInt();
    }

    public Type visit(Let e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	Type t1 = e.e1.accept(this,env,e.t, genEqs);
    	env.put(e.id, t1);
    	Type t2 = e.e2.accept(this,env,exptype, genEqs);
    	genEqs.put(new TInt(), exptype);
        this.equations = genEqs;
    	return t2;
    }

    public Type visit(Var e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	Type t = e.accept(this, env, exptype, genEqs);
    	if(env.containsKey(e.id)){
			genEqs.put(t, exptype);
            this.equations = genEqs;
			return t;
    	} else
			try {
				throw new Exception("Undefined Variable");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	return null;
    		
    }

    public Type visit(LetRec e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        return null;
    }

    public Type visit(App e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Tuple e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(LetTuple e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Array e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Get e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Put e,HashMap<Id,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        return null;
    }

}


