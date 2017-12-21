package lyan.artyom.controller;

import lyan.artyom.controller.dao.DaoService;
import lyan.artyom.controller.dao.MenuItem;
import lyan.artyom.controller.response.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private DaoService daoService;
    private Logger logger = LoggerFactory.getLogger(Controller.class);

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public MenuItem get(@RequestParam String id) {
        return daoService.findOneById(id);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    @ResponseBody
    public SearchResponse name(@RequestParam String name) {
        return daoService.findByName(name);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public boolean delete(@RequestParam String id) {
        return daoService.delete(id);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public String create() {
        return daoService.create();
    }

    @RequestMapping(value = "/update/{id}/{name}/{price}/{quantity}", method = RequestMethod.PUT)
    @ResponseBody
    public String update(@RequestParam String id,
                         @RequestParam String name,
                         @RequestParam Double price,
                         @RequestParam Integer quantity) {
        return daoService.update(id, name, price, quantity);
    }

    @RequestMapping(value = "/price/greater/{price}")
    @ResponseBody
    public SearchResponse findPriceGreater(@RequestParam Double price){
        return daoService.findPriceBy("$gt", price);
    }

    @RequestMapping(value = "/price/lower/{price}")
    @ResponseBody
    public SearchResponse findPriceLower(@RequestParam Double price){
        return daoService.findPriceBy("$lt", price);
    }

    @RequestMapping(value = "/quantity/greater/{q}")
    @ResponseBody
    public SearchResponse findQuantityGreater(@RequestParam Integer q){
        return daoService.findQuantityBy("$gt", q);
    }

    @RequestMapping(value = "/quantity/lower/{q}")
    @ResponseBody
    public SearchResponse findPriceLower(@RequestParam Integer q){
        return daoService.findQuantityBy("$lt", q);
    }
}
