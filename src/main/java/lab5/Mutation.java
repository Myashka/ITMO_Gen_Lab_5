package lab5;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.maths.number.ConstantGenerator;
import org.uncommons.maths.number.NumberGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mutation implements EvolutionaryOperator<Solution> {
    private final NumberGenerator<Integer> CountGen;

    public Mutation(NumberGenerator<Integer> countGen) {
        CountGen = countGen;
    }

    public Mutation(int mutationCount)
    {
        this(new ConstantGenerator<Integer>(mutationCount));
    }

    public List<Solution> apply(List<Solution> population, Random random) {
        // your implementation:
        List<Solution> result = new ArrayList<Solution>(population.size());
        for (Solution candidate : population) {
            Solution newCandidate = new Solution(candidate);
            int mutationCount = Math.abs(CountGen.nextValue());
            for (int i = 0; i < mutationCount; i++) {
                int fromIndex = random.nextInt(newCandidate.dim);
                int toIndex;
                do {
                    toIndex = random.nextInt(newCandidate.dim);
                } while (toIndex == fromIndex);
                
                newCandidate.swap(fromIndex, toIndex);
            }
            result.add(newCandidate);
        }

        return population;
    }
}