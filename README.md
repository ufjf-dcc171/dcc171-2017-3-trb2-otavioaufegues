1. Identificação:
 Otavio Augusto Ferreira Rodrigues 201276030 Sistemas de informação

Trabalho 2

2. Protocolo de persistência desenvolvido:
	Para realizar a persistêcia de dados no primeiro trabalho, foram utilizados quatro arquivos de texto .txt. Dois para inicialização dos dados e dois para guardas os dados gerados da utilização do programa.
	INICIALIZAÇÃO
	- mesas.txt
	- itenscardapio.txt
	PERSISTÊNCIA
	- comandas.txt
	- itenscomanda.txt

Protocolos de cada Arquivo:

MESAS.TXT
1ª linha: numero da mesa
2ª linha: 0/1; 0=fechado e 1=aberto
3ª linha: data de abertura
4ª linha: data de fechamento

ex: 
3 <- nr mesa
1 <- aberto 
2017-12-10 01:21:28 <- data e hora de abertura
- <- data e hora de fechamento 

ITENSCARDAPIO.TXT
1ª linha: código do item
2ª linha: nome do item
3ª linha: valor do item

ex: 
2
Refrigerante
3,0
    
COMANDAS.TXT
1ª linha: numero da mesa - numero da comanda
2ª linha: 0/1; 0=fechado e 1=aberto
3ª linha: data de abertura
4ª linha: data de fechamento

ex:
2-2
0
2017-12-09 08:46:19
2017-12-09 08:47:00

INTENSCOMANDA.TXT
1ª linha: numero da mesa - numero da comanda
demais linhas: 
nr do pedido - codigo do produto - quantidade

ex:
2-2
4-1-1
5-2-1


Trabalho 1
2. Descrição:
	Esse sistema foi feito para controlar os pedidos de um resturante e calcular os valor das comandas no fechamento delas.
	O sistema também controla o status das mesas (Aberto, Fechado), uma mesa aberta pode abrir comandas onde são guardados os pedidos nela inseridos,
	É possível adicionar e excluir itens da comanda, enquanto ela estiver aberta. Após o fechamento das comandas e da mesa é emitido um relatório com
	todos os itens que foram pedidos e o valor total dos gastos na mesa.

3. Modelo de dados utilizado;

	Classe Mesa 
    	int numero;
    	boolean aberto;
    	String horaAbertura;
    	String horaFechamento;
    	ArrayList<Comanda>;

	Classe Comanda
    	int mesa;
    	String codigo;
    	boolean aberto;
    	ArrayList<ItemComanda>;

 	Classe ItemComanda 
    	private int codigoItem;
    	private int quantidade;

 	Classe ItemCardapio
    	private int codigo;
    	private String nome;
    	private double valorUnitario;


4. Construção das telas;
	O sistema todo têm três telas, que são "Janela","JanelaFechamento","JanelaComanda";
	
	Na janela Principal há duas listagens (List), onde pode-se encontrar a lista de mesas disponíveis e a lista de comandas abertas para cada mesa.
	Nessa tela também existem cinco botões: 
	- Abrir Mesa;
	- Fechar Mesa;
	- Abrir Comanda
	- Fechar Comanda
	- Fazer Pedido;
	
	Ao clicar em "Fazer Pedido", estando uma comanda selecionada, abre-se uma tela onde podem ser adicionados os itens à comanda.
	Essa tela tem os Botões de adicionar e remover itens, um comboBox com a lista de itens disponíveis, um campo para escolher a quantidade.
	E também uma tabela onde podem ser visualizados os produtos que foram adicionados a comanda.
	
	A terceira tela, de fechamento, fica visível ao clicar em "Fechar Mesa", ela mostra todos os itens que foram registrados e também a soma
	dos valores dos pedidos da mesa.
	
5. Descrição sucinta dos pontos importantes do funcionamento da interface;
	
	Para o funcionamento ideal da interface, deve-se para abrir uma comanda de uma mesa, ela deve estar aberta.
	Para fazer pedidos em uma comanda, ela também deve estar aberta e selecionada.
	Para o fechamento da mesa todas as comandas devem estar fechadas.

6. Discussão dos pontos que apresentaram maior dificuldade de implementação;
	As dificuldades enfrentadas na realização desse trabalho foram:
	- Adaptação as particularidades da linguagem na qual não estou habituado a programar;
	- Entendimento do funcionamento de certos elementos utilizados como o ComboBox;
	- Dificuldade grande em Montar um bom Layout.

7. Pontos onde podem ser realizadas melhorias futuras.

Melhorar Proteção contra erros de usuário, desabilitando botões por exemplo;
Melhorar layout das Telas;
