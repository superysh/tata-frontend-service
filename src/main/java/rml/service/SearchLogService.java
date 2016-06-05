package rml.service;

import rml.model.SearchLog;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/13.
 */
public interface SearchLogService {
    List<SearchLog> getLogs(SearchLog searchLog);

    void  insertLog(SearchLog searchLog);
}
