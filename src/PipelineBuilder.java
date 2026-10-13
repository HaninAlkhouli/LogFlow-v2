import java.util.Objects;

/** Fluent, type-safe builder for Source -> Stage(s) -> Sink pipelines. */
public final class PipelineBuilder<T> {
    private final PipelineNode<T> output;

    private PipelineBuilder(PipelineNode<T> output) {
        this.output = output;
    }

    public static <T> PipelineBuilder<T> from(Source<T> source) {
        return new PipelineBuilder<>(new SourceNode<>(Objects.requireNonNull(source)));
    }

    public <R> PipelineBuilder<R> via(Stage<T, R> stage) {
        return new PipelineBuilder<>(new StageNode<>(output, Objects.requireNonNull(stage)));
    }

    public Pipeline<T> to(Sink<T> sink) {
        return new Pipeline<>(output, Objects.requireNonNull(sink));
    }
}
