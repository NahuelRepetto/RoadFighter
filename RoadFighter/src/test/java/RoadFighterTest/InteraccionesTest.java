package RoadFighterTest;

import static org.junit.Assert.*;

import org.junit.Test;

import RoadFighter.AceleradorExtremo;
import RoadFighter.AutoJugador;
import RoadFighter.AutoObstaculo;
import RoadFighter.CamionObstaculo;
import RoadFighter.Escudo;
import RoadFighter.ManchaDeAceite;
import RoadFighter.Miniaturizador;
import RoadFighter.Pozo;
import RoadFighter.Punto;


public class InteraccionesTest {

	@Test
	public void alChocarConOtroAutoPierdeControl() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");	
		AutoJugador pj2 = new AutoJugador(new Punto(0, 0), "Denis");	
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));
		
		assertFalse(pj.getPerdiControl());
		assertFalse(pj2.getPerdiControl());
		assertFalse(bot.getPerdiControl());
		
		pj.choqueConObjeto(pj2);
		pj.choqueConObjeto(bot);
		
		assertTrue(pj.getPerdiControl());
		assertTrue(pj2.getPerdiControl());
		assertTrue(bot.getPerdiControl());		
	}	
	
	@Test
	public void alChocarConOtroAutoNoExploto() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");	
		AutoJugador pj2 = new AutoJugador(new Punto(0, 0), "Denis");	
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));
		
		assertFalse(pj.getExplote());
		assertFalse(pj2.getExplote());
		assertFalse(bot.getExplote());
		
		pj.choqueConObjeto(pj2);
		pj.choqueConObjeto(bot);
		
		assertFalse(pj.getExplote());
		assertFalse(pj2.getExplote());
		assertFalse(bot.getExplote());		
	}
	
	@Test
	public void alChocarUnAutoConCamionExplotaSoloElAuto() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");	
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));
		
		assertFalse(pj.getExplote());
		assertFalse(bot.getExplote());
		assertFalse(bot2.getExplote());
		
		pj.choqueConObjeto(bot2);
		bot.choqueConObjeto(bot2);
		
		assertTrue(pj.getExplote());
		assertTrue(bot.getExplote());		
		assertFalse(bot2.getExplote());
	}
	
	@Test
	public void chocarConAceiteHacePerderControlMenosAlCamion() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");	
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));
		ManchaDeAceite aceite = new ManchaDeAceite(new Punto(0, 0));
		
		assertFalse(pj.getPerdiControl());
		assertFalse(bot.getPerdiControl());
		assertFalse(bot2.getPerdiControl());
		
		pj.choqueConObjeto(aceite);
		bot.choqueConObjeto(aceite);
		bot2.choqueConObjeto(aceite);		
		
		assertTrue(pj.getPerdiControl());
		assertTrue(bot.getPerdiControl());
		assertFalse(bot2.getPerdiControl());
	}
	
	@Test
	public void chocarConPozoHaceExplotarMenosAlCamion() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");	
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));
		Pozo pozo = new Pozo(new Punto(0, 0));
		
		assertFalse(pj.getExplote());
		assertFalse(bot.getExplote());
		assertFalse(bot2.getExplote());
		
		pj.choqueConObjeto(pozo);
		bot.choqueConObjeto(pozo);
		bot2.choqueConObjeto(pozo);		
		
		assertTrue(pj.getExplote());
		assertTrue(bot.getExplote());
		assertFalse(bot2.getExplote());
	}
	
	@Test
	public void obtenerElEscudoNosDaInmuidadPorUnChoqueLuegoLoPierdo() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		Pozo pozo = new Pozo(new Punto(0, 0));
		Escudo escudo = new Escudo(new Punto(0, 0));
			
		assertFalse(pj.getEscudo());		
		
		pj.choqueConObjeto(escudo);
		assertTrue(pj.getEscudo());		
				
		assertFalse(pj.getExplote());		
				
		pj.choqueConObjeto(pozo);
						
		assertFalse(pj.getExplote());
		
		assertFalse(pj.getEscudo());
		
		pj.choqueConObjeto(pozo);
		
		assertTrue(pj.getExplote());		
	}
	
	@Test
	public void siUnJugadorChocaConUnEscudoLoObtine() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		Escudo escudo = new Escudo(new Punto(0, 0));
		
		assertFalse(pj.getEscudo());
			
		pj.choqueConObjeto(escudo);
		assertTrue(pj.getEscudo());		
	}	
	
	@Test
	public void siOtroTipoDeVehiculoChocaAlEscudoNoPasaNada() {
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));				
		Pozo pozo = new Pozo(new Punto(0, 0));
		Escudo escudo = new Escudo(new Punto(0, 0));
			
		bot.choqueConObjeto(escudo);
			
		assertFalse(bot.getExplote());		
				
		bot.choqueConObjeto(pozo);
						
		assertTrue(bot.getExplote());
	}
	
	@Test
	public void cualquierCosaQueChoqueConElEscudoExplota() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));	
		Escudo escudo = new Escudo(new Punto(0, 0));			
			
		pj.choqueConObjeto(escudo);					
				
		assertFalse(bot.getExplote());		
				
		pj.choqueConObjeto(bot);
						
		assertTrue(bot.getExplote());
		
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));		
			
		pj.choqueConObjeto(escudo);					
				
		assertFalse(bot2.getExplote());		
				
		pj.choqueConObjeto(bot2);
						
		assertTrue(bot2.getExplote());			
	}
	
	@Test
	public void losEscudosNoSonAcumulables() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));	
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));	
		Escudo escudo = new Escudo(new Punto(0, 0));	
		Escudo escudo2 = new Escudo(new Punto(10, 10));
			
		pj.choqueConObjeto(escudo);	
		pj.choqueConObjeto(escudo2);
				
		pj.choqueConObjeto(bot);
		assertFalse(pj.getExplote());		
				
						
		pj.choqueConObjeto(bot2);
		assertTrue(pj.getExplote());	
	}
		
	@Test
	public void siUnJugadorChocaConAceleradorExtremoSuVelocidadSubePorEncimaDeSuVelocidadMaxima() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		AceleradorExtremo acelerador = new AceleradorExtremo(new Punto(0, 0));
		
		pj.acelerar();
		pj.acelerar();
				
		assertEquals((int)pj.getVelocidadActual(), 2);			
		pj.choqueConObjeto(acelerador);		
		assertEquals((int)pj.getVelocidadActual(), 200);
	}		
	
	@Test
	public void siUnJugadorAdquiereAceleradorExtremoNoPuedoFrenarNiAcelerar() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		AceleradorExtremo acelerador = new AceleradorExtremo(new Punto(0, 0));
			
		pj.choqueConObjeto(acelerador);		
			
		assertFalse(pj.acelerar());
		assertFalse(pj.frenar());
	}
	
	@Test
	public void siOtroTipoDeVehiculoChocaConAceleradorExtremoNoPasaNada() {
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));				
		AceleradorExtremo acelerador = new AceleradorExtremo(new Punto(0, 0));
		
		bot.acelerar(); //VELOCIDAD = 1
		
		assertEquals((int)bot.getVelocidadActual(), 1);
			
		bot.choqueConObjeto(acelerador);
		
		assertEquals((int)bot.getVelocidadActual(), 1);	 //LA VELOCIDAD NO FUE AFECTADA			
	}
	
	@Test
	public void siUnJugadorExplotaTeniendoAceleradorExtremoLoPierde() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		AceleradorExtremo velocidad = new AceleradorExtremo(new Punto(0, 0));
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));
			
		pj.choqueConObjeto(velocidad);		
			
		assertFalse(pj.acelerar());
		assertFalse(pj.frenar());
		assertEquals((int)pj.getVelocidadActual(), 200);
		
		pj.choqueConObjeto(bot);	//ACA NO EXPLOTA, PIERDE EL CONTROL
		assertFalse(pj.acelerar());
		assertFalse(pj.frenar());
		assertEquals((int)pj.getVelocidadActual(), 200);
		
		pj.choqueConObjeto(bot2);	//ACA SI EXPLOTA

		assertEquals((int)pj.getVelocidadActual(), 0);
	}
	
	@Test
	public void siUnJugadorChocaConMiniaturizadorSuAnchoYLargoSeReducenALaMitad() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		Miniaturizador miniaturizacion = new Miniaturizador(new Punto(0, 0));
		
		assertEquals((int)pj.getTamanio(), 30*40);			
		pj.choqueConObjeto(miniaturizacion);		
		assertEquals((int)pj.getTamanio(), 15*20);
	}		
	
	@Test
	public void siOtroTipoDeVehiculoChocaConMiniaturizadorNoPasaNada() {
		AutoObstaculo bot = new AutoObstaculo(new Punto(0, 0));				
		Miniaturizador miniaturizacion = new Miniaturizador(new Punto(0, 0));	
			
		assertEquals((int)bot.getTamanio(), 30*40);
			
		bot.choqueConObjeto(miniaturizacion);
		
		assertEquals((int)bot.getTamanio(), 30*40);	 //EL TAMANIO NO FUE AFECTADO			
	}
	
	@Test
	public void siUnJugadorExplotaTeniendoMiniaturizadorVuelveALaNormalidad() {
		AutoJugador pj = new AutoJugador(new Punto(0, 0), "Nahuel");				
		CamionObstaculo bot2 = new CamionObstaculo(new Punto(0, 0));
		Miniaturizador miniaturizacion = new Miniaturizador(new Punto(0, 0));
		
		assertEquals((int)pj.getTamanio(), 30*40);	//TAMAÑO ORIGINAL
		
		pj.choqueConObjeto(miniaturizacion);	
		
		assertEquals((int)pj.getTamanio(), 15*20);	//TAMAÑO DISMINUIDO	
					
		pj.choqueConObjeto(bot2);					//ACA EXPLOTA
					
		assertEquals((int)pj.getTamanio(), 30*40);	//TAMAÑO NORMALIZADO
	}
}
