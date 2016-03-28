import java.util.ArrayList;


public class Lista {
	private ArrayList<Sala> salas;
	
	Lista(){
		salas = new ArrayList<Sala>();
	}

	public int size(){
		return salas.size();
	}
	
	//adiciona uma sala
	public void add(Sala sala){
		this.salas.add(sala);
	}

	//junta as duas lista prevalecendo a nova
	public void mergeLists(Lista nova){
		ArrayList<Sala> novos = nova.getArrayListSalas();
		for(Sala cur: novos){
			addOrSubstitute(cur);
		}
	}
	
	//adiciona uma nova sala, se ja tiver substitui a antiga
	public void addOrSubstitute(Sala sala){
		for(Sala cur: salas){
			//se for a mesma sala, substitui
			if(cur.getId().equals(sala.getId())){
				salas.remove(cur);
				salas.add(sala);
				return;
			}
		}
	}
	
	//remove peça da lista
	public void remove(String pos){
		Sala aux = getSala(pos);
		salas.remove(aux);
	}

	public void remove(Sala sala){
		salas.remove(sala);
	}
	
	public void remove(int x, int y){
		remove(x+""+y);
	}
	
	// ======= FUNCOES DE RETORNO =========
	
	//recupera uma sala
	public Sala get(int x, int y){
		return getSala((x+1)+""+(y+1)); //monta string e chama o metodo
	}
	
	//retorna a sala de acordo como id
	public Sala getSala(String id){
		for(Sala cur: salas){
			if(cur.getId().equals(id)){
				return cur;
			}
		}
		return null;
	}
	
	//retorna a peca que estiver na pos
	public boolean containsSala(String pos){
		for(Sala cur: salas){
			if(cur.getId().equals(pos)){
				return true;
			}
		}
		return false;
	}
	
	//retorna arrayLista das salas
	public ArrayList<Sala> getArrayListSalas(){
		return salas;
	}
	
	//retorno a sala com menor peso
	public Sala getBestRoom(){
		Sala best = null;
		int melhor = 999999; //considere infinito =(
		for(Sala cur: salas){ //pego a sala com menor peso
			if(cur.pesoTotal() < melhor){
				best = cur;
				melhor = cur.pesoTotal();
			}
		}		
		return best;
	}
	
	// ===== CONTROLE DE PROBABILIDADES ====
	
	//todas as salas livres
	public void todosVazios(){
		for(Sala cur: salas){
			cur.salaLivre();
		}		
	}
	
	//todos Possiveis Buraco e Wumpus
	public void todosPossiveisBuracoEWumpus(int pesoBuraco, int pesoWumpus){
		for(Sala cur: salas){
			cur.possivelBuracoEWumpus(pesoBuraco, pesoWumpus);
		}		

	}
	
	//todos Possiveis Buraco
	public void todosPossiveisBuraco(int pesoBuraco){
		for(Sala cur: salas){
			cur.possivelBuraco(pesoBuraco);
		}		
		
	}
	
	//todos Possiveis Wumpus
	public void todosPossiveisWumpus(int pesoWumpus){
		for(Sala cur: salas){
			cur.possivelWumpus(pesoWumpus);
		}		
	}

}
