# Custom Java Thread Pool

A custom implementation of a [Java Thread Pool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html).

## Requirements

### `MyThreadPool.java`
- Manages thread

| (Nj)-Num of Jobs in Q | Nj<=T1(10) | T1(10) < Nj <= T2(20) | T2(20)<Nj<Capacity(50) |
|-----------------------|------------|-----------------------|------------------------|
| (Nt)-Num of Threads   | 5          | 10                    | 20                     |

### `MyJob.java`
### `ThreadManager.java`
### `MyMonitor.java`
### `CapitalizeServer.java`
### `InstructionSet.java`
### `MyUtility.java`
