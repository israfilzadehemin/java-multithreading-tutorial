package com.tutorials.multithreading.executiveframework;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // Asynchronous method that does not return any value
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(
                () -> System.out.println("This is an operation that runs asynchronously and does not return any value"));

        runAsync.join();

        // Asynchronous method that return value
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> "value from supplyAsync()");
        String suppliedValue = supplyAsync.exceptionally(ex -> "Exception message").get();
        System.out.println(suppliedValue);

        supplyAsync.thenRun(() -> {
            System.out.printf("Name of the thread that executes thenRun(): %s%n", Thread.currentThread().getName());
            System.out.println("This is thenRun() that is executed once supplyAsync() method finished");
        });


        supplyAsync.thenRunAsync(() -> {
            System.out.printf("Name of the thread that executes thenRunAsync(): %s%n", Thread.currentThread().getName());
            System.out.println("This is thenRunAsync() that is executed asynchronously once supplyAsync() method finished");
        });

        supplyAsync.thenAccept((result) -> {
            System.out.printf("Name of the thread that executes thenAccept(): %s%n", Thread.currentThread().getName());
            System.out.printf("This is thenAccept() that is executed once supplyAsync() method finished with result: %s%n", result);
        });

        supplyAsync.thenAcceptAsync((result) -> {
            System.out.printf("Name of the thread that executes thenAcceptAsync(): %s%n", Thread.currentThread().getName());
            System.out.printf("This is thenAcceptAsync() that is executed asynchronously once supplyAsync() method finished with result: %s%n", result);
        });

        // Transforming a CompletableFuture with thenApply()
        CompletableFuture<Integer> celciusFuture = CompletableFuture.supplyAsync(() -> 36);
        CompletableFuture<Double> fahrenHeitFuture = celciusFuture.thenApply((celciusResult) -> (celciusResult * 1.8) + 32);
        fahrenHeitFuture.thenAccept((result) -> System.out.printf("Result in fahrenHeit: %s%n", result));

        // Composing two completableFutures with thenCompose()
        // The second operation is executed once the first one is completed
        CompletableFuture.supplyAsync(() -> "Email")
                .thenCompose(firstResult -> CompletableFuture.supplyAsync(() -> "Playlist by %s".formatted(firstResult)))
                .thenAccept(System.out::println);

        // Combining two completableFutures with thenCombine()
        // The first two operations are executed asynchronously and the third one is executed when both are completed
        CompletableFuture<Integer> amount = CompletableFuture.supplyAsync(() -> 15);
        CompletableFuture<Double> rate = CompletableFuture.supplyAsync(() -> 1.7);
        amount.thenCombine(rate, (amountValue, rateValue) -> amountValue * rateValue)
                .thenAccept(result -> System.out.printf("Result of thenCombine(): %s %n", result));

        CompletableFuture<Integer> first = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<Integer> second = CompletableFuture.supplyAsync(() -> 2);
        CompletableFuture<Integer> third = CompletableFuture.supplyAsync(() -> 3);

        // Executing multiple tasks asynchronously using allOf()
        // It waits for all three futures to complete and then runs a callback
        CompletableFuture.allOf(first, second, third)
                .thenRun(() -> {
                    try {
                        System.out.printf("Result of then first future: %s%n", first.get());
                        System.out.printf("Result of the second future: %s%n", second.get());
                        System.out.printf("Result of the third future: %s%n", third.get());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });


        // Executing multiple tasks asynchronously using anyOf()
        // It gets value of the fastest result
        CompletableFuture<Integer> slowResponse = CompletableFuture.supplyAsync(() -> {
            TimeConsumingTask.executeTask();
            return 1;
        });
        CompletableFuture<Integer> fastResponse = CompletableFuture.supplyAsync(() -> 2);

        CompletableFuture.anyOf(slowResponse, fastResponse)
                .thenAccept(result -> System.out.printf("The fastest response is: %s%n", result));


        // Providing a default value with completeOnTimeout() when task is not completed during a given duration
        CompletableFuture<Integer> timedOutTask = CompletableFuture.supplyAsync(() -> {
            TimeConsumingTask.executeTask();
            return 1;
        });
        Integer timedOutResult = timedOutTask.completeOnTimeout(0, 2, TimeUnit.SECONDS)
                .get();

        System.out.printf("Result of timed out task: %s%n", timedOutResult);


        // Handling an exception using handle() method
        CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException("Timed out");
                }).handle((result, exception) -> {
                    if (exception != null) {
                        return "Exception occured: " + exception.getMessage();
                    } else {
                        return result;
                    }
                })
                .thenAccept(System.out::println);


    }
}
