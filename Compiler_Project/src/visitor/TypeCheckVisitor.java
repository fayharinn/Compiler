package visitor;

import ast.*;
import ast.Float;
import utils.*;
import type.*;
import java.util.*;


public class TypeCheckVisitor implements TypeVisitor<Type> {
	private Environement environement ;
	private HashMap<Type,Type> equations;
	private  HashMap<String,Environement> localsEnvironements;


    public TypeCheckVisitor() {
		this.environement = new Environement();
		this.equations =  new HashMap<>();
		this.localsEnvironements = new HashMap<>();
	}


    public Environement getEnvironement() {
        return environement;
    }

    public HashMap<Type,Type> getEq(){
        return equations;
    }

   public void printEnvironement(){
       for (String keys: environement.getGho().keySet()){
           String id =keys;
           String value = environement.getGho().get(id).toString();
           System.out.println(id + " " + value);
       }
   }

	public Type visit(Unit e,Type expType) {
        return new TUnit();
    }

    public Type visit(Bool e,Type expType) {
        return new TBool();
    }

    public Type visit(Int e,Type expType) {
        return new TInt();
    }

    public Type visit(Float e,Type expType) {
        return new TFloat();
    }


    public Type visit(Not e,Type expType) {
        e.e.accept(this, new TBool());
        equations.put(new TBool(), expType);
        return new TBool();
    }
    public Type visit(Neg e,Type expType) {
    	e.e.accept(this,new TInt());
    	equations.put(new TInt(), expType);
    	return new TInt();
    }

    public Type visit(Add e,Type expType) {
    	Type t1= e.e1.accept(this,new TInt());
    	Type t2= e.e2.accept(this,new TInt());
        equations.put(new TInt(), expType);
    	if(t1.getClass()!=TInt.class || t2.getClass()!=TInt.class) {
    		try {
				throw new Exception("Error in addition between "+t1.getClass()+" and "+t2.getClass() +" : Int expected");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
    	return new TInt();
    }

    public Type visit(Sub e,Type expType) {
    	Type t1 = e.e1.accept(this,new TInt());
    	Type t2 = e.e2.accept(this,new TInt());
    	equations.put(new TInt(), expType);
    	if(t1.getClass()!=TInt.class || t2.getClass()!=TInt.class) {
    		try {
				throw new Exception("Error in sub between "+t1.getClass()+" and "+t2.getClass() +" : Int expected");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
    	return new TInt();
    }

    public Type visit(FNeg e,Type expType){
        e.e.accept(this,new TFloat());
        return new TFloat();
    }

    public Type visit(FAdd e,Type expType){
    	Type t1 = e.e1.accept(this,new TFloat());
    	Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);
    	System.out.println(t1);
        System.out.println(t2);
    	if(t1.getClass()!=TFloat.class || t2.getClass()!=TFloat.class) {
    		try {
				throw new Exception("Error in addition between "+t1.getClass()+" and "+t2.getClass() +" : Float expected");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
    	return new TFloat();
    }

    public Type visit(FSub e,Type expType){
    	Type t1 = e.e1.accept(this,new TFloat());
    	Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);
    	if(t1.getClass()!=TFloat.class || t2.getClass()!=TFloat.class) {
    		try {
				throw new Exception("Error in sub between "+t1.getClass()+" and "+t2.getClass() +" : Float expected");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
    	return new TFloat();
    }

    public Type visit(FMul e,Type expType) {
        Type t1 = e.e1.accept(this,new TFloat());
        Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);
        if(t1.getClass()!=TFloat.class || t2.getClass()!=TFloat.class) {
            try {
                throw new Exception("Error in mul between "+t1.getClass()+" and "+t2.getClass() +" : Float expected");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    	return new TFloat();
    }

    public Type visit(FDiv e,Type expType){
        Type t1 = e.e1.accept(this,new TFloat());
        Type t2 = e.e2.accept(this,new TFloat());
    	equations.put(new TFloat(), expType);
        if(t1.getClass()!=TFloat.class || t2.getClass()!=TFloat.class) {
            try {
                throw new Exception("Error in div between "+t1.getClass()+" and "+t2.getClass() +" : Float expected");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    	return new TFloat();
    }

    public Type visit(Eq e,Type expType){
    	Type t1 = e.e1.accept(this,new TInt());
    	Type t2 = e.e2.accept(this,new TInt());
    	equations.put(new TBool(), expType);
        if(t1.getClass()!=TInt.class || t2.getClass()!=TInt.class) {
            try {
                throw new Exception("Error want to equal between "+t1.getClass()+" and "+t2.getClass() +" : Int expected");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    	return new TBool();
    }

    public Type visit(LE e,Type expType){
    	Type t1 = e.e1.accept(this,new TInt());
    	Type t2 = e.e2.accept(this,new TInt());
    	equations.put(new TBool(), expType);
        if(t1.getClass()!=TInt.class || t2.getClass()!=TInt.class) {
            try {
                throw new Exception("Error in Less Or Equal between "+t1.getClass()+" and "+t2.getClass() +" : Int expected");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    	return new TBool();
    }

    public Type visit(If e,Type expType){
    	Type t1 = e.e1.accept(this,new TBool());
    	Type t2 = e.e2.accept(this,new TInt());
    	Type t3 = e.e3.accept(this,new TInt());
    	equations.put(new TInt(), expType);
        if( t3.getClass() != TBool.class ||t1.getClass()!=TInt.class || t2.getClass()!=TInt.class) {
            try {
                throw new Exception("Error in If construction");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return new TInt();
    }

    public Type visit(Let e,Type expType) {
    	Type t1 = e.e1.accept(this,e.t);
    	if(environement.containsKey(e.id.id)) {
    		try {
				throw new Exception("Error in Let declaration : "+e.id.id+" already defined");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
    	}
		try {
			environement.ajouterVar(e.id.id, t1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		equations.put(t1, expType);
    	Type t2 = e.e2.accept(this,expType);
    	return t2;
    }
    

    public Type visit(Var e,Type expType) {
		if(environement.containsKey(e.id.id)){
			Type t = null;
			try {
				t = environement.getTypeofVar(e.id.id);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			equations.put(t, expType);
			return t;
		} else
			try {
				throw new Exception("Undefined Variable "+e.id.id);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		return null;

	}


	public Type visit(LetRec e,Type expType){

    	return null;
	}

	public Type visit(App e,Type expType){
		return null;
	}

	public Type visit(Tuple e,Type expType){
		return null;
	}

	public Type visit(LetTuple e,Type expType){
		return null;
	}

	public Type visit(Array e,Type expType){
		return null;
	}

	public Type visit(Get e,Type expType){
		return null;
	}

	public Type visit(Put e,Type expType){
		return null;
	}

	@Override
	public Type visit(FunDef e, Type expType) {
		Type t =  e.e.accept(this, new TUnit());
		ArrayList<Type> argsType = new ArrayList<Type>();
		try {
			for (Id vars : e.args){argsType.add(environement.getTypeofVar(vars.toString()));}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Type functionType = new TFun(argsType,t);
		try {
			this.environement.ajouterVar(e.id.id,functionType);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return functionType;
	}
}


