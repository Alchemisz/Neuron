import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Neuron {

    private int n, K, m;
    private double learningRate;
    private InputDataClass[] learningDataSet;
    private WeightVector startWeightVector;

    private List<WeightVector> weightVectors;
    private List<Point[]> points;

    public Neuron(int n, int K, int m, double learningRate, InputDataClass[] learningDataSet, WeightVector startWeightVector) {
        this.n = n;
        this.K = K;
        this.m = m;
        this.learningRate = learningRate;
        this.learningDataSet = learningDataSet;
        this.startWeightVector = startWeightVector;

        this.weightVectors = new LinkedList<WeightVector>();
        this.weightVectors.add(startWeightVector);

        this.points = new LinkedList<Point[]>();


    }

    public void learningProcedure(){
        int lpd = 0;
        int k = 1;
        int t = 0;

        Main.stringBuffer += "<p>L = { ([x<sub>1</sub>, x<sub>2</sub>]<sup>T</sup>, d)<sup>(k)</sup>" +
                "}<sup>K</sup><sub>k=1</sub> = " +
                "{(["+this.learningDataSet[0].getX(1)+", "+this.learningDataSet[0].getX(2)+"], "+this.learningDataSet[0].getD()+")<sup>(1)</sup> " +
                ", (["+this.learningDataSet[1].getX(1)+", "+this.learningDataSet[1].getX(2)+"], "+this.learningDataSet[1].getD()+")<sup>(2)</sup> };" +
                "   n = "+this.n+", m = "+ this.m+", K = "+this.K+" </p><hr>";

        Main.stringBuffer +="<p>START: t = "+ t +",  <u>w</u><sup>(0)</sup> = [w<sub>0</sub><sup>(0)</sup>," +
                "w<sub>2</sub><sup>(0)</sup>,w<sub>2</sub><sup>(0)</sup>]<sup>T</sup> = " +
                ""+this.weightVectors.get(0).toString()+"<sup>T</sup>, <span>n</span> = "+this.learningRate+"</p><hr>";

        Main.stringBuffer += "<p>KROK 1: t = "+t+", k = "+k+", lpd = " +lpd+"</p>";

        Point[] firstPoints = Neuron.getDecisionPoints(weightVectors.get(0), t, lpd);
        Main.stringBuffer += "<hr>";


        System.out.println("t: " + t + " " + Arrays.toString(firstPoints));
        points.add(firstPoints);

        InputDataClass tmp;
        int realNeuronAnswer;

        while (lpd != K) {
            tmp = learningDataSet[k - 1];

            Main.stringBuffer += "<p>KROK 2(" + t + "): <u>x</u><sup>(" + k + ")</sup> = [1.0, x<sub>1</sub><sup>(" + k + ")</sup> , x<sub>2</sub><sup>(" + k + ")</sup>] = " + tmp.toString() + "<sup>T</sup></p>";


            realNeuronAnswer = Neuron.getRealNeuronAnswer(weightVectors.get(t), tmp, k, t);

            if (realNeuronAnswer == tmp.getD()) {
                lpd++;
                Main.stringBuffer += "<p>lpd := lpd + 1 = " + lpd + "</p>";
            } else {
                Main.stringBuffer += "<p>lpd = 0</p>";
                lpd = 0;
            }


            WeightVector newWeightVector = Neuron.calculateNewWeightVector(weightVectors.get(t),
                    realNeuronAnswer, learningRate, tmp, t, k, lpd, this.K);

            weightVectors.add(newWeightVector);

            Point[] newPoints = Neuron.getDecisionPoints(newWeightVector, (lpd != 0) ? t : t + 1, lpd);
            points.add(newPoints);
            System.out.println("t: " + t + " " + Arrays.toString(newPoints) + "     " + newWeightVector.toString());


            if (lpd != K){
                t++;

                Main.stringBuffer += "<p>KROK 5(" + (t - 1) + "): t := t + 1 = " + (t - 1) + " + " + 1 + " = " + t + ", ";

                k++;

                Main.stringBuffer += " k := k + 1 = " + (k - 1) + " + " + 1 + " = " + k + "";

                if (k > K) {
                    Main.stringBuffer += " > K = " + K + " --> k := 1";
                    k = 1;
                }

                Main.stringBuffer += ", przejscie do KROK 2("+t+")</p>";
            }


            if(lpd == K){
                Main.stringBuffer += "<p><p>KROK 5(" + (t - 1) + "): lpd = " + lpd + " = K (warunek spelniony), STOP.</p>";
            }



            Main.stringBuffer += "<hr>";
        }


    }


    public static int getRealNeuronAnswer(WeightVector weightVector, InputDataClass inputDataClass, int k, int t){
        double result = RoundDouble.round((weightVector.getVector(0) * inputDataClass.getX(0) + weightVector.getVector(1) * inputDataClass.getX(1) +
                        weightVector.getVector(2) * inputDataClass.getX(2)), 2);
        int value = ((result >= 0) ? 1 : 0);
        Main.stringBuffer += "<p>KROK 3("+t+"): y<sup>("+k+")</sup> = f<sub>skok</sub>(<u>w</u><sup>("+t+")<sup>T</sup></sup><u>x</u><sup>("+k+")</sup>)" +
                " =  f<sub>skok</sub>("+weightVector.toString()+ " * <span>"+inputDataClass.toString() +"</span>)" +
                " = f<sub>skok</sub>("+result+") = " + value +" <span>" + ((value == (int)inputDataClass.getD()) ? "=" : "!=") + "</span> d<sup>("+k+")</sup> = "+ (int)inputDataClass.getD() +"</p>";



        if( result >= 0)
            return  1;
        else
            return 0;
    }

    public static Point[] getDecisionPoints(WeightVector v, int t, int lpd){
        Point[] points = new Point[2];

        if(v.getVector(0) != 0 && v.getVector(1) != 0 && v.getVector(2) != 0){

            points[0] = new Point(-1 * (v.getVector(0) / v.getVector(1)), 0);
            points[1] = new Point(0, -1 * (v.getVector(0) / v.getVector(2)));

            Main.stringBuffer += "<p> '1' sa <span>" + (( v.getVector(2) > 0) ? " NAD " : "POD") +"</span> linia!</p>";

        if(lpd == 0) {
            Main.stringBuffer += "<p>x<sub>10</sub><sup>(" + t + ")</sup> = " +
                    "- <span>(w<sub>0</sub><sup>(" + t + ")</sup> / w<sub>1</sub><sup>(" + t + ")</sup>) = " +
                    "- (" + v.getVector(0) + "/" + v.getVector(1) + ")</span> = " + points[0].getX10() + "</p>";
            Main.stringBuffer += "<p>x<sub>20</sub><sup>(" + t + ")</sup> = " +
                    "- <span>(w<sub>0</sub><sup>(" + t + ")</sup> / w<sub>2</sub><sup>(" + t + ")</sup>) = " +
                    "- (" + v.getVector(0) + "/" + v.getVector(2) + ")</span> = " + points[1].getX20() + "</p>";
        }


        }else if(v.getVector(0) != 0 && v.getVector(1) == 0 && v.getVector(2) != 0){

            points[1] = new Point(0, -1 * (v.getVector(0) / v.getVector(2)));

            Main.stringBuffer += "<p> '1' sa <span>" + (( v.getVector(2) > 0) ? " NAD " : "POD") +"</span> linia!</p>";

            if(lpd == 0) {
            Main.stringBuffer += "<p>x<sub>10</sub><sup>("+t+")</sup> = " +
                    "- <span>(w<sub>0</sub><sup>("+t+")</sup> / w<sub>2</sub><sup>("+t+")</sup>) = " +
                    "- ("+v.getVector(0)+"/"+v.getVector(1)+")</span> = " + points[0].getX10() + "</p>";
            }

        }else if(v.getVector(0) != 0 && v.getVector(1) != 0 && v.getVector(2) == 0){

            points[0] = new Point(-1 * (v.getVector(0) / v.getVector(2)), 0);

            Main.stringBuffer += "<p> '1' sa <span>" + (( v.getVector(1) > 0) ? " PO PRAWEJ " : "PO LEWEJ") +"</span> lini!</p>";

            if(lpd == 0) {
            Main.stringBuffer += "<p>x<sub>20</sub><sup>("+t+")</sup> = " +
                    "- <span>(w<sub>0</sub><sup>("+t+")</sup> / w<sub>2</sub><sup>("+t+")</sup>) = " +
                    "- ("+v.getVector(0)+"/"+v.getVector(2)+")</span> = " + points[1].getX20() + "</p>";
            }

        }else if(v.getVector(0) == 0 && v.getVector(1) != 0 && v.getVector(2) != 0){

            Main.stringBuffer += "<p> '1' sa <span>" + (( v.getVector(2) > 0) ? " NAD " : "POD") +"</span> linia!</p>";

            points[0] = new Point(0,0);
            points[1] = new Point(0,0);

        }else if(v.getVector(0) == 0 && v.getVector(1) == 0 && v.getVector(2) != 0){

            Main.stringBuffer += "<p> '1' sa <span>" + (( v.getVector(2) > 0) ? " NAD " : "POD") +"</span> linia!</p>";

            points[0] = new Point(1.5,0); //1.5 = c
            points[1] = new Point(0,0);

        }else if(v.getVector(0) == 0 && v.getVector(1) != 0 && v.getVector(2) == 0){

            Main.stringBuffer += "<p> '1' sa <span>" + (( v.getVector(1) > 0) ? " PO PRAWEJ " : "PO LEWEJ") +"</span> lini!</p>";

            points[0] = new Point(0,0);
            points[1] = new Point(0,1.5); // 1.5 = c

        }

        if(lpd == 0) {
            if (points[0] != null) {
                Main.stringBuffer += "<p>P<sub>1</sub><sup>(" + t + ")</sup> = " + "(" + points[0].getX10() + ", " + points[0].getX20() + ")</p>";
            } else {
                Main.stringBuffer += "<p>P<sub>1</sub><sup>(" + t + ")</sup> nie istnieje</p>";
            }

            if (points[1] != null) {
                Main.stringBuffer += "<p>P<sub>2</sub><sup>(" + t + ")</sup> = " + "(" + points[1].getX10() + ", " + points[1].getX20() + ")</p>";
            } else {
                Main.stringBuffer += "<p>P<sub>2</sub><sup>(" + t + ")</sup> nie istnieje</p>";
            }
        }else{
            if (points[0] != null) {
                Main.stringBuffer += "<p>P<sub>1</sub><sup>(" + (t+1) + ")</sup> = P<sub>1</sub><sup>(" + t + ")</sup> = " + "(" + points[0].getX10() + ", " + points[0].getX20() + ")</p>";
            } else {
                Main.stringBuffer += "<p>P<sub>1</sub><sup>(" + (t+1) + ")</sup> nie istnieje</p>";
            }

            if (points[1] != null) {
                Main.stringBuffer += "<p>P<sub>2</sub><sup>(" + (t+1) + ")</sup> = P<sub>2</sub><sup>(" + t + ")</sup> = " + "(" + points[1].getX10() + ", " + points[1].getX20() + ")</p>";
            } else {
                Main.stringBuffer += "<p>P<sub>2</sub><sup>(" + (t+1) + ")</sup> nie istnieje</p>";
            }
        }

        return points;
    }

    public static WeightVector calculateNewWeightVector(WeightVector oldWeightVector, int y, double learningRate, InputDataClass inputDataClass, int t, int k, int lpd, int K){
        WeightVector result = oldWeightVector.addInputDataClass(inputDataClass.mulBy(learningRate * (inputDataClass.getD() - y)));

        if(lpd == 0) {
            Main.stringBuffer += "<p>KROK 4(" + t + "): <u>w</u><sup>(" + (t + 1) + ")</sup> = <u>w</u><sup>(" + t + ") </sup> + <span>n</span>( d<sup>(" + k + ")</sup> - y<sup>(" + k + ")</sup>) <u>w</u><sup>(" + k + ")</sup> " +
                    "= <span>" + oldWeightVector.toString() + "</span> + " + learningRate + "(" + inputDataClass.getD() + " - " + y + ")<span>" + inputDataClass.toString() + " = " + result.toString() + "</span> </p>";
        }else{
            Main.stringBuffer += "<br><p>KROK 4(" + ((lpd == 2) ? (t - 1) : t) + ") : <u>w</u><sup>(" + (t) + ")</sup> = <u>w</u><sup>(" + (t - 1) + ")</sup> = <span>"+oldWeightVector.toString()+"</span></p>";
        }


        return result;
    }

}
