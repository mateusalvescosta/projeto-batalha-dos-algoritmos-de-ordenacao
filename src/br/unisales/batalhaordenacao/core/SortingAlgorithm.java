package br.unisales.batalhaordenacao.core;

public interface SortingAlgorithm {

    void sort(int[] array);

    long getComparisons();

    long getSwaps();

    long getExecutionTime();

    boolean isSorted(int[] array);
}