package br.unisales.batalhaordenacao.algorithms.mergesort; // dividir para conquistar

import br.unisales.batalhaordenacao.core.SortingAlgorithm;

public class MergeSort implements SortingAlgorithm {

    // Quantidade de comparações realizadas
    private long comparisons;

    // Quantidade de movimentações dos elementos
    private long swaps;

    // Tempo de execução em nanossegundos
    private long executionTime;

    @Override
    public void sort(int[] array) { // inicia a ordenação e mede o tempo

        // Reinicia os contadores antes de cada ordenação
        comparisons = 0;
        swaps = 0;
        executionTime = 0;

        // Registra o inicio da execução
        long startTime = System.nanoTime();

        // Verifica se o vetor existe e possui elementos
        if (array != null && array.length > 1) {

            // Cria um vetor auxiliar
            int[] auxiliar = new int[array.length];

            // Inicia a divisão e ordenação
            dividir(array, auxiliar, 0, array.length - 1);
        }
        // Calcula o tempo total em nanossegundos
        executionTime = System.nanoTime() - startTime;
    }

    private void dividir(int[] array, int[] auxiliar,
                         int inicio, int fim) { // realiza a divisão recursiva do vetor

        // Verifica se existe mais de um elemento no trecho atual
        if (inicio < fim) {

            // Calcula o meio do vetor
            int meio = inicio + (fim - inicio) / 2;

            // Divide a metade da esquerda
            dividir(array, auxiliar, inicio, meio);

            // Divide a metade da direita
            dividir(array, auxiliar, meio + 1, fim);

            // Intercala as duas metades - reúne os elementos
            intercalar(array, auxiliar, inicio, meio, fim);
        }
    }

    private void intercalar(int[] array, int[] auxiliar,
                            int inicio, int meio, int fim) {

        // Índice que percorre a metade pra esquerda
        int esquerda = inicio;

        // Índice que percorre a metade direta
        int direita = meio + 1;

        // Posição onde o elemento será colocado como auxiliar
        int posicao = inicio;

        // Compara os elementos das duas metades
        while (esquerda <= meio && direita <= fim){
            // Registra a comparação
            comparisons++;

            // Verifica qual o elemento é o menor
            if (array[esquerda] <= array[direita]){
                auxiliar[posicao] = array[esquerda];
                esquerda++;

            } else {
                auxiliar[posicao] = array[direita];
                direita++;
            }

            // Avança para a proxima posição do auxiliar
            posicao++;

            // Registra uma movimentação
            swaps++;
        }

        // Copia os elementos restantes da metade esquerda
        while (esquerda <= meio){
            auxiliar[posicao] = array[esquerda];
            esquerda++;
            posicao++;
            swaps++;
        }

        // Copia os elementos restantes da direita
        while (direita <= fim){
            auxiliar[posicao] = array[direita];
            direita++;
            posicao++;
            swaps++;
        }

        // Copia os elementos ordenados para o vetor original
        for (int i = inicio; i <= fim; i++){
            array[i] = auxiliar[i];
            swaps++;
        }
    }

    @Override
    public long getComparisons(){
        // Retorna a quantidade de operações
        return comparisons;
    }

    @Override
    public long getSwaps() {
        // Retorna a quantidade de movimentações
        return swaps;
    }
    
    @Override
    public long getExecutionTime() {
        // Retorna o tempo de execução em nanossegundos
        return executionTime;
    }

    @Override
    public boolean isSorted(int[] array) {

        // Verifica se o vetor existe
        if (array == null) {
            return false;
        }

        // Percorre o vetor verificando a ordem
        for (int i = 1; i < array.length; i++) {

            // Verifica se o elemento anterior é maior
            if (array[i - 1] > array[i]) {
                return false;
            }
        }

        // Se não encontrou elementos fora de ordem
        return true;
    }
}


