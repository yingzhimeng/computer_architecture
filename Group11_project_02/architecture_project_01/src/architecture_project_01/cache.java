package architecture_project_01;

//write through cache

//cacheline = tag(10 bits) + dirtybit(1 bit) + word1-4(64 bits, 16 bits per word)

public class cache {
	public static String[]	cacheLine	= new String[16];
	public String			id			= "";
	public static String	tag			= "";
	public static String	offset		= "";
	public static String	dirtybit	= "";
	public static String	word1		= "";
	public static String	word2		= "";
	public static String	word3		= "";
	public static String	word4		= "";

	// To mark the line which will out from cache at the next time
	public static int		nextLine	= 0;

	public cache() {
		// TODO Auto-generated constructor stub
		init();

	}

	public static void init() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 75; j++) {
				cacheLine[i] += "0";
			}

	}

	public static int getCacheLineValue(int addr) {
		String binaryAddr = toBinary(addr, 12);
		tag = binaryAddr.substring(0, 10);
		for (int i = 0; i < 16; i++) {
			if (tag.equals(cacheLine[i].substring(0, 10))) {
				offset = binaryAddr.substring(10);
				int wordStartPosition = Integer.parseInt(offset, 2) * 16 + 11;
				int result = Integer.parseInt(cacheLine[i].substring(
						wordStartPosition, wordStartPosition + 16), 2);
				//return result;

			}

		}
		int result = readFromMemory(addr);
		return result;
	}

	private static String toBinary(int num, int size) {
		// TODO Auto-generated method stub
		String binaryNum = Integer.toBinaryString(num);
		;
		for (; binaryNum.length() < size;)
			binaryNum = "0" + binaryNum;
		return binaryNum;

	}

	public static void setCacheLineValue(int addr, int word) {
		boolean isInCahce = false;
		String binaryAddr = toBinary(addr, 12);
		String binartWord = toBinary(word, 16);
		tag = binaryAddr.substring(0, 10);
		for (int i = 0; i < 16; i++) {
			if (tag.equals(cacheLine[i].substring(0, 10))) {
				dirtybit = "1";
				offset = binaryAddr.substring(10,12);
				int wordStartPosition = Integer.parseInt(offset, 2) * 16 + 11;
				StringBuffer lineBuffer = new StringBuffer(cacheLine[i]);
				lineBuffer.replace(12, 13, dirtybit);
				lineBuffer.replace(wordStartPosition, wordStartPosition + 16,
						binartWord);
				cacheLine[i] = lineBuffer.toString();
				writeToMemory(addr, word);
				isInCahce = true;
			}
		}
		if(!isInCahce){
			tag = binaryAddr.substring(0, 10);
			dirtybit = "1";
			writeToMemory(addr, word);
			word1 = toBinary(memory.memory_arr[Integer.parseInt(tag + "00", 2)], 16);
			word2 = toBinary(memory.memory_arr[Integer.parseInt(tag + "01", 2)], 16);
			word3 = toBinary(memory.memory_arr[Integer.parseInt(tag + "10", 2)], 16);
			word4 = toBinary(memory.memory_arr[Integer.parseInt(tag + "11", 2)], 16);
			cacheLine[nextLine] = tag + dirtybit + word1 + word2 + word3 + word4;
			if (++nextLine == 16)
				nextLine = 0;
		}
	}

	public static void writeToMemory(int addr, int word) {
		memory.memory_arr[addr] = word;

	}

	public static int readFromMemory(int addr) {
		String binaryAddr = toBinary(addr, 12);
		tag = binaryAddr.substring(0, 10);
		dirtybit = "0";
		word1 = toBinary(memory.memory_arr[Integer.parseInt(tag + "00", 2)], 16);
		word2 = toBinary(memory.memory_arr[Integer.parseInt(tag + "01", 2)], 16);
		word3 = toBinary(memory.memory_arr[Integer.parseInt(tag + "10", 2)], 16);
		word4 = toBinary(memory.memory_arr[Integer.parseInt(tag + "11", 2)], 16);
		cacheLine[nextLine] = tag + dirtybit + word1 + word2 + word3 + word4;
		if (++nextLine == 16)
			nextLine = 0;
		return memory.memory_arr[addr];

	}
}