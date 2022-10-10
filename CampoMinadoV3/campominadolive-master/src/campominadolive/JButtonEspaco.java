/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominadolive;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ian, Eduardo,Victor e Thiago
 */

//Onde serão criados os botões do Jogo, além do espaco do campo.
//A classe JButtonEspaco é herdada da classe botao e utiliza muitos metodos da classe espaco
public final class JButtonEspaco extends JButton {

    int linha;
    int coluna;
    Campo campoLogica;
    Espaco espacoLogica;
    JFrameCampo campoGrafico;
    String text;

    
    public JButtonEspaco(Campo c, JFrameCampo cg) {
        this.campoGrafico = cg;
        this.text = "";
        this.setText(text);
        this.campoLogica = c;
        this.addActionListener((java.awt.event.ActionEvent evt) -> {
            botaoPressionado(false);
        });

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    botaoPressionado(true);
                };
            }
        });
    }

    public void reset() {
        this.espacoLogica.reset();
        this.text = "";
        this.setText(text);
        this.setEnabled(true);
        this.setIcon(null);
    }

    
    //ESPACO LOGICO é a regra de negocio dos espaco. Não pode ter duas marcacoes, nao pode clicar em um espaco marcado, nao pode abrir uma mina aberta,etc. 
    private void botaoPressionado(boolean mouseBotaoDireito) {
        if (!mouseBotaoDireito) { //botao esquerdo
            if (!this.espacoLogica.marcado) {
                this.clicar();
            }
        } else {
            this.marcar();
        }
        this.campoGrafico.checkEstado();
    }

    public void clicar() {
        System.out.println("linha: " + linha + " coluna: " + coluna);

        //Retorna numVizinhosMinados se Espaco Atual NAAAAO POSSUI MINA
        int numVizinhosMinados = espacoLogica.clicar();

        if (this.espacoLogica.minado) {
            this.campoGrafico.revelarMinas();
        }

        if (numVizinhosMinados == 0) {
            for (Espaco vizinho : espacoLogica.vizinhos) {
                if (!vizinho.clicado) {
                    vizinho.button.clicar();
                }
            }
            //return;
        }
        this.text = Integer.toString(numVizinhosMinados);

        this.revela(this.text);
    }

    public void marcar() {
        if (this.espacoLogica.clicado) {
            return;
        }
        boolean estaMarcado = this.espacoLogica.marcar();
        if (this.espacoLogica.marcado) {
            try {
                Image img = ImageIO.read(getClass().getResource("imgs/marcado.png"));
                img = img.getScaledInstance(C.TAM_ESPACO, C.TAM_ESPACO, java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                this.setText("M");
                System.out.println("ERRO!");
            }
        } else {
            this.setIcon(null);
            this.setText("");
        }
    }

    public void setPos(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
        this.espacoLogica = campoLogica.getEspaco(linha, coluna);
    }

    public void revela(String cod) {

        if (cod.equals("-1")) {
            try {
                Image img = ImageIO.read(getClass().getResource("imgs/mine.png"));
                img = img.getScaledInstance(C.TAM_ESPACO, C.TAM_ESPACO, java.awt.Image.SCALE_SMOOTH);
                this.setIcon(new ImageIcon(img));
            } catch (Exception ex) {
                this.setText("-1");
                System.out.println("ERRO!");
            }
        } else {
            if (cod.equals("0")) {
                this.setText("");
            } else {
                this.setText(cod);
            }
            this.setEnabled(false);
        }
    }
}
