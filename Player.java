import java.util.ArrayList;


public class Player {
	protected Lista fronteiras;
	protected Lista visitados;
	protected Lista estimados;
	
	protected Sala currentSala;
	protected GUI gui;
	
	protected Mapa mapa; //tem o mapa mas n conhece tudo [somente para controle]
	
	protected boolean discoveredGold;
	protected boolean exitingLabirint;
	protected boolean exitLabirint;
	private boolean died;
	
	protected int totalPeso;
	
	protected String caminho;
	
	protected int PESO_BURACO;
	protected int PESO_CHEIRO;
	protected int PESO_MOVIMENTO;
	protected int PESO_VISITADO;
	protected int PESO_ENCONTRAR_OURO;

	
	Player(){
		fronteiras = new Lista();
		visitados = new Lista();
		estimados = new Lista();
		gui = new GUI();
		discoveredGold = false;
		exitingLabirint = false;
		exitLabirint = false;
		died = false;
		totalPeso = 0; 
		caminho = "";
		//seto pesos
		PESO_BURACO = 10;
		PESO_CHEIRO = 20;
		PESO_MOVIMENTO = 0;
		PESO_VISITADO = 2;
		PESO_ENCONTRAR_OURO = -100;
	}

	public void setMap(Mapa mapa){
		this.mapa = mapa;
	}
	
	public void initialize(int linha, int coluna){
		//inicializo adicionando a sala de inico
		currentSala = new Sala(linha,coluna);
		currentSala.thisIsExit(); //marco que esta sala e a saida
		currentSala.setAnterior("");//seto anterior como vazia
		visitados.add(currentSala);
	}
	
	public void findPath(){
		while(!exitLabirint && !died){//enquanto nao saiu do labirinto & nao morreu 
			move();
		}
		//imprimo mensagens
		gui.showMap(mapa);
		if(!died)
			gui.playerOutLabirint();
		gui.path(caminho);
	}
	
	// ========= CONTROLE =========
	
	//encontrou um Buraco
	protected void foundBuraco(){
		totalPeso+= PESO_BURACO;
		died = true;
		gui.playerDie(currentSala.getLinha(), currentSala.getColuna(),"BURACO");
	}
	
	//encontrou o Wumpus
	protected void foundWumpus(){
		totalPeso += PESO_CHEIRO;
		died = true;
		gui.playerDie(currentSala.getLinha(), currentSala.getColuna(),"WUMPUS");
	}
	
	//saiu do labirinto
	public boolean exitLabirint(){
		return exitLabirint;
	}
	
	// ========= MOVIMENTACAO =======
	
	//move player
	protected boolean move(){
		Sala next = null;
		//guarda no caminho
		caminho += "["+currentSala.getId()+"] ";
		//atualizo peso total
		totalPeso += PESO_MOVIMENTO;
		//informa que a sala foi visitada
		currentSala.foiVisitado(PESO_VISITADO);
		if(discoveredGold){//se ja descobriu o ouro
			back();
			return true;
		}
		//verifica o que tem dentro da sala
		switch(mapa.whatIsInThisPlace(currentSala.getLinha(), currentSala.getColuna())){
			case 1://ouro
				currentSala.discoverGold();
				gui.playerGotGold(currentSala.getLinha(), currentSala.getColuna());
				totalPeso += PESO_ENCONTRAR_OURO;
				discoveredGold = true;
				exitingLabirint = true;
				back();
				return true;
			case 2://buraco
				foundBuraco();
				return false;
			case 3://wumpus
				foundWumpus();
				return false;
			default://nada				
		}
		//recupero fronteiras
		if(!getFrontiers()){//não tem fronteiras não visitadas
			if(getVisitedFrontiers()){//verifica e recupera fronteira dos não visitados

				//recupero a melhor sala para se ir
				next = getBestRoom();
				
				//se nao estiver voltando
				if(!currentSala.getAnterior().equals(next.getId()))
					next.setAnterior(currentSala.getId()); //seto a sala anterior para a atual
				
				//atualizo a sala corrente para a prox
				currentSala = next;
				return true;		
			}
		}else{//tem fronteiras não visitadas
			
			//verifico os sensores e atualizo os estados das fronteiras
			switch(verifySensors()){
				case 0: //vazio
					fronteiras.todosVazios();
				break;
				case 1: //vento
					fronteiras.todosPossiveisBuraco(PESO_BURACO);
				break;
				case 2: // cheiro
					fronteiras.todosPossiveisWumpus(PESO_CHEIRO);
				break;
				case 3: //cheiro e vento
					fronteiras.todosPossiveisBuracoEWumpus(PESO_BURACO, PESO_CHEIRO);
				break;
			}
			//recupero a melhor sala para se ir
			next = getBestRoom();
			//se nao estiver voltando
			if(!currentSala.getAnterior().equals(next.getId()))
				next.setAnterior(currentSala.getId()); //seto a sala anterior para a atual

			//removo da lista de fronteiras
			fronteiras.remove(next);
			//insiro as fronteiras nos estimados
			estimados.mergeLists(fronteiras);
			//adiciono nos visitados
			visitados.add(next);
			currentSala = next;
			return true;
		}
		return false;
	}

	//volta
	protected void back(){
		//se estiver saindo do labirinto
		if(exitingLabirint){
			if(currentSala.isExit()){//se a sala atual for a saida
				exitLabirint = true;
				return;
			}
		}
		//volta
		currentSala = visitados.getSala(currentSala.getAnterior());		
	}
	
	//retorna a melhor sala para ir
	protected Sala getBestRoom(){
		//recupero a sala com o menor peso
		Sala best = fronteiras.getBestRoom();
		Sala ant = visitados.getSala(currentSala.getAnterior());
		if(ant != null){ //se existir um anterior
			//verifico se é melhor voltar uma casa que ir para melhor sala das fronteiras
			if(best.pesoTotal() > ant.pesoTotal()){
				best = ant;
			}
		}
		return best;
	}
	
	//verifica sensores [cheiro e vento = 3 / cheiro - 2 / vento - 1 / vazio - 0]
	protected int verifySensors(){
		ArrayList<Sala> salas = fronteiras.getArrayListSalas();
		boolean cheiro = false;
		boolean vento = false;
		//percorro as salas e verifico os sensores
		for(Sala cur: salas){	
			if(mapa.verifySensors(cur.getLinha(), cur.getColuna()) == 1)
				vento = true;
			else if(mapa.verifySensors(cur.getLinha(), cur.getColuna()) == 2)
				cheiro = true;
		}
		//retorno o que os sensores detectaram
		if(cheiro && vento)
			return 3;
		else if(cheiro)
			return 2;
		else if(vento)
			return 1;
		
		return 0;	
	}
	
	// =============== CONTROLE DE FRONTEIRAS =================
	
	// === FRONTEIRAS JA VISITADAS
	
	protected boolean getVisitedFrontiers(){
		fronteiras = new Lista(); //crio a nova lista de fronteiras [garbage collector seu lindo!]
		boolean ct = false;
		// ==== PEGA FRONTEIRA DA SALA DE CIMA ====
		if(verifyAndAddVisitedFrontier(mapa.getFrontierTop(currentSala))) //recupera fronteira de cima
				ct = true;
		// ==== PEGA FRONTEIRA DA SALA DE BAIXO ====
		if(verifyAndAddVisitedFrontier(mapa.getFrontierBot(currentSala))) //recupera fronteira de cima
				ct = true;
		// ==== PEGA FRONTEIRA DA SALA DA ESQUERDA ====
		if(verifyAndAddVisitedFrontier(mapa.getFrontierLeft(currentSala))) //recupera fronteira de cima
				ct = true;
		// ==== PEGA FRONTEIRA DA SALA DA ESQUERDA ====
		if(verifyAndAddVisitedFrontier(mapa.getFrontierRight(currentSala))) //recupera fronteira de cima
				ct = true;
		
		return ct;
	}

	//verifica e adiciona a fronteira
	protected boolean verifyAndAddVisitedFrontier(Sala front){
		if(front != null){
			if(visitados.containsSala(front.getId())){//se ja foi visitado adiciona
					fronteiras.add(visitados.getSala(front.getId()));//adiciona na lista fronteiras
					return true;
			}
		}
		return false;		
	}

	
	// === FRONTEIRAS NAO VISITADAS
	
	//recupera fronteiras
	protected boolean getFrontiers(){
		fronteiras = new Lista(); //crio a nova lista de fronteiras [garbage collector seu lindo!]
		boolean ct = false;
		// ==== PEGA FRONTEIRA DA SALA DE CIMA ====
		if(verifyAndAddFrontier(mapa.getFrontierTop(currentSala))) //recupera fronteira de cima
				ct = true;
		// ==== PEGA FRONTEIRA DA SALA DE BAIXO ====
		if(verifyAndAddFrontier(mapa.getFrontierBot(currentSala))) //recupera fronteira de cima
				ct = true;
		// ==== PEGA FRONTEIRA DA SALA DA ESQUERDA ====
		if(verifyAndAddFrontier(mapa.getFrontierLeft(currentSala))) //recupera fronteira de cima
				ct = true;
		// ==== PEGA FRONTEIRA DA SALA DA ESQUERDA ====
		if(verifyAndAddFrontier(mapa.getFrontierRight(currentSala))) //recupera fronteira de cima
				ct = true;

		return ct;
	}
	
	//verifica e adiciona a fronteira
	protected boolean verifyAndAddFrontier(Sala front){
		if(front != null){
			if(!visitados.containsSala(front.getId())){//se ja foi visitado nao adiciona
				if(estimados.containsSala(front.getId())){//se ja foi estimado
					fronteiras.add(estimados.getSala(front.getId()));//adiciona o da lista de estimados
					return true;
				}else{
					fronteiras.add(front); //se nao adiciona esse novo
					return true;
				}
			}
		}
		return false;		
	}
}
