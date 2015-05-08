package org.uqbar.wollokui.modifiers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.uqbar.wollokui.domain.GameBoard;
import org.uqbar.wollokui.entes.Persona;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;

public class TestModifiers {
	
	private Persona pepe;
	private CodeInjector codeInjector;
	private GameBoard myGameboard;
	
	@Before
	public void init(){
		this.pepe = new Persona();
		this.codeInjector = new CodeInjector();
		this.myGameboard = new GameBoard();
		codeInjector.setMyGameBoard(this.myGameboard);
	}
	
	@Test
	public void modificoSetNombreDeUnaPersona() throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalConnectorArgumentsException{
		codeInjector.modifyObject(pepe,"setNombre");
		//codeInjector.insertListenerToMethod(pepe.getClass().getName(), "setNombre", myGameboard);
		pepe.setNombre("Pepito");
		Assert.assertEquals("Pepito", pepe.getNombre());
		Assert.assertTrue(myGameboard.isModified());
	}
}
