package by.gpisarev.fibonacci;

public class Runner {
    public static void main(String[] args) {
        int countOfFibonacciNumbers = 5;
        System.out.println("Количество чисел Фибоначчи равно " + countOfFibonacciNumbers);
        for (int i = 0; i < countOfFibonacciNumbers; i++) {
            System.out.print(fibonacci(i) + " ");
        }
    }

    private static int fibonacci(int countOfFibonacciNumbers) {
        if (countOfFibonacciNumbers == 0) {
            return 0;
        }
        if (countOfFibonacciNumbers == 1 || countOfFibonacciNumbers == 2) {
            return 1;
        }
        return fibonacci(countOfFibonacciNumbers - 2) + fibonacci(countOfFibonacciNumbers - 1);
    }
}
