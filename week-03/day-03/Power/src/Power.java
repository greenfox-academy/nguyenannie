public class Power {
    public static void main(String[] args) {
        System.out.println(power(3,10));
    }

    public static int power(int x, int y){
        if(x == 0) {
            return 0;
        } else if(x == 1 || y == 0){
            return 1;
        } else {
            return power(x,y - 1) * x;
        }
    }
}