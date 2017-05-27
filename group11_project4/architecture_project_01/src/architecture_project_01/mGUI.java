package architecture_project_01;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.omg.CORBA.PRIVATE_MEMBER;

public class mGUI extends JFrame {

	public static String inputVal ="";
	
	ControlUnit	cu	= new ControlUnit();
	// read memory from txt file
	memory_io	mio	= new memory_io();
	architecture_project_01.cache cache = new cache();

	public mGUI() {
		// TODO Auto-generated constructor stub

		// set the GUI paramters
		this.setTitle("Architcture Project");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1230, 500);

		java.awt.Container con = this.getContentPane();
		con.setLayout(null);
		JPanel panel = addComponents(con);
		setListeners();

		con.add(panel);
	}
	

	// check if address is a binary array and 12 bits
	private boolean addr_check(String addr, String name) {
		for (int i = 0; i < addr.length(); i++) {
			if (addr.charAt(i) != '0' && addr.charAt(i) != '1') {
				console_textArea.append(name + " must be binary\n");
				return false;
			}
		}
		if (addr.length() != 12) {
			console_textArea.append(name + " must be 12 bits.\n");
			return false;
		}
		return true;
	}

	// check if content in the address is a binary array and 16 bits
	private Boolean cont_check(String cont, String name) {
		for (int i = 0; i < cont.length(); i++) {
			if (cont.charAt(i) != '0' && cont.charAt(i) != '1') {
				console_textArea.append(name + " must be binary\n");
				return false;
			}
		}
		if (cont.length() != 16) {
			console_textArea.append(name + " must be binary\n");
			return false;
		}
		return true;
	}

	// convert decimal number to binary(need to add 0 before binary number )
	private String toBinary_reg(int decNum, int bits) {
		String pre_0 = "";
		String b_i = Integer.toBinaryString(decNum);
		for (int j = 0; j < bits - b_i.length(); j++)
			pre_0 += '0';
		return (pre_0 + b_i);
	}

	// buttons' listeners
	private void setListeners() {
		// TODO Auto-generated method stub

		// store memory button
		store_memory_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean isValid = true; // flag to make sure the address and
										// content is valid
				String tem_addr = addr_text.getText();
				String tem_cont = addr_cont_text.getText();

				isValid = addr_check(tem_addr, "Address");
				isValid = cont_check(tem_cont, "Content");

				if (isValid) {
					memory.inster(Integer.parseInt(tem_addr, 2),
							Integer.parseInt(tem_cont, 2));
					console_textArea.append("Store success.\n");
				}

			}
		});

		// show memory button
		show_memory_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean isValid = true; // flag to make sure the address and
										// content is valid
				String tem_addr = addr_text.getText();

				isValid = addr_check(tem_addr, "Address");

				if (isValid) {
					int cont = memory.getValueAt(Integer.parseInt(tem_addr, 2));
					String tem_zero = "";
					for (int j = 0; j < 16 - Integer.toBinaryString(cont)
							.length(); j++)
						tem_zero += '0';
					addr_cont_text.setText(tem_zero
							+ Integer.toBinaryString(cont));
					console_textArea.append("Fetched memory.\n");
				}
			}
		});

		storeReg_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean isValid = true; // flag to make sure the registers'
										// contents are valid

				String tem_PC = PC_text.getText();
				String tem_R00 = R00_text.getText();
				String tem_R01 = R01_text.getText();
				String tem_R02 = R02_text.getText();
				String tem_R03 = R03_text.getText();
				String tem_IX01 = IX01_text.getText();
				String tem_IX02 = IX02_text.getText();
				String tem_IX03 = IX03_text.getText();

				isValid = addr_check(tem_PC, "PC");
				isValid = cont_check(tem_R00, "R00");
				isValid = cont_check(tem_R01, "R01");
				isValid = cont_check(tem_R02, "R02");
				isValid = cont_check(tem_R03, "R03");
				isValid = cont_check(tem_IX01, "IX01");
				isValid = cont_check(tem_IX02, "IX02");
				isValid = cont_check(tem_IX03, "IX03");

				if (isValid) {
					register_PC.value = Integer.parseInt(tem_PC, 2);
					register_GPR.R0_value = Integer.parseInt(tem_R00, 2);
					register_GPR.R1_value = Integer.parseInt(tem_R01, 2);
					register_GPR.R2_value = Integer.parseInt(tem_R02, 2);
					register_GPR.R3_value = Integer.parseInt(tem_R03, 2);
					register_IX.value_01 = Integer.parseInt(tem_IX01, 2);
					register_IX.value_02 = Integer.parseInt(tem_IX02, 2);
					register_IX.value_03 = Integer.parseInt(tem_IX03, 2);
					console_textArea.append("Store success");
				}

			}
		});

		showReg_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				refresh();
			}
		});

		single_step_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						
						boolean isValid = true;

						String tem_PC = PC_text.getText();
						String tem_R00 = R00_text.getText();
						String tem_R01 = R01_text.getText();
						String tem_R02 = R02_text.getText();
						String tem_R03 = R03_text.getText();
						String tem_IX01 = IX01_text.getText();
						String tem_IX02 = IX02_text.getText();
						String tem_IX03 = IX03_text.getText();

						isValid = addr_check(tem_PC, "PC");
						isValid = cont_check(tem_R00, "R00");
						isValid = cont_check(tem_R01, "R01");
						isValid = cont_check(tem_R02, "R02");
						isValid = cont_check(tem_R03, "R03");
						isValid = cont_check(tem_IX01, "IX01");
						isValid = cont_check(tem_IX02, "IX02");
						isValid = cont_check(tem_IX03, "IX03");

						if (isValid) {
							cu.singleCircle();
							refresh();
						}
					}
				}.start();
				
				
			}
		});

		reset_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// initialize all of values on the screen and values in
				// background
				String zero_12 = "000000000000";
				String zero_16 = "0000000000000000";
				PC_text.setText(zero_12);
				R00_text.setText(zero_16);
				R01_text.setText(zero_16);
				R02_text.setText(zero_16);
				R03_text.setText(zero_16);
				IX01_text.setText(zero_16);
				IX02_text.setText(zero_16);
				IX03_text.setText(zero_16);
				MAR_text.setText(zero_16);
				MBR_text.setText(zero_16);
				IR_text.setText(zero_16);
				register_PC.value = 0;
				register_GPR.R0_value = 0;
				register_GPR.R1_value = 0;
				register_GPR.R2_value = 0;
				register_GPR.R3_value = 0;
				register_IX.value_01 = 0;
				register_IX.value_02 = 0;
				register_IX.value_03 = 0;
				register_IR.value = 0;
				register_MAR.value = 0;
				register_MBR.value = 0;
				register_FR0.value=0;
				register_FR1.value=0;

				for (int i = 0; i < 4096; i++) {
					memory.memory_arr[i] = 0;
				}

				console_textArea.append("Reset all of registers and memory.\n");
			}
		});

		// a test program design by myself
		project1_test_BTN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				// input the initial values of registers
				register_PC.value = 100;
				register_GPR.R0_value = 200;
				register_GPR.R1_value = 300;
				register_GPR.R2_value = 400;
				register_GPR.R3_value = 500;
				register_IX.value_01 = 1000;
				register_IX.value_02 = 1100;
				register_IX.value_03 = 1200;
				register_IR.value = 0;
				register_MAR.value = 0;
				register_MBR.value = 0;

				PC_text.setText(toBinary_reg(register_PC.value, 12));
				R00_text.setText(toBinary_reg(register_GPR.R0_value, 16));
				R01_text.setText(toBinary_reg(register_GPR.R1_value, 16));
				R02_text.setText(toBinary_reg(register_GPR.R2_value, 16));
				R03_text.setText(toBinary_reg(register_GPR.R3_value, 16));
				IX01_text.setText(toBinary_reg(register_IX.value_01, 16));
				IX02_text.setText(toBinary_reg(register_IX.value_02, 16));
				IX03_text.setText(toBinary_reg(register_IX.value_03, 16));
				MAR_text.setText(toBinary_reg(register_MAR.value, 16));
				MBR_text.setText(toBinary_reg(register_MBR.value, 16));
				IR_text.setText(toBinary_reg(register_IR.value, 16));

				// input the values of memory
				memory.memory_arr[100] = 1029; // LDR OPCODE:0000010000000101
				memory.memory_arr[101] = 2310; // STR OPCODE:0000100100000110
				memory.memory_arr[102] = 3591; // LDA OPCODE:0000111000000111
				memory.memory_arr[103] = 42081; // LDX OPCODE:1010010001100001
				memory.memory_arr[104] = 43087;
				memory.memory_arr[105] = 4639;
				memory.memory_arr[106] = 3615;
				memory.memory_arr[107] = 4639;
				memory.memory_arr[108] = 5663;
				memory.memory_arr[108] = 38753;
				memory.memory_arr[5] = 1;
				memory.memory_arr[6] = 2;
				memory.memory_arr[1000] = Integer.parseInt("00000100000110010",
						2);
				memory.memory_arr[1001] = 1100;
				memory.memory_arr[1100] = 1;
				memory.memory_arr[31] = 1;

			}
		});

		p1_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				programLoader p1 = new programLoader();
				p1.load(1);
				refresh();
			}
		});
		
		
		
		
		//phase4
		p2_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				programLoader p2 = new programLoader();
				p2.load(2);
				refresh();
			}
		});
		/*
		set_fNum1_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				float fNum1 = Float.valueOf(fNum1_text.getText());
				floatToMemFormat f = new floatToMemFormat();
				memory.memory_arr[1] = f.toMemFormat(fNum1);
				console_textArea.append("Floating number 1 has be saved.\n");
			}
		});
		
		set_fNum2_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				float fNum2 = Float.valueOf(fNum2_text.getText());
				floatToMemFormat f = new floatToMemFormat();
				memory.memory_arr[2] = f.toMemFormat(fNum2);
				console_textArea.append("Floating number 2 has be saved.\n");

			}
		});
		*/
		float_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				programLoader f1=new programLoader();
				f1.load(3);
				
				refresh();
				
			}
			
		});
		
		convert_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				programLoader f1=new programLoader();
				f1.load(4);
				
				refresh();
				
			}
			
		});
		
		setVectors_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int v1_n1 = Integer.parseInt(v1_n1_text.getText());
				int v1_n2 = Integer.parseInt(v1_n2_text.getText());
				int v1_n3 = Integer.parseInt(v1_n3_text.getText());
				int v2_n1 = Integer.parseInt(v2_n1_text.getText());
				int v2_n2 = Integer.parseInt(v2_n2_text.getText());
				int v2_n3 = Integer.parseInt(v2_n3_text.getText());
				
				memory.memory_arr[10]=v1_n1;
				memory.memory_arr[11]=v1_n2;
				memory.memory_arr[12]=v1_n3;
				
				memory.memory_arr[20]=v2_n1;
				memory.memory_arr[21]=v2_n2;
				memory.memory_arr[22]=v2_n3;
				
				console_textArea.append("Vectors has be saved.\n");
			}
		});
		
		
		vecadd_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				programLoader f1=new programLoader();
				f1.load(5);
				
				refresh();
				
			}
			
		});
		
		vecsub_btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				programLoader f1=new programLoader();
				f1.load(6);
				
				refresh();
				
			}
			
		});
		
		run_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Thread(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
						
						boolean isVaild = true;
						while(isVaild){
							isVaild = cu.singleCircle();
							refresh();
							
						}
						System.out .println("Program complete");
						
						try {
							BufferedWriter out = new BufferedWriter(new FileWriter("TestMemory.txt"));
							for(int i = 0; i < 4096; i++){
								out.newLine();
								//String b_i = Integer.toBinaryString(i);
								//for(int j = 0; j < 12 - b_i.length(); j++ )
									//out.write('0');	
								out.write(Integer.toString(memory.memory_arr[i]));
							}
							out.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}.start();
			}
		});
		
		input_text.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyReleased(e);
				int k = e.getKeyCode();
				if(k == KeyEvent.VK_ENTER)
				{
					if(!input_text.getText().equals("")&&!input_text.getText().equals("\n"))
					{
						inputVal = input_text.getText();
						//System.out.println(input_text.getText().length());
					}
					//System.out.println(inputVal);
					input_text.setText("");
				}
			}
			
		});

	}
	

	public void refresh() {
		PC_text.setText(toBinary_reg(register_PC.value, 12));
		R00_text.setText(toBinary_reg(register_GPR.R0_value, 16));
		R01_text.setText(toBinary_reg(register_GPR.R1_value, 16));
		R02_text.setText(toBinary_reg(register_GPR.R2_value, 16));
		R03_text.setText(toBinary_reg(register_GPR.R3_value, 16));
		IX01_text.setText(toBinary_reg(register_IX.value_01, 16));
		IX02_text.setText(toBinary_reg(register_IX.value_02, 16));
		IX03_text.setText(toBinary_reg(register_IX.value_03, 16));
		MAR_text.setText(toBinary_reg(register_MAR.value, 16));
		MBR_text.setText(toBinary_reg(register_MBR.value, 16));
		IR_text.setText(toBinary_reg(register_IR.value, 16));
		v1_n1_text.setText(""+memory.memory_arr[10]);
		v1_n2_text.setText(""+memory.memory_arr[11]);
		v1_n3_text.setText(""+memory.memory_arr[12]);
		
		//SFR0_Label.setText(Integer.toBinaryString(register_FR0.getValue()));
		//SFR1_Label.setText(Integer.toBinaryString(register_FR1.getValue()));
		showFloatReg(buling(Integer.toBinaryString(register_FR0.getValue())),buling(Integer.toBinaryString(register_FR1.getValue())));
	}

	public JPanel addComponents(java.awt.Container con) {

		// the layout of the GUI
		JPanel panel = new JPanel();

		PC_Label.setBounds(20, 40, 60, 20);
		getContentPane().add(PC_Label);

		MAR_Label.setBounds(20, 70, 60, 20);
		getContentPane().add(MAR_Label);

		MBR_Label.setBounds(20, 100, 60, 20);
		getContentPane().add(MBR_Label);

		IR_Label.setBounds(20, 130, 60, 20);
		getContentPane().add(IR_Label);

		R00_Label.setBounds(20, 160, 60, 20);
		getContentPane().add(R00_Label);

		R01_Label.setBounds(20, 190, 60, 20);
		getContentPane().add(R01_Label);

		R02_Label.setBounds(20, 220, 60, 20);
		getContentPane().add(R02_Label);

		R03_Label.setBounds(20, 250, 60, 20);
		getContentPane().add(R03_Label);

		IX01_Label.setBounds(20, 280, 60, 20);
		getContentPane().add(IX01_Label);

		IX02_Label.setBounds(20, 310, 60, 20);
		getContentPane().add(IX02_Label);

		IX03_Label.setBounds(20, 340, 60, 20);
		getContentPane().add(IX03_Label);

		PC_Label.setBounds(20, 40, 60, 20);
		getContentPane().add(PC_Label);

		PC_text.setBounds(60, 40, 180, 20);
		getContentPane().add(PC_text);

		MAR_text.setBounds(60, 70, 180, 20);
		MAR_text.setEditable(false);
		getContentPane().add(MAR_text);

		MBR_text.setBounds(60, 100, 180, 20);
		MBR_text.setEditable(false);
		getContentPane().add(MBR_text);

		IR_text.setBounds(60, 130, 180, 20);
		IR_text.setEditable(false);
		getContentPane().add(IR_text);

		R00_text.setBounds(60, 160, 180, 20);
		getContentPane().add(R00_text);

		R01_text.setBounds(60, 190, 180, 20);
		getContentPane().add(R01_text);

		R02_text.setBounds(60, 220, 180, 20);
		getContentPane().add(R02_text);

		R03_text.setBounds(60, 250, 180, 20);
		getContentPane().add(R03_text);

		IX01_text.setBounds(60, 280, 180, 20);
		getContentPane().add(IX01_text);

		IX02_text.setBounds(60, 310, 180, 20);
		getContentPane().add(IX02_text);

		IX03_text.setBounds(60, 340, 180, 20);
		getContentPane().add(IX03_text);

		storeReg_button.setBounds(30, 370, 90, 20);
		getContentPane().add(storeReg_button);

		showReg_button.setBounds(130, 370, 90, 20);
		getContentPane().add(showReg_button);
		
		CCR_Label.setBounds(20, 420, 30, 20);
		getContentPane().add(CCR_Label);
		
		CCR0_Label.setBounds(60,420,30,20);
		getContentPane().add(CCR0_Label);
		
		CCR1_Label.setBounds(100,420,30,20);
		getContentPane().add(CCR1_Label);
		
		CCR2_Label.setBounds(140,420,30,20);
		getContentPane().add(CCR2_Label);
		
		CCR3_Label.setBounds(180,420,30,20);
		getContentPane().add(CCR3_Label);

		addr_Label.setBounds(300, 40, 60, 20);
		getContentPane().add(addr_Label);

		addr_cont_Label.setBounds(300, 70, 60, 20);
		getContentPane().add(addr_cont_Label);

		addr_text.setBounds(370, 40, 180, 20);
		getContentPane().add(addr_text);

		addr_cont_text.setBounds(370, 70, 180, 20);
		getContentPane().add(addr_cont_text);

		store_memory_Button.setBounds(310, 100, 110, 20);
		getContentPane().add(store_memory_Button);

		show_memory_Button.setBounds(430, 100, 110, 20);
		getContentPane().add(show_memory_Button);

		input_Label.setBounds(300, 140, 60, 20);
		getContentPane().add(input_Label);

		input_text.setBounds(300, 160, 250, 20);
		getContentPane().add(input_text);

		//input_enter_Button.setBounds(480, 190, 60, 20);
		//this.add(input_enter_Button);

		// project1_test_BTN.setBounds(300, 190, 250, 20);
		// this.add(project1_test_BTN);

		single_step_Button.setBounds(300, 221, 250, 20);
		getContentPane().add(single_step_Button);

		reset_Button.setBounds(300, 251, 250, 20);
		getContentPane().add(reset_Button);

		p1_Button.setBounds(300, 311, 250, 20);
		getContentPane().add(p1_Button);
		
		p2_Button.setBounds(300, 341, 250, 20);
		getContentPane().add(p2_Button);
		
		run_Button.setBounds(300, 421, 250, 20);
		getContentPane().add(run_Button);
		
		
		SFR0_Label.setBounds(1162, 190, 220, 20);
		getContentPane().add(SFR0_Label);
		
		SFR1_Label.setBounds(1162, 220, 220, 20);
		getContentPane().add(SFR1_Label);
		
		

		console_Label.setBounds(690, 40, 60, 20);
		getContentPane().add(console_Label);

		JScrollPane JCT = new JScrollPane(console_textArea);
		JCT.setBounds(600, 70, 240, 320);
		console_textArea.setBounds(600, 70, 240, 320);
		console_textArea.setLineWrap(true);
		console_textArea.setWrapStyleWord(true);
		getContentPane().add(JCT);
		
		P4_explaination.setBounds(600, 400, 270, 70);
		P4_explaination.setLineWrap(true);
		P4_explaination.setWrapStyleWord(true);
		P4_explaination.setEditable(false);
		P4_explaination.setBackground(new Color(238, 238, 238));
		getContentPane().add(P4_explaination);
		
		part4_Label.setBounds(1000, 40, 60, 20);
		getContentPane().add(part4_Label);
		
		fNum1_Label.setBounds(900, 70, 250, 20);
		getContentPane().add(fNum1_Label);
		
		fNum1_text.setBounds(900, 100, 250, 20);
		fNum1_text.setText("6.5");
		fNum1_text.setEnabled(false);
		getContentPane().add(fNum1_text);
		
		//set_fNum1_btn.setBounds(1090, 100, 55, 20);
		//getContentPane().add(set_fNum1_btn);
		
		fNum2_Label.setBounds(900, 130, 250, 20);
		getContentPane().add(fNum2_Label);
		
		fNum2_text.setBounds(900, 160, 250, 20);
		fNum2_text.setText("3.25");
		fNum2_text.setEnabled(false);
		getContentPane().add(fNum2_text);
		
		//set_fNum2_btn.setBounds(1090, 160, 55, 20);
		//getContentPane().add(set_fNum2_btn);

		FR0_Label.setBounds(900, 190, 230, 20);
		getContentPane().add(FR0_Label);
		
		FR1_Label.setBounds(900, 220, 230, 20);
		getContentPane().add(FR1_Label);
		
		FR0_text.setBounds(930, 190, 220, 20);
		getContentPane().add(FR0_text);
		
		FR1_text.setBounds(930, 220, 220, 20);
		getContentPane().add(FR1_text);
		
		float_btn.setBounds(900, 250, 110, 20);
		getContentPane().add(float_btn);
		
		convert_btn.setBounds(1040, 250, 110, 20);
		getContentPane().add(convert_btn);
		
		v1_Label.setBounds(960, 280, 60, 20);
		getContentPane().add(v1_Label);
		
		v2_Label.setBounds(1060, 280, 60, 20);
		getContentPane().add(v2_Label);
		
		line1_Label.setBounds(900, 310, 60, 20);
		getContentPane().add(line1_Label);
		
		v1_n1_text.setBounds(960, 310, 60, 20);
		getContentPane().add(v1_n1_text);
		
		v2_n1_text.setBounds(1060, 310, 60, 20);
		getContentPane().add(v2_n1_text);
		
		line2_Label.setBounds(900, 340, 60, 20);
		getContentPane().add(line2_Label);
		
		v1_n2_text.setBounds(960, 340, 60, 20);
		getContentPane().add(v1_n2_text);
		
		v2_n2_text.setBounds(1060, 340, 60, 20);
		getContentPane().add(v2_n2_text);
		
		line3_Label.setBounds(900, 370, 60, 20);
		getContentPane().add(line3_Label);
		
		v1_n3_text.setBounds(960, 370, 60, 20);
		getContentPane().add(v1_n3_text);
		
		v2_n3_text.setBounds(1060, 370, 60, 20);
		getContentPane().add(v2_n3_text);
		
		setVectors_btn.setBounds(900, 410, 245, 20);
		getContentPane().add(setVectors_btn);
		
		vecadd_btn.setBounds(900, 440, 110, 20);
		getContentPane().add(vecadd_btn);
		
		vecsub_btn.setBounds(1040, 440, 110, 20);
		getContentPane().add(vecsub_btn);

		return panel;
	}

	public static void main(String[] args) {
		mGUI mFrame = new mGUI();
		mFrame.setVisible(true);
	}

	// the widgets definitions
	JLabel						PC_Label			= new JLabel("PC	:");
	JLabel						MAR_Label			= new JLabel("MAR	:");
	JLabel						MBR_Label			= new JLabel("MBR	:");
	JLabel						IR_Label			= new JLabel("IR	:");
	JLabel						R00_Label			= new JLabel("R00	:");
	JLabel						R01_Label			= new JLabel("R01	:");
	JLabel						R02_Label			= new JLabel("R02	:");
	JLabel						R03_Label			= new JLabel("R03	:");
	JLabel						IX01_Label			= new JLabel("IX01	:");
	JLabel						IX02_Label			= new JLabel("IX02	:");
	JLabel						IX03_Label			= new JLabel("IX03	:");
	public static JTextField	PC_text				= new JTextField(16);
	public static JTextField	MAR_text			= new JTextField(16);
	public static JTextField	MBR_text			= new JTextField(16);
	public static JTextField	IR_text				= new JTextField(16);
	public static JTextField	R00_text			= new JTextField(16);
	public static JTextField	R01_text			= new JTextField(16);
	public static JTextField	R02_text			= new JTextField(16);
	public static JTextField	R03_text			= new JTextField(16);
	public static JTextField	IX01_text			= new JTextField();
	public static JTextField	IX02_text			= new JTextField();
	public static JTextField	IX03_text			= new JTextField();
	public static JTextField	FR0_text			= new JTextField();
	public static JTextField	FR1_text			= new JTextField();
	JButton						storeReg_button		= new JButton("store_reg");
	JButton						showReg_button		= new JButton("show_reg");
	JLabel						CCR_Label			= new JLabel("CCR");
	JLabel						CCR0_Label			= new JLabel("0");
	JLabel						CCR1_Label			= new JLabel("0");
	JLabel						CCR2_Label			= new JLabel("0");
	JLabel						CCR3_Label			= new JLabel("0");
	JLabel						FR0_Label			= new JLabel("FR0");
	JLabel						FR1_Label			= new JLabel("FR1");
	public static JLabel						SFR0_Label			= new JLabel("0.0");
	public static JLabel						SFR1_Label			= new JLabel("0.0");
	


	JLabel						input_Label			= new JLabel("Input:");
	JLabel						console_Label		= new JLabel("Console");
	JLabel						addr_Label			= new JLabel("Address	:");
	JLabel						addr_cont_Label		= new JLabel("Content	:");					
	public static JTextField	addr_text			= new JTextField(16);
	public static JTextField	addr_cont_text		= new JTextField(16);
	public static JTextField	input_text			= new JTextField(16);
	public static JTextArea		console_textArea	= new JTextArea();
	JButton						single_step_Button	= new JButton("Single Step");
	JButton						reset_Button		= new JButton("Reset");
	JButton						run_Button			= new JButton("Run");
	JButton						input_enter_Button	= new JButton("Enter");
	JButton						store_memory_Button	= new JButton(
															"Store Memory");
	JButton						show_memory_Button	= new JButton("Show Memory");
	JButton						p1_Button			= new JButton("Program 1");
	JButton						p2_Button			= new JButton("Program 2");
	JButton						project1_test_BTN	= new JButton(
															"project1 test");
	
	JLabel 						part4_Label			= new JLabel("Part IV");
	JLabel 						fNum1_Label			= new JLabel("Floating number 1");
	JLabel 						fNum2_Label			= new JLabel("Floating number 2");;
	JButton						set_fNum1_btn		= new JButton("SET");
	JButton						set_fNum2_btn		= new JButton("SET");
	JTextField					fNum1_text			= new JTextField(16);
	JTextField					fNum2_text			= new JTextField(16);
	JLabel 						v1_Label			= new JLabel("Vector 1");
	JLabel 						v2_Label			= new JLabel("Vector 2");
	JLabel 						line1_Label			= new JLabel("Node 1:");
	JLabel 						line2_Label			= new JLabel("Node 2:");
	JLabel 						line3_Label			= new JLabel("Node 3:");
	JTextField					v1_n1_text			= new JTextField(6);
	JTextField					v1_n2_text			= new JTextField(6);
	JTextField					v1_n3_text			= new JTextField(6);
	JTextField					v2_n1_text			= new JTextField(6);
	JTextField					v2_n2_text			= new JTextField(6);
	JTextField					v2_n3_text			= new JTextField(6);
	JButton						setVectors_btn		= new JButton("SET INTEGER VECTORS");
	JButton						float_btn=new JButton("FLOAT");
	JButton						convert_btn=new JButton("CONVERT");
	JButton						vecadd_btn=new JButton("VECADD");
	JButton						vecsub_btn=new JButton("VECSUB");
	
	JTextArea 						P4_explaination			= new JTextArea("Explaination for part 4: set values firstly, "
			+ "then choose a function and press run.");

	JLabel						reg_proc_Label		= new JLabel(
															"Registers Process Information");
	public static JTextArea		reg_proc_textArea	= new JTextArea();
	
	public static void showFloatReg(String data1, String data2){
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
		mantnum1 = flag1 * mantnum1tmp * Math.pow(10, expnum1) / Math.pow(10, 7); 
		mantnum2 = flag2 * mantnum2tmp * Math.pow(10, expnum2) / Math.pow(10, 7); 
		
		String addbin1, addbin2;
		DecimalFormat decimalFormat = new DecimalFormat("#.#########");
		addbin1 = decimalFormat.format(mantnum1);
		addbin2 = decimalFormat.format(mantnum2);
		String adddec1, adddec2;
		adddec1 = binaryToDecimal(addbin1);
		adddec2 = binaryToDecimal(addbin2);
		
		SFR0_Label.setText(adddec1);
		SFR1_Label.setText(adddec2);

	}
	
	public static String binaryToDecimal(String add1){
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
