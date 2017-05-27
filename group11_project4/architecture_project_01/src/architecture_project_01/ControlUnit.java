package architecture_project_01;

//the class is to control the whole simulating process in CPU. Due to the phase one yet, we just implement the single step function.  
public class ControlUnit {
	

	public static final int PC 	= 0;
	public static final int MAR = 1;
	public static final int MBR = 2;
	public static final int IR 	= 3;
	public static final int R00 = 4;
	public static final int R01 = 5;
	public static final int R02 = 6;
	public static final int R03 = 7;
	public static final int R04 = 8;
	public static final int IX01 = 9;
	public static final int IX02 = 10;
	public static final int IX03 = 11;
	
	operatorUnit	in	= new operatorUnit();
	

	public boolean singleCircle() {
		
		register_MAR.value = register_PC.value;
		register_MBR.value = memory.memory_arr[register_MAR.value];
		register_IR.value = register_MBR.value;
		
		
		register_PC.selfIncrement();

		int instruction = register_IR.value;
		String b_i = toBinary_reg(instruction, 16);
		
		int opCode = Integer.parseInt(b_i.substring(0, 6), 2);
		int R = Integer.parseInt(b_i.substring(6, 8), 2);
		int IX = Integer.parseInt(b_i.substring(8, 10), 2);
		int I = Integer.parseInt(String.valueOf(b_i.charAt(10)));
		int address = Integer.parseInt(b_i.substring(11, 16), 2);
		//Project2
		int cc=register_CCR.CCR[Integer.parseInt(b_i.substring(6, 8),2)];
		int RX=Integer.parseInt(b_i.substring(6, 7),2);
		int RY=Integer.parseInt(b_i.substring(7, 8),2);
		int AL=Integer.parseInt(b_i.substring(8, 9),2);
		int LR=Integer.parseInt(b_i.substring(9, 10),2);
		int count=Integer.parseInt(b_i.substring(12, 16),2);
		int devID = Integer.parseInt(b_i.substring(11, 16),2);
		
		switch (opCode) {
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
				in.LDX(address,R, IX, I);
				break;
			case operatorUnit.STX_opcode:
				in.STX(address,R, IX, I);
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
			case operatorUnit.JZ_opcode://Project2
				in.JZ(address, R, IX, I);
				break;
			case operatorUnit.JNE_opcode:
				in.JNE(address, R, IX, I);
				break;
			case operatorUnit.JCC_opcode:
				in.JCC(address, cc, IX, I);
				break;
			case operatorUnit.JMA_opcode:
				in.JMA(address, IX, I);
				break;
			case operatorUnit.JSR_opcode:
				in.JSR(address, IX, I);
				break;
			case operatorUnit.RFS_opcode:
				in.RFS(address);
				break;
			case operatorUnit.SOB_opcode:
				in.SOB(address, LR, IX, I);
				break;
			case operatorUnit.JGE_opcode:
				in.JGE(address, R, IX, I);
				break;
			case operatorUnit.MLT_opcode:
				in.MLT(RX, RY);
				break;
			case operatorUnit.DVD_opcode:
				in.DVD(RX, RY);
				break;
			case operatorUnit.TRR_opcode:
				in.TRR(RX, RY);
				break;
			case operatorUnit.AND_opcode:
				in.AND(RX, RY);
				break;
			case operatorUnit.ORR_opcode:
				in.ORR(RX, RY);
				break;
			case operatorUnit.SRC_opcode:
				in.SRC(R, LR, count);
				break;
			case operatorUnit.RRC_opcode:
				in.RRC(R, LR, AL, count);
				break;
			case operatorUnit.IN_opcode:
				in.IN(R, devID);
				break;
			case operatorUnit.OUT_opcode:
				in.OUT(R, devID);
				break;
			case operatorUnit.FADD_opcode:
				in.FADD(R,IX,I,address);
				break;
			case operatorUnit.FSUB_opcode:
				in.FSUB(R, IX, I, address);
				break;
			case operatorUnit.VADD_opcode:
				in.VADD(R,IX,I,address);
				break;
			case operatorUnit.VSUB_opcode:
				in.VSUB(R, IX, I, address);
				break;
			case operatorUnit.CNVRT_opcode:
				in.CNVRT(R, IX, I, address);
				break;
			case operatorUnit.LDFR_opcode:
				in.LDFR(R,IX,I,address);
				break;
			case operatorUnit.STFR_opcode:
				in.STFR(R, IX, I, address);
				break;
			default:
				System.out.println("controlUnit: error instruction");
				//memory_io.write_m();
				return false;
		}

		//test(opCode);
		//System.out.println(opCode+" "+ R+" "+IX+" "+address+" ");
		return true;
	}

	private String toBinary_reg(int decNum, int bits) {
		String pre_0 = "";
		String b_i = Integer.toBinaryString(decNum);
		for (int j = 0; j < bits - b_i.length(); j++)
			pre_0 += '0';
		return (pre_0 + b_i);
	}
	
	public void run(){
		Boolean isValid = true;
		while(isValid){
			isValid = singleCircle();
		}
		
		System.out.println("program complete...");
	}
	
	private void test(int opCode){
		switch (opCode) {
			case operatorUnit.LDR_opcode:
				System.out.println("LDR(address, R, IX, I)");
				break;
			case operatorUnit.STR_opcode:
				System.out.println("STR(address, R, IX, I)");
				break;
			case operatorUnit.LDA_opcode:
				System.out.println("LDA(address, R, IX, I)");
				break;
			case operatorUnit.LDX_opcode:
				System.out.println("LDX(address, IX, I)");
				break;
			case operatorUnit.STX_opcode:
				System.out.println("STX(address, IX, I)");
				break;
			case operatorUnit.AMR_opcode:
				System.out.println("AMR(address, R, IX, I)");
				break;
			case operatorUnit.SMR_opcode:
				System.out.println("SMR(address, R, IX, I)");
				break;
			case operatorUnit.AIR_opcode:
				System.out.println("AIR(address, R)");
				break;
			case operatorUnit.SIR_opcode:
				System.out.println("SIR(address, R)");
				break;
			case operatorUnit.JZ_opcode://Project2
				System.out.println("JZ(address, R, IX, I)");
				break;
			case operatorUnit.JNE_opcode:
				System.out.println("JNE(address, R, IX, I)");
				break;
			case operatorUnit.JCC_opcode:
				System.out.println("JCC(address, cc, IX, I)");
				break;
			case operatorUnit.JMA_opcode:
				System.out.println("JMA(address, IX, I)");
				break;
			case operatorUnit.JSR_opcode:
				System.out.println("JSR(address, IX, I)");
				break;
			case operatorUnit.RFS_opcode:
				System.out.println("RFS(address)");
				break;
			case operatorUnit.SOB_opcode:
				System.out.println("SOB(address, LR, IX, I)");
				break;
			case operatorUnit.JGE_opcode:
				System.out.println("JGE(address, R, IX, I)");
				break;
			case operatorUnit.MLT_opcode:
				System.out.println("MLT(RX, RY)");
				break;
			case operatorUnit.DVD_opcode:
				System.out.println("DVD(RX, RY)");
				break;
			case operatorUnit.TRR_opcode:
				System.out.println("TRR(RX, RY)");
				break;
			case operatorUnit.AND_opcode:
				System.out.println("AND(RX, RY)");
				break;
			case operatorUnit.ORR_opcode:
				System.out.println("ORR(RX, RY)");
				break;
			case operatorUnit.SRC_opcode:
				System.out.println("SRC(R, LR, count)");
				break;
			case operatorUnit.RRC_opcode:
				System.out.println("RRC(R, LR, AL, count)");
				break;
			case operatorUnit.IN_opcode:
				System.out.println("IN(R, devID)");
				break;
			case operatorUnit.OUT_opcode:
				System.out.println("OUT(R, devID)");
				break;
			default:
				System.out.println("controlUnit: error instruction");
		}
	}
	
}
