package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.UserMapper;
import rml.model.User;
import rml.service.UserService;
import rml.util.MD5;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;
		
	public UserMapper getMuserMapper() {
		return userMapper;
	}

	@Autowired
	public void setMuserMapper(UserMapper muserMapper) {
		this.userMapper = muserMapper;
	}

	@Override
	public int insert(User user) {
		user.setCreateTime(new Date());
		user.setUuid(UUID.randomUUID().toString());
        String rand = UUID.randomUUID().toString().substring(0,9);
        user.setValid(rand);
		user.setPassword(MD5.GetMD5Code(rand+user.getPassword()));
		return userMapper.insert(user);
	}

	@Override
	public User checkPassword(User muser) {
		muser.setPassword(MD5.GetMD5Code(muser.getValid() + muser.getPassword()));
		return userMapper.checkPassword(muser);
	}

	@Override
	public User selectByToken(String token) {
		return userMapper.selectByToken(token);
	}

	@Override
	public int updateToken(User user) {
		user.setUuid(UUID.randomUUID().toString());
		user.setUpdateTime(new Date());
		return userMapper.updateToken(user);
	}

	@Override
	public int updateEmail(User user) {
		return userMapper.updateEmail(user);
	}

	@Override
	public int updatePassword(User user) {
		user.setUpdateTime(new Date());
        user.setPassword(MD5.GetMD5Code(user.getValid() + user.getPassword()));
        return userMapper.updatePassword(user);
	}

	@Override
	public int updatePayPass(User user) {
		user.setPayPassword(MD5.GetMD5Code(user.getPayPassword()));
		return userMapper.updatePayPass(user);
	}

	@Override
	public List<User> getUsers() {
		return userMapper.getUsers();
	}

	@Override
	public List<User> getUsersDate(User user) {
		return userMapper.getUsersDate(user);
	}

	@Override
	public User selectByMobile(String mobile) {
		return userMapper.selectByMobile(mobile);
	}


	@Override
	public void minusOfflineMoney(User user) {
		 userMapper.minusOfflineMoney(user);
	}

	@Override
	public void updateOnlineMoney(User user) {
		userMapper.updateOnlineMoney(user);
	}

	@Override
	public User checkPasswordAccount(User user) {
		user.setPassword(MD5.GetMD5Code(user.getValid() + user.getPassword()));
		return userMapper.checkPasswordAccount(user);
	}

	@Override
	public User checkPasswordMobile(User user) {
		user.setPassword(MD5.GetMD5Code(user.getValid() + user.getPassword()));
		return userMapper.checkPasswordMobile(user);
	}

	@Override
	public User selectByAccount(String accountName) {
		return userMapper.selectByAccount(accountName);
	}

	@Override
	public User selectThirdQQ(String qqId) {
		return userMapper.selectThirdQQ(qqId);
	}

	@Override
	public User selectThirdWeixin(String weixinId) {
		return userMapper.selectThirdWeixin(weixinId);
	}

	@Override
	public User selectThirdWeibo(String weiboId) {
		return userMapper.selectThirdWeibo(weiboId);
	}

	@Override
	public void updateThirdWeixin(User user) {
		userMapper.updateThirdWeixin(user);
	}

	@Override
	public void updateThirdWeibo(User user) {
		userMapper.updateThirdWeibo(user);
	}

	@Override
	public void updateThirdQQ(User user) {
		userMapper.updateThirdQQ(user);
	}

	@Override
	public void updateMoney(User user) {
		userMapper.updateMoney(user);
	}

	@Override
	public void minusOnlineMonet(User user) {
		userMapper.minusOnlineMonet(user);
	}

	@Override
	public User selectByPrimaryKey(String uuid) {
		return userMapper.selectByPrimaryKey(uuid);
	}

	public int insertThird(User user) {
		user.setUuid(UUID.randomUUID().toString());
		return userMapper.insertThird(user);
	}

	@Override
	public User getThird(String thirdPartUuid) {
		return userMapper.getThird(thirdPartUuid);
	}

	@Override
	public int updateThird(User user) {
		return userMapper.updateThird(user);
	}

	@Override
	public void updateOfflineMoney(User user) {
		 userMapper.updateOfflineMoney(user);
	}

	@Override
	public int updateThirdTwo(User user) {
		return userMapper.updateThirdTwo(user);
	}

	@Override
	public User selectByMobile(User user) {
		return userMapper.selectByMobile(user);
	}

	@Override
	public int deleteUser(User user) {
		return userMapper.deleteUser(user);
	}

	@Override
	public int updateMobile(User user) {
		return userMapper.updateMobile(user);
	}

	@Override
	public int updateType(User user) {
		return userMapper.updateType(user);
	}

	@Override
	public int insertOldDate(User user) {
		return userMapper.insertOldDate(user);
	}

	@Override
	public int updateIden(User user) {
		return userMapper.updateIden(user);
	}

	@Override
	public int updateLevel(User user) {
		return userMapper.updateLevel(user);
	}

	@Override
	public List getUsersFunding(User user) {
		return userMapper.getUsersFunding(user);
	}

	@Override
	public List<User> selectByNickPhone(User user) {
		return userMapper.selectByNickPhone(user);
	}

	@Override
	public int updateLevelTwo(User user) {
		return userMapper.updateLevelTwo(user);
	}

	@Override
	public List<User> getSet(String pid) {
		return userMapper.getSet(pid);
	}

	@Override
	public int updatePasswordTwo(User user) {
		user.setPassword(MD5.GetMD5Code(user.getValid()+user.getPassword()));
		return userMapper.updatePasswordTwo(user);
	}

	@Override
	public User checkPasswordUUID(User user) {
		user.setPassword(MD5.GetMD5Code(user.getValid()+user.getPassword()));
		return userMapper.checkPasswordUUID(user);
	}

	@Override
	public int updateNickName(User user) {
		return userMapper.updateNickName(user);
	}

	@Override
	public int updateSex(User user) {
		return userMapper.updateSex(user);
	}

	@Override
	public int updateDegree(User user) {
		return userMapper.updateDegree(user);
	}

	@Override
	public int updateBirth(User user) {
		return userMapper.updateBirth(user);
	}

	@Override
	public int updateUserLevel(User user) {
		return userMapper.updateUserLevel(user);
	}

	@Override
	public int updateIcon(User user) {
		return userMapper.updateIcon(user);
	}

	@Override
	public User getAccount(String token) {
		return userMapper.getAccount(token);
	}

	public static void main(String[]args){
        System.err.println(MD5.GetMD5Code("T4PKeE4san"+"123456"));
    }

}
