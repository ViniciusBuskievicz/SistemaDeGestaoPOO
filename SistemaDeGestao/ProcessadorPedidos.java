package SistemaDeGestao;

import java.util.Queue;

public class ProcessadorPedidos implements Runnable {
    private Queue<Pedido> fila;

    // Construtor que recebe a fila
    public ProcessadorPedidos(Queue<Pedido> fila) {
        this.fila = fila;
    }

    @Override
    public void run() {
        while (true) {
            Pedido pedido = fila.poll();
            if (pedido != null) {
                pedido.setStatus(StatusPedido.PROCESSANDO);
                System.out.println("Processando pedido de " + pedido.getCliente().getNome());
                try {
                    Thread.sleep(3000); // Simula tempo de processamento
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pedido.setStatus(StatusPedido.FINALIZADO);
                System.out.println("Pedido finalizado: " + pedido);
            }
        }
    }
}