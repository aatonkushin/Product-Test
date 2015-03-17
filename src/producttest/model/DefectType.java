/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 * Вид дефекта
 * @author tonkushin
 */
public class DefectType {
    private int id;
    private String name;
    private String notes;
    
    /**
     * Конструктор по умолчанию.
     */
    public DefectType(){
        
    }
    
    /**
     * Конструктор с параметрами.
     * @param id - код в БД.
     * @param name - название дефекта в БД.
     * @param notes - примечание.
     */
    public  DefectType(int id, String name, String notes){
        this.id = id;
        this.name = name;
        this.notes = notes;
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
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString(){
        if (this.name != null) {
            return this.name;
        }
        
        return "";
    }
}
