/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.model;

/**
 * Класс, представляющий тип образца.
 * @author tonkushin
 */
public class SampleType {
    private String name;
    private int width;
    private int length;
    private int height;

    /**
     * @return название типа образца
     */
    public String getName() {
        return name;
    }

    /**
     * @param name название типа образца
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ширина
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width ширина
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return длина
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length длина
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return высота
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height высота
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    public SampleType(){}
    
    /**
     * Конструктор класса.
     * @param name - название типа образца
     * @param dim - размер (сразу задаёт длину, ширину, высоту).
     */
    public SampleType(String name, int dim){
        this.name = name;
        this.width = this.height = this.length = dim;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
}
