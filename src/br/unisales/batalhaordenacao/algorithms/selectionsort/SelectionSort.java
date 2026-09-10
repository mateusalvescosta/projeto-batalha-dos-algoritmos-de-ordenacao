package br.unisales.batalhaordenacao.algorithms.selectionsort;

import br.unisales.batalhaordenacao.core.SortingAlgorithm;

public class SelectionSort implements SortingAlgorithm {

    @Override
  public void sort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) { // array.length -1 pra desconsiderar o vetor iniciando em 0

            int minIndex = i; //menor valor

            for (int j = i + 1; j < array.length; j++) {

                if (array[j] < array[minIndex]) { //se o menor valor for menor que o vetor encontrado
                    minIndex = j; //menor valor passa a ser o vetor encontrado.
                }
            }

        }
    }

    @Override
    public long getComparisons() {
        return 0;
    }

    @Override
    public long getSwaps() {
        return 0;
    }

    @Override
    public long getExecutionTime() {
        return 0;
    }

    @Override
    public boolean isSorted(int[] array) {
        return false;
    }
}