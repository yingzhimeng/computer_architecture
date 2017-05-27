package architecture_project_01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import sun.misc.Cache;


public class operatorUnit {

	/*
	 * public static void main(String[] args) { operatorUnit a=new
	 * operatorUnit(); register_GPR.R0_value=100; a.JZ(1, 0, 1, 1);
	 * //a.MLT(0,0); //a.DVD(0, 2); //a.AND(0, 2); //a.SRC(0, 0, 2);
	 * System.out.println(register_PC.getValue()); }
	 */
	public static final int	LDR_opcode			= 1;
	public static final int	STR_opcode			= 2;
	public static final int	LDA_opcode			= 3;
	public static final int	AMR_opcode			= 4;
	public static final int	SMR_opcode			= 5;
	public static final int	AIR_opcode			= 6;
	public static final int	SIR_opcode			= 7;
	public static final int	LDX_opcode			= 41;
	public static final int	STX_opcode			= 42;
	// Project two
	public static final int	JZ_opcode			= 10;
	public static final int	JNE_opcode			= 11;
	public static final int	JCC_opcode			= 12;
	public static final int	JMA_opcode			= 13;
	public static final int	JSR_opcode			= 14;
	public static final int	RFS_opcode			= 15;
	public static final int	SOB_opcode			= 16;
	public static final int	JGE_opcode			= 17;
	public static final int	MLT_opcode			= 20;
	public static final int	DVD_opcode			= 21;
	public static final int	TRR_opcode			= 22;
	public static final int	AND_opcode			= 23;
	public static final int	ORR_opcode			= 24;
	public static final int	NOT_opcode			= 25;
	public static final int	SRC_opcode			= 31;
	public static final int	RRC_opcode			= 32;
	public static final int	IN_opcode			= 61;
	public static final int	OUT_opcode			= 62;
	public static final int	OVERFLOW			= 0;
	public static final int	UNDERFLOW			= 1;
	public static final int	DIVZERO				= 2;
	public static final int	EQUALORNOT			= 3;
	public static final int	CONSOLE_KEYBOARD	= 0;
	public static final int	CONSOLE_PRINTER		= 1;
	public static final int	CARD_READER			= 2;
	public static int		characterstore		= 0;
	//phase4
	public static final int FADD_opcode=33;
	public static final int FSUB_opcode=34;
	public static final int VADD_opcode=35;
	public static final int VSUB_opcode=36;
	public static final int CNVRT_opcode=37;
	public static final int LDFR_opcode=50;
	public static final int STFR_opcode=51;

	private int getEffectiveAddr(int IX, int I, int addr_m) {

		if (I == 0) {
			switch (IX) {
				case 0:
					return addr_m;
				case 1:
					return register_IX.value_01 + addr_m;
				case 2:
					return register_IX.value_02 + addr_m;
				case 3:
					return register_IX.value_03 + addr_m;
				default:
					break;
			}
		} else {
			switch (IX) {
				case 0:
					return memory.memory_arr[addr_m];
				case 1:
					return memory.memory_arr[register_IX.value_01 + addr_m];
				case 2:
					return memory.memory_arr[register_IX.value_02 + addr_m];
				case 3:
					return memory.memory_arr[register_IX.value_03 + addr_m];
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
				register_GPR.R0_value = cache.getCacheLineValue(EA);
				break;
			case 1:
				register_GPR.R1_value = cache.getCacheLineValue(EA);
				break;
			case 2:
				register_GPR.R2_value = cache.getCacheLineValue(EA);
				break;
			case 3:
				register_GPR.R3_value = cache.getCacheLineValue(EA);
				break;
			default:
				System.out.println("LDR instruction wrong GPR number.");
				break;
		}
	}

	public void STR(int addr_m, int R, int IX, int I) {
		int EA = getEffectiveAddr(IX, I, addr_m);

		switch (R) {
			case 0:
				cache.setCacheLineValue(EA, register_GPR.R0_value);
				break;
			case 1:
				cache.setCacheLineValue(EA, register_GPR.R1_value);
				break;
			case 2:
				cache.setCacheLineValue(EA, register_GPR.R2_value);
				break;
			case 3:
				cache.setCacheLineValue(EA, register_GPR.R3_value);
				break;
			default:
				System.out.println("STR instruction wrong GPR number.");
				break;
		}
	}

	public void LDA(int addr_m, int R, int IX, int I) {

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

	public void AMR(int addr_m, int R, int IX, int I) {

		int EA = getEffectiveAddr(IX, I, addr_m);

		switch (R) {
			case 0:
				register_GPR.R0_value += cache.getCacheLineValue(EA);
				break;
			case 1:
				register_GPR.R1_value += cache.getCacheLineValue(EA);
				break;
			case 2:
				register_GPR.R2_value += cache.getCacheLineValue(EA);
				break;
			case 3:
				register_GPR.R3_value += cache.getCacheLineValue(EA);
				break;
			default:
				System.out.println("AMR instruction wrong GPR number.");
				break;
		}
	}

	public void SMR(int addr_m, int R, int IX, int I) {

		int EA = getEffectiveAddr(IX, I, addr_m);

		switch (R) {
			case 0:
				register_GPR.R0_value -= cache.getCacheLineValue(EA);
				break;
			case 1:
				register_GPR.R1_value -= cache.getCacheLineValue(EA);
				break;
			case 2:
				register_GPR.R2_value -= cache.getCacheLineValue(EA);
				break;
			case 3:
				register_GPR.R3_value -= cache.getCacheLineValue(EA);
				break;
			default:
				System.out.println("SMR instruction wrong GPR number.");
				break;
		}
	}

	public void AIR(int immed, int R) {

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

	public void SIR(int immed, int R) {

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

	public void LDX(int addr_m, int R, int IX, int I) {

		int EA = getEffectiveAddr(IX, I, addr_m);

		switch (R) {
			case 1:
				register_IX.value_01 = cache.getCacheLineValue(EA);
				break;
			case 2:
				register_IX.value_02 = cache.getCacheLineValue(EA);
				break;
			case 3:
				register_IX.value_03 = cache.getCacheLineValue(EA);
				break;
			default:
				System.out.println("LDX instruction wrong IX number.");
				break;
		}
	}

	public void STX(int addr_m, int R, int IX, int I) {

		int EA = getEffectiveAddr(IX, I, addr_m);

		switch (R) {
			case 1:
				cache.setCacheLineValue(EA, register_IX.value_01);
				break;
			case 2:
				cache.setCacheLineValue(EA, register_IX.value_02);
				break;
			case 3:
				cache.setCacheLineValue(EA, register_IX.value_03);
				break;
			default:
				System.out.println("STX instruction wrong IX number.");
				break;
		}

	}

	// project two
	// Transfer Instructions
	// JZ
	public void JZ(int addr_m, int R, int IX, int I) {
		int EA = getEffectiveAddr(IX, I, addr_m);

		int tempR = getValueFromReById(R);
		if (tempR == 0)
			register_PC.setValue(EA);
	}

	// JNE
	public void JNE(int addr_m, int R, int IX, int I) {
		int EA = getEffectiveAddr(IX, I, addr_m);

		int tempR = getValueFromReById(R);
		if (tempR != 0)
			register_PC.setValue(EA);
	}

	// JCC
	public void JCC(int addr_m, int cc, int IX, int I) {
		int EA = getEffectiveAddr(IX, I, addr_m);
		if (register_CCR.getCCR()[cc] == 1)
			register_PC.setValue(EA);
	}

	// JMA
	public void JMA(int addr_m, int IX, int I) {
		int EA = getEffectiveAddr(IX, I, addr_m);

		register_PC.setValue(EA);

	}

	// JSR
	public void JSR(int addr_m, int IX, int I) {
		int EA = getEffectiveAddr(IX, I, addr_m);
		register_GPR.setR3_value(register_PC.getValue() + 1);
		register_PC.setValue(EA);
	}

	// RFS
	// immediate is equals address
	public void RFS(int immed) {
		register_GPR.setR0_value(immed);
		register_PC.setValue(register_GPR.getR3_value());
	}

	// SOB
	public void SOB(int addr_m, int R, int IX, int I) {
		int EA = getEffectiveAddr(IX, I, addr_m);

		switch (R) {
			case 0:
				register_GPR.setR0_value(register_GPR.getR0_value() - 1);
				if (register_GPR.getR0_value() > 0)
					register_PC.setValue(EA);
				break;
			case 1:
				register_GPR.setR1_value(register_GPR.getR1_value() - 1);
				if (register_GPR.getR1_value() > 0)
					register_PC.setValue(EA);
				break;
			case 2:
				register_GPR.setR2_value(register_GPR.getR2_value() - 1);
				if (register_GPR.getR2_value() > 0)
					register_PC.setValue(EA);
				break;
			case 3:
				register_GPR.setR3_value(register_GPR.getR3_value() - 1);
				if (register_GPR.getR3_value() > 0)
					register_PC.setValue(EA);
				break;

			default:
				System.out.println("Instruction SOB is wrong !");
				break;
		}
	}

	// JGE
	public void JGE(int addr_m, int R, int IX, int I) {

		int EA = getEffectiveAddr(IX, I, addr_m);

		switch (R) {
			case 0:
				if (register_GPR.getR0_value() >= 0)
					register_PC.setValue(EA);
				break;
			case 1:
				if (register_GPR.getR1_value() >= 0)
					register_PC.setValue(EA);
				break;
			case 2:
				if (register_GPR.getR2_value() >= 0)
					register_PC.setValue(EA);
				break;
			case 3:
				if (register_GPR.getR3_value() >= 0)
					register_PC.setValue(EA);
				break;

			default:
				System.out.println("Instruction JGE is wrong !");
				break;
		}
	}

	// Arithmetic and Logical Instruction
	// another instruction format

	// MLT
	public void MLT(int RX, int RY) {
		if (RX != 0 && RX != 2 || RY != 0 && RY != 2) {
			System.out.println("MLT is wrong !");
		}
		if (RX == 0 || RX == 2 && RY == 0 || RY == 2) {
			int data1 = getValueFromReById(RX);
			int data2 = getValueFromReById(RY);
			int temp = data1 * data2;
			if (temp < Math.pow(2, 30) - 1) {
				int next = 0;
				if (RX == 0)
					next = 1;
				else
					next = 3;
				String value = toBinary32(temp);
				System.out.println(value);
				setValuetoReById(RX,
						Integer.parseInt(value.substring(0, 16), 2));
				setValuetoReById(next, Integer.parseInt(value.substring(16), 2));
			} else {
				System.out.println("OVERFLOW");
				register_CCR.CCR[0] = OVERFLOW;
			}

		}
	}

	// DVD
	public void DVD(int RX, int RY) {
		if (RX != 0 && RX != 2 || RY != 0 && RY != 2) {
			System.out.println("DVD is wrong !");
		}
		if (RX == 0 || RX == 2 && RY == 0 || RY == 2) {
			int data1 = getValueFromReById(RX);
			int data2 = getValueFromReById(RY);

			if (data2 != 0) {
				int temp1 = data1 / data2;
				int temp2 = data1 % data2;

				int next = 0;
				if (RX == 0)
					next = 1;
				else
					next = 3;
				String quotient = toBinary16(temp1);
				String remainder = toBinary16(temp2);
				System.out.println(quotient + "   " + remainder);
				setValuetoReById(RX, Integer.parseInt(quotient, 2));
				setValuetoReById(next, Integer.parseInt(remainder, 2));
			} else {
				System.out.println("DIVZERO");
				register_CCR.CCR[1] = DIVZERO;
			}

		}
	}

	// TRR
	public void TRR(int RX, int RY) {
		int data1 = getValueFromReById(RX);
		int data2 = getValueFromReById(RY);

		if (data1 == data2)
			register_CCR.CCR[4] = EQUALORNOT;
		else
			register_CCR.CCR[4] = 0;
	}

	// AND
	public void AND(int RX, int RY) {
		int data1 = getValueFromReById(RX);
		int data2 = getValueFromReById(RY);

		setValuetoReById(RX, data1 & data2);

	}

	// ORR
	public void ORR(int RX, int RY) {
		int data1 = getValueFromReById(RX);
		int data2 = getValueFromReById(RY);

		setValuetoReById(RX, data1 | data2);

	}

	// NOT
	public void NOT(int RX) {
		int data1 = getValueFromReById(RX);

		setValuetoReById(RX, ~data1);
	}

	// get value by ID from general register R0-R3
	public static int getValueFromReById(int id) {
		int temp = 0;
		switch (id) {
			case 0:
				temp = register_GPR.getR0_value();
				break;
			case 1:
				temp = register_GPR.getR1_value();
				break;
			case 2:
				temp = register_GPR.getR2_value();
				break;
			case 3:
				temp = register_GPR.getR3_value();
				break;

			default:
				System.out
						.println("geting value by ID from general register R0-R3 is wrong");
				break;
		}
		return temp;
	}

	// set value by ID to general register R0-R3
	public static void setValuetoReById(int id, int value) {
		switch (id) {
			case 0:
				register_GPR.setR0_value(value);
				break;
			case 1:
				register_GPR.setR1_value(value);
				break;
			case 2:
				register_GPR.setR2_value(value);
				break;
			case 3:
				register_GPR.setR3_value(value);
				break;

			default:
				System.out
						.println("seting value by ID from general register R0-R3 is wrong");
				break;
		}
	}

	// toBinary32
	private String toBinary32(int num) {
		String temp = Integer.toBinaryString(num);

		for (int i = temp.length(); i < 32; i++) {
			temp = "0" + temp;
		}
		return temp;
	}

	// toBinary16
	private String toBinary16(int num) {
		String temp = Integer.toBinaryString(num);

		for (int i = temp.length(); i < 16; i++) {
			temp = "0" + temp;
		}
		return temp;
	}

	/**
	 * //Shift/Rotate Operations
	 */
	// Shift Operation
	// SRC
	public void SRC(int R, int LR, int count) {
		int tempR = getValueFromReById(R);
		if (LR == 1) {
			setValuetoReById(R, tempR << count);
		}
		if (LR == 0) {
			setValuetoReById(R, tempR >> count);
		}
	}

	// Rotate Operation
	// RRC
	public void RRC(int R, int LR, int AL, int count) {
		int tempR = getValueFromReById(R);
		String binaryR = toBinary16(tempR);
		char[] temp = new char[16];
		for (int i = 0; i < 16; i++) {
			temp[i] = binaryR.charAt(i);
		}
		if (LR == 1) {
			for (int i = 0; i < 16 - count; i++) {
				temp[i] = binaryR.charAt(i + count);
			}
			for (int i = 0; i < count; i++) {
				temp[16 - count + i] = binaryR.charAt(i);
			}
		}
		if (LR == 0) {
			for (int i = 0; i < 16 - count; i++) {
				temp[i + count] = binaryR.charAt(i);
			}
			for (int i = 0; i < count; i++) {
				temp[i] = binaryR.charAt(16 - count + i);
			}
		}

		String temp1 = "";
		for (int i = 0; i < 16; i++) {
			temp1 = temp1 + temp[i];
		}
		setValuetoReById(R, Integer.parseInt(temp1));
	}

	public void IN(int R, int devid) {
		if (devid == CONSOLE_KEYBOARD) {
			mGUI.console_textArea
					.append("INPUT A VALUE(use enter key to input)\n");
			while (true) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				int val = 0;
				int flag = 1;
				if (!mGUI.inputVal.equals("") && !mGUI.inputVal.equals("\n")) {
					String content = mGUI.inputVal;
					// System.out.println(content);
					for (int j = 0; j < content.length(); j++) {
						if (content.charAt(j) != '0'
								&& content.charAt(j) != '1'
								&& content.charAt(j) != '2'
								&& content.charAt(j) != '3'
								&& content.charAt(j) != '4'
								&& content.charAt(j) != '5'
								&& content.charAt(j) != '6'
								&& content.charAt(j) != '7'
								&& content.charAt(j) != '8'
								&& content.charAt(j) != '9') {
							flag = 0;
							break;
						}

					}
					if (flag == 1) {

						val = Integer.parseInt(content);
						if (val < 0 || val > 65535)
							flag = 0;

					} else if (flag == 0) {
						mGUI.console_textArea
								.append("KEYBOARD NEED A INTEGER(0 - 65535)\n");
					}
					if (flag == 1) {
						mGUI.inputVal = "";
						setValuetoReById(R, val);
						// MyMemory.insertByAddr(Integer.toBinaryString(bootstart++),temp);
						break;
					}
				}
			}
		}

		else if (devid == 2)// 文件输入以后写
		{
			int bootstart = 500;

			// File file = new File("paragraph.txt");
			File file = new File("paragraph.txt");
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String sentence;
				while ((sentence = br.readLine()) != null) {
					// System.out.println(sentence);
					sentence = sentence + "\n";
					for (int j = 0; j < sentence.length(); j++) {
						memory.memory_arr[bootstart] = (int) (sentence
								.charAt(j));
						bootstart++;
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		else if (devid == 3)// console keyboard
		{
			characterstore = 400;

			mGUI.console_textArea.append("PLZ INPUT VALUE\n");
			while (true) {

				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// System.out.println(MyINPUT.getInputval().equals(""));

				if (!mGUI.inputVal.equals("") && !mGUI.inputVal.equals("\n")) {
					String content = mGUI.inputVal;
					// System.out.println(content);

					for (int j = 0; j < content.length(); j++) {
						int contentCode = (int) (content.charAt(j));
						setValuetoReById(R, contentCode);
						cache.setCacheLineValue(characterstore,
								contentCode);
						// memory.memory_arr[characterstore] =
						// Integer.parseInt(content, 2);
						characterstore++;

						// System.out.println(str1);
					}
					mGUI.inputVal = "";
					break;
				}

			}
		}
	}

	public void OUT(int R, int devid) {
		if (devid == 1) {
			int val = getValueFromReById(R);
			String binaryValue = Integer.toBinaryString(val);
			mGUI.console_textArea.append("OUTPUT:\n" + binaryValue + " (" + val
					+ ')' + '\n');
		}
		else if(devid == 2)//(USE ASC22 TRANSFER)print character to console as output(only character)
		{
			int asc2word = getValueFromReById(R);
			mGUI.console_textArea.append(Character.toString((char)asc2word));
		}
	}
	
	//FADD
	public void FADD(int R, int IX,int I, int addr_m){
		int ea = getEffectiveAddr(IX, I, addr_m);
		
		int dataE, dataM;
		dataE=cache.getCacheLineValue(ea);
		dataM=getValueFromFRById(R);
		//System.out.println(dataE+"   "+dataM);
		
		String data1, data2;
		data1=buling(Integer.toBinaryString(dataE));
		data2=buling(Integer.toBinaryString(dataM));
		//System.out.println(data1);
		//System.out.println(data2);

		//StringBuffer data2 = new StringBuffer(getValueFromFRById(r));
		int flag1 = 1, flag2 = 1, sign1 = 1,sign2 = 1;
		if(data1.charAt(0) == '1')
		{
			flag1 = -1;
		}
		if(data2.charAt(0) == '1')
		{
			flag2 = -1;
		}
		if(data1.charAt(1) == '1')
			sign1 = -1;
		if(data2.charAt(1) == '1')
			sign2 = -1;
		String exp1, exp2, mant1, mant2;
		int expnum1,expnum2, mantnum1tmp, mantnum2tmp;
		double mantnum1, mantnum2;
		
		exp1 = data1.substring(2, 8);
		exp2 = data2.substring(2, 8);
		mant1 = data1.substring(8);
		mant2 = data2.substring(8);
		expnum1 = sign1 * Integer.parseInt(exp1,2);
		expnum2 = sign2 * Integer.parseInt(exp2,2);
		mantnum1tmp = Integer.parseInt(mant1);
		mantnum2tmp = Integer.parseInt(mant2);
//		mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1 - 7);
//		mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2 - 7);
		
		mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 
		mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2) / Math.pow(10, 7); 


		String addbin1, addbin2;
		
		DecimalFormat decimalFormat = new DecimalFormat("#.###");//鏍煎紡鍖栬缃� 
		
       // addbin1 = Double.toString(mantnum1);
		//addbin2 = Double.toString(mantnum2);
        addbin1 = decimalFormat.format(mantnum1);
		addbin2 = decimalFormat.format(mantnum2);

//		
		double adddec1,adddec2;
		String resultdec, resultbin;
		//System.out.println(addbin2);
		adddec1 = Double.parseDouble(binaryToDecimal(addbin1));
		adddec2 = Double.parseDouble(binaryToDecimal(addbin2));
		resultdec = Double.toString(adddec1 + adddec2);
		resultbin = decimalTobinary(resultdec);
		
		//System.out.println(addbin2 + "+" + addbin1 + "=" + resultbin);
		//System.out.println(adddec1 + "+" + adddec2 + "=" + resultdec);

		int expr1 = 0,expr2 = 0;
		String exp;
		String dishu = "";
//		System.out.println(adddec1 + "+" + adddec2 + "=" + resultdec);
//		System.out.println(addbin1 + "+" + addbin2 + "=" + resultbin);

		for(int j = 0; j < resultbin.length(); j++)
		{
			if(resultbin.charAt(j) != '-' && resultbin.charAt(j) != '.')
			{
				dishu = dishu + resultbin.charAt(j);
			}
		}
		for(int j = 0; j < resultbin.length(); j++)
		{
			if(resultbin.charAt(j) == '.')
			{
				expr1 = j;
				break;
				
			}
		}
		if(resultbin.charAt(0) == '0' || resultbin.charAt(0) == '-'&& resultbin.charAt(1) == '0')
		{
			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) == '1')
				{
					expr2 = j;
					break;
					
				}
			}
		}
		else
		{
			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) == '1')
				{
					expr2 = j + 1;
					break;
					
				}
			}
		}
		if(expr1 - expr2 >= 0)
		{
			exp = Integer.toBinaryString((expr1 - expr2));
			exp = '0' + exp;
		}
		else
		{
			String temp = Integer.toBinaryString(expr1 - expr2 - 1).substring(16);//transfer to binary string
			String result = "1";
			for (int j = 1; j < 7; j++) //make it a 16 bit long string
			{
				if(temp.charAt(j) == '0')//because negative number use complemental code so we need to do something here
					result = result + '1';
				else if(temp.charAt(j) == '1')
					result = result + '0';

			}
			exp = result;
		}
		if(exp.length() > 7)
			mGUI.console_textArea.append("FADD OVERFLOW\n");
		if(exp.charAt(0) == '0'){
			for(int j = exp.length(); j < 7; j++)
			{
				exp = '0' + exp;
			}
		}
		else if(exp.charAt(0)=='1')
		{
			String temp = exp.substring(1, exp.length());
			for(int j = exp.length();j<6; j++)
			{
				temp = '0' + temp;
			}
			exp = '1' + temp;
		}

		for(int j = dishu.length(); j < 8; j++)
		{
			dishu = dishu + '0';
		}
		
		String result;
		String signbit = "0";
		if(resultbin.charAt(0) == '-')
			signbit = "1";
		result = signbit + exp + dishu;
		if(result.length() > 16)
			result = result.substring(0, 16);
		
		int result1=Integer.parseInt(result,2);
		setValueToFRById(R, result1);
		//System.out.println(result1);
	
	}
	
	
	//FSUB
	public void FSUB(int R, int IX,int I, int addr_m){
		int ea = getEffectiveAddr(IX, I, addr_m);
		
		int dataE, dataM;
		dataE=cache.getCacheLineValue(ea);
		dataM=getValueFromFRById(R);
		//System.out.println(dataE+"   "+dataM);
		String data1, data2;
		data1=buling(Integer.toBinaryString(dataE));
		data2=buling(Integer.toBinaryString(dataM));
		
		
		//System.out.println(data2);

		//StringBuffer data2 = new StringBuffer(getValueFromFRById(r));
		int flag1 = 1, flag2 = 1, sign1 = 1,sign2 = 1;
		if(data1.charAt(0) == '1')
		{
			flag1 = -1;
		}
		if(data2.charAt(0) == '1')
		{
			flag2 = -1;
		}
		if(data1.charAt(1) == '1')
			sign1 = -1;
		if(data2.charAt(1) == '1')
			sign2 = -1;
		String exp1, exp2, mant1, mant2;
		int expnum1,expnum2, mantnum1tmp, mantnum2tmp;
		double mantnum1, mantnum2;
		
		exp1 = data1.substring(2, 8);
		exp2 = data2.substring(2, 8);
		mant1 = data1.substring(8);
		mant2 = data2.substring(8);
		expnum1 = sign1 * Integer.parseInt(exp1,2);
		expnum2 = sign2 * Integer.parseInt(exp2,2);
		mantnum1tmp = Integer.parseInt(mant1);
		mantnum2tmp = Integer.parseInt(mant2);
//		mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1 - 7);
//		mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2 - 7);
		
		mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 
		mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2) / Math.pow(10, 7); 


		String addbin1, addbin2;
		
		DecimalFormat decimalFormat = new DecimalFormat("#.#########");//鏍煎紡鍖栬缃� 
		
       // addbin1 = Double.toString(mantnum1);
		//addbin2 = Double.toString(mantnum2);
        addbin1 = decimalFormat.format(mantnum1);
		addbin2 = decimalFormat.format(mantnum2);

//		if(addbin1.length() > 9)
//		{
//			addbin1 = addbin1.substring(0, 10);
//		}
//		if (addbin2.length() > 9)
//		{
//			addbin2 = addbin2.substring(0, 10);
//		}
		
		double adddec1,adddec2;
		String resultdec, resultbin;
		//System.out.println(addbin2);
		adddec1 = Double.parseDouble(binaryToDecimal(addbin1));
		adddec2 = Double.parseDouble(binaryToDecimal(addbin2));
		resultdec = Double.toString(adddec2 - adddec1);
		resultbin = decimalTobinary(resultdec);
		
		//System.out.println(addbin2 + "-" + addbin1 + "=" + resultbin);
		//System.out.println(adddec2 + "-" + adddec1 + "=" + resultdec);

		int expr1 = 0,expr2 = 0;
		String exp;
		String dishu = "";
//		System.out.println(adddec1 + "+" + adddec2 + "=" + resultdec);
//		System.out.println(addbin1 + "+" + addbin2 + "=" + resultbin);

		for(int j = 0; j < resultbin.length(); j++)
		{
			if(resultbin.charAt(j) != '-' && resultbin.charAt(j) != '.')
			{
				dishu = dishu + resultbin.charAt(j);
			}
		}
		for(int j = 0; j < resultbin.length(); j++)
		{
			if(resultbin.charAt(j) == '.')
			{
				expr1 = j;
				break;
				
			}
		}
		if(resultbin.charAt(0) == '0' || resultbin.charAt(0) == '-'&& resultbin.charAt(1) == '0')
		{
			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) == '1')
				{
					expr2 = j;
					break;
					
				}
			}
		}
		else
		{
			for(int j = 0; j < resultbin.length(); j++)
			{
				if(resultbin.charAt(j) == '1')
				{
					expr2 = j + 1;
					break;
					
				}
			}
		}
		if(expr1 - expr2 >= 0)
		{
			exp = Integer.toBinaryString((expr1 - expr2));
			exp = '0' + exp;
		
		}
		else
		{
			String temp = Integer.toBinaryString(expr1 - expr2 - 1).substring(16);//transfer to binary string
			String result = "1";
			for (int j = 1; j < 7; j++) //make it a 16 bit long string
			{
				if(temp.charAt(j) == '0')//because negative number use complemental code so we need to do something here
					result = result + '1';
				else if(temp.charAt(j) == '1')
					result = result + '0';

			}
			exp = result;
		}
		if(exp.length() > 7)
			mGUI.console_textArea.append("FSUB OVERFLOW\n");
		if(exp.charAt(0) == '0'){
			for(int j = exp.length(); j < 7; j++)
			{
				exp = '0' + exp;
			}
		}
		else if(exp.charAt(0)=='1')
		{
			String temp = exp.substring(1, exp.length());
			for(int j = exp.length();j<6; j++)
			{
				temp = '0' + temp;
			}
			exp = '1' + temp;
		}

		for(int j = dishu.length(); j < 8; j++)
		{
			dishu = dishu + '0';
		}
		
		String result;
		String signbit = "0";
		if(resultbin.charAt(0) == '-')
			signbit = "1";
		result = signbit + exp + dishu;
		if(result.length() > 16)
			result = result.substring(0, 16);
		int result1=Integer.parseInt(result,2);
		setValueToFRById(R, result1);
		
		System.out.println(result);
		
	}
	
	//VADD
	public void VADD(int R, int IX,int I, int addr_m){
		int ea1=getEffectiveAddr(IX, I, addr_m);
		int ea2=ea1+1;
		
		int len=getValueFromReById(R);
		int location1,location2;
		int val1,val2;
		
		location1=cache.getCacheLineValue(ea1);
		location2=cache.getCacheLineValue(ea2);
		
		for(int j=0;j<len;j++){
			val1=cache.getCacheLineValue(location1+j);
			val2=cache.getCacheLineValue(location2+j);
			
			int temp;
			temp=val1+val2;
			
			cache.setCacheLineValue(location1+j,temp);
		}
		
	}
	
	//VSUB
	public void VSUB(int R, int IX,int I, int addr_m){
		int ea1=getEffectiveAddr(IX, I, addr_m);
		int ea2=ea1+1;
		
		int len=getValueFromReById(R);
		int location1,location2;
		int val1,val2;
		
		location1=cache.getCacheLineValue(ea1);
		location2=cache.getCacheLineValue(ea2);
		
		for(int j=0;j<len;j++){
			val1=cache.getCacheLineValue(location1+j);
			val2=cache.getCacheLineValue(location2+j);
			
			int temp;
			temp=val1-val2;
			
			cache.setCacheLineValue(location1+j,temp);
		}
	}
	
	//CNVRT
	public void CNVRT(int R,int IX, int I, int addr_m){
		
		int ea=getEffectiveAddr(IX, I, addr_m);
		int data=cache.getCacheLineValue(ea);
		String data1=buling(Integer.toBinaryString(data));
		//System.out.println(data1);
		int f = getValueFromReById(R);
		if(f==0)
		{
			int flag1 = 1,sign1 = 1;
			if(data1.charAt(0) == '1')
			{
				flag1 = -1;
			}
			
			if(data1.charAt(1) == '1')
				sign1 = -1;
			
			String exp1, mant1;
			int expnum1, mantnum1tmp;
			double mantnum1;
			
			exp1 = data1.substring(2, 8);
			mant1 = data1.substring(8);
			expnum1 = sign1 * Integer.parseInt(exp1,2);
			mantnum1tmp = Integer.parseInt(mant1);
//			mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1 - 7);
//			mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2 - 7);
			
			mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 


			String addbin1;
			
			DecimalFormat decimalFormat = new DecimalFormat("#.###");//鏍煎紡鍖栬缃� 
			
	       // addbin1 = Double.toString(mantnum1);
			//addbin2 = Double.toString(mantnum2);
	        addbin1 = decimalFormat.format(mantnum1);

//			
			double adddec1,adddec2;
			String resultdec, resultbin;
			//System.out.println(addbin2);
			adddec1 = Double.parseDouble(binaryToDecimal(addbin1));
			int good = (int)adddec1;
			String goodstr;
			if(good >= 0)
			{
				goodstr = buling(Integer.toBinaryString(good));
				
			}
			else
			{
				String temp = Integer.toBinaryString(good - 1).substring(16);//transfer to binary string
				goodstr = "1";
				for (int j = 1; j < 16; j++) //make it a 16 bit long string
				{
					if(temp.charAt(j) == '0')//because negative number use complemental code so we need to do something here
						goodstr = goodstr + '1';
					else if(temp.charAt(j) == '1')
						goodstr = goodstr + '0';

				}
				
			}
			System.out.println(  "FLOAT--->FIXED" );
			
			int goodstr1=Integer.parseInt(goodstr,2);
			setValuetoReById(R, goodstr1);
			//System.out.println(goodstr);
			
		}
		else if(f==1)
		{
			String result = "";
			int sign1 = 1;
			int exp = 0, start = 16;
			String mant = "";
			String expstr = "";
			if(data1.charAt(0) == '0')
				sign1 = 1;
			else if(data1.charAt(0) == '1')
				sign1 = -1;
			if(sign1 == 1)
			{
				for(int j = 0; j < 16; j++)
				{
					if(data1.charAt(j) != '0')
					{
						exp = 15 - j;
						start = j;
						break;
					}
					
				}
				expstr = Integer.toBinaryString(exp);
				for (int j = expstr.length(); j < 7; j++)
				{
					expstr = '0' + expstr;
				}
				mant = data1.substring(start);
				if(mant.length() > 8)
					mant = mant.substring(0, 8);
				for(int j = mant.length(); j < 8; j++)
				{
					mant = mant + '0';
				}
				result = '0' + expstr + mant;
				
				
			}
			else if (sign1 == -1)
			{
				for(int j = 1; j < 16; j++)
				{
					if(data1.charAt(j) != '0')
					{
						exp = 15 - j;
						start = j;
						break;
					}
					
				}
				expstr = Integer.toBinaryString(exp);
				for (int j = expstr.length(); j < 7; j++)
				{
					expstr = '0' + expstr;
				}
				mant = data1.substring(start);
				if(mant.length() > 8)
					mant = mant.substring(0, 8);
				for(int j = mant.length(); j < 8; j++)
				{
					mant = mant + '0';
				}
				result = '1' + expstr + mant;
			}
			System.out.println(  "FIXED--->FLOAT" );
			int result1=Integer.parseInt(result,2);
			setValueToFRById(0, result1);	
			System.out.println(result);
		}
	}
	
	//LDFR
	public void LDFR(int R,int IX, int I, int addr_m){
		int ea=getEffectiveAddr(IX, I, addr_m);
		int data=0;
		data=cache.getCacheLineValue(ea);
		setValueToFRById(R, data);
	}
	
	//STFR
	public void STFR(int R,int IX, int I, int addr_m){
		int ea=getEffectiveAddr(IX, I, addr_m);
		int data=0;
		data = getValueFromFRById(R);
		cache.setCacheLineValue(ea, data);
	}
	
	
	public static String binaryToDecimal(String add1)
	{
		String big1 = "",small1 = "";
		int big = 0;
		double small = 0.0;
		int flag = 1;
		if(add1.charAt(0) == '-')
		{
			flag = -1;
		}
		for(int i = 0; i < add1.length(); i++)
		{
			if(add1.charAt(i) == '.')
			{
				if(flag == 1){
					big1 = add1.substring(0, i);
					small1 = add1.substring(i+1);
				}
				else if (flag == -1)
				{
					big1 = add1.substring(1,i);
					small1 = add1.substring(i+1);
				}
				break;
			}
		}
		if (!add1.contains("."))
		{
			if(flag == 1)
				big1 = add1.substring(0);
			else 
				big1 = add1.substring(1);
			
		}
			
		big = Integer.parseInt(big1, 2);
		for(int i = 0; i < small1.length();i++)
		{
			int temp = Integer.parseInt(Character.toString(small1.charAt(i)));
			small = small + temp * Math.pow(2, -i-1);
		}
		double result = 0;
		if(flag == 1)
			result = big + small;
		else 
			result = -(big + small);
		
		return Double.toString(result);
		
	}
	
	public static String decimalTobinary(String add1)
	{
		String big1 = "", small1 = "";
		int big = 0;
		double small = 0;
		int flag = 1;
		if(add1.charAt(0) == '-')
		{
			flag = -1;
		}
		
		for(int i = 0; i < add1.length(); i++)
		{
			
			if(add1.charAt(i) == '.')
			{
				if(flag == 1){
					big1 = add1.substring(0, i);
					small1 = add1.substring(i+1);
				}
				else if (flag == -1)
				{
					big1 = add1.substring(1,i);
					small1 = add1.substring(i+1);
				}
				break;
			}
		}
		big = Integer.parseInt(big1);
		String goodbig = Integer.toBinaryString(big);
		small = Double.parseDouble("0."+small1);
		//System.out.println(small+"\n");
		String good = "";
		for(int i = 0; i < 8; i++)
		{
			if(small == 0.0)
				break;
			else
			{
				double temp = small * 2;

				if(temp >= 1)
				{
					good = good + '1';
					small = small * 2 - 1;
					
				}
				else
				{
					good = good + '0';
					small = small * 2;
				
				}
				
			}
		}
		if(flag == 1)
			return goodbig+'.'+good;
		else
			return '-' + goodbig+'.'+good;
		
	}
	
	public static void setValueToFRById(int id, int value)
	{
		if(id==0)
		{
			register_FR0.setValue(value);
		}
		else if(id==1)
		{
			register_FR1.setValue(value);
		}
		else
		{
			mGUI.console_textArea.append("WRONG FR ID\n");
		}
	}
	public static int getValueFromFRById(int id)
	{
		int temp=0;
		if(id==0)
		{
			temp = register_FR0.getValue();
		}
		else if(id==1)
		{
			temp = register_FR1.getValue();
		}
		else
		{
			mGUI.console_textArea.append("WRONG FR ID\n");
		}
		return temp;
	}
	public static String buling(String str)//make the binary number  16 bit long
	{
		String temp = str;
		for(int i = temp.length();i < 16;i++)
		{
			temp = "0" + temp;
		}
		return temp;
	}
}
