package stricken.ui;

public interface IDelegatingKeySink extends IKeySink {
	IKeySink getCurrentKeySink();
}
