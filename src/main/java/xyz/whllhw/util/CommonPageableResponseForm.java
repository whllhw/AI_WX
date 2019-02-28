package xyz.whllhw.util;

import org.springframework.data.domain.Page;

import java.util.List;
/**
 * 统一对分页信息的组装类
 * 如无特殊需求，建议使用本类进行组装，配合CommonResponseForm进行返回。
 * @author lhw
 */
public class CommonPageableResponseForm {
    private Long recordQuantity; // 总数
    private List<?> data;        // 列表数据
    
    public CommonPageableResponseForm(Long recordQuantity,List<?> data) {
        this.data = data;
        this.recordQuantity = recordQuantity;
    }

    public CommonPageableResponseForm(Page<?> page) {
        this.data = page.getContent();
        this.recordQuantity = page.getTotalElements();
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Long getRecordQuantity() {
        return recordQuantity;
    }

    public void setRecordQuantity(Long recordQuantity) {
        this.recordQuantity = recordQuantity;
    }
}
