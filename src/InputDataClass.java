import java.util.Arrays;

public class InputDataClass {

    double[] x;
    double d;

    public InputDataClass(int d, int n, double... x) {
        this.d = d;
        this.x = new double[n+1];

        this.x[0] = 1.0; //Bias
        for(int i = 1; i < n+1; i++)
            this.x[i] = x[i-1];

    }

    public double getX(int index) {
        return this.x[index];
    }

    public double[] getX() {
        return x;
    }

    public InputDataClass(InputDataClass inputDataClass) {
        this.x = new double[inputDataClass.getX().length];

        for(int i = 0; i < x.length; i++){
            this.x[i] = inputDataClass.getX(i);
        }
//        this.x = inputDataClass.getX();
        this.d = inputDataClass.getD();
    }

    public double getD() {
        return d;
    }

    public void setX(int index, double val) {
        this.x[index] = RoundDouble.round(val, 2);
    }

    public InputDataClass mulBy(double val){
        InputDataClass result = new InputDataClass(this);
        for(int i = 0; i < result.x.length; i++){
            result.setX(i, result.getX(i) * val);
        }
        return result;
    }

    @Override
    public String toString() {
        return "[" + this.getX(0) + ", " + this.getX(1)+ ", " + this.getX(2) + "]";
    }
}
