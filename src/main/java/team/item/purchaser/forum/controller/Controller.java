package team.item.purchaser.forum.controller;

import team.item.purchaser.forum.service.Service;

import java.util.*;

/**
 * Base controller
 * @author Eloy
 */
public interface Controller <T ,ID>{

    /**
     * Service
     */
    public Service<T, ID> service();

    /**
     * Api to insert data.
     * @param entity The data tended to be inserted.
     * @return The inserted data.
     */
    public default Object insert(T entity){
        return service().insert(entity);
    }

    /**
     * Api to delete data.
     * @param ids The identity list of the data to be deleted.
     * @return Deleted rows.
     */
    public default Object delete(List<ID> ids){
        return service().deleteById(ids);
    }

    /**
     * Api to update data.
     * @param entity The data to be updated.
     * @return Updated data.
     */
    public default Object update(T entity){
        return service().update(entity);
    }

    /**
     * Api to obtain data.
     * @param entity The data to be queried and its properties is considered as condition to filter.
     * @return The data list that meets conditions.
     */
    public default Object list(T entity, Integer page, Integer size){
        return service().list(entity, page, size);
    }

    /**
     * Api to count data.
     * @param entity The data to be queried and its properties is considered as condition to filter.
     * @return The data list that meets conditions.
     */
    public default Object count(T entity){
        return service().count(entity);
    }
}
