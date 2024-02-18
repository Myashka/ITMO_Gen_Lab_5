package lab5;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import java.util.List;

public class FitnessFunction implements FitnessEvaluator<Solution> {

    public FitnessFunction() {}

    @Override
    public double getFitness(Solution solution, List<? extends Solution> population) {
        double totalCrosses = 0;  // total crosses count

        for (int currentColumn = 0; currentColumn < solution.dim; currentColumn++) {
            totalCrosses += calculateConflicts(solution, currentColumn);
        }

        return totalCrosses;
    }

    private int calculateConflicts(Solution currentSolution, int column) {
        int conflictCount = 0;
        int currentRow = currentSolution.get(column);

        for (int otherColumn = 0; otherColumn < currentSolution.dim; otherColumn++) {
            if (otherColumn == column) {
                continue; // Skip the same column
            }
            int otherRow = currentSolution.get(otherColumn);
            if (isDiagonalAttack(currentRow, column, otherRow, otherColumn)) {
                conflictCount++;
            }
        }

        return conflictCount;
    }

    private boolean isDiagonalAttack(int row1, int col1, int row2, int col2) {
        return Math.abs(col1 - col2) == Math.abs(row1 - row2);
    }

    public boolean isNatural() {
        return false;
    }
}
