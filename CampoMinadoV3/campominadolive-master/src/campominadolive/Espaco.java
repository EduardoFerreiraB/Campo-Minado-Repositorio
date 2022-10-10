package campominadolive;

import java.util.ArrayList;

/**
 *
 * @authores Ian, Eduardo, Thiago, Victors
 */


//O Espaco é cada casa do campo minado.
 public class Espaco {
	
	//O Espaco pode ter os seguintes estados.
	//Exemplo de abstração
	//As configurações iniciais são privadas 
	
    
    
    //GETTERS AND SETTERS DOS MÉTODOS
    public boolean isMinado() {
		return minado;
	}


	public void setMinado(boolean minado) {
		this.minado = minado;
	}


	public boolean isClicado() {
		return clicado;
	}


	public void setClicado(boolean clicado) {
		this.clicado = clicado;
	}


	public boolean isRevelado() {
		return revelado;
	}


	public void setRevelado(boolean revelado) {
		this.revelado = revelado;
	}


	public boolean isMarcado() {
		return marcado;
	}


	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}


	protected boolean minado;
	protected boolean clicado;
	protected boolean revelado;
	protected boolean marcado;  

    ArrayList<Espaco> vizinhos;
    
    JButtonEspaco button;

    // As configurações iniciais do espaco. 
    public Espaco() {
        this.minado = false;
        this.revelado = false;
        this.marcado = false;
        this.clicado = false;

        this.vizinhos = new ArrayList();
    }

    //Forma o Campo e determinado quais minas são vizinhas dos espaços
    public void adicionarVizinhos(Espaco e){
        this.vizinhos.add(e);
    }
    
    //Metodo minar: indica que o espaco tem uma mina
    //Se o espaco ja tem uma mina, ele retorna false
    //Se o espaco nao tinha uma mina antes, retorna verdadeiro    
    public boolean minar() {
        if (!this.minado) {
            this.minado = true;
            return true;
        } else {
            return false;
        }

    }  
    //Marca a mina, alterando a variável marcado para TRUE
    public boolean marcar(){
        this.marcado = !this.marcado;
        return this.marcado;
    }   
    
   
    //Abre a mina, se o retorno inteiro for -1, há mina. 
    
    public int clicar(){
        this.clicado = true;
        if(this.minado){
            return -1;
        }
        else{
            return numMinasNosVizinhos();
        }
    }
    
    //Registra o númeo de minas nos vizinhos
    //Caso o vizinho tenha 1 mina, a casa recebe + 1; 
    public int numMinasNosVizinhos(){
        int n = 0;
        for (Espaco vizinho : this.vizinhos) {
            if(vizinho.minado) n++;
        }
        
        return n;
    }
    
    //Reinicia todo o espaco clicado.
    public void reset(){
        this.minado = false;
        this.revelado = false;
        this.marcado = false;
        this.clicado = false;
    }
    
    //O campo é finalizado quando t
    public boolean isFinalizado(){
        if(this.minado && this.marcado) 
        	return true;
        if(!this.minado && !this.marcado && this.clicado) 
        	return true;
        return false;
    }
    
    public void setButton(JButtonEspaco button){
        this.button = button;
    }
    
    

    @Override
    public String toString() {
        if (this.minado)
            return "-1";
        return "+" + this.numMinasNosVizinhos();
    }
}

