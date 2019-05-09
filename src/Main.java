import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//byte b2 = -128; // Or `= (byte)156;`
//		int i2 = (b2 & 0xFF);
//		System.out.println(i2); // 156
		//Leitura da chave
		Scanner scanner = new Scanner(System.in);
		System.out.println("Informe a chave: ");
		String chaveRaw = scanner.nextLine();
		int key[] = new int[16];
		String[] split = chaveRaw.split(",");
		
		for (int i = 0; i < split.length; i++) {
			key[i] = Integer.valueOf(split[i]);
		}
		
		//Todo: Efetuar carregamento do arquivo
		String textoFile = "Desenvolvimento!";
		
		int[][] matrizEstadoChave = new int[4][4];
		int count = 0;
		
		for (int i = 0; i < matrizEstadoChave.length; i++) {
			for (int j = 0; j < matrizEstadoChave[i].length; j++) {
				matrizEstadoChave[j][i] = key[count++];
			}
		}
		
		int[][] keySchedule = new int[44][4];
		
		for (int i = 0; i < matrizEstadoChave.length; i++) {
			for (int j = 0; j < matrizEstadoChave[i].length; j++) {
				keySchedule[j][i] = matrizEstadoChave[j][i];
			}
		}
		
		for (int i = 4; i < keySchedule.length; i++) {
			if (i % 4 == 0) {
				//Primeiro caso
				int[] copia = Arrays.copyOf(keySchedule[i-1], 4);
				int[] rotWord = new int[4];
				
				for (int j = copia.length - 1; j >= 0; j--) {
					if (j - 1 < 0) {
						rotWord[0] = copia[copia.length - 1];
					} else {
						rotWord[j] = copia[j - 1];
					}
				}
				
				System.out.println("Hello");
			} else {
				//Segundo caso
				for (int j = 0; j < keySchedule[i].length; j++) {
					
				}
			}
			
			
		}
		
		
	}
	
	/*
	[[20, 199, 31, 59], 
	[1, 0, 94, 30], 
	[94, 48, 112, 100], 
	[33, 9, 40, 248], 
	[0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0]]
	
	
	[[20, 199, 31, 59], 
	 [1, 0, 94, 30], 
	 [94, 48, 112, 100], 
	 [33, 9, 40, 248]]
	 
	 */

}
