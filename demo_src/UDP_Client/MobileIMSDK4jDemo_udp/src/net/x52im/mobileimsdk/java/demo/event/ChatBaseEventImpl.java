/*
 * Copyright (C) 2021  即时通讯网(52im.net) & Jack Jiang.
 * The MobileIMSDK_UDP (MobileIMSDK v6.x UDP版) Project. 
 * All rights reserved.
 * 
 * > Github地址：https://github.com/JackJiang2011/MobileIMSDK
 * > 文档地址：  http://www.52im.net/forum-89-1.html
 * > 技术社区：  http://www.52im.net/
 * > 技术交流群：320837163 (http://www.52im.net/topic-qqgroup.html)
 * > 作者公众号：“即时通讯技术圈】”，欢迎关注！
 * > 联系作者：  http://www.52im.net/thread-2792-1-1.html
 *  
 * "即时通讯网(52im.net) - 即时通讯开发者社区!" 推荐开源工程。
 * 
 * ChatBaseEventImpl.java at 2021-7-7 22:07:09, code by Jack Jiang.
 */
package net.x52im.mobileimsdk.java.demo.event;

import java.util.Observer;

import javax.swing.JOptionPane;

import net.x52im.mobileimsdk.java.demo.MainGUI;
import net.x52im.mobileimsdk.java.event.ChatBaseEvent;
import net.x52im.mobileimsdk.java.utils.Log;
import net.x52im.mobileimsdk.server.protocal.s.PKickoutInfo;

/**
 * 与IM服务器的连接事件在此ChatBaseEvent子类中实现即可。
 * 
 * @author Jack Jiang, 20170501
 * @version.1.1
 */
public class ChatBaseEventImpl implements ChatBaseEvent
{
	private final static String TAG = ChatBaseEventImpl.class.getSimpleName();
	
	private MainGUI mainGUI = null; 
	
	// 本Observer目前仅用于登陆时（因为登陆与收到服务端的登陆验证结果
	// 是异步的，所以有此观察者来完成收到验证后的处理）
	private Observer loginOkForLaunchObserver = null;
	
	/**
	 * 本地用户的登陆结果回调事件通知。
	 * 
	 * @param errorCode 服务端反馈的登录结果：0 表示登陆成功，否则为服务端自定义的出错代码（按照约定通常为>=1025的数）
	 */
	@Override
	public void onLoginResponse(int errorCode)
	{
		if (errorCode == 0) 
		{
			Log.p(TAG, "【DEBUG_UI】IM服务器登录/重连成功！");
			
			// TODO 以下代码仅用于DEMO哦
			if(this.mainGUI != null)
			{
				this.mainGUI.refreshMyid();
				this.mainGUI.showIMInfo_green("IM服务器登录/连接成功,errorCode="+errorCode);
			}
		}
		else 
		{
			Log.e(TAG, "【DEBUG_UI】IM服务器登录/连接失败，错误代码：" + errorCode);

			// TODO 以下代码仅用于DEMO哦
			if(this.mainGUI != null)
			{
				this.mainGUI.refreshMyid();
				this.mainGUI.showIMInfo_red("IM服务器登录/连接失败,code="+errorCode);
			}
		}
		
		// 此观察者只有开启程序首次使用登陆界面时有用
		if(loginOkForLaunchObserver != null)
		{
			loginOkForLaunchObserver.update(null, errorCode);
			loginOkForLaunchObserver = null;
		}
	}

	/**
	 * 与服务端的通信断开的回调事件通知。
	 * <br>
	 * 该消息只有在客户端连接服务器成功之后网络异常中断之时触发。<br>
	 * 导致与与服务端的通信断开的原因有（但不限于）：无线网络信号不稳定、网络切换等。
	 * 
	 * @param errorCode 本回调参数表示表示连接断开的原因，目前错误码没有太多意义，仅作保留字段，目前通常为-1
	 */
	@Override
	public void onLinkClose(int errorCode)
	{
		Log.e(TAG, "【DEBUG_UI】与IM服务器的网络连接出错关闭了，error：" + errorCode);
		
		// TODO 以下代码仅用于DEMO哦
		if(this.mainGUI != null)
		{
			this.mainGUI.refreshMyid();
			this.mainGUI.showIMInfo_red("与IM服务器的连接已断开, 自动登陆/重连将启动! ("+errorCode+")");
		}
	}
	/**
	 * 本的用户被服务端踢出的回调事件通知。
	 *
	 * @param kickoutInfo 被踢信息对象，{@link PKickoutInfo} 对象中的 code字段定义了被踢原因代码
	 */
	@Override
	public void onKickout(PKickoutInfo kickoutInfo)
	{
		Log.e(TAG, "【DEBUG_UI】已收到服务端的\"被踢\"指令，kickoutInfo.code：" + kickoutInfo.getCode());

		String alertContent = "";
		if(kickoutInfo.getCode() == PKickoutInfo.KICKOUT_FOR_DUPLICATE_LOGIN){
			alertContent = "账号已在其它地方登陆，当前会话已断开，请退出后重新登陆！";
		}
		else if(kickoutInfo.getCode() == PKickoutInfo.KICKOUT_FOR_ADMIN){
			alertContent = "已被管理员强行踢出聊天，当前会话已断开！";
		}
		else{
			alertContent = "你已被踢出聊天，当前会话已断开（kickoutReason="+kickoutInfo.getReason()+"）！";
		}

		if(this.mainGUI != null)
			this.mainGUI.showIMInfo_red(alertContent);

		if(mainGUI != null) {
			// 给出被踢信息提示
			JOptionPane.showMessageDialog(mainGUI, alertContent, "你被踢了",JOptionPane.WARNING_MESSAGE);

			// 退出登陆
			mainGUI.doLogout();
			// 退出程序
			mainGUI.doExit();
		}
	}
	
	public void setLoginOkForLaunchObserver(Observer loginOkForLaunchObserver)
	{
		this.loginOkForLaunchObserver = loginOkForLaunchObserver;
	}
	
	public ChatBaseEventImpl setMainGUI(MainGUI mainGUI)
	{
		this.mainGUI = mainGUI;
		return this;
	}

}
