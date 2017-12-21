package lyan.artyom.controller.dao;

import com.mongodb.*;
import lyan.artyom.controller.response.SearchResponse;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.util.Set;

@Service
public class DaoService{

    public static final String DBNAME = "restaurant";
    public static final String DBCOLNAME = "items";
    private Mongo connection;
    private DB db;
    private DBCollection itemsCollection;
    private Logger logger = LoggerFactory.getLogger(DaoService.class);

    @PostConstruct
    public void postContruct() throws UnknownHostException {
        connection = new Mongo("localhost", 27017);
        db = connection.getDB(DBNAME);
        checkAndCreate();
        itemsCollection = db.getCollection(DBCOLNAME);
        logger.info(itemsCollection.getName());
    }

    private void checkAndCreate(){
        Set<String> collectionNames = db.getCollectionNames();
        if (!collectionNames.contains(DBCOLNAME)) {
            BasicDBObject dbBasicObject = new BasicDBObject();
            db.createCollection(DBCOLNAME, dbBasicObject);
        }
    }

    public static final BasicDBObject toBasicDBObject(String id){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("_id", new ObjectId(id));
        return basicDBObject;
    }

    public boolean delete(String id) {
        BasicDBObject basicDBObject = toBasicDBObject(id);
        itemsCollection.remove(basicDBObject);
        return false;
    }

    public String create() {
        BasicDBObject basicDBObject = new BasicDBObject();
        itemsCollection.insert(basicDBObject);
        return String.valueOf(basicDBObject.get("_id"));
    }

    public String save(MenuItem item) {
        WriteResult insert = itemsCollection.insert(item.toBasicDbObject());
        logger.info(insert.toString());
        return "";
    }

    public String update(String id, String name, Double price, Integer quantity) {
        BasicDBObject one = (BasicDBObject) itemsCollection.findOne(new ObjectId(id));
        one.put("name", name);
        one.put("price", price);
        one.put("quantity", quantity);
        itemsCollection.save(one);
        return one.get("_id").toString();
    }

    public MenuItem findOneById(String id){
        return toItem((BasicDBObject) itemsCollection.findOne(toBasicDBObject(id)));
    }

    private MenuItem toItem(BasicDBObject basicDBObject) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(basicDBObject.getString("name"));
        menuItem.setPrice(basicDBObject.getDouble("price"));
        menuItem.setQuantity(basicDBObject.getInt("quantity"));
        return menuItem;
    }

    public SearchResponse findByName(String name) {
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("name", name);
        DBCursor cursor = itemsCollection.find(basicDBObject);

        SearchResponse searchResponse = cursor2response(cursor);

        return searchResponse;
    }

    private SearchResponse cursor2response(DBCursor cursor) {
        SearchResponse searchResponse = new SearchResponse();

        while (cursor.hasNext()) {
            BasicDBObject dbObject = (BasicDBObject) cursor.next();
            MenuItem menuItem = toItem(dbObject);
            searchResponse.addItem(menuItem);
        }
        return searchResponse;
    }

    public SearchResponse findPriceBy(String condition, Double price){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("price", new BasicDBObject(condition, price));
        DBCursor cursor = itemsCollection.find(basicDBObject);
        return cursor2response(cursor);
    }

    public SearchResponse findQuantityBy(String condition, Integer quantity){
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("quantity", new BasicDBObject(condition, quantity));
        return cursor2response(itemsCollection.find(basicDBObject));
    }
}
