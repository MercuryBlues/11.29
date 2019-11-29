package com.rq.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rq.bean.Book;
import com.rq.bean.QueryVo;
import com.rq.bean.User;
import com.rq.service.UserDao;
import com.sun.deploy.net.HttpRequest;
import net.sf.json.JSONArray;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.tools.Tool;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserDao userDao;

    @Autowired
    HttpServletRequest request;


    @RequestMapping("/login.action")
    public String Login(User user, Model model) throws Exception {
        HttpSession session = request.getSession();
        User loginUser = userDao.Login(user);
        Integer id = loginUser.getId();
        String power = loginUser.getPower();
        session.setAttribute("id", id);
        session.setAttribute("power", power);
        session.setAttribute("user", loginUser);
        if (loginUser != null) {
            return "frame";
        } else {
            model.addAttribute("mas", "用户名密码错误");
            return "login";
        }
    }

    @RequestMapping("/UserList.action")
    public String UserList(@RequestParam(required = false, defaultValue = "1", value = "page") int page, Model model, User user) throws Exception {
        PageHelper.startPage(page, 1);
        List<User> userList = userDao.getUserlist(user);
        PageInfo pageInfo = new PageInfo(userList);
        model.addAttribute("searchName", user.getName());
        model.addAttribute("pageInfo", pageInfo);
        return "userList";

    }

    @RequestMapping("/getUserByid.action")
    public ModelAndView getUserByid(int id) throws Exception {
        ModelAndView mav = new ModelAndView();
        User user = userDao.getUserByid(id);
        mav.addObject("user", user);
        mav.setViewName("userView");
        return mav;
    }

    @RequestMapping("/getUserByid2.action")
    public ModelAndView getUserByid2(int id) throws Exception {
        ModelAndView mav = new ModelAndView();
        User user = userDao.getUserByid(id);
        mav.addObject("user", user);
        mav.setViewName("userUpdate");
        return mav;
    }

    @RequestMapping("/addPicture.action")
    @ResponseBody
    public Map<String, Object> updateuser(@RequestParam("file") MultipartFile file, Integer id) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String filename = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String path = "D:\\picture";
        filename = filename + "." + extension;
        file.transferTo(new File("D:\\picture\\" + filename));
        User user = new User();
        user.setId(id);
        user.setHeadpath(filename);
        int update = userDao.updateUser(user);
        File dir = new File(path, filename);
        if (dir.exists()) {
            update = userDao.updateUser(user);
        }
        if (dir.exists() && update < 1) {
            dir.delete();
            map.put("msg", "上传失败");
            map.put("code", 1);
        } else if (!dir.exists() && update > 0) {
            user.setHeadpath(null);
            userDao.updateUser(user);
            dir.delete();
            map.put("msg", "上传失败");
            map.put("code", 1);
        } else if (!dir.exists() && update < 1) {
            dir.delete();
            map.put("msg", "上传失败");
            map.put("code", 1);
        } else {
            dir.mkdirs();
            map.put("msg", "上传成功");
            map.put("code", 0);
        }
        return map;
    }

    @RequestMapping("/RegisterUser.action")
    public String RegisterUser(User user) throws Exception {
        userDao.RegisterUser(user);
        return "redirect:/UserList.action";
    }

    @RequestMapping("/deleteUserByid.action")
    @ResponseBody
    public int deleteUserByid(User user) throws Exception {
        int result = userDao.deleteUserByid(user);
        String filename=user.getHeadpath();
        if(filename!=null){
            new File("D:\\picture\\"+filename).delete();//删除原先的图片
        }
        return result;
    }

    @RequestMapping("/UserUpdate.action")
    public String UserUpdate() {
        return "addUser";
    }

    @RequestMapping("/addUser.action")
    @ResponseBody
    public int addUser(User user) {
        int i = userDao.addUser(user);
        return i;
    }


//    @RequestMapping("/deleteUser.action")
//    @ResponseBody
//    public int deleteUser(String user_ids) throws Exception {
//        boolean b =user_ids.endsWith(",");
//        if (b){
//            user_ids = user_ids.substring(0,user_ids.length()-1);
//        }
//
//        String[] ids = user_ids.split(",");
//        int result = 0;
//        for (String id : ids){
//            result = userDao.deleteUserByid(Integer.parseInt(id));
//        }
//        return  result;
//    }


    @RequestMapping("/deleteUser.action")
    @ResponseBody
    public int deleteUser(String user_ids) throws Exception {
        boolean b = user_ids.endsWith(",");
        if (b) {
            user_ids = user_ids.substring(0, user_ids.length() - 1);
        }
        String[] ids = user_ids.split(",");
        QueryVo vo = new QueryVo();
        List<Integer> o = new ArrayList<>();
        for (String id : ids) {
            int i = Integer.parseInt(id);
            o.add(i);
        }
        int result = 0;
        vo.setIds(o);
        result = userDao.deleteUser(vo);
        return result;
    }

    @RequestMapping("/modifyPs.action")
    public String modifyPs(User user) throws Exception {
        Integer id = (Integer) request.getSession().getAttribute("id");
        user.setId(id);
        userDao.modifyPs(user);
        return "userList";
    }

    @RequestMapping("/modifyPs2.action")
    public String modifyPs2(User user) throws Exception {

        userDao.modifyPs(user);
        return "userList";
    }


    @RequestMapping("/outSystem.action")
    public String outSystem() {
        HttpSession session = request.getSession();
        session.invalidate();
        return "login";
    }


    @RequestMapping("/checkName.action")
    @ResponseBody
    public String checkName(String name) {
        int i = userDao.checkName(name);
        return (i > 0) ? "用户名被占用" : "用户名可用";
    }


    @RequestMapping("/search.action")
    @ResponseBody
    public List<User> search(@RequestBody User user) {
        List<User> users = userDao.search(user);
        return users;
    }

    @RequestMapping("/LoginTest.action")
    @ResponseBody
    public Map<String, String> LoginTest(@RequestBody User user) {
        HttpSession session = request.getSession();
        User loginUser = userDao.Login(user);
        Map<String, String> msg = new HashMap<>();
        if (loginUser != null) {
            session.setAttribute("user", loginUser);
            msg.put("msg", "yes");
            return msg;
        } else {
            msg.put("msg", "fail");
            return msg;
        }
    }

    @RequestMapping("/layuiPage.action")
    @ResponseBody
    public Map<String, Object> layuiPage() {

        Map<String, Object> returnTable = new HashMap<>();
        returnTable.put("code", 0);
        returnTable.put("msg", "");
        List<User> users = userDao.layuiList();
        returnTable.put("count", users.size());
        JSONArray data = JSONArray.fromObject(users);
        returnTable.put("data", data);
        return returnTable;
    }

    @RequestMapping("/layuiTablePage.action")
    public String layuiTablePage() {
        return "layuiPage";
    }


    @RequestMapping("/selectlayuiTablePage.action")
    @ResponseBody
    public Map<String, Object> selectlayuiTablePage(int page, int limit, User user) {
        HashMap<String, Object> map = new HashMap<>();
        int pageStart = (page - 1) * limit;
        map.put("pagestart", pageStart);
        map.put("size", limit);
        map.put("name", user.getName());
        List<User> users = userDao.selectpage(map);
        Map<String, Object> returnTable = new HashMap<>();
        returnTable.put("code", 0);
        returnTable.put("msg", "");
        Integer pagecount = userDao.userCount(user);
        returnTable.put("count", pagecount);
        JSONArray data = JSONArray.fromObject(users);
        returnTable.put("data", data);
        return returnTable;
    }


    @RequestMapping("/OpenUpdateUser.action")
    @ResponseBody
    public int OpenUpdateUser(User user) throws Exception {
        return userDao.updateUser(user);
    }


//
//    @RequestMapping("/checkPs.action")
//    public String checkPs(User user){
//
//    }
}
