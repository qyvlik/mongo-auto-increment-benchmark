package space.qyvlik.benchmark.utils;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class BatchRunner {
    private final Executor executor;
    private final AtomicInteger ranSize;
    private final AtomicInteger taskSize;
    private final ConcurrentLinkedQueue<Long> costTimeList;

    public BatchRunner(int threadSize) {
        executor = Executors.newFixedThreadPool(threadSize);
        ranSize = new AtomicInteger(0);
        taskSize = new AtomicInteger(0);
        costTimeList = new ConcurrentLinkedQueue<Long>();
    }

    public Executor getExecutor() {
        return executor;
    }

    public ConcurrentLinkedQueue<Long> getCostTimeList() {
        return costTimeList;
    }

    public void submit(final Runnable runnable) {
        taskSize.incrementAndGet();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                try {
                    runnable.run();
                } catch (Exception e) {
                }
                long end = System.currentTimeMillis();

                long gap = end - start;

                costTimeList.add(gap);

                ranSize.incrementAndGet();
            }
        });
    }

    public void loop() {
        while (ranSize.get() < taskSize.get()) {
            continue;
        }
    }

    public void clean() {
        taskSize.set(0);
        ranSize.set(0);
        costTimeList.clear();
    }
}
