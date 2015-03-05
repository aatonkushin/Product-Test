/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producttest.bll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javafx.collections.ObservableList;
import producttest.model.Result;
import producttest.model.SampleTest;

/**
 * Содержит функции для расчёта параметров испытаний.
 *
 * @author tonkushin
 */
public abstract class Calculation {

    /**
     * 1. Рассчитыает влажность образца.
     *
     * @param dryWeight - масса в сухом состоянии (вместе с массой бюксы) m3.
     * @param bottleWeight - масса бюксы m1.
     * @param wetWeight - масса навески исходная (без маччы бюксы) m2
     * @return
     */
    public static Double calcHumidity(Double dryWeight, Double bottleWeight, Double wetWeight) {
        if (dryWeight != 0 && bottleWeight != 0 && !dryWeight.equals(bottleWeight)) {
            Double numerator, denumerator;

            numerator = wetWeight - (dryWeight - bottleWeight);   //
            denumerator = dryWeight - bottleWeight;         //m сух.
            Double result = (numerator / denumerator) * 100;

            long factor = (long) Math.pow(10, 2);
            result = result * factor;
            long tmp = Math.round(result);
            return (double) tmp / factor;
        }

        return 0d;
    }

    /**
     * 2. Рассчитывает плотность во влажном состоянии.
     *
     * @param width - ширина образца.
     * @param height - высота образца.
     * @param length - длина образца.
     * @param weight - вес образца.
     * @return плотность во влажном состоянии P.
     */
    public static Double calcWetDensity(Double width, Double height, Double length, Double weight) {
        Double retVal = 0d;

        if (width == 0 || height == 0 || length == 0 || weight == 0) {
            return retVal;
        }

        retVal = (weight / (length * width * height)) * 1000;
        retVal = Calculation.round(retVal, 2);

        return retVal;
    }

    /**
     * 3. Рассчитывает плотность в сухом состоянии.
     *
     * @param wetDensity - плотность во влажном состоянии
     * @param humidity - влажность.
     * @return плотность в сухом состоянии.
     */
    public static Double calcDryDensity(Double wetDensity, Double humidity) {
        if (humidity != null && (1 + (0.01d * humidity) != 0d)) {
            Double result = wetDensity / (1 + (0.01d * humidity));
            return Calculation.round(result, 2);
        }

        return 0d;
    }

    /**
     * 4. Рассчитывает марку по плотности.
     *
     * @param density - плотность.
     * @return марку по плотности.
     */
    public static String calcDensityMark(Double density) {
        String retVal = "";

        if (density >= 0d && density <= 350d) {
            retVal = "D300";
        } else if (density >= 351d && density <= 450d) {
            retVal = "D400";
        } else if (density >= 451d && density <= 550d) {
            retVal = "D500";
        } else if (density >= 551d) {
            retVal = "D600";
        }

        return retVal;
    }

    /**
     * 5. Расчитывет текущий коэф. вариации в партии (по плотности).
     *
     * @param results - результаты испытаний.
     * @param avgDryDensity - средняя плотность в сухом состоянии.
     * @return текущий коэф. вариации в партии (по плотности).
     */
    public static Double calcDensityVariation(ObservableList<Result> results, Double avgDryDensity) {
        Double Sm;   //Среднее квадратичное отклонение плотности бетона в партии.
        Double Wm;   //Размах значений плотности в партии.
        Double alpha;//Коэффициент в зависимости от кол-ва испытаний (по таблице 1).
        Double Pmin = 0d;
        Double Pmax = 0d;
        //Таблица 1 для расчёта коэффициента вариации в партии по плотности.
        ArrayList<Double> alphaTab;
        //Создаём таблицу 1 с коэффициентом alpha.
        alphaTab = new ArrayList<>();
        alphaTab.add(1d); //0
        alphaTab.add(1d); //1
        alphaTab.add(1.13d); //2
        alphaTab.add(1.69); //3
        alphaTab.add(2.05); //4
        alphaTab.add(2.23); //5
        alphaTab.add(2.5);  //6
        //1. Проверяем, что число испытаний с результатами по плотности больше 2,
        // но меньше 6.
        int n = 0;
        for (int i = 0; i < results.size(); i++) {
            Double dd = results.get(i).getDryDensity();
            if (dd > 0) {
                n++;
                if (Pmin > dd || Pmin == 0) {
                    Pmin = dd;
                }
                if (Pmax < dd || Pmin == 0) {
                    Pmax = dd;
                }
            }
        }

        if (n < 2 || n > 6) {
            return 0d;
        }

        //2. Рассчитываем Sm: Sm = Wm/alpha;
        Wm = Pmax - Pmin;
        alpha = alphaTab.get(n);
        Sm = Wm / alpha;

        //3. Рассчитываем коэффициент вариации в партии.
        Double Vm = (Sm / avgDryDensity) * 100;

        return Vm;
    }

    /**
     * 6. Рассчитывает предел прочности при сжатии.
     *
     * @param selectedSampleType - тип образца.
     * @param humidity - влажность.
     * @param destructLoad - разрушающая нагрузка.
     * @param length - длина.
     * @param width - ширина.
     * @return предел прочности при сжатии.
     */
    public static Double calcDurability(String selectedSampleType, Double humidity, Double destructLoad, Double length, Double width) {
        if (selectedSampleType != null && humidity != null && humidity > 0) {
            //Рассчитываем предел прочности при сжатии.
            Double Ri; //Предел прочности при сжатии.
            Double Kw = 0d;
            Double W = humidity;
            Double mod = 0d;

            //Таблица А. Соответсвие коэффициента Kw и влажности W.
            Map<Double, Double> KwMap;
            //Создаём таблицу А. Соответсвие коэффициента Kw и влажности W.
            KwMap = new HashMap<>();
            KwMap.put(0d, 0.8d);
            KwMap.put(5d, 0.9d);
            KwMap.put(10d, 1.0d);
            KwMap.put(15d, 1.05d);
            KwMap.put(20d, 1.1d);
            KwMap.put(21d, 1.11d);
            KwMap.put(22d, 1.12d);
            KwMap.put(23d, 1.13d);
            KwMap.put(24d, 1.14d);
            KwMap.put(25d, 1.15d);

            //Таблица Б. Выобр Kt в завмсимости от образца.
            Map<String, Double> KtMap;
            //Создаём таблицу Б. Kt в завмсимости от образца.
            KtMap = new HashMap<>();
            KtMap.put("70x70", 0.9d);
            KtMap.put("100x100", 0.95d);

            Iterator<Map.Entry<Double, Double>> iterator = KwMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Double key = iterator.next().getKey();
                if (mod > Math.abs(W - key) || mod == 0) {
                    mod = Math.abs(W - key);
                    Kw = KwMap.get(key);
                }
            }

            Double Kt = KtMap.get(selectedSampleType);

            Ri = destructLoad * Kw * Kt * 102 / (length * width);

            return Calculation.round(Ri, 2);
        }

        return 0d;
    }

    /**
     * 7. Рассчитывает класс по прочности.
     *
     * @param avgDurability - средняя прочность.
     * @param kt
     * @param results - список результатов испытаний.
     * @return класс по прочности
     */
    public static String calcDurabilityMark(Double avgDurability, Double kt, ObservableList<Result> results) {
        
        if (avgDurability == 0.0d)
            return "";
        
        //Рассчитываем класс по прочности B.
        //Double kt = 1.43d;
        Double B20 = 2.0;
        Double B25 = 2.5;
        Double B35 = 3.5;
        Double B50 = 5.0;
        
        if (kt < 1.08) {
            kt = 1.43;
        }

        if (avgDurability > (kt * B50 * 10.2)) {
            return "B5.0";
        } else if (avgDurability > (kt * B35 * 10.2)) {
            return "B3.5";
        } else if (avgDurability > (kt * B25 * 10.2)) {
            return "B2.5";
        } else if (avgDurability > (kt * B20 * 10.2)) {
            return "B2.0";
        }

        return "";
    }

    /**
     * 8. Рассчитывает текущий коэффициент вариации в партии.
     *
     * @param avgDurability - средняя прочность.
     * @param results - список результатов испытаний.
     * @return текущий коэффициент вариации в партии.
     */
    public static Double calcDurabilityVariation(Double avgDurability, ObservableList<Result> results) {
        //Рассчитываем текущий коэффициент вариации в партии (по прочности) Vm
        Double alpha; //- табличное значение. 2 измерения = 1,13; 3 = 1,69;
        //4 = 2,06; 5 = 2,33; 6 = 2,5.
        Double Rm = avgDurability; //Средний предел прочности при сжатии.

        Double Wm;    //Размах Rmax-Rmin;
        Double Rmax = 0d;    //Максимальный предел прочности.
        Double Rmin = 0d;   //Минимальный предел прочности.
        Double Vm;     //Текущий коэффициент вариации прочности Vm;

        //Если число результатов меньше 2-ух, то ничего не расчитываем.
        if (results.size() < 2) {
            return 0d;
        }

        int n = 0;
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getDurability() == 0) {
                continue;
            }

            if (Rmax < results.get(i).getDurability() || Rmax == 0) {
                Rmax = results.get(i).getDurability();
            }

            if (Rmin > results.get(i).getDurability() || Rmin == 0) {
                Rmin = results.get(i).getDurability();
            }

            n++; //считаем количество результатов с ненулевыми результатами.
        }

        Wm = Rmax - Rmin;

        switch (n) {
            case 2:
                alpha = 1.13;
                break;
            case 3:
                alpha = 1.69;
                break;
            case 4:
                alpha = 2.06;
                break;
            case 5:
                alpha = 2.33;
                break;
            case 6:
                alpha = 2.5;
                break;
            default:
                return 0d;
        }

        Vm = (Wm / (alpha * Rm)) * 100;

        return Calculation.round(Vm, 2);
    }

    /**
     * 9. Расчитывает средний коэффициент вариации плотности бетона (по партиям)
     *
     * @param vars - список с коэффициентами вариации.
     * @return средний коэффициент вариации плотности бетона (по партиям).
     */
    public static Double calcDensityVariationByParts(ArrayList<Double> vars) {
        //Проверяем, что число результатов испытаний более 30.
        if (vars.size() < 30) {
            return 0d;
        }

        Double sum = 0d;
        for (Double d : vars) {
            sum += d;
        }

        //Возвращаем среднее по партиям.
        return sum / vars.size();
    }

    /**
     * 10. Расчитывает коэффициент требуемой плотности Kt;
     *
     * @param avgVariation - средний коэффициент вариации плотности бетона (по
     * партиям)
     * @return коэффициент требуемой плотности Kt
     */
    public static Double calcReqDensityRatio(Double avgVariation) {
        long roundedVal = Math.round(avgVariation);

        switch ((int) roundedVal) {
            case 2:
                return 1.07;
            case 3:
                return 1.06;
            case 4:
                return 1.05;
            case 5:
                return 1.04;
            case 6:
                return 1.02;
            case 7:
                return 1.0;
            case 8:
                return 0.98;
            case 9:
                return 0.97;
            default:
                return 0d;
        }
    }

    /**
     * 11. Расчитывает требуемую плотность бетона.
     *
     * @param Kt - коэффициент требуемой плотности.
     * @param densityMark - марка по плотности.
     * @return требуемую плотность бетона.
     */
    public static Double calcReqDensity(Double Kt, String densityMark) {
        String tmp;
        int dm = 0;

        if (densityMark.length() > 0) {
            tmp = densityMark.substring(1, densityMark.length());
            dm = Integer.parseInt(tmp);
        }

        return Kt * dm;
    }

    /**
     * 12. Рассчитывает средний коэффициент вариации прочности бетона.
     *
     * @param vars - коэффициенты вариации для предыдущих партий.
     * @return средний коэффициент вариации прочности бетона.
     */
    public static Double calcDurabilityVariationByParts(ArrayList<Double> vars) {
        //Проверяем, что число результатов испытаний более 30.
        /*
        if (vars.size() < 30) {
            return 0d;
        }
        */
        
        if (vars.isEmpty()) {
            return 0d;
        }

        Double sum = 0d;
        for (Double d : vars) {
            sum += d;
        }

        //Возвращаем среднее по партиям.
        return sum / vars.size();
    }

    /**
     * 13. Рассчитывает коэффициент требуемой прочности Kt;
     * @param avgVariation - Средний коэффициент вариации прочности бетона (по партиям)
     * @return коэффициент требуемой прочности Kt.
     */
    public static Double calcReqDurabilityRatio(Double avgVariation) {
        long roundedVal = Math.round(avgVariation);

        if (roundedVal == 0) {
            return 1.43d;
        }
        
        if (roundedVal <= 6) {
            return 1.08;
        }

        switch ((int) roundedVal) {
            case 7:
                return 1.09;
            case 8:
                return 1.10;
            case 9:
                return 1.12;
            case 10:
                return 1.13;
            case 11:
                return 1.14;
            case 12:
                return 1.17;
            case 13:
                return 1.22;
            case 14:
                return 1.26;
            case 15:
                return 1.32;
            case 16:
                return 1.37;
            case 17:
                return 1.43;
            case 18:
                return 1.50;
            case 19:
                return 1.57;
            default:
                return 0d;
        }
    }
    
    /**
     * 14. Рассчитывает требуемую прочность бетона.
     * @param Kt - коэффициент требуемой прочности.
     * @param durabilityMark - нормируемый класс прочности (В1.5, В2,0, В2,5,  В3,5, В5,0).
     * @return требуемую прочность бетона
     */
    public static Double calcReqDurability(Double Kt, String durabilityMark){
        String tmp;
        Double dm = 0d;

        if (durabilityMark.length() > 0) {
            tmp = durabilityMark.substring(1, durabilityMark.length());
            dm = Double.parseDouble(tmp);
        }

        return Kt * dm * 10.2;
    }
    //--------------------- Вспомогательные функции --------------------------//

    /**
     * Рассчитывает среднюю плотность в сыром состоянии.
     *
     * @param sampleTests - список образцов испытаний.
     * @return среднюю плотность в сыром состоянии.
     */
    public static Double calcAvgWetDensity(ObservableList<SampleTest> sampleTests) {
        int k = 0; //Количество записей с ненулевой плотностью.
        Double sum = 0d; //Сумма ненулевых плотносетй.
        for (int j = 0; j < sampleTests.size(); j++) {
            if (sampleTests.get(j).getWetDensity() > 0) {
                sum += sampleTests.get(j).getWetDensity();
                k++;
            }
        }

        //Если есть ненулевые плотности, то рассчитываем среднюю.
        if (k > 0) {
            return Calculation.round(sum / k, 2);
        }

        return 0d;
    }

    /**
     * Рассчитывает среднюю плотность в сухом состоянии.
     *
     * @param results - список результатов испытаний образцов.
     * @return средняя плотность в сухом состоянии.
     */
    public static Double calcAvgDryDensity(ObservableList<Result> results) {

        if (results.size() < 1) {
            return 0d;
        }
        //Рассчитываем плотность в сухом состоянии.
        int k = 0;
        Double sum = 0d;
        for (Result result : results) {
            if (result.getDryDensity() > 0) {
                sum += result.getDryDensity();
                k++;
            }
        }
        //Если есть ненулевые плотности, то рассчитываем среднюю.
        if (k > 0) {
            return Calculation.round(sum / k, 2);
        }

        return 0d;
    }

    /**
     * Рассчитывает средний предел прочности при сжатии.
     *
     * @param results - список результатов испытаний образцов.
     * @return средний предел прочности при сжатии.
     */
    public static Double calcAvgDurability(ObservableList<Result> results) {
        if (results.size() < 1) {
            return 0d;
        }

        //Рассчитываем средний предел прочности при сжатии.
        int k = 0;
        Double sum = 0d;
        for (int j = 0; j < results.size(); j++) {
            if (results.get(j).getDurability() > 0) {
                sum += results.get(j).getDurability();
                k++;
            }
        }

        //Если есть ненулевые значения, то рассчитываем среднее.
        if (k > 0) {
            return Calculation.round(sum / k, 2);
        }

        return 0d;
    }

    /**
     * Округляет число до заданного количества знаков.
     *
     * @param value - округляемое число.
     * @param places - кол-во знаков после запятой.
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
