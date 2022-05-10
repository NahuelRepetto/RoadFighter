package RoadFighterTest;

import static org.junit.Assert.*;

import org.junit.Test;

import RoadFighter.AutoJugador;
import RoadFighter.AutoObstaculo;
import RoadFighter.CamionObstaculo;
import RoadFighter.Punto;


public class VehiculoTest {

	@Test
	public void vehiculoIniciaEnReposo() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");
		assertEquals((int) pj.getVelocidadActual(), 0);
	}

	@Test
	public void acelerarIncrementaEnUnoLaVelocidad() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");
		pj.acelerar();
		assertEquals((int) pj.getVelocidadActual(), 1);
	}

	@Test
	public void NoPuedoAcelerarPorEncimaDeLaVelocidadMaxima() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");

		for (int i = 0; i < 100; i++) {
			pj.acelerar();
		}

		assertEquals((int) pj.getVelocidadActual(), 100);
		assertFalse(pj.acelerar());

		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));

		for (int i = 0; i < 80; i++) {
			bot.acelerar();
		}

		assertEquals((int) bot.getVelocidadActual(), 80);
		assertFalse(bot.acelerar());

		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));

		for (int i = 0; i < 60; i++) {
			bot2.acelerar();
		}

		assertEquals((int) bot2.getVelocidadActual(), 60);
		assertFalse(bot2.acelerar());
	}
	
	@Test
	public void paraAvanzoOMovermeALosLadosTengoQueTenerEnFalseLaJugabilidadBloqueada() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");
		pj.acelerar();
		
		assertTrue(pj.getJuganilidadBloqueada());
		
		assertFalse(pj.avanzar());	
		
		pj.habilitarDesabilitarJugabilidad();

		assertFalse(pj.getJuganilidadBloqueada());
		
		assertTrue(pj.avanzar());	
	}

	@Test
	public void avanzoSegunLaVelocidadActual() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");
		pj.habilitarDesabilitarJugabilidad();
		pj.acelerar();// VELOCIDAD=1
		pj.acelerar();// VELOCIDAD=2
		pj.acelerar();// VELOCIDAD=3
		pj.avanzar();
		assertEquals((int) pj.getCoordenada().getY(), 3);
		pj.acelerar();// VELOCIDAD=4
		pj.acelerar();// VELOCIDAD=5
		pj.avanzar();
		pj.avanzar();
		assertEquals((int) pj.getCoordenada().getY(), 13);
	}
		
	@Test
	public void FrenarDecrementaLaVelocidadEnUno() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");
		pj.acelerar();// VELOCIDAD=1
		pj.acelerar();// VELOCIDAD=2
		pj.acelerar();// VELOCIDAD=3
		assertEquals((int) pj.getVelocidadActual(), 3);

		pj.frenar();// VELOCIDAD=2
		
		assertEquals((int) pj.getVelocidadActual(), 2);
	}
	
	@Test
	public void alFrenarLaVelocidadNoBajaDeCero() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");
		pj.acelerar();// VELOCIDAD=1
		pj.acelerar();// VELOCIDAD=2
		pj.acelerar();// VELOCIDAD=3
		assertEquals((int) pj.getVelocidadActual(), 3);

		pj.frenar();// VELOCIDAD=2
		pj.frenar();// VELOCIDAD=1
		pj.frenar();// VELOCIDAD=0
		pj.frenar();// SIGUE=0
		pj.frenar();// SIGUE=0
		pj.frenar();// SIGUE=0
		
		assertFalse(pj.frenar());
		assertEquals((int) pj.getVelocidadActual(), 0);
	}

	@Test
	public void meMuevoDerecha() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");	
		pj.habilitarDesabilitarJugabilidad();

		assertEquals((int) pj.getCoordenada().getX(), 0);

		pj.irADerecha();//COORDENADA X=1
		assertEquals((int) pj.getCoordenada().getX(), 1);
		
		pj.irADerecha();//COORDENADA X=2
		assertEquals((int) pj.getCoordenada().getX(), 2);
		
		pj.irADerecha();//COORDENADA X=3
		assertEquals((int) pj.getCoordenada().getX(), 3);
	}

	@Test
	public void meMuevoIzquiuera() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");	
		pj.habilitarDesabilitarJugabilidad();

		assertEquals((int) pj.getCoordenada().getX(), 0);

		pj.irAIzquierda();//COORDENADA Y=-1
		assertEquals((int) pj.getCoordenada().getX(), -1);
		
		pj.irAIzquierda();//COORDENADA Y=-2
		assertEquals((int) pj.getCoordenada().getX(), -2);
		
		pj.irAIzquierda();//COORDENADA Y=-3
		assertEquals((int) pj.getCoordenada().getX(), -3);
	}
	
	@Test
	public void luegoDeChocarReapareceEnMiCoordenadaYPeroConVelocidadEnCeroYcoordenadaXEnLaOriginal() {
		AutoJugador pj = new AutoJugador(new Punto(50, 0), "Nahuel");
		pj.habilitarDesabilitarJugabilidad();
		
		pj.acelerar();// VELOCIDAD=1
		pj.acelerar();// VELOCIDAD=2
		pj.acelerar();// VELOCIDAD=3
		pj.avanzar();//COORDENADA Y=3
		pj.avanzar();//COORDENADA Y=6
		pj.irADerecha();//COORDENADA X=1
		pj.irADerecha();//COORDENADA X=2
		
		assertEquals((int) pj.getVelocidadActual(), 3);
		assertEquals((int) (int) pj.getCoordenada().getX(), 52);
		assertEquals((int) (int) pj.getCoordenada().getY(), 6);

		assertFalse(pj.getExplote());
		assertFalse(pj.getReapareci());
		
		pj.explotar();
		
		assertTrue(pj.getExplote());
		assertTrue(pj.getReapareci());

		assertEquals((int) pj.getVelocidadActual(), 0);
		assertEquals((int) (int) pj.getCoordenada().getX(), 50);
		assertEquals((int) (int) pj.getCoordenada().getY(), 6);
	}
}