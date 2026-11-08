package br.unisales.batalhaordenacao.algorithms;

import br.unisales.batalhaordenacao.algorithms.mergesort.MergeSort;
import br.unisales.batalhaordenacao.algorithms.quicksort.QuickSort;
import br.unisales.batalhaordenacao.algorithms.bubblesort.BubbleSort;
import br.unisales.batalhaordenacao.algorithms.insertionsort.InsertionSort;
import br.unisales.batalhaordenacao.algorithms.selectionsort.SelectionSort;
import br.unisales.batalhaordenacao.core.SortingAlgorithm;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Random;

/*
 * Programa principal do projeto (item 4.2 do PDF).
 *
 * O que ele faz:
 *  1. Gera vetores de 3 tipos (aleatório, ordenado, inverso) para cada tamanho.
 *  2. Roda os 5 algoritmos em cópias do MESMO vetor.
 *  3. Cada algoritmo roda 5 vezes; a 1ª (warm-up) é descartada e tira-se a média das 4 últimas.
 *  4. Os algoritmos O(n²) só rodam até n = 50.000.
 *  5. Valida cada resultado (isSorted + comparação com Arrays.sort).
 *  6. Salva tudo em um CSV (uma linha por algoritmo / tipo / tamanho).
 *
 * Para rodar: java br.unisales.batalhaordenacao.algorithms.Main [arquivo.csv]
 * Se não passar o nome do arquivo, salva em "resultados.csv".
 */
public class Main {

    // Tamanhos pedidos no PDF
    static final int[] TAMANHOS = {100, 1000, 5000, 10000, 50000, 100000};

    // Os algoritmos O(n²) só rodam até este tamanho
    static final int LIMITE_QUADRATICO = 50000;

    // 5 execuções: a primeira é descartada (warm-up) e as outras 4 entram na média
    static final int EXECUCOES = 5;

    // Os 3 tipos de dados pedidos no PDF
    static final String[] TIPOS = {"aleatorio", "ordenado", "inverso"};

    // Algoritmos (a ordem aqui precisa bater com criarAlgoritmo e QUADRATICO)
    static final String[] NOMES = {"BubbleSort", "InsertionSort", "SelectionSort", "MergeSort", "QuickSort"};

    // true = algoritmo O(n²)
    static final boolean[] QUADRATICO = {true, true, true, false, false};

    public static void main(String[] args) {
        String arquivoCsv = "resultados.csv";
        if (args.length > 0) {
            arquivoCsv = args[0];
        }

        // Semente fixa: toda vez que rodar, gera exatamente os mesmos vetores
        Random aleatorio = new Random(42);

        int totalErros = 0;
        long inicioGeral = System.currentTimeMillis();

        try (PrintWriter csv = new PrintWriter(arquivoCsv)) {
            csv.println("algoritmo,tipo,n,tempo_ns,comparacoes,trocas,memoria_bytes");

            for (String tipo : TIPOS) {
                for (int n : TAMANHOS) {

                    // O vetor base é gerado UMA vez e todos os algoritmos usam cópias dele
                    int[] base = gerarVetor(tipo, n, aleatorio);

                    // Vetor de referência, ordenado com Arrays.sort (dica do PDF)
                    int[] referencia = base.clone();
                    Arrays.sort(referencia);

                    for (int a = 0; a < NOMES.length; a++) {
                        if (QUADRATICO[a] && n > LIMITE_QUADRATICO) {
                            continue; // O(n²) não roda acima do limite
                        }

                        // resultado = {tempo_ns, comparacoes, trocas, memoria_bytes, falhas}
                        long[] r = executarAlgoritmo(a, base, referencia);

                        csv.println(NOMES[a] + "," + tipo + "," + n + ","
                                + r[0] + "," + r[1] + "," + r[2] + "," + r[3]);
                        csv.flush(); // se interromper no meio, o que já rodou fica salvo

                        System.out.printf("%-10s n=%-7d %-14s %10.3f ms%n",
                                tipo, n, NOMES[a], r[0] / 1_000_000.0);

                        // Contador negativo = estouro de int dentro do algoritmo (deve ser long)
                        if (r[1] < 0 || r[2] < 0) {
                            System.out.println("   *** AVISO: contador negativo em " + NOMES[a]
                                    + " (estouro de int: troque por long no algoritmo) ***");
                        }

                        if (r[4] > 0) {
                            totalErros++;
                            System.out.println("   *** ERRO: " + NOMES[a]
                                    + " não ordenou corretamente (" + tipo + ", n=" + n + ") ***");
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Não foi possível criar o arquivo " + arquivoCsv + ": " + e.getMessage());
            return;
        }

        long segundos = (System.currentTimeMillis() - inicioGeral) / 1000;
        System.out.println();
        System.out.println("Concluído em " + segundos + " s. Resultados salvos em " + arquivoCsv);
        System.out.println("Erros de validação: " + totalErros);
    }

    // Roda um algoritmo 5 vezes sobre cópias de 'base' e devolve as médias das 4 últimas
    static long[] executarAlgoritmo(int indice, int[] base, int[] referencia) {
        long somaTempo = 0;
        long somaComparacoes = 0;
        long somaTrocas = 0;
        long somaMemoria = 0;
        long falhas = 0;

        for (int execucao = 1; execucao <= EXECUCOES; execucao++) {
            // Cada execução ordena uma cópia, senão da 2ª em diante o vetor já viria ordenado
            int[] copia = base.clone();

            // Objeto novo a cada execução: garante que os contadores começam zerados
            SortingAlgorithm algoritmo = criarAlgoritmo(indice);

            // A própria medição aloca uns bytes; mede esse custo à parte para descontar depois
            long referenciaMedicao = bytesAlocados();
            long custoMedicao = bytesAlocados() - referenciaMedicao;

            long memoriaAntes = bytesAlocados();
            algoritmo.sort(copia);
            long memoriaDepois = bytesAlocados();

            // Validação: o próprio isSorted do algoritmo + comparação com Arrays.sort
            if (!algoritmo.isSorted(copia) || !Arrays.equals(copia, referencia)) {
                falhas++;
            }

            // A 1ª execução é só aquecimento da JVM: roda, mas não entra na média
            if (execucao > 1) {
                somaTempo += algoritmo.getExecutionTime();
                somaComparacoes += algoritmo.getComparisons();
                somaTrocas += algoritmo.getSwaps();
                somaMemoria += Math.max(0, memoriaDepois - memoriaAntes - custoMedicao);
            }
        }

        int usadas = EXECUCOES - 1; // 4 execuções entram na média
        return new long[]{
                somaTempo / usadas,
                somaComparacoes / usadas,
                somaTrocas / usadas,
                somaMemoria / usadas,
                falhas
        };
    }

    // Cria uma instância nova do algoritmo (mesma ordem de NOMES)
    static SortingAlgorithm criarAlgoritmo(int indice) {
        switch (indice) {
            case 0:
                return new BubbleSort();
            case 1:
                return new InsertionSort();
            case 2:
                return new SelectionSort();
            case 3:
                return new MergeSort();
            default:
                return new QuickSort();
        }
    }

    // Gera o vetor do tipo pedido
    static int[] gerarVetor(String tipo, int n, Random aleatorio) {
        int[] vetor = new int[n];
        for (int i = 0; i < n; i++) {
            if (tipo.equals("aleatorio")) {
                vetor[i] = aleatorio.nextInt(1_000_000); // distribuição uniforme
            } else if (tipo.equals("ordenado")) {
                vetor[i] = i;                            // crescente
            } else {
                vetor[i] = n - i;                        // decrescente
            }
        }
        return vetor;
    }

    // Bytes alocados no heap pela thread atual até agora.
    // A diferença antes/depois do sort() mostra a memória EXTRA que o algoritmo usou
    // (ex.: o vetor auxiliar do MergeSort). A pilha de recursão não entra nessa conta.
    static long bytesAlocados() {
        java.lang.management.ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        if (bean instanceof com.sun.management.ThreadMXBean) {
            return ((com.sun.management.ThreadMXBean) bean).getCurrentThreadAllocatedBytes();
        }
        return 0; // JVM sem suporte: a coluna de memória sai zerada
    }
}