package jogo;

import javax.swing.*;
import java.awt.*;

public class TelaJogo extends JFrame {
    private JButton[] botoes = new JButton[9];
    private JogoDaVelha jogo;
    private JLabel status;
    private boolean contraMaquina;
    private int jogadorAtual = 1; 

    private Color rosaClaro = new Color(255, 192, 203);       
    private Color rosaMaisClaro = new Color(255, 228, 235);  

    public TelaJogo() {
        setTitle("Jogo da Velha");
        setSize(300, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Faz com que, ao clicar no X (fechar), o
        //programa seja finalizado completamente
        setLayout(new BorderLayout()); //Define o layout principal da janela. 
        getContentPane().setBackground(rosaMaisClaro);  
        JPanel tabuleiro = new JPanel(new GridLayout(3, 3));
        //Cria um painel chamado tabuleiro que será uma grade de 3 linhas e 3 colunas (como o jogo da velha).
        tabuleiro.setBackground(rosaMaisClaro);          
        for (int i = 0; i < 9; i++) { //Criando os 9 botões
            final int pos = i;
            botoes[i] = new JButton("");
            botoes[i].setFont(new Font("Arial", Font.BOLD, 40));
            botoes[i].setBackground(rosaClaro);          
            botoes[i].setFocusPainted(false); // Tira aquele contorno azul que aparece quando clicamos no botão.
            botoes[i].addActionListener(e -> jogar(pos));
            tabuleiro.add(botoes[i]);
        }

        status = new JLabel("Escolha o modo de jogo", SwingConstants.CENTER);
        status.setOpaque(true);
        status.setBackground(rosaClaro);                  
        status.setFont(new Font("Arial", Font.BOLD, 14));
        add(status, BorderLayout.SOUTH);

        add(tabuleiro, BorderLayout.CENTER);
        escolherModo();

        setVisible(true);
    }

    private void escolherModo() {
        String[] opcoes = {"2 Jogadores", "Contra Máquina"};
        int escolha = JOptionPane.showOptionDialog(this, "Escolha o modo de jogo:", "Modo de Jogo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha == 0) {
            String simbolo1 = JOptionPane.showInputDialog("Símbolo do Jogador 1 (ex: X)");
            String simbolo2 = JOptionPane.showInputDialog("Símbolo do Jogador 2 (ex: O)");
            jogo = new JogoDaVelha(simbolo1, simbolo2);
            contraMaquina = false;
            status.setText("Jogador 1: " + simbolo1);
        } else if (escolha == 1) {
            String simbolo = JOptionPane.showInputDialog("Símbolo do Jogador (ex: X)");
            jogo = new JogoDaVelha(simbolo, 1); 
            contraMaquina = true;
            status.setText("Você: " + simbolo);
        } else {
            System.exit(0);
        }
    }

    private void jogar(int pos) {
        if (jogo.terminou() || !botoes[pos].getText().equals("")) return;

        try {
            if (!contraMaquina) {
                jogo.jogaJogador(jogadorAtual, pos);
                botoes[pos].setText(jogo.getSimbolo(jogadorAtual));
                checarFim();
                jogadorAtual = (jogadorAtual == 1) ? 2 : 1;
                status.setText("Jogador " + jogadorAtual + ": " + jogo.getSimbolo(jogadorAtual));
            } else {
                jogo.jogaJogador(1, pos);
                botoes[pos].setText(jogo.getSimbolo(1));
                checarFim();
                if (!jogo.terminou()) {
                    jogo.JogaMaquina();
                    atualizarTabuleiro();
                    checarFim();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void atualizarTabuleiro() {
        String[] estados = jogo.getFoto().replace(" | ", "").replace("\n", "").split("");
        for (int i = 0; i < estados.length; i++) {
            botoes[i].setText(estados[i].equals("-") ? "" : estados[i]);
        }
    }

    private void checarFim() {
        if (jogo.terminou()) {
            int resultado = jogo.getResultado();
            String msg = "";
            if (resultado == 0) msg = "Empate!";
            else if (resultado == 1) msg = contraMaquina ? "Você venceu!" : "Jogador 1 venceu!";
            else msg = contraMaquina ? "A máquina venceu!" : "Jogador 2 venceu!";

            status.setText(msg);
            JOptionPane.showMessageDialog(this, msg);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaJogo::new);
    }
}
