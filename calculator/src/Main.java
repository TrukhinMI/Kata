import exception.DivideByZero;
import exception.FormatNotSatisfactory;
import exception.NotMathematicalOperation;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static RomanNumeral[] romanNumerals = RomanNumeral.values();
    static String[] arraySing = {"+", "-", "*", "/"};
    static String sing = null;
    static boolean isRoman = false;

    public static void main(String[] args) throws FormatNotSatisfactory, NotMathematicalOperation, DivideByZero, IOException {
        System.out.println("Input:");
        String value = cals(input());
        System.out.print("Output : \n" + value);
    }
    public static String cals(String input) throws FormatNotSatisfactory, NotMathematicalOperation, DivideByZero, IOException {
        int[] arrayNumber = verificationOfCorrectness(input);
        int resultInt = operation(arrayNumber[0], arrayNumber[1], sing);
        String resultStr = String.valueOf(resultInt);
        if (isRoman == true) {
            resultStr = RomanNumeral.values()[resultInt - 1].name();
        }
        return String.valueOf(resultStr);
    }

    static int[] verificationOfCorrectness(String expression) throws NotMathematicalOperation, FormatNotSatisfactory, IOException, DivideByZero {
        String[] arrayValue = expression.split(" ");
        int[] arrayNumber = new int[2];
        if (arrayValue.length < 3)
            throw new NotMathematicalOperation("строка не является математической операцией (Пример 1 + 3 или IV / II)");
        else if (arrayValue.length > 3)
            throw new FormatNotSatisfactory("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) (Пример 1 + 3 или IV / II)");
        else if (comparisonSing(arrayValue[1])) {
            if (isNumber(arrayValue[0]) ^ isNumber(arrayValue[2])) {
                throw new FormatNotSatisfactory("используются одновременно разные системы");
            } else if (isNumber(arrayValue[0]) && isNumber(arrayValue[2])) {
                arrayNumber[0] = Integer.parseInt(arrayValue[0]);
                arrayNumber[1] = Integer.parseInt(arrayValue[2]);
                if (!rangeOfTen(arrayNumber[0]) || !rangeOfTen(arrayNumber[1])) {
                    throw new IOException("Введеные значения не попали в диапазон");
                }
                sing = arrayValue[1];
            } else if (!isNumber(arrayValue[0]) && !isNumber(arrayValue[2])) {
                arrayNumber[0] = parsing(arrayValue[0]);
                arrayNumber[1] = parsing(arrayValue[2]);
                if (!rangeOfTen(arrayNumber[0]) || !rangeOfTen(arrayNumber[1])) {
                    throw new IOException("Введеные значения не попали в диапазон");
                }
                if (arrayNumber[0] <= arrayNumber[1] && arrayValue[1].equals("-")) {
                    throw new DivideByZero("В римской системе нет отрицательных чисел или 0");
                }
                sing = arrayValue[1];
                isRoman = true;
            }
        } else throw new ArithmeticException("Калькулятор работает только со знаками \"+\" \"-\" \"/\" \"*\"");

        return arrayNumber;
    }

    //Ввод строки
    static String input() {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine().trim();
        scanner.close();
        return expression;
    }

    static boolean rangeOfTen(int value) {
        for (int i = 1; i <= 10; i++) {
            if (i == value)
                return true;
        }
        return false;
    }

    static boolean isNumber(String valueString) {
        boolean value = false;
        try {
            Integer.parseInt(valueString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Есть ли знак в выражении
    static boolean comparisonSing(String valueSing) {
        boolean value = false;
        for (String sing : arraySing) {
            if (sing.equals(valueSing))
                value = true;
        }
        return value;
    }


    //возвращает индекс Римского числа
    static int parsing(String value) {
        int index = 1;
        for (RomanNumeral rn : romanNumerals) {
            if (rn.name().equals(value))
                break;
            else index++;
        }
        return index;
    }

    static int operation(int numberOne, int numberTwo, String sing) throws DivideByZero {
        int result = 0;
        switch (sing) {
            case "+":
                result = numberOne + numberTwo;
                break;
            case "-":
                result = numberOne - numberTwo;
                break;
            case "*":
                result = numberOne * numberTwo;
                break;
            case "/":
                if (numberTwo == 0) {
                    throw new DivideByZero("На 0 делить нельзя");
                }
                result = numberOne / numberTwo;
        }
        return result;
    }
}
