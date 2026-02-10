package com.example.default1.base.pager;

import com.example.default1.base.model.BaseModel;
import com.example.default1.base.model.BaseObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.checkerframework.checker.units.qual.K;

import java.util.List;

/**
 * packageName    : com.example.default1.base.model
 * fileName       : PageResponse
 * author         : KIM JIMAN
 * date           : 25. 8. 5. 화요일
 * description    :
 * ===========================================================
 * DATE           AUTHOR          NOTE
 * -----------------------------------------------------------
 * 25. 8. 5.     KIM JIMAN      First Commit
 */
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T extends BaseModel<?>> extends BaseObject {
    private PageInfo pageInfo;
    private List<T> list;

    public PageResponse(PageInfo pageInfo, List<T> list) {
        this.pageInfo = pageInfo;
        this.list = list;
        assignRowNumbers();
    }

    private void assignRowNumbers() {
        if (this.list == null || this.list.isEmpty() || this.pageInfo.isEmpty()) {
            return;
        }

        long startRowNum = this.pageInfo.getTotalRow() - ((this.pageInfo.getPage() - 1) * this.pageInfo.getLimit());
        for (int i = 0; i < this.list.size(); i++) {
            this.list.get(i).setRowNum(startRowNum - i);
        }
    }
}
