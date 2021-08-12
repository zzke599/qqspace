package com.qqspace.tool;

import java.util.List;
/**
 * @author zzke
 * @ClassName
 * @Description 分页工具类
 */
public class PageSupport<T> {
	
	
	
	//当前页码
	private int currentPageNo = 1;
	
	//总记录数（表）
	private int totalCount = 0;
	
	//每页容量
	private int pageSize = 6;
	
	//总页数-totalCount/pageSize（+1）
	private int totalPageCount = 1;
	//每页显示数据的集合
	private List<T> list;
	

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		if(currentPageNo > 0){
			this.currentPageNo = currentPageNo;
		}
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if(totalCount > 0){
			this.totalCount = totalCount;
			//设置总页数
			this.setTotalPageCountByRs();
		}
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(pageSize > 0){
			this.pageSize = pageSize;
		}
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
	public void setTotalPageCountByRs(){
		if(this.totalCount % this.pageSize == 0){
			this.totalPageCount = this.totalCount / this.pageSize;
		}else if(this.totalCount % this.pageSize > 0){
			this.totalPageCount = this.totalCount / this.pageSize + 1;
		}else{
			this.totalPageCount = 0;
		}
	}
	
	
}