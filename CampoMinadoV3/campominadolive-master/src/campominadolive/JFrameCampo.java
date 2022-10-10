package campominadolive;

//import javax.swing.JButton;
import java.awt.Image;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//� aqui onde todo gr�fico do JFrame ser� criado, baseado em informa�oes das outras classes
public class JFrameCampo extends JFrame {

    JOptionPane telinha = new JOptionPane();
    JPanel panel;
    JLabel titulo = new JLabel();
    JButtonEspaco[][] matBut;
    Campo c;
    JButton resetBut;
    Espaco checaMinas;
    JLabel label;
    JButton facilBut;
    JButton medBut;
    JButton difBut;

    JTextArea area = new JTextArea();

    Timer tm = new Timer();
    int tempo = 0;
    JLabel cronometro = new JLabel();

    // Cronometro onde ser� computado 
    public void rodaCronometro() {
        Font fonte = new Font("Serif", Font.BOLD, 15);
        cronometro.setFont(fonte);
        cronometro.setSize(100, 100);
        final long segundo = (1000);

        TimerTask tarefa = new TimerTask() {

            @Override
            public void run() {
                label = new JLabel();
                label.setIcon(new javax.swing.ImageIcon("imgs/marcado.png"));

                cronometro.setText("TEMPO   " + tempo + "s");
                tempo++;
            }

        };

        tm.scheduleAtFixedRate(tarefa, 0, segundo);

    }

    //M�todo que gera o T�tulo
    public void Titulo() {
        Font fonte = new Font("Serif", Font.BOLD, 20);
        titulo.setText("CAMPO " + "\nMINADO");
        titulo.setFont(fonte);
        titulo.setSize(200, 200);

    }

    //M�todo que gera o cron�metro
    public void paraCronometro() {

        int temporador = tempo;
        tempo--;
        cronometro.setText("Tempo" + temporador);;
    }

    ImageIcon reload = new ImageIcon("reload.png");
    //JButton custBut;

    //JFrame do campo retorna as configuracoes iniciais
    public JFrameCampo() {
        confIniciais();
    }

    //Lima Completamente o estado atual do campo
    //Abre espaco para inserir uma nova dificulde, excluindo totalmente o jogo anterior
    public void hardReset() {
        CampoMinadoLive.hardReset();
        this.dispose();
    }

    //Define o tempo do cronometro como 0
    public void Cronometro() {
        int timeRemaining = 0;

    }
//Gera a tela inicial padr�o do projeto campo minado.

    private void confIniciais() {
        //Gera um novo campo.
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
                //matBut[i][j].linha = i;
                //matBut[i][j].coluna = j;
                matBut[i][j].setPos(i, j);
                matBut[i][j].setSize(C.TAM_ESPACO, C.TAM_ESPACO);
                matBut[i][j].setFocusable(false);
                matBut[i][j].setLocation(C.TAM_ESPACO * j, C.TAM_ESPACO * i + C.OFFSET_SUPERIOR);
                //matBut[i][j].setText(Integer.toString(n++));
                panel.add(matBut[i][j]);

            }
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(C.NUM_COLUNAS * C.TAM_ESPACO + 300, C.NUM_LINHAS * C.TAM_ESPACO + C.OFFSET_SUPERIOR + C.ALTURA_BARRA_SUP + 40);
        this.setResizable(false);
        this.setVisible(true);

        titulo.setBounds(C.NUM_COLUNAS * C.TAM_ESPACO + 75, 30, 100, 50);
        cronometro.setBounds(C.NUM_COLUNAS * C.TAM_ESPACO + 120, 150, 100, 50);
        cronometro.setText("Aqui");
        cronometro.setVisible(true);
        this.panel.add(titulo);
        this.panel.add(cronometro);
        this.Titulo();

        this.resetBut = new JButton("");
        try {
            Image img = ImageIO.read(getClass().getResource("imgs/reload.png"));
            img = img.getScaledInstance(C.TAM_ESPACO, C.TAM_ESPACO, java.awt.Image.SCALE_SMOOTH);
            this.resetBut.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            this.resetBut.setText("Reiniciar");
        }
        this.resetBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            this.reset();
            this.tempo = 0;
        });
        this.resetBut.setSize(C.TAM_ESPACO, C.TAM_ESPACO);
        this.resetBut.setLocation((C.TAM_ESPACO * C.NUM_COLUNAS) / 2 - C.TAM_ESPACO / 2, C.OFFSET_SUPERIOR - C.TAM_ESPACO);
        this.panel.add(this.resetBut);
        //5x5 espacos de tamanho 10x10
        //o botao reset deveria estar na posicao 20        
        //

        //Dificuldade  "Facil". Recebe os eventos do botao e Reseta.
        this.facilBut = new JButton("Fácil");
        this.facilBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            C.NUM_COLUNAS = 9;
            C.NUM_LINHAS = 9;
            C.NUM_MINAS = 10;
            this.hardReset();

        });

        //Configura o tamanho e o espaco do botao Facil e sua localizacao
        this.facilBut.setSize((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, C.TAM_ESPACO);
        this.facilBut.setLocation(0, 0);
        //this.facilBut.setBackground(new Color(40, 250, 25));

        //adiciona ao painel o botao 
        this.panel.add(this.facilBut);

        //adiciona o cronometro para repetir a contagem
        this.panel.add(cronometro);

        //Dificuldade "Medio". Recebe os eventos do botao e Reseta.
        this.medBut = new JButton("Médio");
        this.medBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            C.NUM_COLUNAS = 16;
            C.NUM_LINHAS = 16;
            C.NUM_MINAS = 40;
            this.hardReset();
        });
        //Configura o tamanho e o espaco do botao medio e sua localizacao
        this.medBut.setSize((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, C.TAM_ESPACO);
        this.medBut.setLocation((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, 0);

        //adiciona o botao ao painel
        this.panel.add(this.medBut);

        // Dificuldade "Dificil", recebe os eventos do botao e reseta.
        this.difBut = new JButton("Difícil");
        this.difBut.addActionListener((java.awt.event.ActionEvent evt) -> {
            C.NUM_COLUNAS = 30;
            C.NUM_LINHAS = 16;
            C.NUM_MINAS = 99;
            this.hardReset();

        });

        //Configura o tamanho e o espaco do botao dificil e sua localizacao
        this.difBut.setSize((C.TAM_ESPACO * C.NUM_COLUNAS) / 3, C.TAM_ESPACO);
        this.difBut.setLocation((C.TAM_ESPACO * C.NUM_COLUNAS) / 3 * 2, 0);
        this.panel.add(this.difBut);
        this.rodaCronometro();

    }

    //Define o n�mero inserido na casa
    //Esse numero � baseado nos seus vizinhos com mina
    // 1 para 1 mina, 2 para 2 minas, no maximo 6.
    public void definirNumero() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {

                //metodo "numMinasNosVizinhos herdado da classe Espaco.
                //Insere o n�mero e as imagens dos botoes
                if (this.checaMinas.numMinasNosVizinhos() == 0) {
                    try {
                        Image img = ImageIO.read(getClass().getResource("vazio.png"));
                        img = img.getScaledInstance(C.TAM_ESPACO, C.TAM_ESPACO, java.awt.Image.SCALE_SMOOTH);
                        this.matBut[i][j].setIcon(new ImageIcon(img));
                    } catch (Exception ex) {
                        this.matBut[i][j].setText("0");
                    }
                }

            }
        }
    }

    public void reset() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                matBut[i][j].reset();
            }
        }
        this.c.adicionarMinas();
    }

    //
    public void revelarMinas() {
        for (int i = 0; i < C.NUM_LINHAS; i++) {
            for (int j = 0; j < C.NUM_COLUNAS; j++) {
                if (matBut[i][j].espacoLogica.minado) {
                    matBut[i][j].revela("-1");
                }
            }
        }
    }

    //Usado o final do jogo
    //Desativa a funcao dos botoes
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

    //Checa se o jogador ganhou o perdeu, caso perdeu exibe uma mensagem, caso ganhou, outra.
    public void checkEstado() {
        System.out.println("Verificando se Ganhou ou Perdeu");
        if (this.c.isVencido()) {
            this.emiteSom("fogonainveja");
            ImageIcon icone = new ImageIcon();
            telinha.showMessageDialog(this.panel, "GANHOU! 244 não é crime", "FUGA", 0, icone);
            this.desativaBotoes();
        }

        if (this.c.isPerdido()) {
            this.emiteSom("euachei");
            telinha.showMessageDialog(this.panel, "CTB - Art. 244 - III. Multa- R$293,47", "INFRAÇÃO DE TRANSITO", 0, null); 
            this.desativaBotoes();
            this.paraCronometro();
        }

    }

    //Tempo da aplicacao
    private final static long REFRESH_LIST_PERIOD = 10 * 60 * 1000; //10 minutes

}
