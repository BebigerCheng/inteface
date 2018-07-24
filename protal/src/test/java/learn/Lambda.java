package learn;

/**
 *Created by chengliang on 2018/7/20.
 */
public class Lambda {

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
    
    public static void main(String[] args){
        
        
        // 类型声明
        MathOperation addition = (int a, int b) -> a + b;
        System.out.println(addition.operation(3,4));
    }
}
