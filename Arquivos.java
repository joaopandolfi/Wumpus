import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Responsável por ler o Arquivo e gerar a matriz
 */

public class Arquivos {
	private int quantLinha;
	private int quantColuna;
	private int matMapa[][];
	
	Arquivos(){

	}

	public int quantLinha(){
		return quantLinha;
	}
	
	public int quantColuna(){
		return quantColuna;
	}
	
	public int[][] mapa(){
		return matMapa;
	}
	
	
	//le arquivo
	public void readArq(){
		File arquivo = new File("mapa.txt");
		FileReader arqLeitura;
		BufferedReader leitor;
		String linha;
		String[] val;
		try {
			arqLeitura = new FileReader(arquivo);
			leitor = new BufferedReader(arqLeitura);
			
			try {
				//leio primeira linha
				linha = leitor.readLine();
				val = linha.split(" ");
				quantLinha = Integer.parseInt(val[0]);
				quantColuna = Integer.parseInt(val[1]);
				//crio a matriz
				matMapa = new int[quantLinha][quantColuna];
				
				//pulo uma linha na leitura (Não precisa)
				//linha = leitor.readLine();
								
				//leio a matriz
				for(int i=0;i<quantLinha;i++){
					linha = leitor.readLine();
					val = linha.split(" ");	//quebro os dados
					for(int j=0;j<quantColuna;j++){
						matMapa[i][j] = Integer.parseInt(val[j]);
					}
				}
				
			} catch (IOException e) {
				System.out.println("Falha ao ler o mapa");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Falha ao ler o mapa");
			System.exit(0);
		}
	}
}
