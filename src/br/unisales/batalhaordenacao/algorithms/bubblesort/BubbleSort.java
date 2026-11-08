package br.unisales.batalhaordenacao.algorithms.bubblesort;

import br.unisales.batalhaordenacao.core.SortingAlgorithm;

public class BubbleSort implements SortingAlgorithm {
    long comparacoes = 0;
    long trocas = 0;
    long inicio = 0;
    long fim = 0;

    @Override
    public void sort(int[] vetor) {
        inicio = System.nanoTime();

        // Repete a varredura do vetor uma vez para cada elemento.
        for (int i = 0; i < vetor.length; i++) {
            // Percorre todos os pares de vizinhos do vetor.
            for (int j = 0; j < vetor.length - 1; j++) {
                // Compara o valor da posição j com o da posição seguinte.
                if (vetor[j] > vetor[j + 1]) {
                    // Guarda o valor da posição j para não perdê-lo.
                    int valorAnterior = vetor[j];
                    // Copia o valor da posição seguinte para a posição j.
                    vetor[j] = vetor[j + 1];
                    // Coloca o valor guardado na posição seguinte.
                    vetor[j + 1] = valorAnterior;
                    this.trocas++;
                }
                this.comparacoes++;
            }
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
    public boolean isSorted(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            if (vetor[i - 1] > vetor[i]) {
                return false;
            }
        }
        return true;
    }
}
