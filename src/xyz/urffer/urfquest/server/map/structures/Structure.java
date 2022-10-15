package xyz.urffer.urfquest.server.map.structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import xyz.urffer.urfquest.shared.PairInt;

public abstract class Structure {
	
	protected PairInt pos;
	protected PairInt dims;
	
	protected HashMap<String, Structure> substructures = new HashMap<>();
	
	public Structure(PairInt position, PairInt dimensions) {
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
	
	public PairInt getPosition() {
		return pos.clone();
	}
	
	public PairInt getDimensions() {
		return dims.clone();
	}

}
