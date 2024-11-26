package com.reactivespring.reactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){

            Flux<String> stringFlux  = Flux.just("Data01", "Data02", "Data03")
                    .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                    .concatWith(Flux.just("After Error"))
                    .log();

        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println("Exception is" + e)
                ,() -> System.out.println("Completed"));
    }
}
