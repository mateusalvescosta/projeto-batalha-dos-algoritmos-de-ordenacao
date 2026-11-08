# Batalha dos Algoritmos de Ordenação

Projeto desenvolvido para a disciplina de Projeto e Análise de Algoritmos do curso de Sistemas de Informação da UniSales.

## Sobre o projeto

O objetivo deste projeto é implementar, analisar e comparar diferentes algoritmos de ordenação, observando características como desempenho, quantidade de comparações, movimentações realizadas e complexidade computacional.

Cada integrante do grupo será responsável pela implementação e documentação de um algoritmo de ordenação.

Os algoritmos propostos para o projeto são:

- Breno Clemente Pereira - SelectionSort
- João Victor Pereira Marques - MergeSort
- Juan Pablo Rocha Hempel - QuickSort
- Moacyr Filipe Pereira Del Caro - BubbleSort
- Mateus Alves Costa - InsertionSort

## Organização do projeto

O projeto está organizado utilizando packages separados para cada algoritmo.

## Como rodar os experimentos na sua máquina

### Requisitos
- JDK 14 ou superior (`java -version` para conferir)

### 1. Compilar
Na pasta que contém `core/` e `algorithms/`:

**Windows (PowerShell)**
```
javac -d out (Get-ChildItem -Recurse -Filter *.java).FullName
```

Também funciona rodando o `Main` direto pela IDE (IntelliJ, Eclipse, VS Code).

### 2. Executar
```
java -cp out br.unisales.batalhaordenacao.algorithms.Main resultados.csv
```

O programa roda os 5 algoritmos com os 3 tipos de dados (aleatório, ordenado e inverso) para n = 100 até 100.000 (os algoritmos O(n²) vão só até 50.000). Cada configuração roda 5 vezes, a primeira é descartada (warm-up) e a média é feita sobre as 4 últimas. A execução leva cerca de 1 a 2 minutos.

Ao final, confira no terminal:
- `Erros de validação: 0`
- nenhuma linha com `AVISO` (indica estouro de contador)

O arquivo `resultados.csv` é criado na pasta onde o comando foi executado e sobrescreve o anterior.

### 3. Formatar os resultados na planilha
O repositório inclui a planilha `resultados_formatados.xlsx`, que monta sozinha as tabelas de tempo, comparações, trocas e memória a partir do CSV.

1. Abra `resultados_formatados.xlsx` e vá na aba **Dados**.
2. Abra o `resultados.csv` gerado, copie **todo o conteúdo, incluindo o cabeçalho** (`algoritmo,tipo,n,...`).
3. Na aba **Dados**, clique na célula **A1** e cole.
4. Se o Excel colocar tudo em uma coluna só, selecione a coluna A e use **Dados > Texto para Colunas > Delimitado > Vírgula**.
5. Se o novo CSV tiver menos linhas que o anterior, apague as linhas que sobraram no fim da aba Dados.
6. Salve. As abas **Resumo, Tempo, Comparações, Trocas e Memória** atualizam automaticamente.

### Observações
- Os **tempos variam de máquina para máquina** (e entre execuções). Comparações e trocas são determinísticas: devem ser iguais para os mesmos dados.
- Os vetores aleatórios usam semente fixa (42), então todos geram exatamente as mesmas entradas.
- A coluna de memória mostra os bytes extras alocados no heap durante a ordenação (ex.: o vetor auxiliar do Merge Sort). A pilha de recursão não é contada.
- Ao divulgar resultados de uma máquina própria, informe o processador, a memória e a versão do Java usados.


## Desenvolvimento

O desenvolvimento está sendo realizado utilizando:

Java
IntelliJ IDEA
Git
GitHub

Cada algoritmo será desenvolvido separadamente e posteriormente integrado para realização dos testes e comparações.

Disciplina

Projeto e Análise de Algoritmos

Professor: Alexandre Barbosa de Souza

UniSales - Centro Universitário Salesiano

```text
src/
└── br/
    └── unisales/
        └── batalhaordenacao/
            ├── core/
            │   └── SortingAlgorithm.java
            │
            └── algorithms/
                ├── selectionsort/
                ├── insertionsort/
                ├── bubblesort/
                ├── mergesort/
                └── quicksort/
