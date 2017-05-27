package architecture_project_01;

public class register_CCR {
	public static int[] CCR = new int[4];	
	
	public static int[] getCCR() {
		return CCR;
	}
	public static void setCCR(int[] ccR) 
	{
		CCR = ccR;
	}
	
	public static void initial(){
		for(int i=0; i<4;i++){
			CCR[i] = 0;
		}
	}
	
}
