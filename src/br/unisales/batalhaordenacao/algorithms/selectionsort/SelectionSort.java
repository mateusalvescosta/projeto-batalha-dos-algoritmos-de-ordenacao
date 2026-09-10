package br.unisales.batalhaordenacao.algorithms.selectionsort;

import br.unisales.batalhaordenacao.core.SortingAlgorithm;

public class SelectionSort implements SortingAlgorithm {

    @Override
    public void sort(int[] array) {

        // Percorre o vetor até a penúltima posição.
        // A última posição não precisa ser verificada, pois já estará ordenada ao final.
        for (int i = 0; i < array.length - 1; i++) {

            // Assume inicialmente que o menor valor está na posição atual.
            int minIndex = i;

            // Percorre o restante do vetor, começando pela posição seguinte.
            for (int j = i + 1; j < array.length; j++) {

                // Compara o valor atual com o menor valor encontrado até o momento.
                if (array[j] < array[minIndex]) {

                    // Se encontrar um valor menor, guarda o índice dessa nova posição.
                    minIndex = j;
                }
            }

            // Guarda temporariamente o valor da posição atual.
            int temp = array[i];

            // Coloca o menor valor encontrado na posição atual.
            array[i] = array[minIndex];

            // Coloca o antigo valor da posição atual onde estava o menor valor.
            array[minIndex] = temp;
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