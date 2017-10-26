//package com.act.ucenter.login.controller;
//
//import com.act.ucenter.common.BaseController;
//import com.act.ucenter.common.constant.CommonContant;
//import com.act.ucenter.common.entity.TabSysUserEntity;
//import com.act.ucenter.common.util.ConfigLoadUtil;
//import com.act.ucenter.common.util.IpUtil;
//import com.act.ucenter.common.util.SpringContextUtil;
//import com.act.ucenter.login.service.ILoginService;
//import com.act.ucenter.login.vo.AuthorizationVo;
//import com.act.ucenter.login.vo.LoginVo;
//import javafx.scene.control.Tab;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.codehaus.groovy.util.StringUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.Random;
//
///**
// * LoginController
// * Description:
// * author: Administrator
// * 2017/10/26 0026
// */
//@Controller
//@RequestMapping("/user")
//public class LoginController extends BaseController {
//
//
//    @Autowired
//    ILoginService loginService;
//
//    // 初始化页面
//    @ResponseBody
//    @RequestMapping(value = "/login.do")
//    public void login(HttpServletRequest request, HttpServletResponse response,
//                      LoginVo vo) throws Exception {
//
//        response.setContentType("text/xml;charset=UTF-8");
//        response.setHeader("Cache-Control", "no-cache");
//        PrintWriter printWriter = response.getWriter();
//        String errorVerifyCode = "";
//        String errorPassword = "";
//        String errorUser = "";
//        String errorIp = "";
//        String errorState = "";
//        String ip = IpUtil.getIpAddrByRequest(request); // 非本机登录
//        if (ip.contains(":")) {
//            ip = IpUtil.getRealIp(); // 本机登录
//        }
//        boolean flag = false;
//        boolean loginCode = true;
//        TabSysUserEntity tabSysUser = new TabSysUserEntity;
//        request.getSession().removeAttribute(CommonContant.SESSION_USERINFO);
//
//        String userIdBase64 = vo.getUserId();
//        vo.setUserId(new String(Base64.decodeBase64(userIdBase64.getBytes())));
//
//        ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
//        String isCode = conf.getLoginCode();
//        vo.setLoginIsCode(isCode);
//
//        if ("Y".equalsIgnoreCase(vo.getLoginIsCode())) {
//            if (request.getSession().getAttribute("rand") != null
//                    && !vo.getVerifyId().equalsIgnoreCase(
//                    request.getSession().getAttribute("rand")
//                            .toString())) {
//                errorVerifyCode = "<font color='red'>校验码错误</font>";
//                loginCode = false;
//                request.getSession().removeAttribute("rand");
//            } else {
//                request.getSession().removeAttribute("rand");
//            }
//        }
//        if (null != vo.getUserId() && !"".equals(vo.getUserId().trim())
//                && loginCode) {
//            user = loginService.findByUserId(vo.getUserId().trim());
//            if (null != user) {
//                vo.setPassword(new String(new Base64().decode(vo
//                        .getPassword().getBytes())));
//                if (user.getPassword().equals(
//                        StringUtil.encoderByMd5(vo.getPassword().trim()))) {
//                    request.getSession().setAttribute(
//                            //CommonContant.SESSION_USERINFO, user);
//                     判断用户是否被锁定
//                    if (user.getState()) {
//                        errorState = "<font color='red'>用户["
//                                + user.getUsername()
//                                + "]当前状态已被锁定，请联系管理员!</font>";
//                        flag = false;
//                    } else {
//                        // 更新用户最后登录时间
//                        Integer loginAmount = user.getLoginAmount();
//                        loginService.updateLonginUser(loginAmount + 1,
//                                new Timestamp(System.currentTimeMillis()),
//                                user.getUserId());
//                        initCommon(request, user);
//                        flag = true;
//                    }
//                } else {
//                    errorPassword = "<font color='red'>密码错误</font>";
//                }
//            } else {
//                errorUser = "<font color='red'>帐号错误</font>";
//            }
//        }
//
//        StringBuilder jsonData = new StringBuilder();
//        if (flag) {
//            jsonData.append("{\"flag\":\"yes\",\"errorUser\":\"")
//                    .append(errorUser).append("\",\"errorPassword\":\"")
//                    .append(errorPassword).append("\",\"errorVerifyCode\":\"")
//                    .append(errorVerifyCode).append("\",\"errorIp\":\"")
//                    .append(errorIp).append("\",\"errorState\":\"")
//                    .append(errorState).append("\",\"curUser\":\"")
//                    .append(vo.getUserId().trim()).append("\"}");
//        } else {
//            jsonData.append("{\"flag\":\"no\",\"errorUser\":\"")
//                    .append(errorUser).append("\",\"errorPassword\":\"")
//                    .append(errorPassword).append("\",\"errorVerifyCode\":\"")
//                    .append(errorVerifyCode).append("\",\"errorIp\":\"")
//                    .append(errorIp).append("\",\"errorState\":\"")
//                    .append(errorState).append("\"}");
//        }
//        printWriter.write(jsonData.toString());
//        printWriter.close();
//    }
//
//    // 进入子系统选择界面
////	@RequestMapping(value = "/welcome.do")
////	public String welcome(HttpServletRequest request) throws Exception {
////		ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
////		String tableSchema = conf.getTableSchema();
////		CommonContant.TABLE_SCHEMA = tableSchema;
////
////		String virtualHouseCode = conf.getVirtualHouseCode();
////		CommonContant.VIRTUAL_HOUSE_CODE = virtualHouseCode;
////		List<TabSysManageInfo> list = loginService.findAllSubSystem();
////
////		// 返回到系统菜单页面时，更新当前用户的权限信息
////		String curUserId = (String) request.getSession().getAttribute(
////				CommonContant.USER_ID);
////
////		// FIXME 子系统部署 development boolean isNotAdmin = loginService.getRoleByUserId("admin");
////		boolean isNotAdmin = loginService.getRoleByUserId(curUserId);
////		request.getSession().setAttribute(CommonContant.NOT_ADMIN, isNotAdmin);
////
////		if (isNotAdmin) {
////			String sys = ((TabSysUser) request.getSession().getAttribute(
////					CommonContant.SESSION_USERINFO)).getSysmanage();
////			List<TabSysManageInfo> sysList = new ArrayList<TabSysManageInfo>();
////			if (list != null && list.size() > 0) {
////				if (StringUtil.checkEmpty(sys)) {
////					String[] sysIds = sys.split("\\|");
////					for (int i = 0; i < list.size(); i++) {
////						TabSysManageInfo sysmanageinfo = list.get(i);
////						String syamanageId = sysmanageinfo.getSyamanageId()
////								.toString();
////						for (int j = 0; j < sysIds.length; j++) {
////							if (syamanageId.equals(sysIds[j])) {
////								sysList.add(sysmanageinfo);
////							}
////						}
////					}
////				}
////			}
////			request.setAttribute("list", sysList);
////		} else {
////			request.setAttribute("list", list);
////		}
////		// 平台页面的系统标题从系统常量中获取
////		String title = CommonContant.SYSTEMNAME; // loginService.getTitle();
////		request.setAttribute("title", title);
////		// 传递当前登录的用户id，后续做判断是否清空内存
////		request.setAttribute("curUserId", curUserId);
////		return "/common/common";
////	}
//
//    // 返回验证码
//    @ResponseBody
//    @RequestMapping(value = "/verification.do")
//    public void verification(HttpServletRequest request,
//                             HttpServletResponse response, HttpSession session)
//            throws IOException {
//        // 在内存中创建图象
//        int width = 80;
//        int height = 28;
//        // 生成随机类
//        Random random = new Random();
//        // 设置response头信息 禁止缓存
//        response.setHeader("Pragma", "No-cache");
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expires", 0);
//
//        // 生成缓冲区image类
//        BufferedImage image = new BufferedImage(width, height,
//                BufferedImage.TYPE_INT_RGB);
//        // 获取图形上下文
//        Graphics g = image.getGraphics();
//        // 设定背景色
//        g.setColor(new Color(209, 227, 248));
//        g.fillRect(0, 0, width, height);
//        // 设定字体
//        g.setFont(new Font("Times New Roman", Font.PLAIN, 22));
//        StringBuffer randomCode = new StringBuffer();
//        // 随机产生4数字的验证码。
//        for (int i = 0; i < 4; i++) {
//            // 得到随机产生的验证码数字。
//            String strRand = String.valueOf(StringUtil.codeSequence[random
//                    .nextInt(56)]);
//            g.setColor(new Color(7, 85, 134));
//            // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
//            g.drawString(strRand, 16 * i + 4, 20);
//
//            // 将产生的四个随机数组合在一起。
//            randomCode.append(strRand);
//        }
//        // 将字符保存到session中用于前端的验证
//        session.setAttribute("rand", randomCode);
//        // 图象生效
//        g.dispose();
//        ImageIO.write(image, "JPEG", response.getOutputStream());
//        response.getOutputStream().flush();
//    }
//
//    // 找回密码界面
////	@RequestMapping(value = "/forget.do")
////	public String forget(HttpServletRequest request, LoginVo vo, Model model)
////			throws Exception {
////		if (StringUtil.checkEmpty(vo.getUid())) {
////			vo.setUid(new String(Base64.decodeBase64(vo.getUid().getBytes())));
////		}
////		String message = "";
////		if ("forget".equalsIgnoreCase(vo.getAct())) {
////			if ("4".equals(vo.getStep())) {
////				TabSysUser user = loginService.findByUserId(vo.getUid());
////				if (user != null) {
////					String question = user.getQuestion();// 得到问题
////					String answer = user.getAnswer();// 得到答案
////					request.setAttribute("question", question);
////					if (StringUtil.checkEmpty(vo.getQuestion())
////							&& vo.getQuestion().equals(question)
////							&& StringUtil.checkEmpty(vo.getAnswer())
////							&& vo.getAnswer().equals(answer)) {
////						String encryptPwd = StringUtil
////								.encoderByMd5(new String(Base64.decodeBase64(vo
////										.getNewpwd().getBytes())));
////						loginService.updateUserPwd(encryptPwd, vo.getUid());
////						request.setAttribute("step1", "none");
////						request.setAttribute("step2", "none");
////						vo.setForget("none");
////						message = "4";
////					} else {
////						vo.setStep("2");
////						message = "2";
////						vo.setForget("none");
////						request.setAttribute("step4", "none");
////					}
////				} else {
////					message = "1";
////					vo.setForget("");
////					request.setAttribute("step2", "none");
////					request.setAttribute("step4", "none");
////				}
////			}
////			if ("2".equals(vo.getStep())) {
////				TabSysUser user = loginService.findByUserId(vo.getUid());
////				if (user != null) {
////					String question = user.getQuestion();// 得到问题
////					String answer = user.getAnswer();// 得到答案
////					if (StringUtil.checkEmpty(vo.getQuestion())
////							&& vo.getQuestion().equals(question)
////							&& StringUtil.checkEmpty(vo.getAnswer())
////							&& vo.getAnswer().equals(answer)) {
////						vo.setStep("4");
////						request.setAttribute("step2", "none");
////						vo.setForget("none");
////					} else {
////						vo.setStep("2");
////						message = "2";
////						vo.setForget("none");
////						request.setAttribute("step4", "none");
////					}
////				} else {
////					message = "1";
////					vo.setForget("");
////					request.setAttribute("step2", "none");
////					request.setAttribute("step4", "none");
////				}
////			} else {
////
////				if (StringUtil.checkEmpty(vo.getUid())) {
////					TabSysUser user = loginService.findByUserId(vo.getUid());
////					if (user != null) {
////						vo.setQuestion(user.getQuestion());// 得到问题
////						vo.setStep("2");
////						vo.setForget("none");
////						request.setAttribute("step4", "none");
////					} else {
////						message = "1";
////						vo.setForget("");
////						request.setAttribute("step2", "none");
////						request.setAttribute("step4", "none");
////					}
////				} else {
////					vo.setForget("");
////					request.setAttribute("step2", "none");
////					request.setAttribute("step4", "none");
////				}
////			}
////		} else {
////			vo.setForget("");
////			request.setAttribute("step2", "none");
////			request.setAttribute("step4", "none");
////		}
////		request.setAttribute("vo", vo);
////		request.setAttribute("message", message);
////		return "/common/forget";
////	}
//
//
//
//    // 登入子系统
//    @RequestMapping(value = "/loginSub.do")
//    public String loginSub(HttpServletRequest request) throws Exception {
//        String sv = request.getParameter("sys_id");
//        if (sv == null || "".equals(sv)) {
//            sv = (String) request.getSession().getAttribute("sys_id");
//        }
//        try {
//            // 获得登录用户的id并设值为常量值
//            String userId = ((TabSysUser) request.getSession().getAttribute(
//                    CommonContant.SESSION_USERINFO)).getUserId();
//            CommonContant.CURRENT_USER = userId;
//            request.getSession().setAttribute("sys_id", sv);
//            request.getSession().setAttribute("curUserId", userId);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "/common/index";
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "/loadSub.do")
//    public Object loadSub(HttpServletRequest request) throws Exception {
//        VueLoginVo result = new VueLoginVo();
//        String userId = (String) request.getSession().getAttribute("curUserId");
//        String sysId = (String) request.getSession().getAttribute("sys_id");
//        // FIXME 子系统部署 development  与 tab_sysmanageinfo id 对应 initLoad("admin","16", result);
//        initLoad(userId, sysId, result);
//        return result;
//    }
//
//    // 用户退出
//    @RequestMapping(value = "/userLogout.do")
//    public String userLogout(HttpServletRequest request) {
//        if (request.getSession().getAttribute(CommonContant.SESSION_USERINFO) != null) {
//            request.getSession()
//                    .removeAttribute(CommonContant.SESSION_USERINFO);
//        }
//        request.getSession().invalidate();
//        return "redirect:/index.jsp";
//    }
//
//    /**
//     * @Description: 初始化系统参数
//     * @param userId
//     *            用户Id
//     * @param sysId
//     *            系统Id
//     * @return void 返回类型
//     */
//    private void initLoad(String userId, String sysId, VueLoginVo result) {
//        TabSysUser user = loginService.findByUserId(userId);
//        UnitSystemVersionVo unitSystemVersion = loginService.findVersion();
//        TabSysManageInfo tabSysManageInfo = loginService.findSysInfo(sysId);
//        boolean isNoAdmin = loginService.getRoleByUserId(userId);
//        // 启动缓存 返回一级菜单
//        List<TabMenu> list = loginService
//                .getFirstMenu(userId, sysId, isNoAdmin);
//        // 启动缓存 返回二级菜单列表
//        List<Map<String, Object>> menuList = loginService.getSecMenuList(list,
//                userId, sysId, isNoAdmin);
//        // 启动缓存 返回菜单权限
//        List<TabSecondaryMenu> purviewList = loginService.findAllPurview(
//                userId, isNoAdmin);
//        // 启动缓存 返回按钮权限列表
//        Map<String, List<String>> secMenuPurview = loginService.getPurviewMapList(purviewList);
//
//        List<AuthorizationVo> firstList = new ArrayList<AuthorizationVo>();
//
//        for (Map<String, Object> menus : menuList) {
//            TabMenu first = (TabMenu) menus.get("menu");
//            List<TabMenu> seconds = (List<TabMenu>) menus.get("list");
//            AuthorizationVo firstAuth = new AuthorizationVo("/",
//                    first.getMenuParameter(), first.getMenuNameCh(),
//                    first.getMenuImage(), false);
//            List<AuthorizationVo> secondList = new ArrayList<AuthorizationVo>();
//            for (TabMenu second : seconds) {
//                AuthorizationVo secondAuth = new AuthorizationVo("/"
//                        + second.getMenuUrl(), second.getMenuParameter(),
//                        second.getMenuNameCh(), second.getMenuImage(), false);
//                secondList.add(secondAuth);
//            }
//            firstAuth.setChildren(secondList.toArray());
//            firstList.add(firstAuth);
//        }
//        Object[] authorizations = firstList.toArray();
//        result.setUser(user);
//        result.setAuthorization(authorizations);
//        result.setUnitSystemVersion(unitSystemVersion);
//        result.setTabSysManageInfo(tabSysManageInfo);
//        result.setSecMenuPurview(secMenuPurview);
//    }
//
//    // 登入成功后初始化参数
//    private void initCommon(HttpServletRequest request, TabSysUser user) {
//        boolean isNoAdmin = loginService.getRoleByUserId(user.getUserId());
//        if (isNoAdmin) { // 非管理员
//            // 根据登录用户查询具有权限的serviceCode列表
////			String purService = loginService.findServicePurview(user
////					.getUserId());
////			request.getSession().setAttribute("purService", purService);
////
////			Map<String, String> purCodeMap = loginService
////					.findAllRoomName(purService);
////			request.getSession().setAttribute("purCodeMap", purCodeMap);
//        }
//        request.getSession().setAttribute(CommonContant.NOT_ADMIN, isNoAdmin);
//        request.getSession().setAttribute(CommonContant.USER_ID,
//                user.getUserId());
//        // 哨兵封堵ip地址
//        String shaobincenterip = loginService.getConfigValue("netGuardIp");
//        CommonContant.SHAOBIN_CENTER_IP = shaobincenterip;
//        // 读取配置值并修改常量值
//        //loginService.updateConstant();
//
//        // 读取统计日期配置值并修改常量
//        ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
//        String statisticsDay = conf.getStatisticsDay();
//        CommonContant.DISPLAY_DATE_RANGE = Integer.valueOf(statisticsDay);
//
//        request.getSession().setAttribute(CommonContant.LOCAL_IP,
//                request.getParameter("local_ip"));
//        request.getSession().setAttribute(CommonContant.LOCAL_MAC,
//                request.getParameter("local_mac"));
//        request.getSession().setAttribute(CommonContant.LOCAL_MACHINE,
//                request.getParameter("local_machine"));
//
//    }
//
//
//    // 访问接口，获取返回值
//    private static String requestInterface(String url) {
//        String strJson = "";
//        URL myURL = null;
//        URLConnection httpsConn = null;
//        try {
//            myURL = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        }
//        try {
//            httpsConn = (URLConnection) myURL.openConnection();
//            if (httpsConn != null) {
//                InputStreamReader insr = new InputStreamReader(
//                        httpsConn.getInputStream(), "UTF-8");
//                BufferedReader br = new BufferedReader(insr);
//                String line = null;
//                StringBuilder sb = new StringBuilder();
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//                strJson = sb.toString();
//                insr.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return strJson;
//    }
//
//
//}
//
