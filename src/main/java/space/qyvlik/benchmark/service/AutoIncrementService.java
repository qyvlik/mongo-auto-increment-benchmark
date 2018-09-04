package space.qyvlik.benchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import space.qyvlik.benchmark.entity.Counter;

@Service
public class AutoIncrementService {
    public final static String COUNTER = "Counter";

    @Autowired
    private MongoTemplate mongoTemplate;

    public Counter autoIncrement(String name, int increment) {
        if (increment < 0) {
            throw new RuntimeException("autoIncrement increment param must bigger than zero");
        }
        return mongoTemplate.findAndModify(
                Query.query(Criteria.where("_id").is(name)),
                new Update().inc("counter", increment),
                FindAndModifyOptions.options().returnNew(true).upsert(true),
                Counter.class,
                COUNTER);
    }

    public Counter getCounter(String name) {
        Query query = Query.query(Criteria.where("_id").is(name));
        return mongoTemplate.findOne(query, Counter.class, COUNTER);
    }
}
