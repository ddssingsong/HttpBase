package com.dds.net.bean;

import java.io.Serializable;

/**
 * Created by dds on 2016/3/4.
 */

/**
 * ctime : 2016-03-04 08:00 title : 传雅虎CEO自己找好退路 将与核心资产共同离开 description :
 * 传雅虎CEO自己找好退路 将与核心资产共同离开... picUrl :
 * http://img1.gtimg.com/tech/pics/hv1/229/18/2030/132005569.jpg url :
 * http://tech.qq.com/a/20160304/018130.htm
 */
public class NewsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ctime;
	private String title;
	private String description;
	private String picUrl;
	private String url;

	public NewsInfo() {
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCtime() {
		return ctime;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public String toString() {
		return "NewsInfo {ctime=" + ctime + ", title=" + title + ", description=" + description + ", picUrl=" + picUrl
				+ ", url=" + url + "}\n";
	}

}
