# Custom Java Thread Pool

A custom implementation of a [Java Thread Pool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html).
Can support multiple clients. The thread pool dynamically adjusts to the size of the job queue. 

We implemented custom thread-safe JobQueue and SocketQueue classes. This project is built around the producer-consumer design pattern along with the acceptor-handler model. 

The `Acceptor` accepts incomming client socket connections, enqueues them into a socket queue. The socket queue has a maximum connections limit.
The `Handler` enqueues new instructions into the job queue from clients.

The `ThreadPool` contains the running threads along with an internal class, `WorkerThread`, that processes the jobs in the job queue. 

The `ThreadManager` adjusts the number of running threads in the thread pool depending on the number of jobs in the job queue, it polls for the size fo the job queue at specified $n * 10$ millisecond intervals.

| (Nj)-Num of Jobs in Q | Nj<=T1(10) | T1(10) < Nj <= T2(20) | T2(20)<Nj<Capacity(50) |
|-----------------------|------------|-----------------------|------------------------|
| (Nt)-Num of Threads   | 5          | 10                    | 20                     |

## Compile
```
javac src/**/*.java -d bin/
```

## Usage

### Server
- In a terminal
```
cd bin
java main.server.MathServer
```

### Client
- In a another terminal
#### GUI
```
cd bin
java main.client.MathClientGUI <cliendId>
```
clientId: ie. "client1"
  
#### CLI test
```
cd bin
java main.client.MathClientCLITest
```
