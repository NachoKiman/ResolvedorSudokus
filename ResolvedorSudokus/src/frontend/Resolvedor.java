package frontend;

import java.util.ArrayList;
import java.util.List;

import backend.EntidadSudoku;
import backend.Espacio;
import backend.Sudoku;

public class Resolvedor {

	public static void main(String[] args) {


//		Sudoku Vacio
//		int sudo[][]={
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0},
//				{0,0,0,0,0,0,0,0,0}
//				};


//		Sudoku medio libro grande		
		int sudo[][]={
				{0,7,0,0,0,3,0,8,0},
				{0,9,8,0,6,5,0,3,0},
				{3,0,0,8,0,1,0,4,0},
				{0,5,7,0,3,8,2,0,6},
				{6,0,0,0,0,0,8,5,3},
				{8,0,3,2,5,6,0,7,0},
				{0,8,0,5,0,2,3,6,7},
				{0,0,0,3,8,7,0,0,0},
				{7,3,0,6,0,0,5,2,8}
				};
		
//		Segun una pagina uno muy dificil
//		int sudo[][]={
//				{8,0,0,0,0,0,0,0,0},
//				{0,0,3,6,0,0,0,0,0},
//				{0,7,0,0,9,0,2,0,0},
//				{0,5,0,0,0,7,0,0,0},
//				{0,0,0,0,4,5,7,0,0},
//				{0,0,0,1,0,0,0,3,0},
//				{0,0,1,0,0,0,0,6,8},
//				{0,0,8,5,0,0,0,1,0},
//				{0,9,0,0,0,0,4,0,0}
//				};

		
//		Sudoku Muy Dificil Note	cambiado	
//		int sudo[][]={
//				{0,0,0,0,0,0,0,3,0},
//				{0,0,1,0,0,5,9,8,0},
//				{7,0,9,8,0,0,2,0,0},
//				{0,0,0,0,5,3,8,9,0},
//				{0,0,3,6,0,2,4,0,0},
//				{0,2,7,1,8,0,0,0,0},
//				{0,0,2,0,0,7,3,0,8},
//				{0,0,4,9,0,0,5,0,0},
//				{0,7,0,0,0,0,0,0,0}
//				};

//		Sudoku Muy Dificil Note		
//		int sudo[][]={
//				{0,0,0,0,0,0,0,3,0},
//				{0,0,1,0,0,5,9,8,0},
//				{7,0,9,8,0,0,2,0,0},
//				{0,0,0,0,5,3,8,9,0},
//				{0,0,3,6,0,2,4,0,0},
//				{0,2,7,1,8,0,0,0,0},
//				{0,0,2,0,0,7,3,0,8},
//				{0,6,4,9,0,0,5,0,0},
//				{0,7,0,0,0,0,0,0,0}
//				};

		
//		Sudoku Extreme Tablet		
//		int sudo[][]={
//				{0,0,0,7,0,0,0,2,0},
//				{0,7,0,0,0,5,9,0,0},
//				{0,0,4,0,0,1,0,6,0},
//				{0,9,0,0,0,7,0,0,2},
//				{3,0,0,0,0,0,0,0,8},
//				{6,0,0,2,0,0,0,7,0},
//				{0,4,0,3,0,0,1,0,0},
//				{0,0,6,9,0,0,0,8,0},
//				{0,1,0,0,0,4,0,0,0}
//				};
		
//		Sudoku Medium Tablet		
//		int sudo[][]={
//				{0,0,0,2,0,5,0,0,8},
//				{0,0,0,0,0,0,0,3,0},
//				{0,6,9,0,8,3,7,0,0},
//				{3,2,0,0,0,0,0,0,0},
//				{0,0,7,6,0,0,9,0,0},
//				{0,0,0,0,0,0,0,8,4},
//				{0,0,4,5,7,0,8,9,0},
//				{0,0,0,0,0,0,0,0,0},
//				{2,0,0,4,1,8,0,0,0}
//				};
		
//		7 ESTRELLAS 87
//		int sudo[][]={
//				{0,0,0,0,0,3,0,0,0},
//				{4,0,0,0,0,0,0,0,1},
//				{0,0,0,0,8,0,3,0,7},
//				{0,0,8,0,0,0,0,2,0},
//				{0,0,0,5,0,9,0,0,0},
//				{0,0,2,0,0,0,7,6,0},
//				{0,0,0,0,3,0,2,8,0},
//				{0,9,0,0,0,0,0,0,0},
//				{0,7,0,4,1,0,0,0,0}
//				};
//		
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

		
		Sudoku sudoku = nuevoSudoku(sudo);

		sudoku.resolver();


	}
	
	public static Sudoku nuevoSudoku(int[][] sudo)
	{

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
