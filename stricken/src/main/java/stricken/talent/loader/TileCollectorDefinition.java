package stricken.talent.loader;

public class TileCollectorDefinition {
	private String type;
	private int range;
	private String filterType;
	private boolean inclusive;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filter) {
		this.filterType = filter;
	}

	public void setInclusive(boolean inclusive) {
		this.inclusive = inclusive;
	}

	public boolean isInclusive() {
		return inclusive;
	}
}
