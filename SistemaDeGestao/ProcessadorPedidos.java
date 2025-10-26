package SistemaDeGestao;

import java.util.Queue;

public class ProcessadorPedidos implements Runnable {
    private Queue<Pedido> fila;
    private boolean running = true;

    public ProcessadorPedidos(Queue<Pedido> fila) {
        this.fila = fila;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Pedido pedido = fila.poll();
                if (pedido != null) {
                    // Atualiza para status FILA
                    pedido.setStatus(StatusPedido.FILA);
                    System.out.println("Pedido na fila: " + pedido.getCliente().getNome());
                    Thread.sleep(2000); // Aguarda 2 segundos

                    // Atualiza para status PROCESSANDO
                    pedido.setStatus(StatusPedido.PROCESSANDO);
                    System.out.println("Processando pedido de " + pedido.getCliente().getNome());
                    Thread.sleep(3000); // Simula processamento por 3 segundos

                    // Atualiza para status FINALIZADO
                    pedido.setStatus(StatusPedido.FINALIZADO);
                    System.out.println("Pedido finalizado para " + pedido.getCliente().getNome());
                    System.out.println("Status final: " + pedido);
                } else {
                    Thread.sleep(1000); // Espera 1 segundo se a fila estiver vazia
                }
            } catch (InterruptedException e) {
                System.out.println("Processador de pedidos interrompido");
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }
}