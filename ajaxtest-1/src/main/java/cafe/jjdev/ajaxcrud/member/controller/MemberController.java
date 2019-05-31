package cafe.jjdev.ajaxcrud.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cafe.jjdev.ajaxcrud.member.mapper.MemberMapper;
import cafe.jjdev.ajaxcrud.member.service.MemberService;
import cafe.jjdev.ajaxcrud.member.vo.Member;

@RestController
public class MemberController {
	@Autowired private MemberMapper memberMapper;
	@Autowired private MemberService memberService;
	
	@GetMapping("/getMembers")
	public Map<String, Object> getMembers(@RequestParam(value="currentPage", defaultValue = "1") int currentPage ) //값이 없으면 첫페이지는 1페이지로 고정
	{
		System.out.println("[currentPage MemberController ]"+currentPage);
		System.out.println("/getMembers 요청");
		List<Member> list = memberService.selectGetMember(currentPage); //리턴 데이터타입 List<member>
		System.out.println("[currentPage list ]"+list);
		int lastPage = memberService.selectSumMember();
		HashMap<String, Object> map = new HashMap<String, Object>(); //map을 리턴 
		map.put("list", list); //select한 member 객체주소와 값들
		map.put("currentPage", currentPage); //현제 페이지
		map.put("lastPage", lastPage);//라스트 페이지
		
		return map;
	}
	
	
	@PostMapping("/addMember")
	public String addMember(Member member) {
		System.out.println("/addMember 요청");
		System.out.println("MemberController.addMember member : "+member);
		String result = memberService.cheakAccount(member); //아이디 중복검사
		System.out.println("[result MemberController]"+result); //아이디 중복되면 "아이디가 중복됩니다" , 중복 아닌경우  "가입이 완료되었습니다"
		return result;
		
	}
	
	
	@PostMapping("/removeMember")
	public void removeMember(@RequestParam(value="ck[]") String[] ck) { // List<String> ck
 		System.out.println("/removeMember 요청");
		System.out.println(ck);
		for(String id : ck) {
			Member member = new Member();
			member.setId(id);
			memberMapper.deleteMember(member);
		}
	}
	
	
	
	@PostMapping("/modifyMember")
	public void modifyMember(Member member) {
		System.out.println("/modifyMember 요청");
		System.out.println("MemberController.modifyMember member : "+member);
		memberMapper.updateMember(member);
	}









}
