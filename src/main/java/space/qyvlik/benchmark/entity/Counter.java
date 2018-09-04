package space.qyvlik.benchmark.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Counter implements Serializable {
    @Id
    private String id;
    private Long counter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "id='" + id + '\'' +
                ", counter=" + counter +
                '}';
    }
}
