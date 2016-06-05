import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rml.model.Order;
import rml.model.Tt_User;
import rml.model.User;
import rml.service.OrderService;
import rml.service.TtUserService;
import rml.service.UserService;
import rml.service.UserTypeService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class) // = extends SpringJUnit4ClassRunner
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class TestMybatis {


	@Autowired
	private OrderService orderService;


	@Autowired
	TtUserService ttUserService;


	@Autowired
	UserService userService;


	@Test
	public void testInsertUser(){
		List<Tt_User> results = ttUserService.getAll();
		for(Tt_User tt_user:results){
			User user = new User();
			user.setUuid(UUID.randomUUID().toString());
			user.setMobile(tt_user.getMobilePhone());
			user.setLevel(tt_user.getUserType());
			user.setCreateTime(new Date());
			user.setEmail(tt_user.getEmail());
			user.setGroupId(tt_user.getGroupId());
			user.setHomePhone(tt_user.getHomePhone());
			user.setValid(tt_user.getValid());
			user.setStatus(tt_user.getStatus());
			user.setSex(tt_user.getSex());
			user.setIcon(tt_user.getIco());
			user.setNickName(tt_user.getUserName());
			user.setTrueName(tt_user.getTrueName());
			user.setPassword(tt_user.getPassword());
			user.setMsn(tt_user.getMsn());
			user.setQq(tt_user.getQq());
			user.setOfficePhone(tt_user.getOfficePhone());
			user.setUserPayStatus(tt_user.getUserPayStatus());
			user.setThirdPartUuid(tt_user.getWechatOpenid());
			user.setChannel(12);
			user.setAccountTotal(tt_user.getUserMoney());
			userService.insertOldDate(user);
		}
	}


}