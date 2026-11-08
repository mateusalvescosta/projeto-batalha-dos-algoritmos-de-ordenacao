package br.unisales.batalhaordenacao.algorithms.quicksort;

import br.unisales.batalhaordenacao.core.SortingAlgorithm;

//Feito por Juan Pablo Rocha Hempel
public class QuickSort implements SortingAlgorithm {
    // Contadores exigidos pelo projeto (zerados a cada chamada de sort)
    private long comparacoes;
    private long trocas;
    private long tempoExecucao;

    /*
     * Metodo principal da interface: ordena o vetor de forma crescente.
     * Zera os contadores, mede o tempo e chama o quickSort recursivo.
     */
    @Override
    public void sort(int[] array) {
        comparacoes = 0;
        trocas = 0;
        long inicio = System.nanoTime();
        quickSort(array, 0, array.length - 1); // ordena do primeiro ao último índice
        tempoExecucao = System.nanoTime() - inicio;
    }

    /*
     * Ideia do Quick Sort (divisão e conquista):
     * 1. Escolhe um pivô e reorganiza o vetor: menores à esquerda, maiores à direita.
     *    Depois disso, o pivô já está na posição FINAL dele.
     * 2. Repete o processo para o lado esquerdo e para o lado direito.
     *
     * Aqui, 'inicio' e 'fim' delimitam o pedaço do vetor que está sendo ordenado.
     */
    private void quickSort(int[] a, int inicio, int fim) {
        // Se o pedaço tem 0 ou 1 elemento, já está ordenado (condição de parada)
        while (inicio < fim) {
            // p = posição final do pivô; tudo antes de p é <= pivô, tudo depois é > pivô
            int p = particionar(a, inicio, fim);

            // Otimização: faz a chamada recursiva só no lado MENOR
            // e continua o while no lado maior (sem criar outra chamada).
            // Resultado: a pilha de recursão fica O(log n), mesmo no pior caso,
            // evitando StackOverflow.
            if (p - inicio < fim - p) {
                quickSort(a, inicio, p - 1); // lado esquerdo (menor)
                inicio = p + 1;              // o while segue no lado direito
            } else {
                quickSort(a, p + 1, fim);    // lado direito (menor)
                fim = p - 1;                 // o while segue no lado esquerdo
            }
        }
    }

    /*
     * Partição (esquema de Lomuto): é aqui que o trabalho real acontece.
     * Retorna a posição final do pivô.
     */
    private int particionar(int[] a, int inicio, int fim) {
        // --- PASSO 1: escolher o pivô com mediana de três ---
        // Pega 3 elementos (primeiro, do meio e último) e usa o valor do MEIO
        // entre eles como pivô. Isso evita o pior caso O(n²) em vetores já
        // ordenados ou inversos, que são 2 dos 3 tipos de dados do projeto.
        int meio = inicio + (fim - inicio) / 2;
        if (menor(a[meio], a[inicio])) trocar(a, meio, inicio);
        if (menor(a[fim], a[inicio])) trocar(a, fim, inicio);
        if (menor(a[fim], a[meio])) trocar(a, fim, meio);
        // Depois dessas 3 linhas: a[inicio] <= a[meio] <= a[fim]
        // Então a[meio] é a mediana. Joga ela para o fim, para usar como pivô.
        trocar(a, meio, fim);

        // --- PASSO 2: particionar ---
        int pivo = a[fim];
        // 'i' marca o fim da região dos "menores ou iguais ao pivô"
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            // Se o elemento atual é <= pivô, ele pertence à região dos menores:
            // expande a região (i++) e traz o elemento pra dentro dela (troca).
            if (menorOuIgual(a[j], pivo)) {
                i++;
                trocar(a, i, j);
            }
        }
        // Coloca o pivô entre as duas regiões (menores à esquerda, maiores à direita)
        trocar(a, i + 1, fim);
        return i + 1; // posição final do pivô
    }

    // As três funções abaixo existem só para CONTAR operações.
    // Toda comparação e toda troca do algoritmo passam por elas.

    // Compara x < y e conta 1 comparação
    private boolean menor(int x, int y) {
        comparacoes++;
        return x < y;
    }

    // Compara x <= y e conta 1 comparação
    private boolean menorOuIgual(int x, int y) {
        comparacoes++;
        return x <= y;
    }

    // Troca dois elementos de lugar e conta 1 troca
    private void trocar(int[] a, int i, int j) {
        trocas++;
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Getters exigidos pela interface (o Main do grupo usa para montar o CSV)
    @Override
    public long getComparisons() {
        return comparacoes;
    }

    @Override
    public long getSwaps() {
        return trocas;
    }

    @Override
    public long getExecutionTime() {
        return tempoExecucao;
    }

    // Validação exigida pelo projeto: percorre o vetor e confere se está crescente
    @Override
    public boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) return false;
        }
        return true;
    }
}