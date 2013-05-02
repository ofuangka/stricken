package stricken.ui;

public interface IDelegatingKeySink extends IKeySink {
	public IKeySink getCurrentKeySink();
}
