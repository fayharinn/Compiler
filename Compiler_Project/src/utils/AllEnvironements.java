package utils;

import type.Type;

import java.util.ArrayList;

public class AllEnvironements {
    private ArrayList<Environement> LesEnvironements;

    /**
     * Construit une liste d'environement à partir dune liste d'environnement fourni
     * @param lesEnvironements : liste d'environnement fourni
     */
    public AllEnvironements(ArrayList<Environement> lesEnvironements) {
        LesEnvironements = lesEnvironements;
    }

    /**
     * COnstruit une liste d'environnement vide.
     */
    public AllEnvironements() {
        LesEnvironements = new ArrayList<Environement>();
        LesEnvironements.add(new Environement());
    }

    /**
     *
     * @return La liste des environnements
     */
    public ArrayList<Environement> getLesEnvironements() {
        return LesEnvironements;
    }

    /**
     *
     * @return le dernier element de Les Environements, qui référence aà l'environement actuel.
     */
    public Environement getCurentEnvironement(){
        return LesEnvironements.get(LesEnvironements.size()-1);
    }

    /**
     * affecte à LesEnvironnements une valeur donné
     */
    public void setLesEnvironements(ArrayList<Environement> lesEnvironements) {
        LesEnvironements = lesEnvironements;
    }

<<<<<<< HEAD
    public void creerEnvironementLocale(){
=======
    /**
     * Ajoute une nouveau element à LesEnvironnements puis copie le contenu de l'avant dernier element de LesEnvironnements dans le nouveau element.
     * Cette opération correspond à la creation d'un environements Local.
     */
    public void créerEnvironementLocale(){
>>>>>>> master
        Environement newEnvLocale = new Environement();
        newEnvLocale.copyEnv(LesEnvironements.get(EnvironementTableSize()-1));
        LesEnvironements.add(newEnvLocale);
    }

    /**
     * Supprime Le dernier element de LesEnvironnements.
     */
    public void supprimerEnvironementLocale(){
        LesEnvironements.remove(EnvironementTableSize()-1);
    }

    /**
     *
     * @return La taille de LesEnvironnements
     */
    public Integer EnvironementTableSize(){
        return LesEnvironements.size();
    }

    /**
     * ajouter une nouvelle valeur au dernier element du tableau : l'environnements courant.
     * Cette opération correspond à la declaration d'une nouvella variable ou fonctions
     * @param id : Nom de la variable
     * @param t : Type de la variable
     */
    public void ajouterVar(String id, Type t){
        try {
            this.getCurentEnvironement().ajouterVar(id,t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un variable de l'environnement courant.
     * @param id : nom de la variable à supprimer
     */
    public void supprimerVar(String id){
        this.getCurentEnvironement().removeVar(id);
    }

    /**
     *
     * @param id : nom de la variable dont on cherche le type
     * @return : Le type de la variable spécifié
     */
    public Type getTypeOfVar(String id){
        try {
            return this.getCurentEnvironement().getTypeofVar(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Vérifie l'existance de la variable id dans l'environnement courant
     * @param id : Nom de la variable
     * @return : True si elle existe, false sinon
     */
    public Boolean containsKey(String id){
        return this.getCurentEnvironement().containsKey(id);
    }


}
