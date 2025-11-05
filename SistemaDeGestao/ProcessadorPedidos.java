package SistemaDeGestao;

import java.util.Queue;

// Classe responsável pelo processamento assíncrono dos pedidos
public class ProcessadorPedidos implements Runnable {
    // Atributos do processador
    private Queue<Pedido> fila;         // Fila de pedidos a serem processados
    private boolean running = true;      // Controle de execução da thread

    // Construtor que recebe a fila de pedidos
    public ProcessadorPedidos(Queue<Pedido> fila) {
        this.fila = fila;
    }

    // Método principal de execução da thread
    @Override
    public void run() {
        while (running) {
            try {
                // Tenta obter próximo pedido da fila
                Pedido pedido = fila.poll();
                if (pedido != null) {
                    // Simulação do fluxo de processamento do pedido
                    
                    // Fase 1: Pedido na fila
                    pedido.setStatus(StatusPedido.FILA);
                    System.out.println("Pedido na fila: " + pedido.getCliente().getNome());
                    Thread.sleep(2000); // Aguarda 2 segundos

                    // Fase 2: Processamento do pedido
                    pedido.setStatus(StatusPedido.PROCESSANDO);
                    System.out.println("Processando pedido de " + pedido.getCliente().getNome());
                    Thread.sleep(3000); // Simula processamento por 3 segundos

                    // Fase 3: Finalização do pedido
                    pedido.setStatus(StatusPedido.FINALIZADO);
                    System.out.println("Pedido finalizado para " + pedido.getCliente().getNome());
                    System.out.println("Status final: " + pedido);
                } else {
                    Thread.sleep(1000); // Espera 1 segundo se a fila estiver vazia
                }
            } catch (InterruptedException e) {
                // Tratamento de interrupção da thread
                System.out.println("Processador de pedidos interrompido");
                running = false;
            }
        }
    }

    // Método para parar o processamento de forma segura
    public void stop() {
        running = false;
    }
}