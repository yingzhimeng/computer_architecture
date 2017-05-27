package architecture_project_01;

public class memory {
	public static int[] memory_arr = new int[4096];
	
	public static void inster(int addr, int cont) {
		memory_arr[addr] = cont;
	}
	
	public static int getValueAt(int addr) {
		return memory_arr[addr];
	}
}
