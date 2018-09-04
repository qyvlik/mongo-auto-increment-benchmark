package space.qyvlik.benchmark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import space.qyvlik.benchmark.entity.EntityObject;

@Service
public class InsertEntityService {
    public static String STRING_OF_32_LENGTH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345";

    @Autowired
    private MongoTemplate mongoTemplate;


    public EntityObject createEntityObject(Long id) {
        EntityObject entityObject = new EntityObject();

        entityObject.setId(id);
        entityObject.setField0(STRING_OF_32_LENGTH);
        entityObject.setField1(STRING_OF_32_LENGTH);
        entityObject.setField2(STRING_OF_32_LENGTH);
        entityObject.setField3(STRING_OF_32_LENGTH);
        entityObject.setField4(STRING_OF_32_LENGTH);
        entityObject.setField5(STRING_OF_32_LENGTH);
        entityObject.setField6(STRING_OF_32_LENGTH);
        entityObject.setField7(STRING_OF_32_LENGTH);
        entityObject.setField8(STRING_OF_32_LENGTH);
        entityObject.setField9(STRING_OF_32_LENGTH);

        return entityObject;
    }

    public void insertEntityObject(Long id, String name) {
        EntityObject entityObject = createEntityObject(id);

        mongoTemplate.insert(entityObject, name);
    }

}
