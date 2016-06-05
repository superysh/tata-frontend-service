package rml.service.impl;

import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.SearchLogMapper;
import rml.model.SearchLog;
import rml.service.SearchLogService;

import java.util.List;

/**
 * Created by edward-echo on 2016/4/13.
 */

@Service("searchLogService")
public class SearchLogServiceImpl implements SearchLogService {

    @Autowired
    private SearchLogMapper searchLogMapper;

    @Override
    public List<SearchLog> getLogs(SearchLog searchLog) {
        return searchLogMapper.getLogs(searchLog);
    }

    @Override
    public void insertLog(SearchLog searchLog) {
        searchLogMapper.insertLog(searchLog);
    }
}
