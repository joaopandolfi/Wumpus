

public class Main {
	
	public static void main(String[] args) { 
		Player player;
		Mapa mapa;
		Arquivos arqControl;
		String position;
		String[] ps;
		
		//construo as classes
		player = new Player();
		arqControl = new Arquivos();
		
		//leio arquivo
		arqControl.readArq();
		
		//crio mapa
		mapa = new Mapa(arqControl.mapa());
		mapa.tamMapa(arqControl.quantLinha(), arqControl.quantColuna());
		
		//passo mapa para o player [Ele nao terá acesso ao mapa todo, somente para verificar sensores]
		player.setMap(mapa);
		
		//recupero a posição inicial do player
		position = mapa.getInitialPosition();
		ps = position.split(" ");
		
		//inicializo o player
		player.initialize(Integer.parseInt(ps[0]), Integer.parseInt(ps[1]));
		
		//encontro caminho
		player.findPath();
		
		// ======= Player com Pontuacao =======
		player = new PlayerWithPontuation();
		player.setMap(mapa);
		player.initialize(Integer.parseInt(ps[0]), Integer.parseInt(ps[1]));
		player.findPath();
	  }
}
