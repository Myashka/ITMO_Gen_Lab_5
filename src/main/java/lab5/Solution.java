package lab5;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {
    // any required fields and methods
    public int dim;
    public int[] values;

    public Solution(int dim) {
        this.dim = dim;
        Random rand = new Random();
        values = generate(rand);
    }

    public Solution(int dim, Random rand) {
        this.dim = dim;
        values = generate(rand);
    }

    public Solution(int[] values) {
        dim = values.length;
        this.values = values.clone();
    }

    public Solution(Solution from) {
        dim = from.dim;
        this.values = from.values.clone();
    }

    public String toString() {
        return Arrays.stream(values)
            .mapToObj(String::valueOf)
            .collect(Collectors.joining(",","[","]"));
    }

    public int get(int i) {
        return values[i];
    }

    public void set(int i, int v) {
        values[i] = v;
    }

    public void swap(int a, int b) {
        int tmp = values[a];
        values[a] = values[b];
        values[b] = tmp;
    }

    public int[] generate(Random rand) {
        List<Integer> valList = IntStream.range(0, dim).boxed().collect(Collectors.toList());
        Collections.shuffle(valList);

        return valList.stream().mapToInt(i->i).toArray();
    }
}