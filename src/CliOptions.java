import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/** Validated command-line configuration for LogFlow. */
public record CliOptions(
        Path input, Path output, Integer minStatus, String pathPrefix, List<String> fields) {
    private static final List<String> DEFAULT_FIELDS =
            List.of("timestamp", "clientIp", "status", "path");

    public static CliOptions parse(String[] args) {
        if (args.length < 2) throw new IllegalArgumentException("Input and output files are required");
        Path input = Path.of(args[0]);
        Path output = Path.of(args[1]);
        Integer minStatus = null;
        String pathPrefix = null;
        List<String> fields = DEFAULT_FIELDS;

        for (int i = 2; i < args.length; i += 2) {
            if (i + 1 >= args.length) throw new IllegalArgumentException("Missing value for " + args[i]);
            String value = args[i + 1];
            switch (args[i]) {
                case "--min-status" -> minStatus = parseStatus(value);
                case "--path-prefix" -> pathPrefix = requireText(value, "path prefix");
                case "--fields" -> fields = parseFields(value);
                default -> throw new IllegalArgumentException("Unknown option: " + args[i]);
            }
        }
        return new CliOptions(input, output, minStatus, pathPrefix, fields);
    }

    public static String usage() {
        return "Usage: java Main <input> <output> "
                + "[--min-status N] [--path-prefix PREFIX] [--fields a,b,c]";
    }

    private static int parseStatus(String value) {
        try {
            int status = Integer.parseInt(value);
            if (status < 100 || status > 599) throw new NumberFormatException();
            return status;
        } catch (NumberFormatException error) {
            throw new IllegalArgumentException("Status must be between 100 and 599");
        }
    }

    private static List<String> parseFields(String value) {
        List<String> fields = Arrays.stream(value.split(",", -1)).map(String::trim).toList();
        if (fields.isEmpty() || fields.stream().anyMatch(String::isEmpty)) {
            throw new IllegalArgumentException("Fields must be a comma-separated non-empty list");
        }
        if (fields.stream().distinct().count() != fields.size()) {
            throw new IllegalArgumentException("Fields must not contain duplicates");
        }
        return fields;
    }

    private static String requireText(String value, String name) {
        if (value.isBlank()) throw new IllegalArgumentException("Missing " + name);
        return value;
    }
}
