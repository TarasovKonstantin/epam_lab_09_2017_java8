package optional;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Convert2MethodRef", "ExcessiveLambdaUsage", "ResultOfMethodCallIgnored", "OptionalIsPresent"})
public class OptionalExample {

    @Test
    public void get() {
        Optional<String> o1 = Optional.empty();

        o1.ifPresent(s -> System.out.println(s));

        o1.orElse("t");
        o1.orElseGet(() -> "t");
        o1.orElseThrow(() -> new UnsupportedOperationException());
    }

    @Test
    public void ifPresent() {
        Optional<String> o1 = getOptional();

        o1.ifPresent(System.out::println);

        if (o1.isPresent()) {
            System.out.println(o1.get());
        }
    }

    @Test
    public void map() {
        Optional<String> o1 = getOptional();

        Function<String, Integer> getLength = String::length;

        Optional<Integer> expected = o1.map(getLength);

        //System.out.println(expected.toString());

        Optional<Integer> actual;
        if (o1.isPresent()) {
            actual = Optional.ofNullable(getLength.apply(o1.get()));
        } else {
            actual = Optional.empty();
        }

        //System.out.println(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    public void flatMap() {
        //throw new UnsupportedOperationException("Not implemented");
        Optional<String> optional = getOptional();

        Function<String, Optional<List<Character>>> function = s -> {
            Optional<List<Character>> list = Optional.of(new ArrayList<Character>());
            s.chars().forEach(c->list.get().add(((char) c)));
            return list;
        };


        Optional<List<Character>> expected = optional.flatMap(function);
        Optional<List<Character>> actual = (optional.isPresent())? function.apply(optional.get()) : Optional.empty();

        assertEquals(expected, actual);

    }

    @Test
    public void filter() {

        //throw new UnsupportedOperationException("Not implemented");
        Optional<String> optional = getOptional();

        Predicate<String> predicate = s -> s.length() == 3;

        Optional<String> expected = optional.filter(predicate);
        Optional<String> actual = (optional.isPresent()) ? (predicate.test(optional.get()) ? optional : Optional.empty()) : Optional.empty();

        assertEquals(expected, actual);

    }

    private Optional<String> getOptional() {
        return ThreadLocalRandom.current().nextBoolean() ? Optional.empty() : Optional.of("abc");
    }
}
