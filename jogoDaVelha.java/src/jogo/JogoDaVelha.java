package jogo;
import java.util.LinkedHashMap;
import java.util.ArrayList;
//ooi
public class JogoDaVelha {
	  	private String[] celulas = new String[9]; 
	    private String[] simbolos = new String[2];
	    private LinkedHashMap<Integer, String> historico = new LinkedHashMap<>();
	    private int quantidadeJogadas = 0;
	    private int nivel = 2; 
	    
	    
	    public JogoDaVelha(String simbolo1, String simbolo2){
	    	this.simbolos[0] = simbolo1;
	    	this.simbolos[1] = simbolo2;
	    	
	    }
	    
	   
	    public JogoDaVelha(String nomeJogador1, int nivel) {
	    	this.nivel = nivel;
	    	this.simbolos[0] = "X";
	    	
	    }
	    
	    public void jogaJogador(int numeroJogador, int posicao) throws Exception {
	     
	        if (posicao < 0 || posicao > 8) {
	            throw new Exception("Posição inválida! Escolha de 0 a 8.");
	        }
	        if (numeroJogador !=1 && numeroJogador !=2) {
	        	throw new Exception("Número errado. Escolha 1 ou 2.");
	        }
	        
	        if (celulas[posicao]!= null) {
	            throw new Exception("Essa posição já está ocupada!");
	        }

	   
	        celulas[posicao] = simbolos[numeroJogador - 1];

	       
	        historico.put(posicao, simbolos[numeroJogador - 1]);

	        quantidadeJogadas++;
	    }

	    
	    public int JogaMaquina() {
	        
	        if (this.nivel == 1) {
	            for (int i = 0; i < celulas.length; i++) {
	                if (celulas[i] == null) {
	                    celulas[i] = "m";
	                    quantidadeJogadas++;
	                    return i;
	                }
	            }
	        }

	       
	        if (this.nivel == 2) {
	            int[][] combinacoes = {
	                {0,1,2}, {3,4,5}, {6,7,8},
	                {0,3,6}, {1,4,7}, {2,5,8},
	                {0,4,8}, {2,4,6}
	            };

	          
	            for (int[] c : combinacoes) {
	                int countM = 0;
	                int posVazia = -1;

	                for (int pos : c) {
	                    if ("m".equals(celulas[pos])) countM++;
	                    else if (celulas[pos] == null) posVazia = pos;
	                }

	                if (countM == 2 && posVazia != -1) {
	                    celulas[posVazia] = "m";
	                    quantidadeJogadas++;
	                    return posVazia;
	                }
	            }

	         
	            for (int[] c : combinacoes) {
	                int countJ = 0;
	                int posVazia = -1;

	                for (int pos : c) {
	                    if ("X".equals(celulas[pos])) countJ++;
	                    else if (celulas[pos] == null) posVazia = pos;
	                }

	                if (countJ == 2 && posVazia != -1) {
	                    celulas[posVazia] = "m";
	                    quantidadeJogadas++;
	                    return posVazia;
	                }
	            }

	          
	            for (int j = 0; j < celulas.length; j++) {
	                if (celulas[j] == null) {
	                    celulas[j] = "m";
	                    quantidadeJogadas++;
	                    return j;
	                }
	            }
	        }

	        return -1; // não conseguiu jogar
	    }
	    
	    
	    
	    
	    private String getSimboloVencedor() {
	        int[][] combinacoes = {
	            {0,1,2}, {3,4,5}, {6,7,8},
	            {0,3,6}, {1,4,7}, {2,5,8},
	            {0,4,8}, {2,4,6}
	        };

	        for (int[] combinacao : combinacoes) {
	            int a = combinacao[0];
	            int b = combinacao[1];
	            int c = combinacao[2];

	            if (celulas[a] != null && celulas[a].equals(celulas[b]) && celulas[a].equals(celulas[c])) {
	                return celulas[a]; 
	            }
	        }

	        return null; 
	    }
	    
	    
	    
	    public boolean terminou() {
	        return getSimboloVencedor() != null || quantidadeJogadas == 9;
	    }

	    public int getResultado() {
	        if (!terminou()) return -1;

	        String vencedor = getSimboloVencedor();

	        if (vencedor == null) return 0; 

	        if (vencedor.equals(simbolos[0])) return 1;
	        else return 2;
	    }

	   	    
	    public String getFoto() {
	        String foto = "";

	        for (int i = 0; i < celulas.length; i++) {
	            if (celulas[i] == null) {
	                foto += "-";
	            } else {
	                foto += celulas[i];
	            }

	            if ((i + 1) % 3 != 0) {
	                foto += " | ";
	            } else {
	                foto += "\n";
	            }
	        }

	        return foto;
	    }

	    public String getSimbolo(int numeroJogador) {
	    	return simbolos[numeroJogador - 1];
	    }
	    
	    
	    public ArrayList<Integer> getPosicoesDisponiveis(){
	    	ArrayList<Integer> disponiveis = new ArrayList<>();
	    	for (int i=0; i<celulas.length; i++) {
	    		if(celulas[i] == null) {
	    			disponiveis.add(i);
	    		}
	    	}
	    	return disponiveis;
	    }
	    
	    
	    
	    public LinkedHashMap<Integer, String> getHistorico(){
	    	return historico;
	    }
	    
	    public int getTotalJogadas() {
		    return quantidadeJogadas;
		}
    
	
	    public static void main(String[] args) {
	        try {
	           
	            JogoDaVelha jogo = new JogoDaVelha("Jogador", 2);
	            
	      
	            jogo.jogaJogador(1, 0);
	            int jogadaMaquina = jogo.JogaMaquina(); 
	            System.out.println("Máquina jogou na posição: " + jogadaMaquina);

	            jogo.jogaJogador(1, 2);
	            jogadaMaquina = jogo.JogaMaquina();
	            System.out.println("Máquina jogou na posição: " + jogadaMaquina);

	            jogo.jogaJogador(1, 4);

	            System.out.println(jogo.getFoto());

	            if (jogo.terminou()) {
	                int resultado = jogo.getResultado();
	                if (resultado == 1) {
	                    System.out.println("Jogador venceu!");
	                } else if (resultado == 2) {
	                    System.out.println("Máquina venceu!");
	                } else {
	                    System.out.println("Empate!");
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Erro: " + e.getMessage());
	        }
	    }


}
