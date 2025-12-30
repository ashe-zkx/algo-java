package pers.zkx.algo.benchmark.string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * JMH 基准，测量多种字符串拼接方式的吞吐量。
 */
@BenchmarkMode(org.openjdk.jmh.annotations.Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 5)
@Fork(1)
public class StringConcatJmhBenchmark {

    @State(Scope.Benchmark)
    public static class ItemsState {
        static final int SIZE = 1_000_000;
        final List<Item> items = new ArrayList<>(SIZE);

        @Setup
        public void setup() {
            for (int i = 0; i < SIZE; i++) {
                String f1 = "key" + (i % 1000);
                int f2 = ThreadLocalRandom.current().nextInt(0, 1_000_000);
                items.add(new Item(f1, f2));
            }
            System.out.println(String.format(Locale.ENGLISH, "Prepared %,d items", SIZE));
        }
    }

    @Benchmark
    @OperationsPerInvocation(ItemsState.SIZE)
    public void stringFormat(ItemsState state, Blackhole bh) {
        for (Item it : state.items) {
            it.buildIdByFormat();
            bh.consume(it.getId());
        }
    }

    @Benchmark
    @OperationsPerInvocation(ItemsState.SIZE)
    public void plus(ItemsState state, Blackhole bh) {
        for (Item it : state.items) {
            it.buildIdByPlus();
            bh.consume(it.getId());
        }
    }

    @Benchmark
    @OperationsPerInvocation(ItemsState.SIZE)
    public void builder(ItemsState state, Blackhole bh) {
        for (Item it : state.items) {
            it.buildIdByBuilder();
            bh.consume(it.getId());
        }
    }

    @Benchmark
    @OperationsPerInvocation(ItemsState.SIZE)
    public void streamPlus(ItemsState state, Blackhole bh) {
        state.items.stream().forEach(it -> {
            it.setId(it.getF1() + "-" + it.getF2());
            bh.consume(it.getId());
        });
    }
}

