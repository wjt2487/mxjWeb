package cn.mxj.mxjweb.dao;

import cn.mxj.ibatis.IBatisDao;
import cn.mxj.mxjweb.bean.UserRegistTempBean;
import cn.mxj.mxjweb.utils.SqlConfig;
import cn.mxj.net.OperationResult;

public class UserRegistTempDao {

	private IBatisDao dao = new IBatisDao(SqlConfig.getSqlMapInstance());
	
	/**
	 * @author wlb
	 * @param bean
	 * @return boolean
	 * 插入
	 */
	public OperationResult add(UserRegistTempBean bean){
		return dao.executeInsert("UserRegistTemp.add", bean);
	}
	/**
	 * @author wlb
	 * @param UserRegistTempBean
	 * @return UserRegistTempBean
	 * 查询
	 */
	public UserRegistTempBean getUserRegistTempByCondition(UserRegistTempBean condition){
		return (UserRegistTempBean) dao.getFirstObject("UserRegistTemp.getUserRegistTempByCondition", condition);
	}
	
	/**
	 * @author wlb
	 * @param bean
	 * @return boolean
	 * 修改
	 */
	public OperationResult update(UserRegistTempBean bean){
		return dao.executeInsert("UserRegistTemp.update", bean);
	}
}
