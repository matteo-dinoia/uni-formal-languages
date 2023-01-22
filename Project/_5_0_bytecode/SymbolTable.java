package _5_0_bytecode;

import java.util.*;

public class SymbolTable {
	public static final int NOT_EXIST = -1;
	private Map <String, Integer> OffsetMap = new HashMap <String,Integer>();
	private int nextIdFree = 0;

	private void insert(String s, int address) {
		if(address >= 0 && !OffsetMap.containsValue(address))
			OffsetMap.put(s,address);
		else throw new IllegalArgumentException("Reference to a memory location already occupied by another variable");
	}

	public int lookupAddress (String s) {
		if(OffsetMap.containsKey(s))
			return OffsetMap.get(s);
		else return NOT_EXIST;
	}

	public int lookupOrNewAddress(String s){
		int res = lookupAddress(s);

		if(res == NOT_EXIST){
			res = nextIdFree++;
			insert(s, res);
		}
		return res;
	}
}
