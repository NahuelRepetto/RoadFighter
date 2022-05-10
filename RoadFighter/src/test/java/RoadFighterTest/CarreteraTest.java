package RoadFighterTest;

import static org.junit.Assert.*;

import org.junit.Test;

import RoadFighter.AutoJugador;
import RoadFighter.AutoObstaculo;
import RoadFighter.CamionObstaculo;
import RoadFighter.Carretera;
import RoadFighter.ManchaDeAceite;
import RoadFighter.Pozo;
import RoadFighter.Punto;

public class CarreteraTest {

	@Test
	public void noPuedoInsertarUnObjetoFueraDeLosLimites() {
		Carretera carretera = new Carretera (300, 1000);	
		
		assertTrue(carretera.getLimDer() == 150);
		assertTrue(carretera.getLimIzq() == -150);
		
		AutoJugador pj = new AutoJugador(new Punto(150, 0), "Nahuel");	
		AutoObstaculo bot = new AutoObstaculo(new Punto(-150, 0));
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(135, 0));
		ManchaDeAceite aceite = new ManchaDeAceite(new Punto(-135, 0));
		Pozo pozo = new Pozo(new Punto(0, -1));
		
		assertFalse(carretera.agregarObjeto(pj));
		assertFalse(carretera.agregarObjeto(bot));
		assertFalse(carretera.agregarObjeto(bot2));
		assertFalse(carretera.agregarObjeto(aceite));
		assertFalse(carretera.agregarObjeto(pozo));		
	}
	
	@Test
	public void puedoInsertarUnObjetoSiEstaDentroDeLosLimites() {
		Carretera carretera = new Carretera (300, 1000);	
		
		assertTrue(carretera.getLimDer() == 150);
		assertTrue(carretera.getLimIzq() == -150);
		
		AutoJugador pj = new AutoJugador(new Punto(0, 30), "Nahuel");	
		AutoObstaculo bot = new AutoObstaculo(new Punto(50, 50));
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(100, 100));
		ManchaDeAceite aceite = new ManchaDeAceite(new Punto(-130, 50));
		Pozo pozo = new Pozo(new Punto(7, 50));		
		
		assertTrue(carretera.agregarObjeto(pj));
		assertTrue(carretera.agregarObjeto(bot));
		assertTrue(carretera.agregarObjeto(bot2));
		assertTrue(carretera.agregarObjeto(aceite));
		assertTrue(carretera.agregarObjeto(pozo));		
	}
	
	@Test
	public void carreteraDetectaQueUnVehiculoSalioDeLosLimites() {
		Carretera carretera = new Carretera (300, 1000);	
		
		assertTrue(carretera.getLimDer() == 150);
		assertTrue(carretera.getLimIzq() == -150);
		
		AutoJugador pj = new AutoJugador(new Punto(134, 50), "Nahuel");	
		pj.habilitarDesabilitarJugabilidad();
		
		assertTrue(carretera.agregarObjeto(pj));
		
		carretera.actualizar();			//LO DETECTA DENTRO DE LOS LIMITES, NO EXPLOTA	
		
		assertFalse(pj.getExplote());
		
		assertEquals((int) pj.getCoordenada().getX(), 134);
		
		pj.irADerecha();				//AL IRME UN LUGAR A LA DERECHA EL CUERPO DEL VEHICULO TOCA EL LIMITE DERECHO
		
		assertEquals((int) pj.getCoordenada().getX(), 135);
		
		carretera.actualizar();			//LO DETECTA Y HACE EXPLOTAR EL VEHICULO		
		
		assertTrue(pj.getExplote());				
	}
	
	@Test
	public void carreteraDetectaQueLosChoques() {
		Carretera carretera = new Carretera (300, 1000);	
		
		assertTrue(carretera.getLimDer() == 150);
		assertTrue(carretera.getLimIzq() == -150);
		
		AutoJugador pj = new AutoJugador(new Punto(50, 95), "Nahuel");	
		pj.habilitarDesabilitarJugabilidad();
		Pozo pozo = new Pozo(new Punto(50, 130));	
		
		assertTrue(carretera.agregarObjeto(pj));
		assertTrue(carretera.agregarObjeto(pozo));
		
		carretera.actualizar();			//LO DETECTA DENTRO DE LOS LIMITES, NO HAY CHOQUES
		
		pj.acelerar();	 	//VEL = 1
		pj.acelerar();	 	//VEL = 2
		pj.acelerar();  	//VEL = 3
		pj.acelerar();	 	//VEL = 4
		pj.acelerar();	 	//VEL = 5
		
		pj.avanzar();
		pj.avanzar();		//ACA YA DEBERIAN COLISIONAR
		
		assertFalse(pj.getExplote());
		assertFalse(pozo.getExplote());
		
		carretera.actualizar();			//DETECTA EL CHOQUE, Y LOS HACE EXPLOTAR
		
		assertTrue(pj.getExplote());
		assertTrue(pozo.getExplote());			
	}
	
	@Test
	public void alExplotarAlgoQueNoEsAutoJugadorEsEliminadoDeLosObjetosDeLaCarretera() {
		Carretera carretera = new Carretera (300, 1000);	
		
		assertTrue(carretera.getLimDer() == 150);
		assertTrue(carretera.getLimIzq() == -150);
		
		AutoJugador pj = new AutoJugador(new Punto(50, 95), "Nahuel");	
		pj.habilitarDesabilitarJugabilidad();
		Pozo pozo = new Pozo(new Punto(50, 130));	
		
		assertTrue(carretera.agregarObjeto(pj));
		assertTrue(carretera.agregarObjeto(pozo));
		
		carretera.actualizar();			//LO DETECTA DENTRO DE LOS LIMITES, NO HAY CHOQUES
		
		pj.acelerar();	 	//VEL = 1
		pj.acelerar();	 	//VEL = 2
		pj.acelerar();  	//VEL = 3
		pj.acelerar();	 	//VEL = 4
		pj.acelerar();	 	//VEL = 5		
		pj.avanzar();
		pj.avanzar();		//ACA YA DEBERIAN COLISIONAR
		
		assertFalse(pj.getExplote());
		assertFalse(pozo.getExplote());
		
		assertEquals(carretera.getCantidadDeObjetos(), 2);		
		
		carretera.actualizar();			//DETECTA EL CHOQUE Y EXPLOSION, Y ELIMINA AL POZO SE SU LISTA DE OBJETOS
		
		assertTrue(pj.getExplote());
		assertTrue(pozo.getExplote());		
		
		assertEquals(carretera.getCantidadDeObjetos(), 1);	
	}
}
