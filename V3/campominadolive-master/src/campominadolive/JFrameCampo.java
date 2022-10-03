/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campominadolive;

//import javax.swing.JButton;
import java.awt.Image;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 *
 * @author paunoseucu
 */
public class JFrameCampo extends JFrame {

    JPanel panel;
    JButtonEspaco[][] matBut;
    Campo c;
    Espaco checaMinas;

    JButton facilBut;
    JButton medBut;
    JButton difBut;
    JButton resetBut;

    public JFrameCampo() {
        confIniciais();
    }

    public void hardReset() {
        CampoMinadoLive.hardReset();
        this.dispose();
    }

    private void confIniciais() {

        this.c = new Campo();
        c.adicionarMinas();
        this.panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
        matBut = new JButtonEspaco[C.NUM_LINHAS][C.NUM_COLUNAS];
        int n = 0;
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                matBut[i][j] = new JButtonEspaco(this.c, this);
                c.getEspaco(i, j).setButton(matBut[i][j]);
                matBut[i][j].setPos(i, j);
                matBut[i][j].setSize(C.TAM_ESPACO, C.TAM_ESPACO);
                matBut[i][j].setFocusable(false);
                matBut[i][j].setLocation(C.TAM_ESPACO * j, C.TAM_ESPACO * i + C.OFFSET_SUPERIOR);
                panel.add(matBut[i][j]);

            }
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(C.NUM_COLUNAS * C.TAM_ESPACO + 300, C.NUM_LINHAS * C.TAM_ESPACO + C.OFFSET_SUPERIOR + C.ALTURA_BARRA_SUP + 15);
        this.setResizable(false);
        this.setVisible(true);

        this.resetBut = new JButton("");
        try {
            Image img = ImageIO.read(getClass().getResource("imgs/reload.png"));
            img = img.getScaledInstance(C.TAM_ESPACO, C.TAM_ESPACO, java.awt.Image.SCALE_SMOOTH);
            this.resetBut.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            this.resetBut.setText("R");
        }
        this.resetBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            this.reset();
        });
        this.resetBut.setSize(C.TAM_ESPACO, C.TAM_ESPACO);
        this.resetBut.setLocation((C.TAM_ESPACO * C.NUM_COLUNAS) / 2 - C.TAM_ESPACO / 2, C.OFFSET_SUPERIOR - C.TAM_ESPACO);
        this.panel.add(this.resetBut);

        this.facilBut = new JButton("Fácil");
        this.facilBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            C.NUM_COLUNAS = 9;
            C.NUM_LINHAS = 9;
            C.NUM_MINAS = 10;
            this.hardReset();
        });
        this.facilBut.setSize((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, C.TAM_ESPACO);
        this.facilBut.setLocation(0, 0);
        //this.facilBut.setBackground(new Color(40, 250, 25));
        this.panel.add(this.facilBut);

        this.medBut = new JButton("Médio");
        this.medBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            C.NUM_COLUNAS = 16;
            C.NUM_LINHAS = 16;
            C.NUM_MINAS = 40;
            this.hardReset();
        });
        this.medBut.setSize((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, C.TAM_ESPACO);
        this.medBut.setLocation((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, 0);
        this.panel.add(this.medBut);

        this.difBut = new JButton("Difícil");
        this.difBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            C.NUM_COLUNAS = 30;
            C.NUM_LINHAS = 16;
            C.NUM_MINAS = 100;
            this.hardReset();

        });
        this.difBut.setSize((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, C.TAM_ESPACO);
        this.difBut.setLocation((C.TAM_ESPACO * C.NUM_COLUNAS) / 3 * 2, 0);
        this.panel.add(this.difBut);
    }

    public void reset() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                matBut[i][j].reset();
            }
        }
        this.c.adicionarMinas();
    }

    public void revelarMinas() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                if (matBut[i][j].espacoLogica.minado) {
                    matBut[i][j].revela("-1");
                }
            }
        }
    }

    public void desativaBotoes() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                matBut[i][j].setEnabled(false);
            }
        }
    }

    public void emiteSom(String nome) {
        try {
            AudioInputStream audioInputStream = AudioSystem
                    .getAudioInputStream(new File("src//campominadolive//audio//" + nome + ".wav").getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Erro ao executar SOM!");
            ex.printStackTrace();
        }
    }

    public void checkEstado() {
        System.out.println("Verificando se Ganhou ou Perdeu");
        if (this.c.isVencido()) {
            System.out.println("Venceu");
            this.emiteSom("fogonainveja");
            this.desativaBotoes();
        }

        if (this.c.isPerdido()) {
            System.out.println("Perdeu");
            this.emiteSom("euachei");
            this.desativaBotoes();
        }
    }
}
