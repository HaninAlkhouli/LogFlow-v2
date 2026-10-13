import java.util.Objects;

final class SourceNode<T> implements PipelineNode<T> {
    private final Source<T> source;

    SourceNode(Source<T> source) {
        this.source = Objects.requireNonNull(source);
    }

    @Override
    public void open() {
    }

    @Override
    public void produce(Emitter<T> out) {
        source.produce(out);
    }

    @Override
    public void close() {
    }
}
