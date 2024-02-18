package lab5;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class Crossover extends AbstractCrossover<Solution> {
    protected Crossover() {
        super(1); // Указывает на одноточечный кроссовер
    }

    @Override
    protected List<Solution> mate(Solution parent1, Solution parent2, int numberOfCrossoverPoints, Random rng) {
        ArrayList<Solution> offspring = new ArrayList<>();

        List<Integer> crossoverPoints = IntStream.range(0, parent1.dim).boxed().collect(Collectors.toList());
        Collections.shuffle(crossoverPoints, rng);

        int crossPointOne = crossoverPoints.get(0);
        int crossPointTwo = crossoverPoints.get(1);
        int start = Math.min(crossPointOne, crossPointTwo);
        int end = Math.max(crossPointOne, crossPointTwo);

        HashSet<Integer> includedInFirstChild = new HashSet<>();
        HashSet<Integer> includedInSecondChild = new HashSet<>();

        int[] child1 = new int[parent1.dim];
        int[] child2 = new int[parent2.dim];

        for (int i = start; i < end; i++) {
            child1[i] = parent1.get(i);
            child2[i] = parent2.get(i);
            includedInFirstChild.add(child1[i]);
            includedInSecondChild.add(child2[i]);
        }

        fillRemainingPositions(parent1, parent2, child1, child2, includedInFirstChild, includedInSecondChild, end);

        offspring.add(new Solution(child1));
        offspring.add(new Solution(child2));

        return offspring;
    }

    private void fillRemainingPositions(Solution parent1, Solution parent2, int[] child1, int[] child2, HashSet<Integer> includedInFirst, HashSet<Integer> includedInSecond, int startIndex) {
        int indexForFirst = startIndex;
        int indexForSecond = startIndex;

        for (int i = 0; i < parent1.dim; i++) {
            int index = (i + startIndex) % parent1.dim;
            int fromParent1 = parent1.get(index);
            int fromParent2 = parent2.get(index);

            if (!includedInFirst.contains(fromParent2)) {
                if (indexForFirst < parent1.dim) {
                    child1[indexForFirst % parent1.dim] = fromParent2;
                    indexForFirst++;
                }
                includedInFirst.add(fromParent2);
            }

            if (!includedInSecond.contains(fromParent1)) {
                if (indexForSecond < parent2.dim) {
                    child2[indexForSecond % parent2.dim] = fromParent1;
                    indexForSecond++;
                }
                includedInSecond.add(fromParent1);
            }
        }
    }
}
