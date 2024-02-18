package lab5;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;;
import org.uncommons.watchmaker.framework.termination.Stagnation;


import lab5.Factory;
import lab5.FitnessFunction;
import lab5.Mutation;
import lab5.Solution;

import org.uncommons.maths.number.NumberGenerator;

import java.util.ArrayList;
import java.util.Random;

public class Alg {

    double best_fit;
    int best_epoch;
    Solution best_solution;

    public static void main(String[] args) {

        int populationSize = 1000; // size of population
        int mutations = 10; // number of mutations
        int dim = 128;
        Alg alg = new Alg();

        alg.run(
            dim,
            populationSize,
            mutations
        );
    }

    public Alg() {
        best_epoch = 0;
        best_fit = Double.POSITIVE_INFINITY;
    }

    public void run(int dim,
                    int populationSize,
                    int mutations) {


        Random random = new Random(); // random

        FitnessEvaluator<Solution> evaluator = new FitnessFunction(); // Fitness function

        CandidateFactory<Solution> factory = new Factory(dim); // generation of solutions

        ArrayList<EvolutionaryOperator<Solution>> operators = new ArrayList<EvolutionaryOperator<Solution>>();
        operators.add(new Crossover()); // Crossover
        operators.add(new Mutation(mutations));

        EvolutionPipeline<Solution> pipeline = new EvolutionPipeline<Solution>(operators);

        // Selection operator
        SelectionStrategy<Object> selection = new RankSelection();

        EvolutionEngine<Solution> algorithm = new GenerationalEvolutionEngine<Solution>(
                    factory, pipeline, evaluator, selection, random);

        algorithm.addEvolutionObserver(new EvolutionObserver() {
            public void populationUpdate(PopulationData populationData) {
                double bestFit = populationData.getBestCandidateFitness();
                System.out.println("Generation " + populationData.getGenerationNumber() + ": " + bestFit);
                Solution best = (Solution)populationData.getBestCandidate();
                if (bestFit < best_fit) {
                    best_fit = bestFit;
                    best_epoch = populationData.getGenerationNumber();
                    best_solution = best;
                }
                // System.out.println("\tBest solution = " + best.toString());
            }
        });

        TerminationCondition terminate = new Stagnation(1000, false);
        algorithm.evolve(populationSize, 1, terminate);
    }
}