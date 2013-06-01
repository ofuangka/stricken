package stricken.board.loader;

public class BoardDefinition {
	private TileDefinition[][] cells;
	private PieceDefinition[] pieces;
	private EntranceDefinition[] entrances;

	public TileDefinition[][] getCells() {
		return cells;
	}

	public EntranceDefinition[] getEntrances() {
		return entrances;
	}

	public PieceDefinition[] getPieces() {
		return pieces;
	}

	public void setCells(TileDefinition[][] cells) {
		this.cells = cells;
	}

	public void setEntrances(EntranceDefinition[] entrances) {
		this.entrances = entrances;
	}

	public void setPieces(PieceDefinition[] pieces) {
		this.pieces = pieces;
	}

}
