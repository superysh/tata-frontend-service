package rml.service;

import rml.model.User;

import java.util.List;

public interface UserService {

    void minusOfflineMoney(User user);

    void updateOnlineMoney(User user);

    User checkPasswordAccount(User user);

    User checkPasswordMobile(User user);

    User selectByAccount(String accountName);

    User selectThirdQQ(String qqId);

    User selectThirdWeixin(String weixinId);

    User selectThirdWeibo(String qqId);

    void updateThirdWeixin(User user);

    void updateThirdWeibo(User user);

    void updateThirdQQ(User user);

    void updateMoney(User user);

    void minusOnlineMonet(User user);

    User selectByPrimaryKey(String uuid);

    int insert(User muser);

    User checkPassword(User user);

    User selectByToken(String id);

    User selectByMobile(String mobile);

    int updateToken(User user);

    int updateEmail(User user);

    int updatePassword(User user);

    int updatePayPass(User user);

    List<User> getUsers();

    List<User> getUsersDate(User user);

    int insertThird(User user);

    User getThird(String thirdPartUuid);

    int updateThird(User user);

    void updateOfflineMoney(User user);

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

    User checkPasswordUUID(User user);

    int updateNickName(User user);

    int updateSex(User user);

    int updateDegree(User user);

    int updateBirth(User user);

    int updateUserLevel(User user);

    int updateIcon(User user);

    User getAccount(String token);
}
