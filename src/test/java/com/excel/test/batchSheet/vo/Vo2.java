package com.excel.test.batchSheet.vo;

import com.jindz.excel.anno.Excel;
import com.jindz.excel.model.BaseVo;

public class Vo2 extends BaseVo{
	
	@Override
	public Integer getStartRow() {
		// TODO Auto-generated method stub
		return 6;
	}
	
	@Excel(index = 0, title = "a")
	private String a;
	@Excel(index = 1, title = "b")
	private String b;
	@Excel(index = 2, title = "c")
	private String c;
	@Excel(index = 3, title = "d")
	private String d;
	@Excel(index = 4, title = "e")
	private String e;
	@Excel(index = 5, title = "f")
	private String f;
	@Excel(index = 6, title = "g")
	private String g;

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getE() {
		return e;
	}

	public void setE(String e) {
		this.e = e;
	}

	public String getF() {
		return f;
	}

	public void setF(String f) {
		this.f = f;
	}

	public String getG() {
		return g;
	}

	public void setG(String g) {
		this.g = g;
	}

}
