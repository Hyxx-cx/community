package xyz.yuxx.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    //这个DTO中将存放每个页面中所需要承载的元素
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;

    private Integer page;
    private Integer totalPage;
    private List<Integer> pages;


    private List<QuestionDTO> questions;

    public void setPagination(Integer totalCount, Integer page, Integer size) {


        this.page = page;
        if (totalCount % size == 0) {
            this.totalPage = totalCount / size;
        } else {
            this.totalPage = totalCount / size + 1 ;
        }

        if (page > this.totalPage) {
            this.page = this.totalPage;
        }
        if (page < 1) {
            this.page = 1;
        }




        pages = new ArrayList<>();
        pages.add(this.page);//List的add默认是尾部加入
        for (int i=1;i<=3;i++) {
            if (this.page - i > 0) {
                /*pages.add(i-1,page - i);    //我们可以设置指定索引加入*/ //error
                pages.add(0, this.page - i);//我们只需要一直加到零的位置就可以了，会往后推
            }
            if (this.page + i <= this.totalPage) {
                pages.add(this.page + i);
            }
        }


        //如果页面在第一页就不显示 <
        if (this.page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }

        //如果页面在最后一页就不显示 >
        if (this.page == this.totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //如果页面列表包含第一页不显示 <<
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //如果页面列表包含最后一页不显示 >>
        if (pages.contains(this.totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
