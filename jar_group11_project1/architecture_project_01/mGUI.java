package architecture_project_01;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import sun.awt.WindowClosingListener;

public class mGUI extends JFrame {

	ControlUnit cu = new ControlUnit();
	
	//read memory from txt file
	memory_io mio = new memory_io();
	
	public mGUI() {
		// TODO Auto-generated constructor stub
		
		//set the GUI paramters
		this.setTitle("Architcture Project 01");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 450);

		java.awt.Container con = this.getContentPane();
		con.setLayout(null);
		JPanel panel = addComponents(con);
		setListeners();

		con.add(panel);
	}

	//check if address is a binary array and 12 bits
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

	//check if content in the address is a binary array and 16 bits
	private Boolean cont_check(String cont,String name) {
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
	
	//convert decimal number to binary(need to add 0 before binary number )
	private String toBinary_reg(int decNum, int bits){
		String pre_0 = "";
		String b_i = Integer.toBinaryString(decNum);
		for(int j = 0; j < bits - b_i.length(); j++ )
			pre_0 += '0';	
		return (pre_0 + b_i);
	}

	//buttons' listeners
	private void setListeners() {
		// TODO Auto-generated method stub
		
		//store memory button
		store_memory_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean isValid = true;	//flag to make sure the address and content is valid
				String tem_addr = addr_text.getText();
				String tem_cont = addr_cont_text.getText();
				
				isValid = addr_check(tem_addr,"Address");
				isValid = cont_check(tem_cont,"Content");

				if (isValid) {
					memory.inster(Integer.parseInt(tem_addr, 2),
							Integer.parseInt(tem_cont, 2));
					console_textArea.append("Store success.\n");
				}

			}
		});

		//show memory button
		show_memory_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				boolean isValid = true;	//flag to make sure the address and content is valid
				String tem_addr = addr_text.getText();
				
				isValid = addr_check(tem_addr, "Address");
				
				if(isValid){
					int cont = memory.getValueAt(Integer.parseInt(tem_addr, 2));
					String tem_zero = "";
					for(int j = 0; j < 16 - Integer.toBinaryString(cont).length(); j++ )
						tem_zero += '0';	
					addr_cont_text.setText(tem_zero + Integer.toBinaryString(cont));
					console_textArea.append("Fetched memory.\n");
				}
			}
		});
		
	
		storeReg_button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				boolean isValid = true;	//flag to make sure the registers' contents are valid
				
				String tem_PC 	= PC_text.getText();
				String tem_R00	= R00_text.getText();
				String tem_R01	= R01_text.getText();
				String tem_R02 	= R02_text.getText();
				String tem_R03	= R03_text.getText();
				String tem_IX01	= IX01_text.getText();
				String tem_IX02	= IX02_text.getText();
				String tem_IX03	= IX03_text.getText();
				
				isValid = addr_check(tem_PC, "PC");
				isValid = cont_check(tem_R00, "R00");
				isValid = cont_check(tem_R01, "R01");
				isValid = cont_check(tem_R02, "R02");
				isValid = cont_check(tem_R03, "R03");
				isValid = cont_check(tem_IX01, "IX01");
				isValid = cont_check(tem_IX02, "IX02");
				isValid = cont_check(tem_IX03, "IX03");
				
				if(isValid){
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
				PC_text.setText(toBinary_reg(register_PC.value, 12));
				R00_text.setText(toBinary_reg(register_GPR.R0_value, 16));
				R01_text.setText(toBinary_reg(register_GPR.R1_value, 16));
				R02_text.setText(toBinary_reg(register_GPR.R2_value, 16));
				R03_text.setText(toBinary_reg(register_GPR.R3_value, 16));
				IX01_text.setText(toBinary_reg(register_IX.value_01, 16));
				IX02_text.setText(toBinary_reg(register_IX.value_02, 16));
				IX03_text.setText(toBinary_reg(register_IX.value_03, 16));
			}
		});
		
		single_step_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				cu.singleCircle();
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
				
			}
		});
		
		
		reset_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//initialize all of values on the screen and values in background
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
				register_PC.value 		= 0;
				register_GPR.R0_value 	= 0;
				register_GPR.R1_value 	= 0;
				register_GPR.R2_value 	= 0;
				register_GPR.R3_value 	= 0;
				register_IX.value_01 	= 0;
				register_IX.value_02 	= 0;
				register_IX.value_03 	= 0;
				register_IR.value		= 0;
				register_MAR.value		= 0;
				register_MBR.value		= 0;
				
				for(int i = 0; i < 4096; i++){
					memory.memory_arr[i] = 0;
				}
				
				console_textArea.append("Reset all of registers and memory.\n");
			}
		});
		
		//a test program design by myself
		project1_test_BTN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//input the initial values of registers
				register_PC.value 		= 100;
				register_GPR.R0_value 	= 200;
				register_GPR.R1_value 	= 300;
				register_GPR.R2_value 	= 400;
				register_GPR.R3_value 	= 500;
				register_IX.value_01 	= 1000;
				register_IX.value_02 	= 1100;
				register_IX.value_03 	= 1200;
				register_IR.value		= 0;
				register_MAR.value		= 0;
				register_MBR.value		= 0;
				
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
				
				
				//input the values of memory
				memory.memory_arr[100] = 1029; //LDR OPCODE:0000010000000101
				memory.memory_arr[101] = 2310; //STR OPCODE:0000100100000110
				memory.memory_arr[102] = 3591; //LDA OPCODE:0000111000000111
				memory.memory_arr[103] = 42081; //LDX OPCODE:1010010001100001
				memory.memory_arr[104] = 43087;
				memory.memory_arr[105] = 4639;
				memory.memory_arr[106] = 3615;
				memory.memory_arr[107] = 4639;
				memory.memory_arr[108] = 5663;
				memory.memory_arr[108] = 38753;
				memory.memory_arr[5] = 1;
				memory.memory_arr[6] = 2;
				memory.memory_arr[1000] = Integer.parseInt("00000100000110010", 2);
				memory.memory_arr[1001] = 1100;
				memory.memory_arr[1100] = 1;
				memory.memory_arr[31] = 1;
				
				
			}
		});

	}
	
	//override the closing methods write memory to txt file
	class MyWinAdapter extends WindowAdapter {
		 public void windowClosing(WindowEvent e) {
		 
		  mio.write_m(memory_io.memory_arr);	 
		  System.exit(0);//exit program
		 }
		}

	public JPanel addComponents(java.awt.Container con) {
		
		//the layout of the GUI
		JPanel panel = new JPanel();

		PC_Label.setBounds(20, 40, 60, 20);
		this.add(PC_Label);

		MAR_Label.setBounds(20, 70, 60, 20);
		this.add(MAR_Label);

		MBR_Label.setBounds(20, 100, 60, 20);
		this.add(MBR_Label);

		IR_Label.setBounds(20, 130, 60, 20);
		this.add(IR_Label);

		R00_Label.setBounds(20, 160, 60, 20);
		this.add(R00_Label);

		R01_Label.setBounds(20, 190, 60, 20);
		this.add(R01_Label);

		R02_Label.setBounds(20, 220, 60, 20);
		this.add(R02_Label);

		R03_Label.setBounds(20, 250, 60, 20);
		this.add(R03_Label);

		IX01_Label.setBounds(20, 280, 60, 20);
		this.add(IX01_Label);

		IX02_Label.setBounds(20, 310, 60, 20);
		this.add(IX02_Label);

		IX03_Label.setBounds(20, 340, 60, 20);
		this.add(IX03_Label);

		PC_Label.setBounds(20, 40, 60, 20);
		this.add(PC_Label);

		PC_text.setBounds(60, 40, 180, 20);
		this.add(PC_text);

		MAR_text.setBounds(60, 70, 180, 20);
		MAR_text.setEditable(false);
		this.add(MAR_text);

		MBR_text.setBounds(60, 100, 180, 20);
		MBR_text.setEditable(false);
		this.add(MBR_text);

		IR_text.setBounds(60, 130, 180, 20);
		IR_text.setEditable(false);
		this.add(IR_text);

		R00_text.setBounds(60, 160, 180, 20);
		this.add(R00_text);

		R01_text.setBounds(60, 190, 180, 20);
		this.add(R01_text);

		R02_text.setBounds(60, 220, 180, 20);
		this.add(R02_text);

		R03_text.setBounds(60, 250, 180, 20);
		this.add(R03_text);

		IX01_text.setBounds(60, 280, 180, 20);
		this.add(IX01_text);

		IX02_text.setBounds(60, 310, 180, 20);
		this.add(IX02_text);

		IX03_text.setBounds(60, 340, 180, 20);
		this.add(IX03_text);

		storeReg_button.setBounds(30, 370, 90, 20);
		this.add(storeReg_button);

		showReg_button.setBounds(130, 370, 90, 20);
		this.add(showReg_button);

		addr_Label.setBounds(300, 40, 60, 20);
		this.add(addr_Label);

		addr_cont_Label.setBounds(300, 70, 60, 20);
		this.add(addr_cont_Label);

		addr_text.setBounds(370, 40, 180, 20);
		this.add(addr_text);

		addr_cont_text.setBounds(370, 70, 180, 20);
		this.add(addr_cont_text);

		store_memory_Button.setBounds(310, 100, 110, 20);
		this.add(store_memory_Button);

		show_memory_Button.setBounds(430, 100, 110, 20);
		this.add(show_memory_Button);

		//leave to phase 2
		//input_Label.setBounds(300, 160, 60, 20);
		//this.add(input_Label);

		//input_text.setBounds(300, 190, 180, 20);
		//this.add(input_text);

		//input_enter_Button.setBounds(480, 190, 60, 20);
		//this.add(input_enter_Button);
		
		project1_test_BTN.setBounds(300, 190, 250, 20);
		this.add(project1_test_BTN);

		single_step_Button.setBounds(310, 250, 110, 20);
		this.add(single_step_Button);

		reset_Button.setBounds(430, 250, 110, 20);
		this.add(reset_Button);

		//leave to phase 2
		//single_step_Button.setBounds(310, 310, 110, 20);
		//this.add(single_step_Button);

		console_Label.setBounds(690, 40, 60, 20);
		this.add(console_Label);

		JScrollPane JCT = new JScrollPane(console_textArea);
		JCT.setBounds(600, 70, 240, 320);
		console_textArea.setBounds(600, 70, 240, 320);
		console_textArea.setLineWrap(true);
		console_textArea.setWrapStyleWord(true); 
		this.add(JCT);

		return panel;
	}

	public static void main(String[] args) {
		mGUI mFrame = new mGUI();
		mFrame.setVisible(true);
	}
	
	//the widgets definitions
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
	JButton						storeReg_button		= new JButton("store_reg");
	JButton						showReg_button		= new JButton("show_reg");

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
	JButton						store_memory_Button	= new JButton("Store Memory");
	JButton						show_memory_Button	= new JButton("Show Memory");
	JButton						p1_Button			= new JButton("Program 1");
	JButton						project1_test_BTN	= new JButton("project1 test");

	JLabel						reg_proc_Label		= new JLabel(
															"Registers Process Information");
	public static JTextArea		reg_proc_textArea	= new JTextArea();

}
