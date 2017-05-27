package architecture_project_01;

// all instructions

public class operatorUnit {
	
	public static final int LDR_opcode = 1;
	public static final int STR_opcode = 2;
	public static final int LDA_opcode = 3;
	public static final int AMR_opcode = 4;
	public static final int SMR_opcode = 5;
	public static final int AIR_opcode = 6;
	public static final int SIR_opcode = 7;
	public static final int LDX_opcode = 41;
	public static final int STX_opcode = 42;

	//get the effective address
	private int getEffectiveAddr(int IX,int I, int addr_m){
		if(I == 0){
			switch (IX) {
				case 0:
					return addr_m;
				case 1:
					return register_IX.value_01+addr_m;
				case 2:
					return register_IX.value_02+addr_m;
				case 3:
					return register_IX.value_03+addr_m;
				default:
					break;
			}
		}else{
			switch (IX) {
				case 0:
					return memory.memory_arr[addr_m];
				case 1:
					return memory.memory_arr[register_IX.value_01+addr_m];
				case 2:
					return memory.memory_arr[register_IX.value_02+addr_m];
				case 3:
					return memory.memory_arr[register_IX.value_03+addr_m];
				default:
					break;
			}
		}
		
		return -1;
	}
	
	public void LDR(int addr_m, int R, int IX, int I) {
		
		int EA = getEffectiveAddr(IX, I, addr_m);
		
		switch (R) {
			case 0:
				register_GPR.R0_value = memory_io.memory_arr[EA];
				break;
			case 1:
				register_GPR.R1_value = memory_io.memory_arr[EA];
				break;
			case 2:
				register_GPR.R2_value = memory_io.memory_arr[EA];
				break;
			case 3:
				register_GPR.R3_value = memory_io.memory_arr[EA];
				break;
			default:
				System.out.println("LDR instruction wrong GPR number.");
				break;
		}
	}
	
	public void STR(int addr_m, int R, int IX, int I){
		int EA = getEffectiveAddr(IX, I, addr_m);
		
		switch (R) {
			case 0:
				memory_io.memory_arr[EA] = register_GPR.R0_value;
				break;
			case 1:
				memory_io.memory_arr[EA] = register_GPR.R1_value;
				break;
			case 2:
				memory_io.memory_arr[EA] = register_GPR.R2_value;
				break;
			case 3:
				memory_io.memory_arr[EA] = register_GPR.R3_value;
				break;
			default:
				System.out.println("STR instruction wrong GPR number.");
				break;
		}
	}
	
	public void LDA(int addr_m,int R, int IX, int I) {
		
		int EA = getEffectiveAddr(IX, I, addr_m);
		
		switch (R) {
			case 0:
				register_GPR.R0_value = EA;
				break;
			case 1:
				register_GPR.R1_value = EA;
				break;
			case 2:
				register_GPR.R2_value = EA;
				break;
			case 3:
				register_GPR.R3_value = EA;
				break;
			default:
				System.out.println("LDA instruction wrong GPR number.");
				break;
		}
	}
	
	public void AMR(int addr_m,int R, int IX, int I) {
		
		int EA = getEffectiveAddr(IX, I, addr_m);
		
		switch (R) {
			case 0:
				register_GPR.R0_value += memory_io.memory_arr[EA];
				break;
			case 1:
				register_GPR.R1_value += memory_io.memory_arr[EA];
				break;
			case 2:
				register_GPR.R2_value += memory_io.memory_arr[EA];
				break;
			case 3:
				register_GPR.R3_value += memory_io.memory_arr[EA];
				break;
			default:
				System.out.println("AMR instruction wrong GPR number.");
				break;
		}
	}
	
	public void SMR(int addr_m,int R, int IX, int I){
		
		int EA = getEffectiveAddr(IX, I, addr_m);
		
		switch (R) {
			case 0:
				register_GPR.R0_value -= memory_io.memory_arr[EA];
				break;
			case 1:
				register_GPR.R1_value -= memory_io.memory_arr[EA];
				break;
			case 2:
				register_GPR.R2_value -= memory_io.memory_arr[EA];
				break;
			case 3:
				register_GPR.R3_value -= memory_io.memory_arr[EA];
				break;
			default:
				System.out.println("SMR instruction wrong GPR number.");
				break;
		}
	}
	
	public void AIR(int immed, int R){
		
		switch (R) {
			case 0:
				register_GPR.R0_value += immed;
				break;
			case 1:
				register_GPR.R1_value += immed;
				break;
			case 2:
				register_GPR.R2_value += immed;
				break;
			case 3:
				register_GPR.R3_value += immed;
				break;
			default:
				System.out.println("AIR instruction wrong GPR number.");
				break;
		}
	}
	
	public void SIR(int immed, int R){
		
		switch (R) {
			case 0:
				register_GPR.R0_value -= immed;
				break;
			case 1:
				register_GPR.R1_value -= immed;
				break;
			case 2:
				register_GPR.R2_value -= immed;
				break;
			case 3:
				register_GPR.R3_value -= immed;
				break;
			default:
				System.out.println("AIR instruction wrong GPR number.");
				break;
		}
	}
	
public void LDX(int addr_m, int IX, int I){
		
		int EA = getEffectiveAddr(IX, I, addr_m);
		
		switch (IX) {
			case 0:
				break;
			case 1:
				register_IX.value_01 = memory_io.memory_arr[EA];
				break;
			case 2:
				register_IX.value_02 = memory_io.memory_arr[EA];
				break;
			case 3:
				register_IX.value_03 = memory_io.memory_arr[EA];
				break;
			default:
				System.out.println("LDX instruction wrong IX number.");
				break;
		}
	}

public void STX(int addr_m, int IX, int I){
	
	int EA = getEffectiveAddr(IX, I, addr_m);
	
	switch (IX) {
		case 0:
			memory_io.memory_arr[EA] = register_IX.value_01;
			break;
		case 1:
			memory_io.memory_arr[EA] = register_IX.value_02;
			break;
		case 2:
			memory_io.memory_arr[EA] = register_IX.value_03;
			break;
		default:
			System.out.println("STX instruction wrong GPR number.");
			break;
	}
}
	
}
