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
import cn.mxj.mxjweb.dao.UserDao;
import cn.mxj.net.OperationResult;
import cn.mxj.web.SafeRequestValue;

public class EditPassword extends Action{

	public static JsonConfig jsonConfig = new JsonConfig();
	private String account;
	private String oldPassword;
	private String newPassword;
	static{
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	}
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		account = SafeRequestValue.getSafeRequestStringValue(request, "account", "");
		oldPassword = SafeRequestValue.getSafeRequestStringValue(request, "oldPassword", "");
		newPassword = SafeRequestValue.getSafeRequestStringValue(request, "newPassword", "");
		response.setCharacterEncoding("utf-8");
		//返回结果统一类型
		MxjOperationResult mor = new MxjOperationResult(false,"密码修改失败！");
		
		UserDao dao = new UserDao();
		OperationResult or = dao.updatePassword(account, oldPassword, newPassword);
		
		mor.setSuccessful(or.isSuccessful());
		response.getWriter().print(JSONObject.fromObject(mor));
		return null;
	}
}
