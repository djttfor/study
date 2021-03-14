package my.dao;

import my.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class MemberTest {
    @Autowired
    MemberDao memberDao;
    @Test
    public void findMemberById() throws Exception {
        Member member = memberDao.findMemberById("864F86FE6D9611EA9194000C29C426BD");
        System.out.println(member);
    }
}
