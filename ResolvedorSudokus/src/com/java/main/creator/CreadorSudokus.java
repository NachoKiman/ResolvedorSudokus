package com.java.main.creator;

import java.util.ArrayList;
import java.util.List;

import com.java.main.backend.Sudoku;

public class CreadorSudokus {

	Sudoku sudoku;
	public Sudoku crearSudoku() {
		sudoku = new Sudoku();
		sudoku.resolver();
		return sudoku;
	}
	
	public long cuantosSudokusHay(){
		List<Sudoku> sudokusEncontrados = new ArrayList<>();
		long cantidadSudokusEncontrados=0;
		while(true){
			sudoku = crearSudoku();
			if(!sudokusEncontrados.contains(sudoku)){
				sudokusEncontrados.add(sudoku);
				cantidadSudokusEncontrados++;
				System.out.println(cantidadSudokusEncontrados);
			}
		}
		
	}

}
