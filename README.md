# LogFlow

LogFlow is a small Java pipeline application. It reads a text log file line by line and prints each line to the console through separate source, pipeline, and sink components.

## Requirements

- Java JDK 21 or newer

## Project structure

- `src/Emitter.java`: sends an item to the next component.
- `src/Source.java`: interface for components that produce data.
- `src/Sink.java`: interface for components that consume data.
- `src/Record.java`: marker interface reserved for future record types.
- `src/Stage.java`: general processing-stage interface and lifecycle hooks.
- `src/FileLineSource.java`: reads one `String` per file line.
- `src/ConsoleSink.java`: prints each received line.
- `src/Pipeline.java`: connects the source to the sink.
- `src/Main.java`: reads the argument, assembles the pipeline, and runs it.
- `data/access-small.log`: sample input log.

## Compile

From the project root:

```bash
javac -d out src/*.java
```

## Run

```bash
java -cp out Main data/access-small.log
```

## Expected result

Every line from `data/access-small.log` is printed in the same order.
