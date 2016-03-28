/*
 * Classe que representa as sala no qual o player vai visitar
 */

public class Sala {
	private int pesoBuraco;
	private int pesoWumpus;
	private int pesoVisitado;
	private int pesoTotal;
	private int linha;
	private int coluna;
	
	private String anterior;
	
	private boolean findGold;
	private boolean isExit;
	private boolean clear;
	
	public Sala(int linha, int coluna){
		this.linha = linha;
		this.coluna = coluna;
		clear = false;
		pesoBuraco = 0;
		pesoWumpus = 0;
		pesoVisitado = 0;
		pesoTotal = 0;
	}
	
	public void possivelBuraco(int pesoBuraco){
		if(clear)//se eu tiver certeza que a sala esta vazia n somo os pesos
			return;
		this.pesoBuraco += pesoBuraco;
	//	this.pesoWumpus = 0;
	}
	
	public void possivelWumpus(int pesoWumpus){
		if(clear)//se eu tiver certeza que a sala esta vazia n somo os pesos
			return;
		this.pesoWumpus += pesoWumpus;
	//	this.pesoBuraco = 0;
	}
	
	public void possivelBuracoEWumpus(int pesoBuraco, int pesoWumpus){
		if(clear)//se eu tiver certeza que a sala esta vazia n somo os pesos
			return;
		this.pesoBuraco += pesoBuraco;
		this.pesoWumpus += pesoWumpus;
	}
	
	public void foiVisitado(int pesoVisitado){
		this.pesoVisitado += pesoVisitado;
	}
	
	public void salaLivre(){
		this.pesoWumpus = 0;
		this.pesoBuraco = 0;
		clear = true;
	}
	
	public int pesoTotal(){
		return pesoBuraco+pesoWumpus+pesoVisitado;
	}
	
	public int getLinha(){
		return linha;
	}
	
	public int getColuna(){
		return coluna;
	}
	
	//seto sala anterior a ela
	public void setAnterior(String ant){
		this.anterior = ant;
	}
	
	public String getAnterior(){
		return anterior;
	}
	
	//chou ouro
	public void discoverGold(){
		findGold = true;
	}

	//é a saida do labirinto
	public void thisIsExit(){
		isExit = true;
	}
	
	//retorna se achou ouro ou nao
	public boolean findGold(){
		return findGold;
	}
	
	//responde se essa sala é a saida
	public boolean isExit(){
		return isExit;
	}
	
	//idPara Identificação da sala ex:(2:3)
	public String getId(){
		return linha+":"+coluna;
	}
}
