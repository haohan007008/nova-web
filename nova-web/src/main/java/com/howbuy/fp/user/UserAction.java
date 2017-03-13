package com.howbuy.fp.user;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.howbuy.fp.utils.Constants;
import com.howbuy.fp.utils.RespResult;

/** 
 * @author LvMeng
 * @version 2017年2月8日 上午9:56:09
 */
@Controller
public class UserAction {
	private static Logger log = Logger.getLogger(UserAction.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public  String index(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		RespResult<UserVO> userResp = (RespResult<UserVO>)session.getAttribute("USER_KEY");
		if (userResp != null) {
			request.setAttribute("user", userResp.getObj());
			return "/index";
			
		}else return "/login";
        
	}
	
	@RequestMapping("/login")
	public  String login(HttpServletRequest request){
        return "/login";
	}
	
	@RequestMapping("/welcome")
	public  String welcome(HttpServletRequest request){
		RespResult<UserVO> resp = (RespResult<UserVO>)request.getSession().getAttribute("USER_KEY");
		
		request.setAttribute("user", resp.getObj());
        return "/welcome";
	}
	
	@RequestMapping("/logout")
	public  String logout(HttpServletRequest request){
		request.getSession().removeAttribute("USER_KEY");
        return "/login";
	}
	
	@RequestMapping(value="/getauthcode", produces = "application/json; charset=utf-8")
	@ResponseBody
	public  String getAuthcode( HttpServletRequest request , HttpServletResponse response,HttpSession session){
		RespResult<UserVO> resp = new RespResult<UserVO>();
		String authCode = request.getParameter("authCode");
		
		if(!session.getAttribute("strCode").toString().equals(authCode)){
			resp.setSuccess(false);
			resp.setDesc("验证码错误！");
		}
		
		System.out.println(JSON.toJSONString(resp));
        return JSON.toJSONString(resp);
	}
	
	@RequestMapping(value="/user/dologin", produces = "application/json; charset=utf-8")
	@ResponseBody
	public  String dologin( HttpServletRequest request , HttpServletResponse response,HttpSession session){
		RespResult<UserVO> resp = new RespResult<UserVO>();
		String username = request.getParameter("username");
		String userpwd = request.getParameter("userpwd");
		String authCode = request.getParameter("authCode");
		
		if(session.getAttribute("strCode").toString().equals(authCode)){
			resp = userService.login(username, userpwd);
			if(resp != null && resp.getObj() !=null){
				userService.log(resp.getObj().getUserId(), "login", Constants.getIP(request));
				session.setAttribute("USER_KEY",resp);
			}
		}else {
			resp.setSuccess(false);
			resp.setDesc("验证码错误！");
		}
		
		System.out.println(JSON.toJSONString(resp));
        return JSON.toJSONString(resp);
	}
	
	@RequestMapping({"authcode"})
	public void getAuthCode(HttpServletRequest request, HttpServletResponse response,HttpSession session)
	            throws IOException {
		int width = 63;
        int height = 37;
        Random random = new Random();
        //设置response头信息
        //禁止缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        //生成缓冲区image类
        BufferedImage image = new BufferedImage(width, height, 1);
        //产生image类的Graphics用于绘制操作
        Graphics g = image.getGraphics();
        //Graphics类的样式
        g.setColor(this.getRandColor(200, 250));
        g.setFont(new Font("Times New Roman",0,28));
        g.fillRect(0, 0, width, height);
        //绘制干扰线
        for(int i=0;i<40;i++){
            g.setColor(this.getRandColor(130, 200));
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(12);
            int y1 = random.nextInt(12);
            g.drawLine(x, y, x + x1, y + y1);
        }

        //绘制字符
        String strCode = "";
        for(int i=0;i<4;i++){
            String rand = String.valueOf(random.nextInt(10));
            strCode = strCode + rand;
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand, 13*i+6, 28);
        }
        //将字符保存到session中用于前端的验证
        session.setAttribute("strCode", strCode);
        g.dispose();

        ImageIO.write(image, "JPEG", response.getOutputStream());
        response.getOutputStream().flush();
	 }
	
	 Color getRandColor(int fc,int bc){
	        Random random = new Random();
	        if(fc>255)
	            fc = 255;
	        if(bc>255)
	            bc = 255;
	        int r = fc + random.nextInt(bc - fc);
	        int g = fc + random.nextInt(bc - fc);
	        int b = fc + random.nextInt(bc - fc);
	        return new Color(r,g,b);
	    }
}
