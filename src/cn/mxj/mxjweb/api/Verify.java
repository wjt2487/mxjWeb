package cn.mxj.mxjweb.api;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.mxj.mxjweb.bean.UserBean;
import cn.mxj.mxjweb.dao.UserDao;
import cn.mxj.string.MD5;
import cn.mxj.web.SafeRequestValue;

public class Verify extends Action {
	
	public static UserBean currentUser;
	public static JsonConfig jsonConfig = new JsonConfig();
	static{
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String account = SafeRequestValue.getSafeRequestStringValue(request, "account", "");
		String password = SafeRequestValue.getSafeRequestStringValue(request, "password", "");
		response.setCharacterEncoding("utf-8");
		//返回结果统一类型
		MxjOperationResult mor = new MxjOperationResult(false,"登录失败! 请核对用户名和密码");
		UserDao dao = new UserDao();
		currentUser = dao.getUserByAccount(account);
		if (currentUser == null) {
			mor.setMsg("账号不存在！");
		} else if (currentUser.getIsActive()== 0) {
			mor.setMsg("账号被禁用！");
		} else if (currentUser.getPassword().equals(MD5.encrypt(password))) {
			mor.setSuccessful(true);
			JSONObject jsonArray = JSONObject.fromObject(currentUser,jsonConfig);
			mor.setStrValue(jsonArray.toString());
			mor.setMsg("登录成功！");
		} else {
			mor.setMsg("登录失败! 请核对用户名和密码");
		}
		response.getWriter().print(JSONObject.fromObject(mor));
		return null;
	}
}
