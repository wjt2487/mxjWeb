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

import cn.mxj.mxjweb.bean.UserRegistTempBean;
import cn.mxj.mxjweb.dao.UserRegistTempDao;
import cn.mxj.net.OperationResult;
import cn.mxj.web.SafeRequestValue;

public class CheckCodeExpired extends Action{

	public static JsonConfig jsonConfig = new JsonConfig();
	private String phone;
	private String msgCheckCode;
	static{
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		MxjOperationResult mor = new MxjOperationResult(false,"");
		
		phone = SafeRequestValue.getSafeRequestStringValue(request, "phone", "");
		msgCheckCode = SafeRequestValue.getSafeRequestStringValue(request, "msgCheckCode", "");
		
		UserRegistTempBean bean = new UserRegistTempBean();
		UserRegistTempDao dao = new UserRegistTempDao();
		bean.setAccount(phone);
		bean.setMsgCheckCode(Integer.parseInt(msgCheckCode));
		OperationResult or = dao.update(bean);
		mor.setSuccessful(or.isSuccessful());
		mor.setMsg("网络异常！");
		response.getWriter().print(JSONObject.fromObject(mor));
		return null;
	}
}
