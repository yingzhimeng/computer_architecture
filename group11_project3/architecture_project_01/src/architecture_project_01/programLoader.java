package architecture_project_01;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class programLoader {

	public int						proID		= 0;
	public HashMap<Integer, String>	pseudocode	= new HashMap<Integer, String>();
	public HashMap<String, String>	instruction	= new HashMap<String, String>();
	public int						line		= 0;
	public int						mypcstart	= 100;

	public void load(int id) {
		if (id == 1) {
			int pc = 100;
			String pcStr = "000001100100"; // 100
			memory.memory_arr[11] = 128;
			if (id == 1) {
				register_PC.value = 100;
				register_IX.value_01 = 500;
				register_IX.value_02 = 100;
				register_IX.value_03 = 500;

				readProFile("program1.txt");

				for (int i = pc; i < pc + line; i++) {
					memory.memory_arr[i] = Integer.parseInt(
							(instruction.get(Integer.toBinaryString(i))), 2);
				}
			}
		}
		
		if(id == 2){
			int pc = 100;
			
			memory.memory_arr[20] = 100;//boot
			memory.memory_arr[21] = 400;
			memory.memory_arr[22] = 500;
			memory.memory_arr[15] = 32;
			memory.memory_arr[16] = 10;
			memory.memory_arr[9] = 126;
			memory.memory_arr[25] = 135;
			memory.memory_arr[24] = 199;//boot
			memory.memory_arr[23] = 169;
			memory.memory_arr[2] = 260;
			memory.memory_arr[3] = 230;
			
			
			memory.memory_arr[10] = 1000;
			memory.memory_arr[11] = 1100;
			memory.memory_arr[12] = 1200;
			memory.memory_arr[13] = 1300;
			
			memory.memory_arr[4] = 1000;
			memory.memory_arr[5] = 1100;
			memory.memory_arr[6] = 1200;
			memory.memory_arr[7] = 1300;
			
			register_PC.value = 100;
			register_IX.value_01 = 500;
			register_IX.value_02 = 100;
			register_IX.value_03 = 400;

			readProFile("program2.txt");

			for (int i = pc; i < pc + line; i++) {
				memory.memory_arr[i] = Integer.parseInt(
						(instruction.get(Integer.toBinaryString(i))), 2);
			}
			
		}
	}

	public void readProFile(String fileName) {
		line = 0;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));
			String content = null;
			while ((content = in.readLine()) != null) {
				pseudocode.put(line, content);
				line++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for (int i = mypcstart; i < mypcstart + line; i++)// form the binary
															// code according to
															// machine code
		{
			String temp = pseudocode.get(i - mypcstart);
			// System.out.println(temp);
			String opcode = "";
			String split[] = temp.split(" ");
			String linestr = Integer.toBinaryString(i);
			String command = "";
			opcode = findOpcode(split[0]);
			if (split[0].equals("LDR") || split[0].equals("STR")
					|| split[0].equals("LDA") || split[0].equals("AMR")
					|| split[0].equals("SMR") || split[0].equals("JNE")
					|| split[0].equals("JZ") || split[0].equals("SOB")
					|| split[0].equals("JGE") || split[0].equals("JCC")
					|| split[0].equals("FADD") || split[0].equals("FSUB")
					|| split[0].equals("LDFR") || split[0].equals("STFR")
					|| split[0].equals("CNVRT") || split[0].equals("VADD")
					|| split[0].equals("VSUB"))// OP R IX I ADDR
			{// 10个
				command = command + opcode;
				if (split[1].equals("R0"))
					command = command + "00";
				else if (split[1].equals("R1"))
					command = command + "01";
				else if (split[1].equals("R2"))
					command = command + "10";
				else if (split[1].equals("R3"))
					command = command + "11";

				if (split[2].equals("X0"))
					command = command + "00";
				else if (split[2].equals("X1"))
					command = command + "01";
				else if (split[2].equals("X2"))
					command = command + "10";
				else if (split[2].equals("X3"))
					command = command + "11";

				if (split[3].equals("I"))
					command = command + "1";
				else if (split[3].equals("0"))
					command = command + "0";

				if (Integer.parseInt(split[4]) >= 0
						&& Integer.parseInt(split[4]) <= 31) {

					String addr = Integer.toBinaryString(Integer
							.parseInt(split[4]));
					command = command + add0(addr);

				} else
					mGUI.console_textArea.append("LOAD PROGRAM ERROR");
				// System.out.println(linestr);

			} else if (split[0].equals("LDX") || split[0].equals("STX"))// OPCODE
																		// X X I
																		// ADDR
			{// 2GE
				command = command + opcode;
				if (split[1].equals("X0"))
					command = command + "00";
				else if (split[1].equals("X1"))
					command = command + "01";
				else if (split[1].equals("X2"))
					command = command + "10";
				else if (split[1].equals("X3"))
					command = command + "11";

				if (split[2].equals("X0"))
					command = command + "00";
				else if (split[2].equals("X1"))
					command = command + "01";
				else if (split[2].equals("X2"))
					command = command + "10";
				else if (split[2].equals("X3"))
					command = command + "11";

				if (split[3].equals("I"))
					command = command + "1";
				else if (split[3].equals("0"))
					command = command + "0";

				if (Integer.parseInt(split[4]) >= 0
						&& Integer.parseInt(split[4]) <= 31) {
					String addr = Integer.toBinaryString(Integer
							.parseInt(split[4]));
					command = command + add0(addr);
				} else
					mGUI.console_textArea.append("LOAD PROGRAM ERROR");
				// System.out.println(linestr);
			}

			else if (split[0].equals("JMA") || split[0].equals("JSR"))// OP X I
																		// ADDR
			{// 2个
				command = command + opcode + "00";
				if (split[1].equals("X0"))
					command = command + "00";
				else if (split[1].equals("X1"))
					command = command + "01";
				else if (split[1].equals("X2"))
					command = command + "10";
				else if (split[1].equals("X3"))
					command = command + "11";

				if (split[2].equals("I"))
					command = command + "1";
				else if (split[2].equals("0"))
					command = command + "0";

				if (Integer.parseInt(split[3]) >= 0
						&& Integer.parseInt(split[3]) <= 31) {
					String addr = Integer.toBinaryString(Integer
							.parseInt(split[3]));
					command = command + add0(addr);
				} else
					mGUI.console_textArea.append("LOAD PROGRAM ERROR");
				// System.out.println(linestr);
			}

			else if (split[0].equals("SIR") || split[0].equals("AIR")
					|| split[0].equals("IN") || split[0].equals("OUT")
					|| split[0].equals("CHK"))// OP R IMMD
			{// 5ge
				command = command + opcode;
				if (split[1].equals("R0"))
					command = command + "00";
				else if (split[1].equals("R1"))
					command = command + "01";
				else if (split[1].equals("R2"))
					command = command + "10";
				else if (split[1].equals("R3"))
					command = command + "11";

				command = command + "00" + "0";
				if (Integer.parseInt(split[2]) >= 0
						&& Integer.parseInt(split[2]) <= 31) {
					String addr = Integer.toBinaryString(Integer
							.parseInt(split[2]));
					command = command + add0(addr);
				} else
					mGUI.console_textArea.append("LOAD PROGRAM ERROR");
				// System.out.println(linestr);

			} else if (split[0].equals("RFS"))// OPCODE IMMD
			{// 1ge
				command = command + opcode + "00" + "00" + "0";
				if (Integer.parseInt(split[1]) >= 0
						&& Integer.parseInt(split[1]) <= 31) {
					String addr = Integer.toBinaryString(Integer
							.parseInt(split[1]));
					command = command + add0(addr);
				} else
					mGUI.console_textArea.append("LOAD PROGRAM ERROR");
			} else if (split[0].equals("MLT") || split[0].equals("DVD")
					|| split[0].equals("TRR") || split[0].equals("AND")
					|| split[0].equals("ORR")) {// 5ge
				command = command + opcode;
				if (split[1].equals("R0"))
					command = command + "00";
				else if (split[1].equals("R1"))
					mGUI.console_textArea.append("LOAR PROGRAM ERROR");
				else if (split[1].equals("R2"))
					command = command + "10";
				else if (split[1].equals("R3"))
					mGUI.console_textArea.append("LOAR PROGRAM ERROR");

				if (split[2].equals("R0"))
					command = command + "00";
				else if (split[2].equals("R1"))
					mGUI.console_textArea.append("LOAR PROGRAM ERROR");
				else if (split[2].equals("R2"))
					command = command + "10";
				else if (split[2].equals("R3"))
					mGUI.console_textArea.append("LOAR PROGRAM ERROR");
				command = command + "0" + "00000";
			} else if (split[0].equals("NOT")) {// 1ge
				command = command + opcode;
				if (split[1].equals("R0"))
					command = command + "00";
				else if (split[1].equals("R1"))
					command = command + "01";
				else if (split[1].equals("R2"))
					command = command + "10";
				else if (split[1].equals("R3"))
					command = command + "11";
				command = command + "00" + "0" + "00000";
			}
			instruction.put(linestr, command);
			// System.out.println(command);

		}
		System.out.println();

	}

	public String findOpcode(String str) {
		String result = "";
		if (str.equals("LDR"))
			result = "000001";
		else if (str.equals("STR"))
			result = "000010";
		else if (str.equals("LDA"))
			result = "000011";
		else if (str.equals("LDX"))
			result = "101001";
		else if (str.equals("STX"))
			result = "101010";
		else if (str.equals("AMR"))
			result = "000100";
		else if (str.equals("SMR"))
			result = "000101";
		else if (str.equals("AIR"))
			result = "000110";
		else if (str.equals("SIR"))
			result = "000111";
		else if (str.equals("JZ"))
			result = "001010";
		else if (str.equals("JNE"))
			result = "001011";
		else if (str.equals("JCC"))
			result = "001100";
		else if (str.equals("JMA"))
			result = "001101";
		else if (str.equals("JSR"))
			result = "001110";
		else if (str.equals("RFS"))
			result = "001111";
		else if (str.equals("SOB"))
			result = "010000";
		else if (str.equals("JGE"))
			result = "010001";
		else if (str.equals("MLT"))
			result = "010100";
		else if (str.equals("DVD"))
			result = "010101";
		else if (str.equals("TRR"))
			result = "010110";
		else if (str.equals("AND"))
			result = "010111";
		else if (str.equals("ORR"))
			result = "011000";
		else if (str.equals("NOT"))
			result = "011001";
		else if (str.equals("SRC"))
			result = "011111";
		else if (str.equals("RRC"))
			result = "100000";
		else if (str.equals("IN"))
			result = "111101";
		else if (str.equals("OUT"))
			result = "111110";
		else if (str.equals("LDFR"))
			result = "110010";
		else if (str.equals("STFR"))
			result = "110011";
		else if (str.equals("FADD"))
			result = "100001";
		else if (str.equals("FSUB"))
			result = "100010";
		else if (str.equals("CNVRT"))
			result = "100101";
		else if (str.equals("VADD"))
			result = "100011";
		else if (str.equals("VSUB"))
			result = "100100";
		else
			System.out.println("INSTRUCTION NOT TRANSFER");

		return result;
	}

	public String add0(String addr) {
		String result = addr;
		for (int i = addr.length(); i < 5; i++) {
			result = "0" + result;
		}
		return result;
	}
}
