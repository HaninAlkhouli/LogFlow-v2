public class Main {
    public static void main(String[] args) {
        try {
            run(CliOptions.parse(args));
        } catch (IllegalArgumentException error) {
            System.err.println("Error: " + error.getMessage());
            System.err.println(CliOptions.usage());
        }
    }

    static void run(CliOptions options) {
        ParserStage parser = new ParserStage();
        PipelineBuilder<LogRecord> records = PipelineBuilder
                .from(new FileLineSource(options.input()))
                .via(parser);

        if (options.minStatus() != null) {
            records = records.via(new PredicateFilterStage(
                    record -> record.status() >= options.minStatus()));
        }
        if (options.pathPrefix() != null) {
            records = records.via(new PredicateFilterStage(
                    record -> record.path().startsWith(options.pathPrefix())));
        }

        Pipeline<ProjectedRecord> pipeline = records
                .via(new ProjectionStage(options.fields()))
                .to(new FileSink(options.output()));
        pipeline.run();

        System.out.println("Malformed lines skipped: " + parser.malformedCount());
        System.out.println("Output written to: " + options.output().toAbsolutePath());
    }
}
