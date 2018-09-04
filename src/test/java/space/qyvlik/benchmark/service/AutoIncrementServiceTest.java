package space.qyvlik.benchmark.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;
import space.qyvlik.benchmark.entity.Counter;
import space.qyvlik.benchmark.utils.BatchRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoIncrementServiceTest {

    private final String name = "qyvlik.space";

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AutoIncrementService autoIncrementService;

    @Test
    public void autoIncrement() throws Exception {
        Counter counter = autoIncrementService.autoIncrement(name, 1);
        logger.info("autoIncrement:{}", counter);
    }

    @Test
    public void batchAutoIncrement() throws Exception {
        BatchRunner batchRunner = new BatchRunner(8);

        StopWatch stopWatch = new StopWatch("batchAutoIncrement");

        stopWatch.start("submit task");

        final int totalCount = 100000;
        int count = totalCount;
        while (count-- > 0) {
            batchRunner.submit(new Runnable() {
                @Override
                public void run() {
                    Counter counter = autoIncrementService.autoIncrement(name, 1);
                }
            });
        }

        stopWatch.stop();

        stopWatch.start("loop task");

        batchRunner.loop();

        stopWatch.stop();

        Counter counter = autoIncrementService.getCounter(name);

        logger.info("counter:{}, {}", counter, stopWatch.prettyPrint());
    }
}