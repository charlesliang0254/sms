package cn.edu.chd.sms;

import cn.edu.chd.sms.mapper.ScoreMapper;
import cn.edu.chd.sms.mapper.UserMapper;
import cn.edu.chd.sms.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("cn.edu.chd.sms.mapper")
public class SmsApplicationTests {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ScoreMapper scoreMapper;

    @Test
    public void contextLoads() {

    }

    /*@Test
    public void m1() {
        User user = userMapper.getUserById(1L);
        System.out.println("user = " + user);
    }

    @Test
    public void m2() {
        Integer row = userService.reg("LJH", "123", "123", 0);
        System.out.println("row = " + row);
    }

    @Test
    public void m3() {
        Score score = new Score();
        score.setStudentId(1L);
        score.setCourseId(1L);
        score.setUsualScore(100.0);
        score.setAssignmentScore(100.0);
        score.setAttendanceScore(100.0);
        score.setExperimentScore(100.0);
        score.setMidtermScore(100.0);
        score.setFinalexamScore(100.0);
        score.setTotalScore(100.0);
        score.setReexamScore(100.0);
        Integer row = scoreMapper.addScore(score);
        System.out.println("row = " + row);
    }

    @Test
    public void m4() {
        Integer row = scoreMapper.getTotalScorePosition(1L, 1L);
        System.out.println("row = " + row);
    }

    @Test
    public void testXML() {
        Map<String, Object> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        Map<String,Object> map1 = new HashMap<>();
        map1.put("four",4);
        map.put("map1",map1);
        try {
            XMLParser.generateXMLFile("c:/Users/lenovo/Desktop/111.xml",map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

*/
}
