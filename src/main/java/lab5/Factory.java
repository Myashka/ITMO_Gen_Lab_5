package lab5;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

import java.util.Random;

public class Factory extends AbstractCandidateFactory<Solution> {

    private int dimension;

    public Factory() {
        dimension = 10;
    }

    public Factory(int dimension) {
        this.dimension = dimension;
    }

    public Solution generateRandomCandidate(Random random) {
        Solution solution = new Solution(dimension, random);
        //your implementation
        return solution;
    }
}
