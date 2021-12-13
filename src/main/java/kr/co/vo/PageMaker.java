package kr.co.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

// 게시판 페이징 하단 부분
public class PageMaker {

	private int totalCount; // 게시판 글 전체 개수
	private int startPage; // 게시판 화면에 보이는 startPage 번호
	private int endPage; // 게시판 화면에 보이는 endPage 번호
	private boolean prev;
	private boolean next;
	private int displayPageNum = 10; //하단에 필요한 페이지 개수
	
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public boolean isPrev() {
		return prev;
	}
	
	public boolean isNext() {
		return next;
	}
	
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	
	public Criteria getCri() {
		return cri;
	}
	 
	private void calcData() {
		endPage = (int) (Math.ceil(cri.getPage() / (double)displayPageNum) * displayPageNum);
		//        (int)(Math.ceil(현재 페이지번호 / (double)한번에 보여질 페이지 번호개수) * 한번에 보여질 페이지번호 개수)
		startPage = (endPage - displayPageNum) + 1;
		//			(endPage - 한번에 보여질 페이지 번호 개수) + 1
		int tempEndPage = (int) (Math.ceil(totalCount / (double)cri.getPerPageNum()));
		if (endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		prev = startPage == 1 ? false : true;
		//     startPage가 1이면 비활성화, 1이 아니면 활성화
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
		//     (endPage * Criteria의 한페이지 당 게시글 개수) 가 totlaCount보다 크거나 같으면 비활성화, 아니면 활성화
	}
	
	public String makeQuery(int page) {
		UriComponents uriComponents =
		UriComponentsBuilder.newInstance()
						    .queryParam("page", page)
							.queryParam("perPageNum", cri.getPerPageNum())
							.build();
		   
		return uriComponents.toUriString();
	}
	
	public String makeSearch(int page)
	{
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
								    .queryParam("page", page)
									.queryParam("perPageNum", cri.getPerPageNum())
									.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
									.queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword()))
									.build();
		return uriComponents.toUriString();
	}

	private String encoding(String keyword) {
		if(keyword == null || keyword.trim().length() == 0) {
			return "";	
		}
		try {
			return URLEncoder.encode(keyword, "UTF-8");
		}catch(UnsupportedEncodingException e) {
			return "";
		}
		
	}
	
	
	
	
	
	
	
	
	
}