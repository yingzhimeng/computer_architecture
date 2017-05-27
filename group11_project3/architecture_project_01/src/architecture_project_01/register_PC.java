package architecture_project_01;

public class register_PC {

	public static int value = 0;
	
	public static void selfIncrement(){
		value++;
	}
	
	public static int getValue() {
		return value;
	}
	
	public static void setValue(int value) {
		register_PC.value = value;
	}
}
