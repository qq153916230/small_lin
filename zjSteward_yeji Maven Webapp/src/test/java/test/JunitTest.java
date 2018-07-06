package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.app.dao.ScenicDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml","classpath:spring-mybatis.xml"})
public class JunitTest {
	
	@Autowired
	ScenicDao scenicDao;
	
	@Test
	public void testGetName(){
		String picName = this.scenicDao.selectByPrimaryKey(6).getSpicture();	//要删除的景区图片
		picName = picName.substring(picName.lastIndexOf('/'));
		System.out.println(picName);
		System.out.println("20180331100711.jpg".equals(picName));
		
	}

}
