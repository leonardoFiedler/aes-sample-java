import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	public static final String[][] S_BOX_HEXADECIMAl = {
			{"63", "7c", "77", "7b", "f2", "6b", "6f", "c5", "30", "01", "67", "2b", "fe", "d7", "ab", "76"},
			{"ca", "82", "c9", "7d", "fa", "59", "47", "f0", "ad", "d4", "a2", "af", "9c", "a4", "72", "c0"},
			{"b7", "fd", "93", "26", "36", "3f", "f7", "cc", "34", "a5", "e5", "f1", "71", "d8", "31", "15"},
			{"04", "c7", "23", "c3", "18", "96", "05", "9a", "07", "12", "80", "e2", "eb", "27", "b2", "75"},
			{"09", "83", "2c", "1a", "1b", "6e", "5a", "a0", "52", "3b", "d6", "b3", "29", "e3", "2f", "84"},
			{"53", "d1", "00", "ed", "20", "fc", "b1", "5b", "6a", "cb", "be", "39", "4a", "4c", "58", "cf"},
			{"d0", "ef", "aa", "fb", "43", "4d", "33", "85", "45", "f9", "02", "7f", "50", "3c", "9f", "a8"},
			{"51", "a3", "40", "8f", "92", "9d", "38", "f5", "bc", "b6", "da", "21", "10", "ff", "f3", "d2"},
			{"cd", "0c", "13", "ec", "5f", "97", "44", "17", "c4", "a7", "7e", "3d", "64", "5d", "19", "73"},
			{"60", "81", "4f", "dc", "22", "2a", "90", "88", "46", "ee", "b8", "14", "de", "5e", "0b", "db"},
			{"e0", "32", "3a", "0a", "49", "06", "24", "5c", "c2", "d3", "ac", "62", "91", "95", "e4", "79"},
			{"e7", "c8", "37", "6d", "8d", "d5", "4e", "a9", "6c", "56", "f4", "ea", "65", "7a", "ae", "08"},
			{"ba", "78", "25", "2e", "1c", "a6", "b4", "c6", "e8", "dd", "74", "1f", "4b", "bd", "8b", "8a"},
			{"70", "3e", "b5", "66", "48", "03", "f6", "0e", "61", "35", "57", "b9", "86", "c1", "1d", "9e"},
			{"e1", "f8", "98", "11", "69", "d9", "8e", "94", "9b", "1e", "87", "e9", "ce", "55", "28", "df"},
			{"8c", "a1", "89", "0d", "bf", "e6", "42", "68", "41", "99", "2d", "0f", "b0", "54", "bb", "16"}
			};
	public static final String[] VETOR_ROUND_CONSTANT_HEXADECIMAl = {"01", "02", "04", "08", "10", "20", "40", "80", "1B", "36"};
	private static Scanner scanner;
	static int[] vetorRoundConstant;
	static int[][] sBox;
	static int key[];
	static int[][] keySchedule;
	
	public static int[][] sBoxToDecimal() {
		sBox = new int[16][16];
		for (int i = 0; i < S_BOX_HEXADECIMAl.length; i++) {
			for (int j = 0; j < S_BOX_HEXADECIMAl[i].length; j++) {
					sBox[i][j] = Integer.parseInt(S_BOX_HEXADECIMAl[i][j], 16);		
			}
		}
		return sBox;
	}
	
	public static int[] vetorRoundConstantToDecimal() {
		int[] vetorRoundConstant = new int[VETOR_ROUND_CONSTANT_HEXADECIMAl.length];
		for (int i = 0; i < vetorRoundConstant.length; i++) {
			vetorRoundConstant[i] = Integer.parseInt(VETOR_ROUND_CONSTANT_HEXADECIMAl[i], 16);
		}
		return vetorRoundConstant;
	}

	public static void main(String[] args) {
		sBox = sBoxToDecimal();
		vetorRoundConstant = vetorRoundConstantToDecimal();
		scanner = new Scanner(System.in);
		System.out.println("Informe a chave: ");
		String chaveRaw = scanner.nextLine();
		key = new int[16];
		String[] split = chaveRaw.split(",");
		
		for (int i = 0; i < split.length; i++) {
			key[i] = Integer.valueOf(split[i]);
		}
		
		//TODO: Efetuar carregamento do arquivo
		String textoFile = "DESENVOLVIMENTO!";
		
		expandKey();
		blockCipher(textoFile);
		
   		System.out.println("Hello");
	}
	
	public static void expandKey() {
		int[][] matrizEstadoChave = new int[4][4];
		int count = 0;
		
		for (int i = 0; i < matrizEstadoChave.length; i++) {
			for (int j = 0; j < matrizEstadoChave[i].length; j++) {
				matrizEstadoChave[j][i] = key[count++];
			}
		}
		
		keySchedule = new int[44][4];
		for (int i = 0; i < matrizEstadoChave.length; i++) {
			for (int j = 0; j < matrizEstadoChave[i].length; j++) {
				keySchedule[i][j] = matrizEstadoChave[j][i];
			}
		}
		
   		for (int i = 4; i < keySchedule.length; i++) {
			if (i % 4 == 0) {
				//Primeiro caso
				int[] copia = Arrays.copyOf(keySchedule[i-1], 4);
				int[] rotWord = new int[4];
				
				rotWord[0] = copia[1];
				rotWord[1] = copia[2];
				rotWord[2] = copia[3];
				rotWord[3] = copia[0];
				
				for (int indexRotWord = 0; indexRotWord < rotWord.length; indexRotWord++) {
					String hexByte = Integer.toString(rotWord[indexRotWord], 16);					
					int primeirosBits = 0;
					int ultimosBits = 0;
					if (hexByte.length() == 2) {
						primeirosBits = Integer.parseInt(Character.toString(hexByte.charAt(0)), 16);
						ultimosBits = Integer.parseInt(Character.toString(hexByte.charAt(1)), 16);
					} else {
						primeirosBits = 0;
						ultimosBits = Integer.parseInt(Character.toString(hexByte.charAt(0)), 16);						
					}								
					rotWord[indexRotWord] = sBox[primeirosBits][ultimosBits];
				}
				int[] roundConstant = new int[4];
				roundConstant[1] = 0;
				roundConstant[2] = 0;
				roundConstant[3] = 0;
				roundConstant[0] = vetorRoundConstant[i/4-1];
				int[] palavraGerada = new int[4];
				for (int indexPalavraGerada = 0; indexPalavraGerada < rotWord.length; indexPalavraGerada++) {
					palavraGerada[indexPalavraGerada] = rotWord[indexPalavraGerada] ^ roundConstant[indexPalavraGerada];
				}
				for (int indexPalavraGerada = 0; indexPalavraGerada < rotWord.length; indexPalavraGerada++) {				
					palavraGerada[indexPalavraGerada] = keySchedule[i-4][indexPalavraGerada] ^ palavraGerada[indexPalavraGerada];
				}
				keySchedule[i] = palavraGerada; 
			} else {
				//Segundo caso
				for (int j = 0; j < keySchedule[i].length; j++) {
					int anterior = keySchedule[i-1][j];
					int rkAnterior = keySchedule[i-4][j];
					keySchedule[i][j] = anterior ^ rkAnterior;
				}
			}
		}
	}
	
	public static void blockCipher(String textoSimples) {
		
		
	}
	
	//TODO:
	public static void padding(String textoSimples) {
		int diferenca = textoSimples.getBytes().length % 16;
		
		if (diferenca == 0) {
			int[] word = new int[textoSimples.getBytes().length + 16];
		} else {
			int div = 16 % textoSimples.getBytes().length;
			int[] word = new int[textoSimples.getBytes().length + div];
		}
		
	}
}
