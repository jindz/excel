package com.jindz.excel.anno;

@SuppressWarnings("unused")
public class BaseVo {

    private Integer startLine = 2;//小标从0开始，默认排除表头从第二行开始读取
    private Integer endLine;//默认读10行
    public String info_type;

    public String getInfo_type() {
        return info_type;
    }

    public void setInfo_type(String info_type) {
        this.info_type = info_type;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Integer getEndLine() {
        return getStartLine() + 10;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

}
