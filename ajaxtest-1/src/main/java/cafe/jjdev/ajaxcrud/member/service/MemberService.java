package cafe.jjdev.ajaxcrud.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cafe.jjdev.ajaxcrud.member.mapper.MemberMapper;
import cafe.jjdev.ajaxcrud.member.vo.Member;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;

	// 페이징 작업
	public List<Member> selectGetMember(int currentPage){
	
	//페이지
	int beginPage = currentPage-0;
	//페이지당 갯수
	final int rowPerPage = 10;
	Map<String , Integer> map = new HashMap<String , Integer>();	
	map.put("beginPage", beginPage);
	map.put("rowPerPage", rowPerPage);
	
	return memberMapper.selectMemberList(map);
	
	}

	
	public int selectSumMember() {
		//Member테이블 데이터 총 갯수
		int sum = memberMapper.sumMember();
		System.out.println("[MemberService sum]"+sum);
		final int rowPerPage = 10;
		
		//딱 나누어 떨어지면 다음페이지 필요x
		if(sum%rowPerPage==0){
		int lastPage = sum/rowPerPage;	
		System.out.println("[MemberService lastPage]"+lastPage);
		return lastPage;
		}
		
		//총 데이터 갯수가 페이지당 데이터나눈 나머지가 0이 아니면 뒷페이지 필요+1
		else{
		int lastPage = (sum/rowPerPage)+1;
		System.out.println("[MemberService lastPage]"+lastPage);
		return lastPage;
		}
	
	

	}

	public String cheakAccount(Member member){
		String memberId = member.getId(); //comment객체로 member데이터 입력된 값 가져오기
		List<Member> list = memberMapper.cheakId(); //아이디값 출력
		for(int i=0;i<list.size();i++){ //아이디 중복 검사
			String memberId2 = list.get(i).getId();
			if(memberId.equals(memberId2)){
			return "아이디가 중복됩니다";
			}
		}
		
		memberMapper.insertMember(member);
		
		return "가입이 완료되었습니다";
	}








}
