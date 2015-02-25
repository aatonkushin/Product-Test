/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 * Класс, представляющий сотрудника.
 * @author tonkushin
 */
public class Person {
    private int id;
    private String name;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public Person(){
        this.id = -1;
        this.name = "";
    }
    
    public Person(String name){
        this.id = -1;
        this.name = name;
    }
    
    public Person(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
}
