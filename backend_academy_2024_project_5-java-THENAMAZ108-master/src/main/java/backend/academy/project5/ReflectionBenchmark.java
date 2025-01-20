package backend.academy.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.SneakyThrows;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@SuppressWarnings({"UncommentedMain", "MagicNumber"})
@State(Scope.Thread)
public class ReflectionBenchmark {
    private static final String METHOD_NAME = "name";
    private static final String LAMBDA_METHOD_NAME = "get";
    private Student student;
    private Method method;
    private MethodHandle methodHandle;
    private Supplier<String> supplier;

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(5))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(5))
            .build();

        new Runner(options).run();
    }

    @SneakyThrows
    @Setup
    public void setup() {
        student = new Student("Alexander", "Biryukov");

        method = Student.class.getDeclaredMethod(METHOD_NAME);

        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(String.class);
        methodHandle = lookup.findVirtual(Student.class, METHOD_NAME, methodType);

        Method lambdaMethod = Supplier.class.getDeclaredMethod(LAMBDA_METHOD_NAME);
        MethodType lambdaMethodType =
            MethodType.methodType(lambdaMethod.getReturnType(), lambdaMethod.getParameterTypes());
        MethodHandles.Lookup studentLookup = MethodHandles.lookup().in(Student.class);
        MethodType implMethodType = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
        MethodHandle implMethodHandle = lookup.findVirtual(Student.class, method.getName(), implMethodType);
        MethodType factoryMethodType = MethodType.methodType(Supplier.class, Student.class);
        CallSite callSite = LambdaMetafactory.metafactory(
            studentLookup,
            LAMBDA_METHOD_NAME,
            factoryMethodType,
            lambdaMethodType,
            implMethodHandle,
            implMethodType
        );
        supplier = (Supplier<String>) callSite.getTarget().invokeExact(student);
    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @SneakyThrows
    @Benchmark
    public void reflection(Blackhole bh) {
        String name = (String) method.invoke(student);
        bh.consume(name);
    }

    @SneakyThrows
    @Benchmark
    public void testMethodHandle(Blackhole bh) {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @SneakyThrows
    @Benchmark
    public void testLambdaFactory(Blackhole bh) {
        String name = supplier.get();
        bh.consume(name);
    }

    record Student(String name, String surname) {
    }
}
