package utils;

import type.Type;

import java.util.HashMap;

public class Environement {
    private HashMap<String, Type> gho;


    public Environement() {
        this.gho = new HashMap<String, Type>();
    }

    public Environement(HashMap<String, Type> gho) {
        this.gho = gho;
    }

    public HashMap<String, Type> getGho() {
        return gho;
    }

    public void setGho(HashMap<String, Type> gho) {
        this.gho = gho;
    }

    public void ajouterVar(String id, Type t) throws Exception {
        if (gho.containsKey(id)) throw new Exception("Variable already exist");
        this.gho.put(id,t);
    }

    public void removeVar(String id){
        gho.remove(id);
    }

    public Type getTypeofVar(String id) throws Exception {
        if (!gho.containsKey(id)) throw new Exception("Undefined Variable");
        return gho.get(id);
    }

    public  boolean containsKey(String id){
        return gho.containsKey(id);
    }

    public void copyEnv(Environement e){
        this.gho = e.getGho();
    }

}
