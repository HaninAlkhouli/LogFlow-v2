import java.util.Objects;

final class StageNode<I, O> implements PipelineNode<O> {
    private final PipelineNode<I> previous;
    private final Stage<I, O> stage;

    StageNode(PipelineNode<I> previous, Stage<I, O> stage) {
        this.previous = Objects.requireNonNull(previous);
        this.stage = Objects.requireNonNull(stage);
    }

    @Override
    public void open() {
        previous.open();
        try {
            stage.open();
        } catch (RuntimeException error) {
            previous.close();
            throw error;
        }
    }

    @Override
    public void produce(Emitter<O> out) {
        previous.produce(input -> process(input, out));
    }

    private void process(I input, Emitter<O> out) {
        try {
            stage.process(input, out);
        } catch (StageException error) {
            throw new RuntimeException("Pipeline stage failed", error);
        }
    }

    @Override
    public void close() {
        try {
            stage.close();
        } finally {
            previous.close();
        }
    }
}
