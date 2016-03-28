# Wumpus
Implementação do mundo de wumpus

# Explicação
 >Implementação de IA para resolver o problema do Mundo de Wumpus 

 > Link para explicação do problema: http://www.cs.rochester.edu/trac/quagents/wiki/Wumpus%20World#no1

 > O personagem NÃO conhece o mapa todo
 
 > Implementado duas versões
 
 #Versão 1
  > Não há custos de movimentação e o objetivo é capturar o tesouro sem cair em nenhum abismo ou ser pego pelo Wumpus
  
  > Caso seja pego ou caia no abismo, o personagem morre
  
  > Deverá mostrar o melhor caminho
  
  #Versão 2
   > Cada movimentação custa -1 pontos
   
   > Pegar o pote de ouro ganha-se 200 pontos
   
   > Encontrar o Wumpus custa -1000 pontos
   
   > Cair em um abismo custa -20 pontos
   
   > Objetivo é sair do labirinto com maior pontuação possível
   
   > O Personagem não morre, somente perde pontuação de acordo com as informadas acima

#Compilar
 > Navegue pelo terminal até a pasta onde estão os arquivos

 > Execute: javac Main.java

#Executar
 > Dentro da pasta após compilar

 > Execute: 

```
 java Main <arquivo_do_mapa>
```

#Arquivo de teste
 > mapa.txt

#Configuração do arquivo de entrada
 > Modelo:

```
<numero_de_linhas> <numero_de_colunas>

<matriz_do_mapa>

```
 > 0 -> Espaço vazio [Nada]

 > 1 -> Player
 
 > 2 -> Ouro
 
 > 3 -> Abismo
 
 > 4 -> Wumpus

 > Exemplo:
 
```
5 5
0 0 0 4 2
0 0 0 0 0
0 3 0 0 0
0 0 0 0 0
1 0 0 0 3
```
