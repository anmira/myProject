package kr.co.vo;

import java.util.Date;

public class TripPicVO {

	private int bno;
	private int fileno;
	private String orgfilename;
	private String storedfilename;
	private int filesize;
	private Date regdate;
	private String delgb;
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getFileno() {
		return fileno;
	}
	public void setFileno(int fileno) {
		this.fileno = fileno;
	}
	public String getOrgfilename() {
		return orgfilename;
	}
	public void setOrgfilename(String orgfilename) {
		this.orgfilename = orgfilename;
	}
	public String getStoredfilename() {
		return storedfilename;
	}
	public void setStoredfilename(String storedfilename) {
		this.storedfilename = storedfilename;
	}
	public int getFilesize() {
		return filesize;
	}
	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public String getDelgb() {
		return delgb;
	}
	public void setDelgb(String delgb) {
		this.delgb = delgb;
	}
	@Override
	public String toString() {
		return "TripPicVO [bno=" + bno + ", fileno=" + fileno + ", orgfilename=" + orgfilename + ", storedfilename="
				+ storedfilename + ", filesize=" + filesize + ", regdate=" + regdate + ", delgb=" + delgb + "]";
	}


}
