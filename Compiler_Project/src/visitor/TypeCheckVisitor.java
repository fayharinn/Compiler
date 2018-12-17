package visitor;

import ast.*;
import ast.Float;
import utils.Id;
import utils.findType;
import type.*;
import java.util.*;


public class TypeCheckVisitor implements TypeVisitor<Type> {
	public HashMap<String, Type> env ;
	public HashMap<Type,Type> equations;

    public TypeCheckVisitor() {
		this.env = new HashMap<>();
		this.equations =  new HashMap<>();
	}

	public Type visit(Unit e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TUnit();
    }

    public Type visit(Bool e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TBool();
    }

    public Type visit(Int e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TInt();
    }

    public Type visit(Float e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
        return new TFloat();
    }

    public Type visit(Not e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e.accept(this,env,new TBool(), this.equations);
    	genEqs.put(new TBool(), exptype);
    	return new TBool();
    }

    public Type visit(Neg e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e.accept(this,env,new TInt(), this.equations);
    	genEqs.put(new TInt(), exptype);
    	return new TInt();
    }

    public Type visit(Add e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	Type t1= e.e1.accept(this,env,new TInt(), this.equations);
    	Type t2= e.e2.accept(this,env,new TInt(), this.equations);
    	if(t1.getClass()!=TInt.class || t2.getClass()!=TInt.class) {
    		try {
				throw new Exception("Error in addition between "+t1.getClass()+" and "+t2.getClass() +" : Int expected");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	return new TInt();
    }

    public Type visit(Sub e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	Type t1 = e.e1.accept(this,env,new TInt(), this.equations);
    	Type t2 = e.e2.accept(this,env,new TInt(), this.equations);
    	genEqs.put(new TInt(), exptype);
    	if(t1.getClass()!=TInt.class || t2.getClass()!=TInt.class) {
    		try {
				throw new Exception("Error in sub between "+t1.getClass()+" and "+t2.getClass() +" : Int expected");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	return new TInt();
    }

    public Type visit(FNeg e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        e.e.accept(this,env,new TFloat(),genEqs);
        return new TFloat();
    }

    public Type visit(FAdd e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	Type t1 = e.e1.accept(this,env,new TFloat(), this.equations);
    	Type t2 = e.e2.accept(this,env,new TFloat(), this.equations);
    	genEqs.put(new TFloat(), exptype);
    	if(t1.getClass()!=TFloat.class || t2.getClass()!=TFloat.class) {
    		try {
				throw new Exception("Error in addition between "+t1.getClass()+" and "+t2.getClass() +" : Float expected");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	return new TFloat();
    }

    public Type visit(FSub e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	Type t1 = e.e1.accept(this,env,new TFloat(), this.equations);
    	Type t2 = e.e2.accept(this,env,new TFloat(), this.equations);
    	genEqs.put(new TFloat(), exptype);
    	if(t1.getClass()!=TFloat.class || t2.getClass()!=TFloat.class) {
    		try {
				throw new Exception("Error in sub between "+t1.getClass()+" and "+t2.getClass() +" : Float expected");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	return new TFloat();
    }

    public Type visit(FMul e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	e.e1.accept(this,env,new TFloat(), this.equations);
    	e.e2.accept(this,env,new TFloat(), this.equations);
    	genEqs.put(new TFloat(), exptype);
    	return new TFloat();
    }

    public Type visit(FDiv e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TFloat(), this.equations);
    	e.e2.accept(this,env,new TFloat(), this.equations);
    	genEqs.put(new TFloat(), exptype);
    	return new TFloat();
    }

    public Type visit(Eq e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	Type t1 = e.e1.accept(this,env,new TInt(), this.equations);
    	Type t2 = e.e2.accept(this,env,new TInt(), this.equations);
    	genEqs.put(new TBool(), exptype);
    	return new TBool();
    }

    public Type visit(LE e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TInt(), this.equations);
    	e.e2.accept(this,env,new TInt(), this.equations);
    	genEqs.put(new TBool(), exptype);
    	return new TBool();
    }

    public Type visit(If e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	e.e1.accept(this,env,new TBool(), this.equations);
    	e.e2.accept(this,env,new TInt(), this.equations);
    	e.e3.accept(this,env,new TInt(), this.equations);
    	genEqs.put(new TInt(), exptype);
        return new TInt();
    }

    public Type visit(Let e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	Type t1 = e.e1.accept(this,env,e.t, this.equations);
    	if(env.containsKey(e.id.id)) {
    		try {
				throw new Exception("Error in Let declaration : "+e.id.id+" already defined");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    	env.put(e.id.id, t1);
    	genEqs.put(t1, exptype);// remove?
    	Type t2 = e.e2.accept(this,env,exptype, this.equations);
    	return t2;
    }
    


    public Type visit(Var e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs) {
    	System.out.println(env);
    	System.out.println(e.id.id);
    	if(env.containsKey(e.id.id)){
    		Type t = new TInt();
			genEqs.put(t, exptype);
			return t;
    	} else
			try {
				throw new Exception("Undefined Variable "+e.id.id);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	return null;
    		
    }

    public Type visit(LetRec e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        return null;
    }

    public Type visit(App e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Tuple e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(LetTuple e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Array e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Get e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
    	return null;
    }

    public Type visit(Put e,HashMap<String,Type> env,Type exptype,HashMap<Type,Type> genEqs){
        return null;
    }



	
	public HashMap<Type,Type> getEq(){
		return equations;
	}
}


