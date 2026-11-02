package br.unisales.batalhaordenacao.core;

public interface SortingAlgorithm {

    void sort(int[] array);

    long getComparisons();

    long getSwaps();

    double getExecutionTime();

    boolean isSorted(int[] array);
}