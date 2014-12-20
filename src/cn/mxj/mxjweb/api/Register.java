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
import cn.mxj.string.MD5;
import cn.mxj.web.SafeRequestValue;

public class Register extends Action{

	public static JsonConfig jsonConfig = new JsonConfig();
	private String phone;
	private String msgCheckCode;
	private String msg = "";
	private String password;
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
		password = SafeRequestValue.getSafeRequestStringValue(request, "password", "");
		if(getUserRegistTempByCondition()){
			if(register()){
				mor.setSuccessful(true);
			}else{
				mor.setMsg("注册失败，请重新获取验证码注册！");
			}
		}else{
			mor.setMsg(msg);
			
		}
		//返回结果统一类型
		response.getWriter().print(JSONObject.fromObject(mor));
		return null;
	}

	private boolean register(){
		UserDao dao = new UserDao();
		UserBean bean = new UserBean();
		bean.setAccount(phone);
		bean.setPassword(MD5.encrypt(password));
		bean.setSecret(password);
		bean.setStatus(2);
		bean.setIsActive(1);
		bean.setType(2);
		bean.setName(phone);
		bean.setAge(0);
		bean.setSex(0);
		bean.setNativePlace("");
		bean.setBirthday("");
		bean.setIsMarried(0);
		bean.setIsDelete(0);
		bean.setRemark("");
		OperationResult or = dao.add(bean);
		return or.isSuccessful();
		
	}
	/**
	 * @author wlb
	 * @return boolean
	 * @category 验证验证码是否正确
	 */
	private boolean getUserRegistTempByCondition(){
		boolean b = false;
		UserRegistTempDao dao = new UserRegistTempDao();
		UserRegistTempBean condition = new UserRegistTempBean();
		condition.setAccount(phone);
		if(!"".equals(msgCheckCode)){
			condition.setMsgCheckCode(Integer.parseInt(msgCheckCode));
		}else{
			condition.setMsgCheckCode(0);
		}
		condition.setIsdelete(0);
		UserRegistTempBean bean = dao.getUserRegistTempByCondition(condition);
		if(bean != null){
			if(bean.getIsdelete() == 0){
				b = true;
			}else{
				msg = "验证码失效，请重新获取验证码！";
				b = false;
			}
		}else{
			msg = "验证码填写不正确，请重新填写！";
			b = false;
		}
		return b;
	}
}
