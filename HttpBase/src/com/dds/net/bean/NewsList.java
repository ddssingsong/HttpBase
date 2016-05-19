package com.dds.net.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dds on 2016/3/4.
 */
public class NewsList implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<NewsInfo> newslist;

	public NewsList() {
	}

	public void setNewslist(List<NewsInfo> newslist) {
		this.newslist = newslist;
	}

	public List<NewsInfo> getNewslist() {
		return newslist;
	}

	@Override
	public String toString() {
		return "NewsList [newslist=" + newslist + "]";
	}

}
