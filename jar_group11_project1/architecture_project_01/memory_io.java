package architecture_project_01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class memory_io extends memory{

	String file_name = "memory.txt";
	String introduction = "This is the simulator memory file.\nThe first conlum is to record the memory address, "
					+ "the second cnlum is to record the content of address";
	
	
	public memory_io() {
		// TODO Auto-generated constructor stub
		read_m(memory_arr);
	}
	
	//to build the txt file. All contents in the memory are 0 
	public void initialize_m(){
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file_name));
			out.write(introduction);
			for(int i = 0; i < 4096; i++){
				out.newLine();
				String b_i = Integer.toBinaryString(i);
				for(int j = 0; j < 12 - b_i.length(); j++ )
					out.write('0');	
				out.write(b_i + " 0000000000000000");
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	//load memory from txt file
	public void read_m(int[] memory_arr) {
		String line = "";
		String address = "";
		String content = "";
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(file_name));
			in.readLine();
			in.readLine(); 
			
			while((line = in.readLine()) != null){
				address = line.substring(0, 11); //if memory.txt is incorrect, it will throw the outofindex exception
				content = line.substring(13, 28);
				if(Integer.parseInt(address, 2) >= 4095){
					System.out.println(address);
					System.out.println(content);
				}
				memory_arr[Integer.parseInt(address, 2)] = Integer.parseInt(content, 2);
				
			}
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			//if no file, build it 
			initialize_m();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//write memory to txt file
	public void write_m(int[] memomemory_arr) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file_name));
			out.write(introduction);
			for(int i = 0; i < 4096; i++){
				out.newLine();
				String b_i = Integer.toBinaryString(i);
				for(int j = 0; j < 12 - b_i.length(); j++ )
					out.write('0');	
				out.write(b_i + " " + memomemory_arr[i]);
				out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
