package org.example;

import com.aegis.core.MyLinkedList;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();

        System.out.println("=== Testando MyLinkedList ===\n");

        // Teste 1: Adicionar elementos
        System.out.println("1. Adicionando elementos (1, 2, 3):");
        list.add(1);
        list.add(2);
        list.add(3);
        printList(list);

        // Teste 2: Adicionar no início
        System.out.println("\n2. Adicionando 0 no início:");
        list.addFirst(0);
        printList(list);

        // Teste 3: Obter elemento por índice
        System.out.println("\n3. Obtendo elemento no índice 2:");
        System.out.println("Elemento: " + list.get(2));

        // Teste 4: Remover primeiro elemento
        System.out.println("\n4. Removendo primeiro elemento:");
        Integer removed = list.removeFirst();
        System.out.println("Removido: " + removed);
        printList(list);

        // Teste 5: Verificar tamanho e isEmpty
        System.out.println("\n5. Tamanho da lista: " + list.size());
        System.out.println("Lista vazia? " + list.isEmpty());

        // Teste 6: Testar exceções
        System.out.println("\n6. Testando exceções:");
        try {
            list.get(100);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        // === TESTES DO MÉTODO REMOVE(INDEX) ===
        System.out.println("\n=== Testando método remove(int index) ===\n");

        // Preparar lista para testes
        MyLinkedList<Integer> testList = new MyLinkedList<>();
        testList.add(10);
        testList.add(20);
        testList.add(30);
        testList.add(40);
        testList.add(50);
        System.out.println("Lista inicial:");
        printList(testList);

        // Teste 8: Remover do início (índice 0)
        System.out.println("\n8. Removendo elemento no índice 0:");
        Integer removedFirst = testList.remove(0);
        System.out.println("Removido: " + removedFirst);
        printList(testList);
        System.out.println("Tamanho: " + testList.size());

        // Teste 9: Remover do final (último índice)
        System.out.println("\n9. Removendo elemento no último índice (" + (testList.size() - 1) + "):");
        Integer removedLast = testList.remove(testList.size() - 1);
        System.out.println("Removido: " + removedLast);
        printList(testList);
        System.out.println("Tamanho: " + testList.size());

        // Teste 10: Remover do meio (índice 1)
        System.out.println("\n10. Removendo elemento no índice 1 (meio):");
        Integer removedMiddle = testList.remove(1);
        System.out.println("Removido: " + removedMiddle);
        printList(testList);
        System.out.println("Tamanho: " + testList.size());

        // Teste 11: Remover até esvaziar a lista
        System.out.println("\n11. Removendo todos os elementos usando remove(0):");
        while (!testList.isEmpty()) {
            Integer elem = testList.remove(0);
            System.out.println("Removido: " + elem + " | Tamanho: " + testList.size());
        }
        System.out.println("Lista vazia? " + testList.isEmpty());

        // Teste 12: Testar exceção de índice inválido
        System.out.println("\n12. Testando exceção com índice inválido:");
        MyLinkedList<Integer> emptyList = new MyLinkedList<>();
        try {
            emptyList.remove(0);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exceção capturada (lista vazia): " + e.getMessage());
        }

        MyLinkedList<Integer> smallList = new MyLinkedList<>();
        smallList.add(100);
        try {
            smallList.remove(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exceção capturada (índice fora do range): " + e.getMessage());
        }

        try {
            smallList.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Exceção capturada (índice negativo): " + e.getMessage());
        }

        System.out.println("\n=== Testes concluídos! ===");
    }

    private static <T> void printList(MyLinkedList<T> list) {
        System.out.print("Lista: [");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
            if (i < list.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}