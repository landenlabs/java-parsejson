package landenlabs;

import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/*
  Java Microbench Test -
  https://github.com/melix/jmh-gradle-plugin#configuration-options
 */


// Throughput, AverageTime, SampleTime, and SingleShotTime.
// @BenchmarkMode(Mode.AverageTime) //
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 0, time = 2000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 1)

// Prevents compiler constant optimization.
@State(Scope.Benchmark) // Benchmark, Group or Thread
public class TestJsonParser {

    private static final boolean BENCHTEST = true;
    volatile public static long parser1Size = 0;

    @Param({ /*"1000",*/ "10000" })
    private int N;

    private static String json;
    @Setup // @Setup(Level.Trial)
    public void setup() {
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            System.out.println("CWD=" + currentPath);
            json = new String(Files.readAllBytes(Paths.get("test2.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public void jsonParser1(Blackhole blackhole) {
        JsonReader reader = new JsonReader(json, true);
        blackhole.consume(reader.data.base.size());
        parser1Size = reader.data.base.size();
        // System.out.println("jsonParser1=" + reader.data.base.size());
    }

    public static void main(String[] args) throws IOException, RunnerException {
        System.out.println("==== TestJsonParser ====");
        if (BENCHTEST) {
            Options opt = new OptionsBuilder()
            //        .include(".*" + "TestStream2" + ".*")
                    .build();

            new Runner(opt).run();
            // org.openjdk.jmh.Main.main(args);
        } else {
            // Debug test
            TestJsonParser t2 = new TestJsonParser();
            t2.setup();
            // System.out.println("Test parser1 size=" + parser1Size);
        }
    }
}
