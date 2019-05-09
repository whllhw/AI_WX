package xyz.whllhw.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import xyz.whllhw.domain.DataSet;
import xyz.whllhw.task.State;

@RunWith(SpringRunner.class)
@MybatisTest
@Import(DataSetDao.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataSetDaoTest {

    @Autowired
    private DataSetDao dataSetDao;

    @Test
    public void saveAndFlush() {
        DataSet dataSet = new DataSet();
        dataSet.setState(State.FINISHED);
        dataSet.setFileName("test_filename");
        dataSet.setLabel("test_Label");
        dataSet.setTaskId(1L);
        dataSet.setUserId("test________");
        dataSet.setType("test_type");
        dataSet.setId(99L);
        dataSetDao.saveAndFlush(dataSet);

        dataSet = dataSetDao.getOne(1L);
        System.out.println(dataSet);

        dataSet = dataSetDao.getOne(99L);
        System.out.println(dataSet);

        Assert.isTrue(dataSet.getFileName().equals("test_filename"));
        Assert.isTrue(dataSet.getState().equals(State.FINISHED));

        dataSetDao.delete(99L);
        Assert.isNull(dataSetDao.getOne(99L));
    }

    @Test
    public void findAllByTaskIdAndState() {
        for (DataSet i : dataSetDao.findAllByTaskIdAndState(1L, State.FINISHED)) {
            System.out.println(i);
        }
    }

    @Test
    public void findAllByState() {
        for (DataSet i : dataSetDao.findAllByState(State.FINISHED))
            System.out.println(i);
    }

    @Test
    public void countAllByState() {
        System.out.println(dataSetDao.findAllByState(State.FINISHED));
    }

}