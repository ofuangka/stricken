package stricken.board.loader;

public class BoardDefinition {
	private String id;
	private TileDefinition[][] tiles;
	private CritterDefinition[] critters;
	private EntranceDefinition[] entrances;
	private DecorationDefinition[] decorations;

	public TileDefinition[][] getTiles() {
		return tiles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EntranceDefinition[] getEntrances() {
		return entrances;
	}

	public CritterDefinition[] getCritters() {
		return critters;
	}

	public DecorationDefinition[] getDecorations() {
		return decorations;
	}

	public void setTiles(TileDefinition[][] tiles) {
		this.tiles = tiles;
	}

	public void setEntrances(EntranceDefinition[] entrances) {
		this.entrances = entrances;
	}

	public void setCritters(CritterDefinition[] pieces) {
		this.critters = pieces;
	}

	public void setDecorations(DecorationDefinition[] decorations) {
		this.decorations = decorations;
	}

}
