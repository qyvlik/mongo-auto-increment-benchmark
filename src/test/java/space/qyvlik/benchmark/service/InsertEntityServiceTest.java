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
public class InsertEntityServiceTest {
    private final String name = "qyvlik.space";

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AutoIncrementService autoIncrementService;
    @Autowired
    private InsertEntityService insertEntityService;

    @Test
    public void batchInsertSingle() throws Exception {
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
                    insertEntityService.insertEntityObject(counter.getCounter(), name);
                }
            });
        }

        stopWatch.stop();

        stopWatch.start("loop task");

        batchRunner.loop();

        stopWatch.stop();

        logger.info("batchInsertSingle:{}", stopWatch.prettyPrint());
    }
}