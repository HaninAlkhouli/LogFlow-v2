import java.util.Objects;

/** A fully assembled, type-safe Source -> Stage(s) -> Sink pipeline. */
public final class Pipeline<T> {
    private final PipelineNode<T> output;
    private final Sink<T> sink;

    Pipeline(PipelineNode<T> output, Sink<T> sink) {
        this.output = Objects.requireNonNull(output);
        this.sink = Objects.requireNonNull(sink);
    }

    public void run() {
        boolean outputOpened = false;
        boolean sinkOpened = false;
        try {
            output.open();
            outputOpened = true;
            sink.open();
            sinkOpened = true;
            output.produce(sink::consume);
        } finally {
            try {
                if (sinkOpened) {
                    sink.close();
                }
            } finally {
                if (outputOpened) {
                    output.close();
                }
            }
        }
    }
}
