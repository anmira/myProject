package kr.co.vo;

import java.util.Date;

public class MovieVO {

	private int movieNo;
	private String movieName;
	private String movieCont;
	private String movieSite;
	private Date movieBirth;
	private String movieGenre;
	private int movieClick;
	private int movieTime;
	private int movieRate;
	private String movieActor;
	private String movieDirector;
	private String movieGrade;
	private String movieNational;
	
	public int getMovieNo() {
		return movieNo;
	}
	public void setMovieNo(int movieNo) {
		this.movieNo = movieNo;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieCont() {
		return movieCont;
	}
	public void setMovieCont(String movieCont) {
		this.movieCont = movieCont;
	}
	public String getMovieSite() {
		return movieSite;
	}
	public void setMovieSite(String movieSite) {
		this.movieSite = movieSite;
	}
	public Date getMovieBirth() {
		return movieBirth;
	}
	public void setMovieBirth(Date movieBirth) {
		this.movieBirth = movieBirth;
	}
	public String getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	public int getMovieClick() {
		return movieClick;
	}
	public void setMovieClick(int movieClick) {
		this.movieClick = movieClick;
	}
	public int getMovieTime() {
		return movieTime;
	}
	public void setMovieTime(int movieTime) {
		this.movieTime = movieTime;
	}
	public int getMovieRate() {
		return movieRate;
	}
	public void setMovieRate(int movieRate) {
		this.movieRate = movieRate;
	}
	public String getMovieActor() {
		return movieActor;
	}
	public void setMovieActor(String movieActor) {
		this.movieActor = movieActor;
	}
	public String getMovieDirector() {
		return movieDirector;
	}
	public void setMovieDirector(String movieDirector) {
		this.movieDirector = movieDirector;
	}
	public String getMovieGrade() {
		return movieGrade;
	}
	public void setMovieGrade(String movieGrade) {
		this.movieGrade = movieGrade;
	}
	public String getMovieNational() {
		return movieNational;
	}
	public void setMovieNational(String movieNational) {
		this.movieNational = movieNational;
	}
	
	@Override
	public String toString() {
		return "MovieVO [movieNo=" + movieNo + ", movieName=" + movieName + ", movieCont=" + movieCont + ", movieSite="
				+ movieSite + ", movieBirth=" + movieBirth + ", movieGenre=" + movieGenre + ", movieClick=" + movieClick
				+ ", movieTime=" + movieTime + ", movieRate=" + movieRate + ", movieActor=" + movieActor
				+ ", movieDirector=" + movieDirector + ", movieGrade=" + movieGrade + ", movieNational=" + movieNational
				+ "]";
	}
	
}
