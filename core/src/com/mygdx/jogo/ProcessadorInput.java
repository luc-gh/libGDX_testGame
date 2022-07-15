package com.mygdx.jogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class ProcessadorInput implements InputProcessor { //implementa a classe com os métodos de entrada de dados


    private bolinha bolinha;
    private com.mygdx.jogo.jogo jogo;

    //É necessário um método construtor para passar as classes jogo e bolinha para esta
    public ProcessadorInput(bolinha bolinha, com.mygdx.jogo.jogo jogo){
        //No método, atualizamos as referências às classes
        this.jogo = jogo;
        this.bolinha= bolinha;
    }

    //A cada vez que o jogador clicar numa tecla, deve-se verificar qual o estado atual de jogo
    @Override
    public boolean keyDown(int keycode) {
        if(jogo.getEstadoAtual() == com.mygdx.jogo.EstadoJogo.MENU){
            jogo.setEstadoAtual(com.mygdx.jogo.EstadoJogo.JOGO);
            jogo.inicializarVariaveis();
        }else if(jogo.getEstadoAtual() == com.mygdx.jogo.EstadoJogo.FIM_DE_JOGO) {
            jogo.setEstadoAtual(com.mygdx.jogo.EstadoJogo.MENU);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    //Será usado o método touchDown para detectar o click na tela
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //button armazena um número correspondente ao botão clicado (padrão: 0 -> botão esquerdo do mouse)
        //verificamos então, se o button é 0 e se o estado de jogo é JOGO (para não executar nada em outros estados)
        if(button == 0 && jogo.getEstadoAtual() == com.mygdx.jogo.EstadoJogo.JOGO){
            //É preciso verificar se a distância entre a posição do mouse e da bolinha é <= ao raio da bolinha
            //A classe Vector2 contém métodos com operações de vetores. Um deles, 'dst', calcula a distância entre dois pontos
            float distancia = Vector2.dst(bolinha.getX(), bolinha.getY(), screenX, Gdx.graphics.getHeight() - screenY);
            //x e y do primeiro e segundo objetos (bolinha e mouse)
            /*
            OBS: screenX conta a partir da janela do jogo, enquanto screenY conta a partir do início da tela
                 por isso, é necessária a subtração da altura da tela pela posição (y) do mouse
            */

            //Se o click tiver sido na bolinha, o jogador acertou
            if(distancia <= bolinha.getRaio()){
                bolinha.teleportar();           //bolinha muda de lugar
                jogo.aumentarPontuacao();  //pontuação aumenta
                bolinha.diminuirRaio();         //tamanho da bolinha diminui
            } else {
                if(jogo.getPontuacao() > 0){
                    jogo.dimunuirPontuacao();
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
