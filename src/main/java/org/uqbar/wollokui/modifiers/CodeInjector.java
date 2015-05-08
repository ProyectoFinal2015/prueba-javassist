package org.uqbar.wollokui.modifiers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.uqbar.wollokui.domain.GameBoard;

import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.util.HotSwapper;

public class CodeInjector {
	
	private GameBoard myGameBoard;
	
	public void modifyObject(Object anObject, String targetMethod) throws NotFoundException, CannotCompileException, IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalConnectorArgumentsException{
		this.insertListenerToMethod(anObject, targetMethod);
		
		Class[] cArg = new Class[1];
		cArg[0] = GameBoard.class;
		Method method = anObject.getClass().getMethod("setMyGameboard",cArg);
		method.invoke(anObject, this.getMyGameBoard());
	}
	
	private void insertListenerToMethod(Object targetObject, String targetMethod) throws NotFoundException, CannotCompileException, IOException, IllegalConnectorArgumentsException {
		      Logger logger = Logger.getLogger("Javassist");
		      final String targetFolder = "./target/classes";
		      String targetClass = targetObject.getClass().getName();
		      try {
		         final ClassPool pool = ClassPool.getDefault();
		         // Tell Javassist where to look for classes - into our ClassLoader
		         //pool.appendClassPath(new LoaderClassPath(getClass().getClassLoader()));
		         //pool.appendClassPath(new LoaderClassPath(targetObject.getClass().getClassLoader()));
		         final CtClass compiledClass = pool.get(targetClass);
		         final CtClass gameBoardClass = pool.get(myGameBoard.getClass().getName());
		         
		         pool.importPackage("org.uqbar.wollokui.domain");
		         
		         CtField nuevoCampo = new CtField(gameBoardClass, "myGameboard", compiledClass);
		         
		         //Agrego nueva property
		         compiledClass.addField(nuevoCampo);
		         
		         CtMethod.make("public void setMyGameboard(GameBoard x) { this.myGameboard=x; }",
		        		 compiledClass);
		         
		         final CtMethod method = compiledClass.getDeclaredMethod(targetMethod);
		         
		         // And also to its very end:
		         
		         method.insertAfter("{  this.myGameboard.updateGameBoard(this); }");

		         //compiledClass.writeFile(targetFolder);
		         HotSwapper swap = new HotSwapper(8000);
		         swap.reload(targetClass, compiledClass.toBytecode());

		         logger.info(targetClass + "." + targetMethod +
		               " has been modified and saved under " + targetFolder);
		      } catch (NotFoundException e) {
		         logger.warning("Failed to find the target class to modify, " +
		               targetClass + ", verify that it ClassPool has been configured to look " +
		               "into the right location");
		      }
		      catch (RuntimeException e){
		    	  
		      }
		   }

	public GameBoard getMyGameBoard() {
		return myGameBoard;
	}

	public void setMyGameBoard(GameBoard myGameBoard) {
		this.myGameBoard = myGameBoard;
	}

}
