package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    private static int[] positions = {6, 7, 12, 17, 22};
    private static List<Double> vectorS = fillList(positions);
    private static List<Double> vectorX = calculateVectorX();
    private static List<List<Double>> vectorC = calculateVectorC(vectorS);
    private static List<List<Double>> vectorW = calcualteVectorW(vectorC);

    public static void main(String[] args) {
        printVector(vectorS);
        for(int i=0; i < 5; i++) {
            vectorX = resultMethod(vectorW, vectorC, vectorX);
            printVector(vectorX);
        }
    }

    private static List<Double> fillList(int[] positions) {
        List<Double> temp = new ArrayList<>();
        int pos = 0;
        for (int i=0; i < 25; i++) {
            if(positions[pos] == i) {
                if(pos < positions.length-1)
                    pos++;
                temp.add(1.0);
            } else
                temp.add(0.0);
        }
        return temp;
    }

    private static double calulcateTheta(List<List<Double>> vector, int i) {
        double sum = 0;
        for(int j=0; j < 25; j++) {
            sum = sum + vector.get(i).get(j);
        }
        return sum;
    }

    private static List<List<Double>> calculateVectorC(List<Double> vector) {
        List<List<Double>> result = new ArrayList<>();
        List<Double> temporaryVector = new ArrayList<>();

        for(int i=0; i < 25; i++) {
            temporaryVector.clear();
            for(int j=0; j < 25; j++) {
                if (j != i)
                    temporaryVector.add((vector.get(i) - 0.5) * (vector.get(j) - 0.5));
                else
                    temporaryVector.add(0.0);
            }
            result.add(temporaryVector);
        }
        return result;
    }

    private static void printVector(List<Double> vector) {
        for (int i=0; i < 25; i++) {
            if(vector.get(i) == 0.0) {
                System.out.print(" - ");
            } else
                System.out.print(" * ");

            if (i % 5 == 4)
                System.out.println();
        }
        System.out.println();
    }

    private static List<List<Double>> calcualteVectorW(List<List<Double>> vectorC) {
        List<List<Double>> vectorW = new ArrayList<>();
        List<Double> temporaryVectorW = new ArrayList<>();;
        for(int i=0; i < 25; i++) {
            temporaryVectorW.clear();
            for(int j=0; j < 25; j++)
                temporaryVectorW.add(2*vectorC.get(i).get(j));
            vectorW.add(temporaryVectorW);
        }
        return vectorW;
    }

    private static List<Double> calculateVectorU(List<List<Double>> vectorW, List<List<Double>> vectorC, List<Double> vectorX) {
        List<Double> vectorU = new ArrayList<>();
        for(int i=0; i < 25; i++) {
            double sum = 0;
            for(int j=0; j < 25; j++) {
                sum = sum + (vectorW.get(i).get(j) * vectorX.get(j));
            }
            sum = sum - calulcateTheta(vectorC, i);
            vectorU.add(sum);
        }
        return vectorU;
    }

    private static List<Double> calculateVectorX() {
        List<Double> vectorX = new ArrayList<>();
        for(int i=0; i < 25 ; i++) {
            vectorX.add(randomize());
        }
        return vectorX;
    }

    private static double randomize(){
        return ((new Random().nextInt(2) % 2) == 0) ? 0.0 : 1.0;
    }

    private static List<Double> resultMethod(List<List<Double>> vectorW, List<List<Double>> vectorC, List<Double> vectorX) {
        List<Double> vectorU = calculateVectorU(vectorW, vectorC, vectorX);
        List<Double> finalVector = new ArrayList<>();
        for (int i=0; i < 25; i++) {
            if(vectorU.get(i) > 0)
                finalVector.add(1.0);
            if(vectorU.get(i) == 0)
                finalVector.add(vectorX.get(i));
            if(vectorU.get(i) < 0)
                finalVector.add(0.0);
        }
        return finalVector;
    }

}
