package com.example.default1.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pager extends BaseModel {
    private final Integer INIT_LIMIT = 10;
    private final Integer INIT_PAGE_SIZE = 5;

    private Integer page;                           // 현재 페이지
    private Integer limit = INIT_LIMIT;             // 보여줄 글의 개수
    private Integer offset;                         // 시작페이지 (DB)
    private Integer pageSize = INIT_PAGE_SIZE;      // 페이지 개수
    private Integer totalRow;                       // 총 글의 개수
    private Integer totalPage;                      // 총 페이지의 개수
    private Integer startPage;                      // 시작페이지
    private Integer lastPage;                       // 마지막페이지
    private Integer prevPage;                       // 이전페이지
    private Integer nextPage;                       // 다음페이지
    private boolean isStartPage = true;             // 시작페이지 체크
    private boolean isLastPage = true;              // 마지막페이지 체크
    private boolean isPrevPage = true;              // 이전페이지 체크
    private boolean isNextPage = true;              // 다음페이지 체크
    private Integer rowNum;                         // 페이지 번호

    /**
     * @param totalRow 총 글의 개수
     */
    public void of(Integer totalRow) {
        this.totalRow = totalRow;
        this.totalPage = this.totalRow / this.limit;
        if (this.totalRow % this.limit > 0) {
            this.totalPage = totalPage + 1;
        }

        if(this.page == null || this.page < 1) {
            this.page = 1;
        }

        if(this.page >= this.totalPage) {
            this.page = this.totalPage;
        }

        this.startPage = (this.page + 1) - this.page % this.pageSize;
        if(this.page <= this.pageSize) {
            this.startPage = 1;
            this.isStartPage = false;
        }

        this.offset = startPage - 1;

        this.lastPage = this.startPage + this.pageSize - 1;
        if(this.lastPage >= this.totalPage) {
            this.lastPage = this.totalPage;
            this.isLastPage = false;
        }

        this.prevPage = (this.page - this.pageSize + 1) - this.page % this.pageSize;
        if(this.page <= this.pageSize) {
            this.prevPage = 1;
            this.isPrevPage = false;
        }

        this.nextPage = this.prevPage + this.limit;
        if(this.nextPage >= this.totalPage) {
            this.nextPage = this.totalPage;
            this.isNextPage = false;
        }
    }

    /**
     * @param page 현재 페이지
     * @param totalRow 총 글의 개수
     */
    public void of(Integer page, Integer totalRow) {
        this.page = page;
        this.of(totalRow);
    }

    /**
     * @param totalRow 총 글의 개수
     * @param limit 글의 개수
     * @param pageSize 페이지 사이즈
     */
    public void of(Integer totalRow, Integer limit, Integer pageSize) {
        this.limit = limit;
        this.pageSize = pageSize;
        this.of(totalRow);
    }

    /**
     * @param page 현재 페이지
     * @param totalRow 총 글의 개수
     * @param limit 글의 개수
     * @param pageSize 페이지 사이즈
     */
    public void of(Integer page, Integer totalRow, Integer limit, Integer pageSize) {
        this.page = page;
        this.limit = limit;
        this.pageSize = pageSize;
        this.of(totalRow);
    }

    /**
     * 글의 번호 매기기
     */
    public <T extends Pager> void assignRowNumbers(List<T> objectList) {
        int startRowNum = this.totalRow - ((this.page - 1) * this.limit);
        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).setRowNum(startRowNum - i);
        }
    }
}
