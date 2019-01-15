package utils;

import type.Type;

import java.util.HashMap;

public class Environement {
    private HashMap<String, Type> gho;

    /**
     * Construit un environement vide.
     */
    public Environement() {
        this.gho = new HashMap<String, Type>();
    }

    /**
     * construit un environement à partir d'un hashmap spécifié
     */
    public Environement(HashMap<String, Type> gho) {
        this.gho = gho;
    }

    /**
     *
     * @return Le contenu de cet environnement
     */
    public HashMap<String, Type> getGho() {
        return gho;
    }

    /**
     * affecte à l'environneement le contenu de l'objet spécifié
     * @param gho
     */
    public void setGho(HashMap<String, Type> gho) {
        this.gho = gho;
    }

    /**
     * ajoute à l'environement la variable ou la fonction spécifié
     * @param id : nom de la variable ou la fonction
     * @param t : type de la variable ou de la fonction
     * @throws Exception : lance l'exception "Variable already exist" si la varible existe déja dans l'environnement
     */
    public void ajouterVar(String id, Type t) throws Exception {
        if (gho.containsKey(id)) throw new Exception("Variable already exist");
        this.gho.put(id,t);
    }

    /**
     * Supprime la variable ou la fontion spécifié de l'environnement
     * @param id : Le nom de la variable ou la fonction à supprimer
     */
    public void removeVar(String id){
        gho.remove(id);
    }

    /**
     * Retourne le type de la variable spécifié
     * @param id : Le nom de la variable
     * @throws Exception : lance l'exception "Undefined Variable" si celle n'est pas dans l'environement
     */
    public Type getTypeofVar(String id) throws Exception {
        if (!gho.containsKey(id)) throw new Exception("Undefined Variable");
        return gho.get(id);
    }

    /**
     *
     * @param id : Variable à chercher dans l'environnement
     * @return : True si elle existe, false sinon
     */
    public  boolean containsKey(String id){
        return gho.containsKey(id);
    }

    /**
     * construit un nouveau Environement et y Copie le contenu de l'environnement spécifié
     */
    public void copyEnv(Environement e){
        this.gho = new HashMap<>(e.getGho());
    }

}
