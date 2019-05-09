package xyz.whllhw.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import xyz.whllhw.domain.DataSet;
import xyz.whllhw.task.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataSetDao {
    private final SqlSession sqlSession;

    public DataSetDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<DataSet> findAllByTaskIdAndState(Long taskId, State state) {
        Map<String, Object> map = new HashMap<>();
        map.put("taskId", taskId);
        map.put("state", state);
        return sqlSession.selectList("findAllByTaskIdAndState", map);
    }

    public List<DataSet> findAllByStateAndLabel(State state, String label) {
        Map<String, Object> map = new HashMap<>();
        map.put("label", label);
        map.put("state", state);
        return sqlSession.selectList("findAllByStateAndLabel", map);
    }

    public List<DataSet> findAllByState(State state) {
        return sqlSession.selectList("findAllByState", state);
    }

    public Integer countAllByState(State state) {
        return sqlSession.selectOne("countAllByState", state);
    }

    public void saveAndFlush(DataSet dataSet) {
        sqlSession.insert("saveAndFlush", dataSet);
    }

    public DataSet getOne(Long id) {
        return sqlSession.selectOne("getOne", id);
    }

    public void delete(Long id) {
        sqlSession.delete("delete", id);
    }
}
