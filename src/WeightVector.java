import java.util.Arrays;

public class WeightVector {

    private double[] vector;

    public WeightVector(int n, double... w) {
        this.vector = new double[n+1];

        for(int i = 0; i < n + 1; i++){
            this.vector[i] = w[i];
        }
    }

    public WeightVector(double[] v) {
        this.vector = new double[v.length];
        for(int i = 0; i < this.vector.length; i++)
            this.vector[i] = v[i];
    }

    public WeightVector addInputDataClass(InputDataClass inputDataClass){
        WeightVector result = new WeightVector(this.getVector());


        for(int i = 0; i < result.vector.length; i++){
            result.vector[i] += inputDataClass.getX(i);
        }
        result.roundThisVectorValues(2);
        return result;
    }

    public void roundThisVectorValues(int presicion){
        for(int i = 0; i < this.vector.length; i++){
            this.vector[i] = RoundDouble.round(this.vector[i], presicion);
        }
    }

    public void setVector(int index ,double vector) {
        this.vector[index] = vector;
    }

    public double[] getVector() {
        return vector;
    }

    public double getVector(int index){
        return this.vector[index];
    }

    @Override
    public String toString() {
        return  "" + Arrays.toString(vector);
    }
}
