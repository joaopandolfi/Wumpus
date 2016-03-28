
public class Mapa {

	private int[][] mapa;
	
	private int tamLinha;
	private int tamColuna;
	
	Mapa(int[][] mapa){
		this.mapa = mapa;
	}

	public void tamMapa(int tamLinha, int tamColuna){
		this.tamColuna = tamColuna;
		this.tamLinha = tamLinha;
	}
	
	// ===== FUNCOES USADAS PELA GUI ======
	
	public int[][] getMapForPrint(){
		return mapa;
	}
	
	public int getTamLinhaForPrint(){
		return tamLinha;
	}

	public int getTamColunaForPrint(){
		return tamColuna;
	}
	
	// ===== FUNCOES DO MAPA =======
	
	public String getInitialPosition(){
		for(int i=0;i<tamLinha;i++)
			for(int j=0;j<tamColuna;j++)
				if(mapa[i][j] == 1)
					return i+" "+j;
							
		return tamLinha+" 0";
	}
	
	public void imInHere(int linha, int coluna){
		//informo que o player esta ou passou por aqui
		mapa[linha][coluna] = 9;
	}
	
	public void imOuThis(int linha, int coluna){
		//informo que o player passou por la mas voltou
		mapa[linha][coluna] = 8;
	}
	
	//recupera o que tem na sala / 0 - nada / 1 - ouro / 2 - buraco / 3 - wumpus
	public int whatIsInThisPlace(int linha, int coluna){
		if(mapa[linha][coluna] == 2)//Ouro
			return 1;
		if(mapa[linha][coluna] == 3)//Buraco
			return 2;
		if(mapa[linha][coluna] == 4)//Wumpus
			return 3;

		return 0;
	}
	
	// ======= RECUPERA FRONTEIRAS ========
	
	//recupera fronteira de cima
	public Sala getFrontierTop(Sala cur){
		Sala next = null;
		
		//se nao esiver no mapa retorna nulo
		if(!inMap(cur.getLinha()-1, cur.getColuna()))
			return null;
		
		//retorno a sala fronteira
		next = new Sala(cur.getLinha()-1,cur.getColuna());
		
		return next;
	}
	

	//recupera fronteira de baixo
	public Sala getFrontierBot(Sala cur){
		Sala next = null;
		
		//se nao esiver no mapa retorna nulo
		if(!inMap(cur.getLinha()+1, cur.getColuna()))
			return null;
		
		//retorno a sala fronteira
		next = new Sala(cur.getLinha()+1,cur.getColuna());
		
		return next;
	}
	
	//recupera fronteira da direita
	public Sala getFrontierRight(Sala cur){
		Sala next = null;
		
		//se nao esiver no mapa retorna nulo
		if(!inMap(cur.getLinha(), cur.getColuna()+1))
			return null;
		
		//retorno a sala fronteira
		next = new Sala(cur.getLinha(),cur.getColuna()+1);
		
		return next;
	}
	
	//recupera fronteira da esquerda
	public Sala getFrontierLeft(Sala cur){
		Sala next = null;
		
		//se nao esiver no mapa retorna nulo
		if(!inMap(cur.getLinha(), cur.getColuna()-1))
			return null;
		
		//retorno a sala fronteira
		next = new Sala(cur.getLinha(),cur.getColuna()-1);
		
		return next;
	}


	// ===== VERIFICA SENSORES =====

	// 0 Nada / 1 Vento / 2 cheiro 
	public int verifySensors(int linha, int coluna){
		if(mapa[linha][coluna] == 3)
			return 1;
		else if(mapa[linha][coluna] == 4)
			return 2;
		
		return 0;
	}

	
	//verifica se a coordenada esta no mapa
	private boolean inMap(int linha, int coluna){
		if(linha >= tamLinha || linha < 0)
			return false;
		if(coluna >= tamColuna || coluna <0)
			return false;

		return true;
	}
}
