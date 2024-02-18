package lab5;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;

public class ExperimentRunner {

    public static void main(String[] args) {

        int n = 10;
        int populationSize = 10; // Population size
        int[] problemDims = {62, 128};
        int[] mutations = {2, 6, 10};
        String outputFile = "./exps.txt";

        Path filePath = Paths.get(outputFile);
        try {
            Files.createFile(filePath);
        } catch (IOException ex) {
            System.err.println("Error creating file: " + ex.getMessage());
        }
        appendToFile(outputFile, "Experiment Results\n");

        for (int mutation : mutations) {
            for (int problemDim : problemDims) {
                ExperimentData avgData = new ExperimentData();
                avgData.mutations = mutation;
                for (int i = 0; i < n; i++) {
                    ExperimentData data = executeExperiment(populationSize, problemDim, mutation);
                    avgData.epochs += data.epochs;
                    avgData.best_fit += data.best_fit;
                }
                avgData.epochs /= n;
                avgData.best_fit /= n;

                String report = generateReport(problemDim, avgData, populationSize);
                appendToFile(outputFile, report);
            }
        }
    }

    private static ExperimentData executeExperiment(int populationSize, int problemDim, int mutation) {
        Alg algorithm = new Alg();
        algorithm.run(problemDim, populationSize, mutation);
        return new ExperimentData(algorithm.best_epoch, algorithm.best_fit, algorithm.best_solution);
    }

    private static String generateReport(int challengeSize, ExperimentData data, int populationSize) {
        return String.format("| %d | %d | %d | %.2f | %.2f\n", challengeSize, populationSize, data.mutations, data.epochs, data.best_fit);
    }

    private static void appendToFile(String filePath, String content) {
        try {
            Files.writeString(Paths.get(filePath), content, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.err.println("Error writing to file: " + ex.getMessage());
        }
    }
}

class ExperimentData {
    double epochs;
    double best_fit;
    Solution best_solution;
    int mutations;

    ExperimentData() {
        epochs = 0;
        best_fit = 0;
    }

    ExperimentData(double epochs, double best_fit, Solution best_solution) {
        this(epochs, best_fit, best_solution, 0);
    }

    ExperimentData(double ep, double bf, Solution solution, int mut) {
        epochs = ep;
        best_fit = bf;
        best_solution = solution;
        mutations = mut;
    }
}
