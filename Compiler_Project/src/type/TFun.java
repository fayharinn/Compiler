package type;

import java.util.ArrayList;

public class TFun extends Type {
    private ArrayList<Type> argsType;
    private Type returnType;

    public TFun (ArrayList<Type> argsType, Type returnType) {
        this.argsType = argsType;
        this.returnType = returnType;
    }

    public void ajouterArgsType(Type t){
        argsType.add(t);
    }
    public ArrayList<Type> getargsType() {
        return argsType;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setArgsType(ArrayList<Type> argsType) {
        this.argsType = argsType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public Type typeConversion(){
        return returnType;
    }
    @Override
    public String toString() {
        return "(" + this.argsType.toString() +"->" + returnType.toString()+")" ;
    }
}

