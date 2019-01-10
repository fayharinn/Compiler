package utils;

import type.Type;

import java.util.ArrayList;

public class AllEnvironements {
    private ArrayList<Environement> LesEnvironements;

    public AllEnvironements(ArrayList<Environement> lesEnvironements, Integer nbEnvLocal) {
        LesEnvironements = lesEnvironements;
    }

    public AllEnvironements() {
        LesEnvironements = new ArrayList<Environement>();
    }

    public ArrayList<Environement> getLesEnvironements() {

        return LesEnvironements;
    }

    public Environement getCurentEnvironement(){
        return LesEnvironements.get(LesEnvironements.size());
    }

    public void setLesEnvironements(ArrayList<Environement> lesEnvironements) {

        LesEnvironements = lesEnvironements;
    }

    public void créerEnvironementLocale(){
        Environement newEnvLocale = new Environement();
        newEnvLocale.copyEnv(LesEnvironements.get(EnvironementTableSize()));
        LesEnvironements.add(newEnvLocale);
    }

    public Integer EnvironementTableSize(){
        return LesEnvironements.size();
    }

    public void ajouterVar(String id, Type t){
        try {
            this.getCurentEnvironement().ajouterVar(id,t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerVar(String id){
        this.getCurentEnvironement().removeVar(id);
    }

    public Type getTypeOfVar(String id){
        try {
            return this.getCurentEnvironement().getTypeofVar(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean containsKey(String id){
        return this.getCurentEnvironement().containsKey(id);
    }


}
