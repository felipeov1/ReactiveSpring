package com.reactivespring.reactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){

            Flux<String> stringFlux  = Flux.just("Data01", "Data02", "Data03")
//                    .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                    .concatWith(Flux.just("After Error"))
                    .log();

        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println("Exception is" + e)
                ,() -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_WithoutError(){

        Flux<String> stringFlux = Flux.just("Data01", "Data02", "Data03")
            .log();

        StepVerifier.create(stringFlux)
                .expectNext("Data01")
                .expectNext("Data02")
                .expectNext("Data03")
                .verifyComplete();
    }

    @Test
    public void fluxTestElements_WithError1(){

        Flux<String> stringFlux = Flux.just("Data01", "Data02", "Data03")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Data01","Data02", "Data03")
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElements_WithError2(){

        Flux<String> stringFlux = Flux.just("Data01", "Data02", "Data03")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Data01")
                .expectNext("Data02")
                .expectNext("Data03")
//                .expectError(RuntimeException.class)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void fluxTestElementsCount_WithError3(){

        Flux<String> stringFlux = Flux.just("Data01", "Data02", "Data03")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String>  stringMono = Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_Error(){
        Mono<String> stringMono = Mono.just("String");

        StepVerifier.create(Mono.error(new RuntimeException("Exception Ocurred")).log())
                .expectError(RuntimeException.class)
                .verify();
    }




}
