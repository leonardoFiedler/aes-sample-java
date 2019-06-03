import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	
	//As duas constantes abaixo servem para o usuario indicar o arquivo de entrada e onde deseja salvar o arquivo
	public static final String INPUT_TEXT_FILE = "input.txt";
	public static final String OUTPUT_ENCRYPTED_FILE = "output.txt";
	
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
	public static final int[][] MULTIPLY_MATRIX = {
	        {2, 3, 1, 1},
            {1, 2, 3, 1},
            {1, 1, 2, 3},
            {3, 1, 1, 2}
	        };
	public static final int BLOCK_SIZE = 16;

	public static final String[][] L_TABLE = {
	        {"00", "00", "19", "01", "32", "02", "1a", "c6", "4b", "c7", "1b", "68", "33", "ee", "df", "03"},
            {"64", "04", "e0", "0e", "34", "8d", "81", "ef", "4c", "71", "08", "c8", "f8", "69", "1c", "c1"},
            {"7d", "c2", "1d", "b5", "f9", "b9", "27", "6a", "4d", "e4", "a6", "72", "9a", "c9", "09", "78"},
            {"65", "2f", "8a", "05", "21", "0f", "e1", "24", "12", "f0", "82", "45", "35", "93", "da", "8e"},
            {"96", "8f", "db", "bd", "36", "d0", "ce", "94", "13", "5c", "d2", "f1", "40", "46", "83", "38"},
            {"66", "dd", "fd", "30", "bf", "06", "8b", "62", "b3", "25", "e2", "98", "22", "88", "91", "10"},
            {"7e", "6e", "48", "c3", "a3", "b6", "1e", "42", "3a", "6b", "28", "54", "fa", "85", "3d", "ba"},
            {"2b", "79", "0a", "15", "9b", "9f", "5e", "ca", "4e", "d4", "ac", "e5", "f3", "73", "a7", "57"},
            {"af", "58", "a8", "50", "f4", "ea", "d6", "74", "4f", "ae", "e9", "d5", "e7", "e6", "ad", "e8"},
            {"2c", "d7", "75", "7a", "eb", "16", "0b", "f5", "59", "cb", "5f", "b0", "9c", "a9", "51", "a0"},
            {"7f", "0c", "f6", "6f", "17", "c4", "49", "ec", "d8", "43", "1f", "2d", "a4", "76", "7b", "b7"},
            {"cc", "bb", "3e", "5a", "fb", "60", "b1", "86", "3b", "52", "a1", "6c", "aa", "55", "29", "9d"},
            {"97", "b2", "87", "90", "61", "be", "dc", "fc", "bc", "95", "cf", "cd", "37", "3f", "5b", "d1"},
            {"53", "39", "84", "3c", "41", "a2", "6d", "47", "14", "2a", "9e", "5d", "56", "f2", "d3", "ab"},
            {"44", "11", "92", "d9", "23", "20", "2e", "89", "b4", "7c", "b8", "26", "77", "99", "e3", "a5"},
            {"67", "4a", "ed", "de", "c5", "31", "fe", "18", "0d", "63", "8c", "80", "c0", "f7", "70", "07"}
	        };

	public static final String[][] E_TABLE = {
	        {"01", "03", "05", "0f", "11", "33", "55", "ff", "1a", "2e", "72", "96", "a1", "f8", "13", "35"},
            {"5f", "e1", "38", "48", "d8", "73", "95", "a4", "f7", "02", "06", "0a", "1e", "22", "66", "aa"},
            {"e5", "34", "5c", "e4", "37", "59", "eb", "26", "6a", "be", "d9", "70", "90", "ab", "e6", "31"},
            {"53", "f5", "04", "0c", "14", "3c", "44", "cc", "4f", "d1", "68", "b8", "d3", "6e", "b2", "cd"},
            {"4c", "d4", "67", "a9", "e0", "3b", "4d", "d7", "62", "a6", "f1", "08", "18", "28", "78", "88"},
            {"83", "9e", "b9", "d0", "6b", "bd", "dc", "7f", "81", "98", "b3", "ce", "49", "db", "76", "9a"},
            {"b5", "c4", "57", "f9", "10", "30", "50", "f0", "0b", "1d", "27", "69", "bb", "d6", "61", "a3"},
            {"fe", "19", "2b", "7d", "87", "92", "ad", "ec", "2f", "71", "93", "ae", "e9", "20", "60", "a0"},
            {"fb", "16", "3a", "4e", "d2", "6d", "b7", "c2", "5d", "e7", "32", "56", "fa", "15", "3f", "41"},
            {"c3", "5e", "e2", "3d", "47", "c9", "40", "c0", "5b", "ed", "2c", "74", "9c", "bf", "da", "75"},
            {"9f", "ba", "d5", "64", "ac", "ef", "2a", "7e", "82", "9d", "bc", "df", "7a", "8e", "89", "80"},
            {"9b", "b6", "c1", "58", "e8", "23", "65", "af", "ea", "25", "6f", "b1", "c8", "43", "c5", "54"},
            {"fc", "1f", "21", "63", "a5", "f4", "07", "09", "1b", "2d", "77", "99", "b0", "cb", "46", "ca"},
            {"45", "cf", "4a", "de", "79", "8b", "86", "91", "a8", "e3", "3e", "42", "c6", "51", "f3", "0e"},
            {"12", "36", "5a", "ee", "29", "7b", "8d", "8c", "8f", "8a", "85", "94", "a7", "f2", "0d", "17"},
            {"39", "4b", "dd", "7c", "84", "97", "a2", "fd", "1c", "24", "6c", "b4", "c7", "52", "f6", "01"}
	        };

	private static Scanner scanner;
	static int[] vetorRoundConstant;
	static int[][] sBox;
	static int key[];
	static int[][] keySchedule;

	public static int[][] tableToDecimal(String[][] table) {
	    int[][] tableInt = new int[table.length][table[0].length];
	    for (int i = 0; i < table.length; i++) {
	        for (int j = 0; j < table[i].length; j++) {
	            tableInt[i][j] = Integer.parseInt(table[i][j], 16);
            }
        }
	    return tableInt;
    }

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
	
	public static String loadFileContents(String file) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String fileContents = "";
		
		String line = reader.readLine(); 
		while (line != null) {
			fileContents += line;
			line = reader.readLine();
		}
		
		reader.close();
		return fileContents;
	}

	public static void main(String[] args) throws Exception {
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
		
		String textoFile = loadFileContents(INPUT_TEXT_FILE);
		
		expandKey();
		String txt = blockCipher(textoFile);
		
		BufferedWriter writer = null;
        try {
            File file = new File(OUTPUT_ENCRYPTED_FILE);
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(txt);
            writer.flush();
            writer.close();
            System.out.println("Encrypt Finalizado!");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
				    int[] bits = getMinMaxBits(rotWord[indexRotWord]);
					rotWord[indexRotWord] = sBox[bits[0]][bits[1]];
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

	public static int[] getMinMaxBits(int value) {
        String hexByte = Integer.toString(value, 16);
        int primeirosBits = 0;
        int ultimosBits = 0;
        if (hexByte.length() == 2) {
            primeirosBits = Integer.parseInt(Character.toString(hexByte.charAt(0)), 16);
            ultimosBits = Integer.parseInt(Character.toString(hexByte.charAt(1)), 16);
        } else {
            primeirosBits = 0;
            ultimosBits = Integer.parseInt(Character.toString(hexByte.charAt(0)), 16);
        }
        return new int[]{primeirosBits, ultimosBits};
    }

	public static String blockCipher(String textoSimples) {
		int[] texto = padding(textoSimples);
		int[][] tableL = tableToDecimal(L_TABLE);
		int[][] tableE = tableToDecimal(E_TABLE);
		int[][] matrizEstadoPrincipal = new int[texto.length / 4][4];
		int indexTexto = 0;
		for (int i = 0; i < matrizEstadoPrincipal.length; i++) {
			for (int j = 0; j < matrizEstadoPrincipal[i].length; j++) {
				matrizEstadoPrincipal[i][j] = texto[indexTexto];
				indexTexto++;
			}
		}
		int qtdBlocks = texto.length / 16;
		int[][] matrizEstado = new int[4][4];
		for (int index = 0; index < qtdBlocks; index++) {

		    // Xor com RoundKey 0
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    matrizEstado[i][j] = keySchedule[i][j] ^ matrizEstadoPrincipal[i + index * 4][j];
                }
            }

            // Round 1..10
            for (int iRound = 1; iRound < 11; iRound ++) {

                // Sub-Bytes
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        int[] bits = getMinMaxBits(matrizEstado[i][j]);
                        matrizEstado[i][j] = sBox[bits[0]][bits[1]];
                    }
                }

                // Shift Rows
                int[][] shiftRows = new int[4][4];
                shiftRows[0][0] = matrizEstado[0][0];
                shiftRows[1][0] = matrizEstado[1][0];
                shiftRows[2][0] = matrizEstado[2][0];
                shiftRows[3][0] = matrizEstado[3][0];

                shiftRows[0][1] = matrizEstado[1][1];
                shiftRows[1][1] = matrizEstado[2][1];
                shiftRows[2][1] = matrizEstado[3][1];
                shiftRows[3][1] = matrizEstado[0][1];

                shiftRows[0][2] = matrizEstado[2][2];
                shiftRows[1][2] = matrizEstado[3][2];
                shiftRows[2][2] = matrizEstado[0][2];
                shiftRows[3][2] = matrizEstado[1][2];

                shiftRows[0][3] = matrizEstado[3][3];
                shiftRows[1][3] = matrizEstado[0][3];
                shiftRows[2][3] = matrizEstado[1][3];
                shiftRows[3][3] = matrizEstado[2][3];

                if (iRound != 10) {
                    // MixColumns
                		int[][] mixedColumns = new int[4][4];
					int[] shiftRowsBits;
					int[] multiplyMatrix;
					int value01;
					int value02;
					int resultado[] = new int[4];
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                        		for (int k = 0; k < 4; k++) {
                        			int val1 = shiftRows[i][k];
                        			int val2 = MULTIPLY_MATRIX[j][k];
                        			
                        			if (val1 == 0 || val2 == 0) {
    									resultado[k] = 0;
    								} else if (val1 == 1) {
    									resultado[k] = val2;
    								} else if (val2 == 1) {
    									resultado[k] = val1;
    								} else {
    									shiftRowsBits = getMinMaxBits(shiftRows[i][k]);
    									multiplyMatrix = getMinMaxBits(MULTIPLY_MATRIX[j][k]);
    									value01 = tableL[shiftRowsBits[0]][shiftRowsBits[1]];
    									value02 = tableL[multiplyMatrix[0]][multiplyMatrix[1]];
    									    									
    									resultado[k] = value01 + value02;
    									//Se passou do #FF
    									if (resultado[k] > 255) {
    										resultado[k] = resultado[k] - 255;
    									}
    									
    									int[] result = getMinMaxBits(resultado[k]);
    									resultado[k] = tableE[result[0]][result[1]];
    								}
							}
                        		
                        		mixedColumns[i][j] = resultado[0];
							for (int k = 1; k < resultado.length; k++) {
								mixedColumns[i][j] = mixedColumns[i][j] ^ resultado[k];
							}
						}
                    }
                    
                    matrizEstado = mixedColumns;
                } else {
                		matrizEstado = shiftRows;
                }

                // Xor Round Key [iRound]
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        matrizEstado[i][j] = keySchedule[ i + iRound * 4][j] ^ matrizEstado[i][j];
                    }
                }
            }
            
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					matrizEstadoPrincipal[i + index * 4][j] = matrizEstado[i][j];
				}
			}
        }
        StringBuilder result = new StringBuilder();
		for (int i = 0; i < matrizEstadoPrincipal.length; i++) {
		    for (int j = 0; j < matrizEstadoPrincipal[i].length; j++) {
		    		String val = Integer.toString(matrizEstadoPrincipal[i][j], 16);
		    		
		    		if (val.length() == 1) {
		    			result.append("0").append(val);
		    		} else {
		    			result.append(val);
		    		}		    	
            }
        }
        return result.toString();
    }
	
	public static int[] padding(String textoSimples) {
		int diferenca = BLOCK_SIZE - (textoSimples.getBytes().length % BLOCK_SIZE);
		int[] result = new int[textoSimples.getBytes().length + diferenca];
		for (int i = 0; i < textoSimples.getBytes().length; i++) {
		    result[i] = textoSimples.charAt(i);
        }
        for (int i = textoSimples.getBytes().length; i < result.length; i++) {
		    result[i] = diferenca;
        }
        return result;
	}
}
