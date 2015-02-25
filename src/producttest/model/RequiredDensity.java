/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 * Класс сущности, представляющей требуемую плотность.
 * @author tonkushin
 */
public class RequiredDensity {
    
    // <editor-fold defaultstate="collapsed" desc="Поля, геттеры, сеттеры">
    private String name;
    private Double min;
    private Double max;

    /**
     * @return the name - название класса по плотности
     */
    public String getName() {
        return name;
    }

    /**
     * @param name название класса по плотности
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the min - минимальный диапазон плотности
     */
    public Double getMin() {
        return min;
    }

    /**
     * @param min минимальный диапазон плотности
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     * @return the max - максимальный диапазон для класса плотности
     */
    public Double getMax() {
        return max;
    }

    /**
     * @param max the max - максимальный диапазон для класса плотности
     */
    public void setMax(Double max) {
        this.max = max;
    }

// </editor-fold>

    /**
     * Конструктор класса по-умолчанию.
     */
    public RequiredDensity(){
        this.name = "";
        this.min = 0d;
        this.max = 0d;
    }
    
    /**
     * Конструктор класса.
     * @param name - название марки по плотности.
     * @param min - минимально допустимая марка.
     * @param max  - максимально допустимая марка.
     */
    public RequiredDensity(String name, Double min, Double max){
        this.name = name;
        this.min = min;
        this.max= max;
    }
    
    /**
     * Определяет: соответсвует ли плотность заданной марке по плотности.
     * @param density - плотность для проверки.
     * @return 
     */
    public boolean isSuitable(Double density){
        return (density >= min && density <= max);
    }
}
