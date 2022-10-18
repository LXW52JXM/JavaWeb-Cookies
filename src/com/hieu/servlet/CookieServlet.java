package com.hieu.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "CookieServlet",urlPatterns = "/CookieServlet")
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //指定输出的编码格式
        resp.setContentType("text/html;charset=utf-8");
        //获取所有Cookies
        Cookie[] cookies=req.getCookies();
        boolean flag=false;
        //cookie判断是否为空
        if (cookies != null&&cookies.length>0) {
            for (Cookie cookie:cookies) {
                //获取cookie的名称
                String name=cookie.getName();
                //判断获取的cookie名称是否是最后时间的名称
                if ("lastTime".equals(name)) {
                    flag=true;
                    String value=cookie.getValue();
                    //utf-8解码前
                    System.out.println("解码之前："+value);
                    //utf-8解码后
                    value= URLDecoder.decode(value,"utf-8");
                    System.out.println("解码之后："+value);
                    resp.getWriter().write("最近访问时间："+value);


                    //重新设置cookie的值
                    Date date=new Date();//获取当前时间
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM" + "月dd日 HH:mm:ss");

                    String time=simpleDateFormat.format(date);
                    //编码前
                    System.out.println("重新赋值编码前："+time);
                    //编码后
                    time= URLEncoder.encode(time,"utf-8");
                    System.out.println("重新赋值编码后："+time);
                    //重新赋值时间,cookie存活时间
                    cookie.setValue(time);
                    cookie.setMaxAge(60*60);
                    //加入
                    resp.addCookie(cookie);
                }
            }
        }
        if (cookies==null|| !flag ||cookies.length==0) {
            Date date=new Date();//获取当前时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM" + "月dd日 HH:mm:ss");

            String time=simpleDateFormat.format(date);
            time=URLEncoder.encode(time,"utf-8");
            Cookie cookie=new Cookie("lastTime",time);
            cookie.setMaxAge(60*60);
            resp.addCookie(cookie);
            resp.getWriter().write("欢迎首次访问！");

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
