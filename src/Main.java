import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static String stringBuffer;

    public static void main(String[] args) {
        stringBuffer = "<html><head><style>p{font-size: 26px;} span{color:red;}</style></head><body>";
        FileWriter file = null;

        try {
            file = new FileWriter("rozwiazanie.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        double learningRate = 0.1;
        int n = 2, m = 1, K = 2;

//        /Przyklad 1

//        InputDataClass inputDataClass1 = new InputDataClass(1, 2, 0.1, 0.9);
//        System.out.println(inputDataClass1);
//        InputDataClass inputDataClass2 = new InputDataClass(0, 2, 1.0, 0.1);
//        System.out.println(inputDataClass2);
//
//        InputDataClass[] learningDataSet = new InputDataClass[]{inputDataClass1, inputDataClass2};
//
//        WeightVector weightVector = new WeightVector(2, 0.1, -0.1, -0.2);
//        System.out.println(weightVector);
//        Neuron neuron = new Neuron(n, K, m, learningRate, learningDataSet, weightVector);
//
//
//        neuron.learningProcedure();

        ///Przyklad 2

        InputDataClass inputDataClass3 = new InputDataClass(1, 2, 0.9, 0.1);
        InputDataClass inputDataClass4 = new InputDataClass(0, 2, 0.1, 1.0);
        WeightVector weightVector2 = new WeightVector(2, -0.1, -0.1, -0.2);

        System.out.println(weightVector2);
        InputDataClass[] learningDataSet2 = new InputDataClass[]{inputDataClass3, inputDataClass4};

        Neuron neuron2 = new Neuron(n,K,m, learningRate, learningDataSet2, weightVector2);

        neuron2.learningProcedure();


        //Przyklad 3

//        WeightVector weightVector4 = new WeightVector(2, 0.0, -0.2, -0.1);
//        InputDataClass inputDataClass7 = new InputDataClass(1, 2,0.9, 0.3);
//        InputDataClass inputDataClass8 = new InputDataClass(0, 2,0.1, 0.5);
//        InputDataClass[] learningDataSet3 = new InputDataClass[]{inputDataClass7, inputDataClass8};
//
//        Neuron neuron3 =new Neuron(n,K,m, 0.3, learningDataSet3, weightVector4);
//
//        neuron3.learningProcedure();


//        WeightVector weightVector10 = new WeightVector(2, 0.1, -0.1, -0.2);
//        InputDataClass inputDataClas12 = new InputDataClass(1, 2, 0.1, 0.9);
//        InputDataClass inputDataClass15 = new InputDataClass(0, 2, 1.0, 0.1);
//
//        InputDataClass[] learningDataSet10 = new InputDataClass[]{inputDataClas12, inputDataClass15};
//
//        Neuron neuron4 = new Neuron(n,K,m, 0.2, learningDataSet10, weightVector10);
//
//        neuron4.learningProcedure();

//        InputDataClass inputDataClass3 = new InputDataClass(1, 2, 0.1, 0.9);
//        InputDataClass inputDataClass4 = new InputDataClass(0, 2, 1.0, 0.1);
//        WeightVector weightVector2 = new WeightVector(2, 0.1, -0.1, -0.2);
//
//        System.out.println(weightVector2);
//        InputDataClass[] learningDataSet2 = new InputDataClass[]{inputDataClass3, inputDataClass4};
//
//        Neuron neuron2 = new Neuron(n,K,m, 0.3, learningDataSet2, weightVector2);
//
//        neuron2.learningProcedure();




        stringBuffer += "</body><html>";
        try {
            file.write(stringBuffer);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
