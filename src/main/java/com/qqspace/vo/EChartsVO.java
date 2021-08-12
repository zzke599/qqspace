package com.qqspace.vo;

/**
 * @Title
 * @Description
 * @Author zzke
 * @Email 956468746@qq.com
 */
public class EChartsVO implements Comparable {

    private String date;
    private Integer count;

    public EChartsVO() {
    }

    public EChartsVO(String date, Integer count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "date='" + date + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        EChartsVO obj = (EChartsVO)o;
        return this.date.compareTo(obj.getDate());
    }
}
