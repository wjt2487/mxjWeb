package cn.mxj.mxjweb.dao;

import java.util.HashMap;
import java.util.Map;

import cn.mxj.ibatis.IBatisDao;
import cn.mxj.mxjweb.bean.UserBean;
import cn.mxj.mxjweb.utils.SqlConfig;
import cn.mxj.net.OperationResult;
import cn.mxj.string.MD5;

public class UserDao {

	private IBatisDao dao = new IBatisDao(SqlConfig.getSqlMapInstance());
	
	/**
	 * @author wlb
	 * @param account
	 * @return UserInfoBean
	 * 查询
	 */
	public UserBean getUserByAccount(String account){
		UserBean bean = new UserBean();
		bean = (UserBean) dao.getFirstObject("User.getUserByAccount", account);
		return bean;
	}
	/**
	 * @author wlb
	 * @param bean
	 * @return OperationResult
	 * 添加
	 */
	public OperationResult add(UserBean bean){
		return dao.executeInsert("User.add", bean);
	}
	
	public OperationResult updatePassword(String account,String oldPassword,String newPassword){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("account", account);
		map.put("secret", newPassword);
		map.put("oldPassword", MD5.encrypt(oldPassword));
		map.put("newPassword", MD5.encrypt(newPassword));
		return dao.executeUpdate("User.updatePassword", map);
	}
}
