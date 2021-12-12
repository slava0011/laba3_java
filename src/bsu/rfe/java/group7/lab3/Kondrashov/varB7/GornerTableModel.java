package bsu.rfe.java.group7.lab3.Kondrashov.varB7;

import javax.swing.table.AbstractTableModel;
@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;
    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
    public Double getStep() {
        return step;
    }
    public int getColumnCount() {
// В данной модели два столбца
        return 3;
    }
    public int getRowCount() {
// Вычислить количество точек между началом и концом отрезка
// исходя из шага табулирования
        return new Double(Math.ceil((to-from)/step)).intValue()+1;
    }
    public char getSignX(int row) {
        char sign;
        double x = from + step*row;
        if(x >= 0) sign = '+';
        else sign = '-';
        return sign;
    }
    public Object getValueAt(int row, int col) {
// Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step*row;
        // Схема Горнера
        double result = coefficients[0];
        for (int i = 1; i < coefficients.length; ++i)
            result = result * x + coefficients[i];

        switch(col) {
            // Если запрашивается значение 1-го столбца, то это X
            case 0: {
                return x;
            }
            // Если запрашивается значение 2-го столбца, то это значение
            // многочлена
            case 1: {
                return result;
            }
            // Если запрашивается значение 3-го столбца, то проверяем на палиндром
            case 2: {
                if(SequentialSeries(result))
                    return true;
                else return false;
            }
            default: return 0.0;
        }
    }

    public static Boolean SequentialSeries(double number) {
        Boolean itog = false;
        StringBuffer numberIntStringBuffer = new StringBuffer(String.valueOf(number));
        String numberStrInt = numberIntStringBuffer.toString();
        char[] resultInt = numberStrInt.toCharArray();

        int score = 0;
        for(int i = 0; i < resultInt.length; i++) {
            if((i + 1 <= resultInt.length - 1)){
                if((resultInt[i]==resultInt[i+1] - 1)) score++;
                else score = 0;
            }
            if(score >= 2) itog = true;
        }
        return itog;
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
// Название 1-го столбца
                return "Значение X";
            case 1:
// Название 2-го столбца
                return "Значение многочлена";
// Название 3-го столбца
            case 2:
                return "Целая часть последовательный ряд?";
            default:
                return "";
        }
    }
    public Class<?> getColumnClass(int col) {
        // В третьем столбце находятся значения типа Boolean
        if (col == 2) return Boolean.class;
            // В остальных типа Double
        else return Double.class;
    }
}
