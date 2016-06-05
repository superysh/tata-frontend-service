package rml.dao;

import rml.model.Area;
import rml.model.SearchLog;

import java.util.List;

public interface SearchLogMapper {

       List<SearchLog> getLogs(SearchLog searchLog);

       void  insertLog(SearchLog searchLog);

}