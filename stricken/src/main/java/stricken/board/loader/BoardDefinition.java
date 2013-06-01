package stricken.board.loader;

public class BoardDefinition {
	private TileDefinition[][] cells;
	private PieceDefinition[] pieces;
	private EntranceDefinition[] entrances;

	public void setCells(TileDefinition[][] cells) {
		this.cells = cells;
	}

	public TileDefinition[][] getCells() {
		return cells;
	}

	public PieceDefinition[] getPieces() {
		return pieces;
	}

	public void setPieces(PieceDefinition[] pieces) {
		this.pieces = pieces;
	}

	public EntranceDefinition[] getEntrances() {
		return entrances;
	}

	public void setEntrances(EntranceDefinition[] entrances) {
		this.entrances = entrances;
	}

}
