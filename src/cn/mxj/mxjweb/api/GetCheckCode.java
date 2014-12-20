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
import cn.mxj.mxjweb.bean.UserRegistTempBean;
import cn.mxj.mxjweb.dao.UserDao;
import cn.mxj.mxjweb.dao.UserRegistTempDao;
import cn.mxj.net.OperationResult;
import cn.mxj.web.SafeRequestValue;

public class GetCheckCode extends Action{

	public static JsonConfig jsonConfig = new JsonConfig();
	private String phone;
	private int msgCheckCode;
	static{
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("utf-8");
		MxjOperationResult mor = new MxjOperationResult(false,"");
		
		phone = SafeRequestValue.getSafeRequestStringValue(request, "phone", "");
		UserDao dao = new UserDao();
		UserBean bean = dao.getUserByAccount(phone);
		if(bean == null){
			if(createCheckCode()){
				mor.setSuccessful(true);
				mor.setStrValue(String.valueOf(msgCheckCode));
			}else{
				mor.setMsg("验证码获取失败，请重新获取！");
			}
			
		}else{
			mor.setMsg("该手机号已被注册！");
		}
		//返回结果统一类型
		response.getWriter().print(JSONObject.fromObject(mor));
		return null;
	}
	/**
	 * @author wlb
	 * 生成验证码
	 */
	private boolean createCheckCode(){
		msgCheckCode = (int)(Math.random()*900000+100000);
		UserRegistTempBean bean = new UserRegistTempBean();
		bean.setAccount(phone);
		bean.setMsgCheckCode(msgCheckCode);
		bean.setRemark("");
		bean.setValidity(60);
		UserRegistTempDao dao = new UserRegistTempDao();
		OperationResult or = dao.add(bean);
		if(or.isSuccessful()){
			//发送短信
		}
		return or.isSuccessful();
	}
}
