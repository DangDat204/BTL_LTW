package com.example.BTL.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    @GetMapping("/index")
    public String index() {
        return "index.html"; // tương ứng với templates/index.html
    }
    // tenant view schedule
    @GetMapping("/view-schedule")
    public String viewSchedule() {
        return "tenant/view-schedule-tenant.html"; // tương ứng với templates/index.html
    }

    // landlord
    
    @GetMapping("/addRoom")
    public String addRoom(){
        return "landlord/addRoom.html";
    }
    @GetMapping("/list-room")
    public String listRoom(){
        return "landlord/list-room.html";
    }
    @GetMapping("/manager-schedule")
    public String managerSchedule(){
        return "landlord/manager-schedule.html";
    }

    // chỉnh sửa thông tin cá nhân
    @GetMapping("/account")
    public String account(){
        return "user/account.html";
    }

    // admin
    @GetMapping("/manager-user")
    public String managerUser(){
        return "admin/manager-user.html";
    }
    @GetMapping("/manager-room")
    public String managerRoom(){
        return "admin/manager-room.html";
    }

    @GetMapping("/tin1")
    public String tin1(){
        return "user/new-1.html";
    }
    @GetMapping("/tin2")
    public String tin2(){
        return "user/new-2.html";
    }
}
