package my.dao;

import my.domain.Traveller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TravellerDaoTest {
    @Autowired
    TravellerDao travellerDao;
    @Test
    public void findTravellerById(){
        List<Traveller> t = travellerDao.findTravellerById("DBF5EDA071C711EA910D000C29C426BD");
        System.out.println(t);
    }
}
