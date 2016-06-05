package rml.dao;

import rml.model.User;
import rml.model.UserType;
import rml.model.UserTypeDesc;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uuid);

    User selectByToken(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User checkPassword(User user);

    User checkPasswordUUID(User user);

    List<User> getAll();

    User selectByMobile(String mobile);

   User  selectUid(String uid);

    User selecthxUid(String huanxinUid);

    User checkPasswordHuanxin(User user);

    int updateToken(User user);

    int updateEmail(User user);

    List<UserTypeDesc> getUserTypes();

    int insertUserType(UserType userType);

    int updatePassword(User user);

    int updatePayPass(User user);

    List<User> getUsers();

    List<User> getUsersDate(User user);

    int insertThird(User user);

    User getThird(String thirdPartUuid);

    int updateThird(User user);

    int updateThirdTwo(User user);

    User selectByMobile(User user);

    int deleteUser(User user);

    int updateMobile(User user);

    int updateType(User user);

    int insertOldDate(User user);

    int updateIden(User user);

    int updateLevel(User user);

    List getUsersFunding(User user);

    List<User> selectByNickPhone(User user);

    int updateLevelTwo(User user);

    List<User> getSet(String pid);

    int updatePasswordTwo(User user);

    int updateNickName(User user);

    int updateSex(User user);

    int updateDegree(User user);

    int updateBirth(User user);

    int updateUserLevel(User user);

    int updateIcon(User user);

    User getAccount(String token);

    void updateMoney(User user);

    void minusOnlineMonet(User user);

    void updateOfflineMoney(User user);

    void updateThirdWeixin(User user);

    void updateThirdWeibo(User user);

    void updateThirdQQ(User user);

    User selectThirdQQ(String qqId);

    User selectThirdWeixin(String weixinId);

    User selectThirdWeibo(String weiboId);

    User selectByAccount(String  accountName);

    User checkPasswordAccount(User user);

    User checkPasswordMobile(User user);

    void updateOnlineMoney(User user);

    void minusOfflineMoney(User user);
}