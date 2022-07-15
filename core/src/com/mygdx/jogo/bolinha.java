package com.mygdx.jogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class bolinha {

    private ShapeRenderer shape;  //classe usada para criar formas geométricas na tela

    //variáveis que representam as coordenadas da bolinha
    private Float x;
    private Float y;



    private Float raio; //raio da bolinha

    private Float tempoMaxTeleporte;
    private Float tempoMinTeleporte;
    private Float tempoProxTeleporte;
    private Float tempoAtual;

    private Color cor; //cor da bolinha

    public bolinha(Float x, Float y, Float raio){  //método construtor da bolinha
        //reatribuição de valores
        this.x = x;
        this.y = y;
        this.raio = raio;

        shape = new ShapeRenderer(); //criamos a instância de ShapeRenderer (renderização de formas)
        cor = new Color(1,1,1,1);

        tempoMinTeleporte = 0.6f;
        tempoMaxTeleporte = 1.2f;
        teleportar(); //gera nova posição para a bolinha
    }

    public void update(float delta){  //atualiza lógica da bolinha
        tempoAtual += delta;
        if(tempoAtual >= tempoProxTeleporte){
            teleportar(); //gera nova posição para a bolinha
        }
    }

    public void draw(){ //função para desenhar bolinha na tela
        //método de desenho, pede como parâmetro uma chapa, que pode ser:
        //com contorno e preenchimento ou apenas com contorno
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(cor);
        shape.circle(x, y, raio);
        shape.end();
    }

    public void teleportar(){ //função para mover bolinha de um lado pro outro
        //random() gera valor aleatório diante de um limite
        x = MathUtils.random(raio, Gdx.graphics.getWidth() - raio); //limite entre raio e (largura da tela)-(raio)
        y = MathUtils.random(raio, Gdx.graphics.getHeight() - raio); //limite entre raio e (altura da tela)-(raio)

        //cor da bolinha será valor aleatório entre 0 e 1
        cor.r = MathUtils.random(1f); //vermelho
        cor.g = MathUtils.random(1f); //verde
        cor.b = MathUtils.random(1f); //azul

        //gera tempo aleatório
        tempoProxTeleporte = MathUtils.random(tempoMinTeleporte, tempoMaxTeleporte);
        tempoAtual = 0f; //zera contador, para contar e teleportar de novo

    }

    //Para dificultar o jogo, vamos diminuir a raio da bolinha
    public void diminuirRaio(){
        raio -= MathUtils.random(3f); //diminui raio da bolinha em valor aleatório entre 0 e 3
    }

    //Os getters e setters são necessários para acessar os valores de x, y e raio em qualquer classe
    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getRaio() {
        return raio;
    }

    public void setRaio(Float raio) {
        this.raio = raio;
    }

    //---------------------------------------------------------------------------------
    public void dispose(){
        shape.dispose();
    }
}
