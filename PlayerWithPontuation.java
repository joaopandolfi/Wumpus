import java.util.ArrayList;


public class PlayerWithPontuation extends Player{
	
	protected ArrayList<String> path;
	protected int bestPontuation;
	
	public PlayerWithPontuation() {
		path = new ArrayList<String>();
		PESO_BURACO = 20;
		PESO_CHEIRO = 1000;
		PESO_MOVIMENTO = 1;
		PESO_VISITADO = 1;
		PESO_ENCONTRAR_OURO = -200;
		bestPontuation = 0;
	}

	
	public void findPath(){
		gui.iAmPlayerWithPontuation();
		while(!exitLabirint){//enquanto nao saiu do labirinto 
			move();
		}
		//imprimo mensagens
		gui.showMap(mapa);
		gui.playerOutLabirintWithPontuation(totalPeso);
		gui.path(caminho);
		gui.bestPath(path);
		gui.showBestPontuation(bestPontuation);
	}
	
	//encontrou um Buraco
	protected void foundBuraco(){
		totalPeso+= PESO_BURACO;
		gui.playerReceivedPenality(currentSala.getLinha(), currentSala.getColuna(),"BURACO",PESO_BURACO);
		moveOn();
	}
	
	//encontrou o Wumpus
	protected void foundWumpus(){
		totalPeso += PESO_CHEIRO;
		gui.playerReceivedPenality(currentSala.getLinha(), currentSala.getColuna(),"WUMPUS",PESO_CHEIRO);
		moveOn();
	}
	
	
	protected void moveOn(){
		Sala next = null;
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
				return;		
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
		}
	}
	
	//volta
	protected void back(){
		path.add("["+currentSala.getId()+"] ");
		
		//contabiliza a melhor pontuacao
		contabilizeBestPontuation();
		
		//faz a mesma coisa
		super.back();
	}
	
	protected void contabilizeBestPontuation(){
		//verifica o que tem dentro da sala
		switch(mapa.whatIsInThisPlace(currentSala.getLinha(), currentSala.getColuna())){
			case 1://ouro
				bestPontuation += PESO_ENCONTRAR_OURO;
			break;
			case 2://buraco
				bestPontuation+= PESO_BURACO;
			break;
			case 3://wumpus
				bestPontuation+= PESO_CHEIRO;
			break;
			default://nada				
				bestPontuation+= PESO_MOVIMENTO;
		}
	}
}
