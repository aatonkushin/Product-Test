/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 * Класс, представляющий месяц в году.
 * @author tonkushin
 */
public class Month {
    private int id;
    private String name;
    
    public Month(){
    }
    
    public Month(int id, String name){
        this.id = id;
        this.name = name;
    }

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
    
    /**
     * Вычетает из текущего месяца 1(месяц).
     * @return true - если перешёл 1->12, т.е. нужно изменять год.
     * false - если новый месяц в текущем году.
     */
    public boolean substractByOne(){
        if (getId() == 1) {
            setId(12) ;
            return true;
        } else {
            id--;
            return false;
        }
    }
    
    /**
     * Копирует месяц.
     * @return Копия текущего месяца.
     */
    public Month copy(){
        return new Month(id, name);
    }
    
    @Override
    public String toString(){
        if (this.name != null) {
            return this.name;
        } else {
            return "";
        }
    }
}
