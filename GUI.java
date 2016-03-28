import java.util.ArrayList;


public class GUI {

	GUI(){
		
	}
	
	public void playerDie(int linha, int coluna,String killer){
		System.out.println("\n O Player morreu na casa "+linha+" "+coluna+" por um "+killer);
	}
	
	public void playerReceivedPenality(int linha, int coluna,String killer,int penalidade){
		System.out.println("\n O Player Recebeu penalidade na casa "+linha+" "+coluna+" DE "+penalidade+" pontos por um "+killer);		
	}

	public void playerGotGold(int linha, int coluna){
		System.out.println("\n O Player pegou o ouro na casa "+linha+" "+coluna);		
	}

	public void playerOutLabirint(){
		System.out.println("\n O Player Saiu do labirinto ");		
	}

	public void playerOutLabirintWithPontuation(int pontuation){
		System.out.println("\n O Player Saiu do labirinto Com "+(pontuation*(-1))+" Pontos");		
	}
	
	public void showBestPontuation(int best){
		System.out.println("\n A pontucao para o melhor caminho é "+(best*(-1))+" Pontos");		
	}
	
	public void path(String path){
		System.out.println("\n Caminho percorrido: "+path);		
	}

	public void bestPath(ArrayList<String> path){
		//recebe o caminho de volta
		int tam = path.size();
		System.out.println("\n Melhor caminho: ");
		//imprimo Ida
		for(int i = (tam-1);i>0;i--)
			System.out.print(path.get(i));		
		//imprimo retorno
		for(String cur : path)
			System.out.print(cur);		
		System.out.println("");		
	}

	public void debug(String msg){
		System.out.println("\n DEBUG: "+msg);		
	}
	
	public void iAmPlayerWithPontuation(){
		System.out.println("\n ==== PLAYER COM PONTUACAO ==== \n");				
	}
	
	public void showMap(Mapa mapa){
		//recupero dados
		int[][] map = mapa.getMapForPrint();
		int linha  = mapa.getTamLinhaForPrint();
		int coluna = mapa.getTamColunaForPrint();

		System.out.println("\n================== MAPA =====================\n");		
		for(int i=0;i<linha;i++){
			for(int j=0;j<coluna;j++)
				System.out.print(map[i][j]);
			System.out.println("");
		}
		System.out.println("\n=============================================\n");		
	}
}
