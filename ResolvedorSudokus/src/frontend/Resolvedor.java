package frontend;

import java.util.ArrayList;
import java.util.List;

import backend.EntidadSudoku;
import backend.Espacio;
import backend.Sudoku;

public class Resolvedor {

	public static void main(String[] args) {
		
		Sudoku sudoku = nuevoSudoku();

		sudoku.resolver();

		sudoku.mostrar();

	}
	
	public static Sudoku nuevoSudoku()
	{
		
		
//		7 ESTRELLAS 87
		int sudo[][]={
				{0,0,0,0,0,3,0,0,0},
				{4,0,0,0,0,0,0,0,1},
				{0,0,0,0,8,0,3,0,7},
				{0,0,8,0,0,0,0,2,0},
				{0,0,0,5,0,9,0,0,0},
				{0,0,2,0,0,0,7,6,0},
				{0,0,0,0,3,0,2,8,0},
				{0,9,0,0,0,0,0,0,0},
				{0,7,0,4,1,0,0,0,0}
				};
		
//		6 ESTRELLAS 77
//		int sudo[][]={
//				{0,1,7,0,0,0,0,0,4},
//				{0,0,0,4,0,0,9,0,0},
//				{0,8,0,0,0,0,3,0,0},
//				{0,0,5,1,2,0,0,9,0},
//				{0,0,0,5,6,0,0,0,0},
//				{0,0,0,0,0,0,4,0,7},
//				{0,2,0,0,0,8,0,0,0},
//				{5,0,0,0,0,0,0,2,6},
//				{9,0,0,0,0,3,0,0,0}
//				};
		
		
//		5 ESTRELLAS 49
//		int sudo[][]={
//				{3,0,6,0,0,0,0,0,0},
//				{0,0,0,0,2,0,4,0,3},
//				{0,0,0,9,1,0,0,0,0},
//				{0,8,0,4,0,5,0,0,0},
//				{5,7,0,0,0,0,0,0,9},
//				{0,0,0,0,0,0,2,0,6},
//				{0,6,0,0,0,0,0,8,0},
//				{0,0,9,0,8,0,0,1,0},
//				{0,0,4,2,0,6,0,0,0}
//				};
		
//		4 ESTRELLAS 19
//		int sudo[][]={
//				{0,0,0,4,0,0,0,3,6},
//				{0,0,0,7,0,0,0,0,0},
//				{0,2,0,1,0,0,0,0,0},
//				{0,0,0,0,0,0,4,0,1},
//				{8,0,0,0,0,0,0,0,0},
//				{3,0,0,0,9,5,0,0,0},
//				{0,0,6,0,0,3,0,0,8},
//				{0,0,3,0,0,6,0,7,5},
//				{0,0,7,0,0,0,1,0,0}
//				};
		

		List<EntidadSudoku> filas = new ArrayList<>();
		List<EntidadSudoku> columnas = new ArrayList<>();
		List<EntidadSudoku> cuadrados = new ArrayList<>();
		
		//Esqueleto de sudokus sin Espacios
		for(int i=0; i<sudo.length;i++)
		{
			filas.add(new EntidadSudoku());
			columnas.add(new EntidadSudoku());
			cuadrados.add(new EntidadSudoku());
		}
		
		for(int i=1; i<=sudo.length;i++)
		{
			for(int j=1; j<=sudo.length;j++)
			{
				//Si te preguntas "por que -1?", es que hice el 
				//obtenerCuadrado y me dio paja cambiarlo :D
				Espacio espacio = new Espacio(sudo[i-1][j-1], sudo.length);
				filas.get(i-1).getEspacios().add(espacio);
				columnas.get(j-1).getEspacios().add(espacio);
				//Agregar los cudrados es mas dificil!
				int cuadrado = obtenerCuadrado(i,j);
				cuadrados.get(cuadrado).getEspacios().add(espacio);
			}
		}
		
		
		
		
		return new Sudoku(filas, columnas, cuadrados);
	}
	
	public static int obtenerCuadrado(int i, int j)
	{
		
		if((i==1)||(i==2)||(i==3))
		{
			if((j==1)||(j==2)||(j==3))
			{
				return 0;
			}
			else if((j==4)||(j==5)||(j==6))
			{
				return 1;
			}
			else if((j==7)||(j==8)||(j==9))
			{
				return 2;
			}

		}
		else if((i==4)||(i==5)||(i==6))
		{
			if((j==1)||(j==2)||(j==3))
			{
				return 3;
			}
			else if((j==4)||(j==5)||(j==6))
			{
				return 4;
			}
			else if((j==7)||(j==8)||(j==9))
			{
				return 5;
			}

		}
		else if((i==7)||(i==8)||(i==9))
		{
			if((j==1)||(j==2)||(j==3))
			{
				return 6;
			}
			else if((j==4)||(j==5)||(j==6))
			{
				return 7;
			}
			else if((j==7)||(j==8)||(j==9))
			{
				return 8;
			}

		}
		//Imposible llegar pero java es medio puto
		return -1;
	}
	

}
