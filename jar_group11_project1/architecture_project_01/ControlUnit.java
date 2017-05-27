package architecture_project_01;

//the class is to control the whole simulating process in CPU. Due to the phase one yet, we just implement the single step function.  
public class ControlUnit {
	
	operatorUnit	in	= new operatorUnit();

	public void singleCircle() {
		
		
		//process of the information transfer in registers
		register_MAR.value = register_PC.value;
		mGUI.console_textArea.append("MAR <- PC\nMAR: " + toBinary_reg(register_MAR.value,12) + "\n");
		register_MBR.value = memory.memory_arr[register_MAR.value];
		mGUI.console_textArea.append("MBR <- C(MAR)\nMBR: " + toBinary_reg(register_MAR.value,16) + "\n");
		register_IR.value = register_MBR.value;
		mGUI.console_textArea.append("IR <- MBR\nIR: " + toBinary_reg(register_IR.value,16) + "\n");
		
		register_PC.selfIncrement();
		mGUI.console_textArea.append("PC <- PC + 1\nPC: " + toBinary_reg(register_PC.value,12) + "\n");

		//get the instruction and decode it
		int instruction = register_IR.value;
		String b_i = toBinary_reg(instruction, 16);
		
		int opCode = Integer.parseInt(b_i.substring(0, 6), 2);
		int R = Integer.parseInt(b_i.substring(6, 8), 2);
		int IX = Integer.parseInt(b_i.substring(8, 10), 2);
		int I = Integer.parseInt(String.valueOf(b_i.charAt(10)));
		int address = Integer.parseInt(b_i.substring(11, 16), 2);
		
		mGUI.console_textArea.append("DECODING INSTRUCTION: " + b_i +"\n");
		switch (opCode) {
			//case 0:
			//	System.out.println("controlUnit error instruction");
			case operatorUnit.LDR_opcode:
				in.LDR(address, R, IX, I);
				break;
			case operatorUnit.STR_opcode:
				in.STR(address, R, IX, I);
				break;
			case operatorUnit.LDA_opcode:
				in.LDA(address, R, IX, I);
				break;
			case operatorUnit.LDX_opcode:
				in.LDX(address, IX, I);
				break;
			case operatorUnit.STX_opcode:
				in.STX(address, IX, I);
				break;
			case operatorUnit.AMR_opcode:
				in.AMR(address, R, IX, I);
				break;
			case operatorUnit.SMR_opcode:
				in.SMR(address, R, IX, I);
				break;
			case operatorUnit.AIR_opcode:
				in.AIR(address, R);
				break;
			case operatorUnit.SIR_opcode:
				in.SIR(address, R);
				break;
			default:
				mGUI.console_textArea.append("controlUnit error instruction!!!\n");
		}

		mGUI.console_textArea.append("ONE CIRCLE FINISHED.\n");
	}

	
	//add 0 in front of the binary number
	private String toBinary_reg(int decNum, int bits) {
		String pre_0 = "";
		String b_i = Integer.toBinaryString(decNum);
		for (int j = 0; j < bits - b_i.length(); j++)
			pre_0 += '0';
		return (pre_0 + b_i);
	}
}
