package xyz.urffer.urfquest.server.map.structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public abstract class Structure {
	
	protected int[] pos;
	protected int[] dims;
	
	protected HashMap<String, Structure> substructures = new HashMap<>();
	
	public Structure(int[] position, int[] dimensions) {
		this.pos = position;
		this.dims = dimensions;
	}
	
	
	/*
	 * Child structure management
	 */
	
	public void addSubstruct(String structName, Structure structure) {
		substructures.put(structName, structure);
	}
	
	public Structure getSubstruct(String structName) {
		return substructures.get(structName);
	}
	
	public Set<String> getSubstructNames() {
		return substructures.keySet();
	}
	
	public Collection<Structure> getSubstructs() {
		return substructures.values();
	}
	
	
	/*
	 * Position and size management
	 */
	
	public int[] getPosition() {
		return pos;
	}
	
	public int[] getDimensions() {
		return dims;
	}

}
