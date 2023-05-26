package com.asia.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.asia.dto.BoardSearchDto;
import com.asia.entity.QVoc;
import com.asia.entity.Voc;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

//사용자 정의 인터페이스 구현
public class VocRepositoryCustomImpl implements VocRepositoryCustom{
	
	private JPAQueryFactory queryFactory; //동적으로 쿼리를 생성하기 위해 JPAQueryFactory 클래스 사용
	
	public VocRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em); //JPAQueryFactory 생성자로 EntityManager 객체 넣어줌
	}
	
	
	private BooleanExpression regDtsAfter(String searchDateType){
		//searchDateType 의 값에 따라서 dateTime의 값을 이전 시간의 값으로 세팅 후 해당 시간 이후로 등록된 글만 조회
		LocalDateTime dateTime = LocalDateTime.now();
		
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
			return null;
		}else if (StringUtils.equals("1d", searchDateType)) {
			dateTime = dateTime.minusDays(1);	
		}else if (StringUtils.equals("1w", searchDateType)) {
			dateTime = dateTime.minusWeeks(1);	
		}else if (StringUtils.equals("1m", searchDateType)) {
			dateTime = dateTime.minusMonths(1);	
		}else if (StringUtils.equals("6m", searchDateType)) {
			dateTime = dateTime.minusMonths(6);	
		}
		return QVoc.voc.regTime.after(dateTime);
		
	}
	
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		//searchBy의 값에 따라서 글제목에 검색어를 포함하고 있는 글 또는 글 생성자 아이디에 검색어를 포함하고 있는 글을 조회하도록 조건값 반환
		if(StringUtils.equals("name", searchBy)) {
			return QVoc.voc.name.like("%" + searchQuery + "%");
		}else if (StringUtils.equals("createdBy", searchBy)) {
			return QVoc.voc.createdBy.like("%" + searchQuery + "%");
		}
			return null;
		
	}
	
	@Override
	public Page<Voc> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable){
		QueryResults<Voc> results = queryFactory // queryFactory 이용해서 쿼리 생성
				.selectFrom(QVoc.voc) //글 데이터 조회하기 위해 QVoc의 voc 지정
				.where(regDtsAfter(boardSearchDto.getSearchDateType()), // BooleanExpression 반환하는 조건문들 넣어줘
						searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
				.orderBy(QVoc.voc.num.desc()) //우리는 num
				.offset(pageable.getOffset()) // 데이터를 가져 올 시작 인덱스를 지정
				.limit(pageable.getPageSize()) // 한번에 가져 올 최대 개수 지정
				.fetchResults(); // 조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환, 글 데이터 리스트 조회 및 글 데이터 전체 개수를 조회하는 2번의 쿼리문 실행
		
		List<Voc> contents = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(contents, pageable, total);//조회한 데이터를 Page 클래스의 구현체인 PageImpl로 반환
						
	}

	
	@Override
	public Page<Voc> getVocLists(Pageable pageable){
		QueryResults<Voc> results = queryFactory // queryFactory 이용해서 쿼리 생성
				.selectFrom(QVoc.voc) //글 데이터 조회하기 위해 QVoc의 voc 지정
				.orderBy(QVoc.voc.originNo.desc(), QVoc.voc.groupOrd.asc()) //우리는 num
				.offset(pageable.getOffset()) // 데이터를 가져 올 시작 인덱스를 지정
				.limit(pageable.getPageSize()) // 한번에 가져 올 최대 개수 지정
				.fetchResults(); // 조회한 리스트 및 전체 개수를 포함하는 QueryResult를 반환, 글 데이터 리스트 조회 및 글 데이터 전체 개수를 조회하는 2번의 쿼리문 실행
		
		List<Voc> contents = results.getResults();
		long total = results.getTotal();
		
		return new PageImpl<>(contents, pageable, total);//조회한 데이터를 Page 클래스의 구현체인 PageImpl로 반환
	
	}

	
	
}
