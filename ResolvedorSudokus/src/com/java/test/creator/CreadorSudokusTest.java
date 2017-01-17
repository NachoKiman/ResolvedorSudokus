package com.java.test.creator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.java.main.backend.Sudoku;
import com.java.main.creator.CreadorSudokus;

public class CreadorSudokusTest {

	private CreadorSudokus creador;
	
	@Before
	public void setUp() throws Exception {
		creador = new CreadorSudokus();
	}

	@Test
	public void testCrearSudoku() {
		Sudoku sudoku = creador.crearSudoku();

		assertFalse(sudoku.existeError());
	}
	
	@Test
	public void testCuantosSudokusHay() {
		creador.cuantosSudokusHay();

	}

}
