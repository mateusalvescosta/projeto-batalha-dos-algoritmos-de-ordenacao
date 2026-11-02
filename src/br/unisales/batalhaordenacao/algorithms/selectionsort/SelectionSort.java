package selectionsort;

import br.unisales.batalhaordenacao.core.SortingAlgorithm;

public class SelectionSort implements SortingAlgorithm {
    int comparacoes = 0;
    int trocas = 0;
    long inicio = 0;
    long fim = 0;

    @Override
    public void sort(int[] array) {
        inicio = System.nanoTime();

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
                this.comparacoes++;
            }

            // Guarda temporariamente o valor da posição atual.
            int temp = array[i];

            // Coloca o menor valor encontrado na posição atual.
            array[i] = array[minIndex];
            this.trocas++;

            // Coloca o antigo valor da posição atual onde estava o menor valor.
            array[minIndex] = temp;
            this.trocas++;
        }

        fim = System.nanoTime();
    }

    @Override
    public long getComparisons() {
        return this.comparacoes;
    }

    @Override
    public long getSwaps() {
        return this.trocas;
    }

    @Override
    public long getExecutionTime() {
        return this.fim - this.inicio;
    }

    @Override
    public boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}