package com.mygdx.jogo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

import java.text.DecimalFormat;

/*
	— TEXTURA — não será usada neste projeto.
	— Caso seja necessária textura em outros projetos, essa é a estrutura:
	*
	*    classe principal: Texture img;
	*    função create: img = new Texture("badlogic.jpg");
	*    função render: batch.draw(img, 0, 0);
	*    função dispose: img.dispose();
*/

public class jogo extends ApplicationAdapter {
	SpriteBatch batch;

	//Atributos do jogo
	private Integer pontuacao;        //pontuação do jogador
	private String txtPontuacao;      //texto exibido, sobre a pontuação
	private Float tempoRestante;      //tempo restante para o fim do jogo
	private String txtTemporestante;  //texto exibido, sobre o tempo restante

	//atributo da classe bolinha
	private bolinha bolinha;

	//Entradas de usuário — Processador de entradas (libGDX -> InputProcess)
	//Como inputProcess representa uma interface, será necessária uma classe para implementa-la
	private ProcessadorInput processadorInput;

	//Adicionar textos na tela: métodos BitmapFont
	//A classe BitmapFont trabalha com fontes já renderizadas, permitindo o uso de métodos com elas, na libGDX
	private BitmapFont font;
	//A fonte é gerada na função create e impressa na função render

	//ESTADOS DE JOGO — enum EstadoJogo
	private EstadoJogo estadoAtual = EstadoJogo.MENU;  //Toda vez que o jogo executar, começará no MENU

	@Override
	public void create() { //método chamado uma vez sempre que o jogo inicia
		batch = new SpriteBatch();

		//criação de nova instância da bolinha, passando uma posição e um raio
		bolinha = new bolinha(100f,100f,64f);

		//RENDERIZAÇÃO DE FONTE — 2 classes:
		//A classe FreeTypeFontGenerator gera fontes Bitmap a partir de fontes ttf (ou semelhantes)
		//Com ela, criamos uma variável com o método da classe
		//Criamos nela, uma instância que recebe como parâmetro o arquivo de fontes, com a função 'internal' da libGDX
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonte-pixel.ttf"));
		//Para gerar a fonte, configuramos o tamanho e a cor da fonte, com a classe FreeTypeFontParameter
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 22;  //definimos com método size, o tamanho da fonte como 22
		parameter.color = Color.WHITE; //e com o método color, definimos a cor branca

		//atribuímos à variável BitmapFont nosso gerador, com a função generateFont, e passando os parâmetros definidos
		font = generator.generateFont(parameter); //assim, font vai gerar a fonte a ser usada no jogo

		//Especificamos que a classe ProcessadorInput é necessária
		processadorInput = new ProcessadorInput(bolinha, this); //cria-se um atributo da classe
		Gdx.input.setInputProcessor(processadorInput); //define-se este atributo como responsável pela entrada de dados

		inicializarVariaveis(); //reinicializa as variáveis a cada reinício de jogo
	}

	@Override
	public void render() {
		//O que desenhar na tela:
		switch(estadoAtual){
			case MENU: //se o estado for MENU, chamamos a função begin de batch para descrever a tela inicial
				ScreenUtils.clear(0.5f, 0.5f, 0.5f, 1); //Define a cor da tela (cada estado terá cor diferente)

				batch.begin();
				font.draw(batch, "JOGO DAS BOLINHAS",30,Gdx.graphics.getHeight() - 30);
				font.draw(batch, "Clique em uma tecla para começar",30,Gdx.graphics.getHeight() - 60);
				batch.end();
				break;
			case JOGO: //se o estado for JOGO, basicamente todos os comandos de jogo serão executados
				ScreenUtils.clear(0.1f, 0.5f, 0.7f, 1); //Define a cor da tela (cada estado terá cor diferente)

				update(Gdx.graphics.getDeltaTime()); //método executa à cada frame do jogo, ou seja, com FPS 30, executa 30x/s
				//'getDeltaTime' é o parâmetro usado para detectar a diferença de tempo entre frames
				batch.begin();
				//Para desenhar fonte na tela, entre o batch (begin-end), colocamos a função draw do atributo font
				//A função draw pede (uma referência batch, um texto qualquer, uma posição de x, uma posição de y)
				//Vamos desenhar então, a pontuação e o tempo restante:
				font.draw(batch, txtPontuacao, 20, Gdx.graphics.getHeight() - 20);
				font.draw(batch, txtTemporestante, 20, Gdx.graphics.getHeight() - 50);

				batch.end();

				bolinha.draw(); //para desenhar bolinha na tela
				break;
			case FIM_DE_JOGO: //a tela de fim de jogo é quase igual a de menu, mas com textos diferentes
				ScreenUtils.clear(0, 0, 0, 1); //Define a cor da tela (cada estado terá cor diferente)

				batch.begin();
				font.draw(batch, "FIM DE JOGO",30,Gdx.graphics.getHeight() - 30);
				font.draw(batch, "Clique em uma tecla para voltar ao menu",30,Gdx.graphics.getHeight() - 60);
				batch.end();
				break;
		}


	}

	private void update(float delta){  //método que atualiza a lógica do jogo
		tempoRestante -= delta; //diminui o tempo restante conforme o FPS
		if(tempoRestante <= 0){ //se tempo acabar, o jogo acaba
			estadoAtual = EstadoJogo.FIM_DE_JOGO;
		}
		//passamos o tempo restante formatado, com uma casa decimal
		txtTemporestante = "Tempo: " + new DecimalFormat("##.#").format(tempoRestante);

		bolinha.update(delta);
	}

	public void aumentarPontuacao(){  //aumenta a pontuação do jogador e o tempo se ele acertar
		pontuacao++;
		tempoRestante += 0.5f;
		atualizarPontuacao();
	}

	public void dimunuirPontuacao(){  //diminui a pontuação do jogador e o tempo se ele errar
		pontuacao--;
		tempoRestante -= 0.5f;
		atualizarPontuacao();
	}

	public void atualizarPontuacao(){  //atualiza pontuação do jogador, conforme os acertos ou erros
		txtPontuacao = "Pontos: " + pontuacao;
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void inicializarVariaveis() {  //método para (re)inicializar as variáveis sempre que o jogo começar
		pontuacao = 0;
		tempoRestante = 10f;
		txtPontuacao = "Pontos: ";
		txtTemporestante = "Tempo: ";
		bolinha.setRaio(64f);  //é preciso reinicializar o raio para que o tamanho dele resete a cada reinício
	}


	//Getter e setter da variável pontuação, para acessá-la na classe ProcessadorInput
	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	//Getter e setter da variável EstadoAtual, acessada na classe ProcessadorInput
	public EstadoJogo getEstadoAtual() {
		return estadoAtual;
	}

	public void setEstadoAtual(EstadoJogo estadoAtual) {
		this.estadoAtual = estadoAtual;
	}
}
