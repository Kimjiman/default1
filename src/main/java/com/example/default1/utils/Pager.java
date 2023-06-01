package com.example.default1.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Pager {
    private Integer page;               // 현재 페이지
    private Integer limit = 10;         // 보여줄 글의 개수
    private Integer offset;             // 시작페이지 (DB)
    private Integer pageSize = 5;       // 페이지 개수
    private Integer totalRow;           // 총 글의 개수
    private Integer totalPage;          // 총 페이지의 개수
    private Integer startPage;          // 시작페이지
    private Integer lastPage;           // 마지막페이지
    private Integer prevPage;           // 이전페이지
    private Integer nextPage;           // 다음페이지


    /**
     * @param totalRow 총 글의 개수
     */
    public void of(Integer totalRow) {
        this.totalRow = totalRow;
        this.totalPage = this.totalRow / this.limit;
        if (this.totalRow % this.limit > 0) {
            this.totalPage = this.totalRow / this.limit + 1;
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
        }

        this.offset = startPage - 1;

        this.lastPage = this.startPage + this.pageSize - 1;
        if(this.lastPage >= this.totalPage) {
            this.lastPage = this.totalPage;
        }

        this.prevPage = (this.page - this.pageSize + 1) - this.page % this.pageSize;
        if(this.page <= this.pageSize) {
            this.prevPage = 1;
        }

        this.nextPage = this.prevPage + this.limit;
        if(this.nextPage >= this.totalPage) {
            this.nextPage = this.totalPage;
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
}
