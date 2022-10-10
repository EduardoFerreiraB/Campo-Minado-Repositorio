/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominadolive;

import java.util.Random;

/**
 *
 * @author paunoseucu
 */

//Classe pública que gera as casas do campo minado.



//Como é protegido, a Classe Campo tem acesso a classe Espaco.
//Exemplo de Herança
//Todo Espaco deve estar contido em um campo
public class Campo{

	
    Espaco[][] matriz;
    //Dá valor a matriz Espaço, o número de linhas e colunas é herdado inicialmente da classe C e depois da classe JFrameCampo.
    public Campo() {
        matriz = new Espaco[C.NUM_LINHAS][C.NUM_COLUNAS];
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                matriz[i][j] = new Espaco();
            }
        }

        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                if (i > 0) {
                    if (j > 0) {
                        matriz[i][j].adicionarVizinhos(matriz[i - 1][j - 1]);
                    }
                    matriz[i][j].adicionarVizinhos(matriz[i - 1][j]);
                    if (j < C.NUM_COLUNAS - 1) {
                        matriz[i][j].adicionarVizinhos(matriz[i - 1][j + 1]);
                    }
                }

                if (j > 0) {
                    matriz[i][j].adicionarVizinhos(matriz[i][j - 1]);
                }
                if (j < C.NUM_COLUNAS - 1) {
                    matriz[i][j].adicionarVizinhos(matriz[i][j + 1]);
                }

                if (i < C.NUM_LINHAS - 1) {
                    if (j > 0) {
                        matriz[i][j].adicionarVizinhos(matriz[i + 1][j - 1]);
                    }
                    matriz[i][j].adicionarVizinhos(matriz[i + 1][j]);
                    if (j < C.NUM_COLUNAS - 1) {
                        matriz[i][j].adicionarVizinhos(matriz[i + 1][j + 1]);
                    }
                }
            }
        }

    }
    
    
    //Adiciona minas no campo já configurado, baseado no número de minas do campo. Para a adição, é feito um sorteio da posição o campo. 
    public void adicionarMinas() {
        int n = C.NUM_MINAS;
        Random rand = new Random();
        while (n > 0) {
            int l = rand.nextInt(C.NUM_LINHAS);
            int c = rand.nextInt(C.NUM_COLUNAS);
            if (matriz[l][c].minar()) {
                n--;
            }
        }
        System.out.println(this);

    }
    //Seleciona a linha e a coluna que será clicado
    public int clicar(int linha, int coluna) {
        return matriz[linha][coluna].clicar();
    }

    //Vence se a matriz for finalizada
    public boolean isVencido() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                if (!matriz[i][j].isFinalizado()) {
                    return false;
                }
            }
        }
        return true;
    }

    //Perde se em um dos campos clicados tiver mina
    public boolean isPerdido() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                if (matriz[i][j].clicado && matriz[i][j].minado) {
                    return true;
                }
            }
        }
        return false;
    }

    //Tamanho do espaco
    public Espaco getEspaco(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                str += matriz[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }
}
