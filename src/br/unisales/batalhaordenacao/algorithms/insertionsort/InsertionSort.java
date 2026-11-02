package insertionsort;

import br.unisales.batalhaordenacao.core.SortingAlgorithm;

public class InsertionSort implements SortingAlgorithm {
    int comparacoes = 0;
    int trocas = 0;
    long inicio = 0;
    long fim = 0;

    @Override
    public void sort(int[] vetor) {
        inicio = System.nanoTime();

        // Percorre o vetor a partir da segunda posição.
        for (int i = 1; i < vetor.length; i++) {

            // Guarda o número atual que será inserido na posição correta.
            int numero = vetor[i];

            // Inicializa a posição anterior à atual.
            int posicaoAnterior = i - 1;

            // Move os elementos do vetor que são maiores que o número para uma posição à frente.
            while (posicaoAnterior >= 0 && vetor[posicaoAnterior] > numero) {
                vetor[posicaoAnterior + 1] = vetor[posicaoAnterior];
                this.comparacoes++;
                this.trocas++;
                posicaoAnterior--;
            }

            // Insere a número na posição correta.
            vetor[posicaoAnterior + 1] = numero;
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
    public boolean isSorted(int[] vetor) {
        for (int i = 1; i < vetor.length; i++) {
            if (vetor[i - 1] > vetor[i]) {
                return false;
            }
        }
        return true;
    }

}
