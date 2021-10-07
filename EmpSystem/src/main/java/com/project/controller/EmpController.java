package com.project.controller;
import com.project.entity.Employee;
import com.project.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EmpController {

    @Autowired
    private EmpService service;

    @GetMapping("/")
    public String home(Model m){

        List<Employee> emp =service.getAllEmp();
        m.addAttribute("emp",emp);

        return "index";
    }

    @GetMapping("/addEmp")
    public String addEmp(){
        return "add_emp";
    }

    @PostMapping("/register")
    public String empRegister(@ModelAttribute Employee e, HttpSession session){
        System.out.println(e);
        service.addEmp(e);
        session.setAttribute("msg","Employee data Added successfully");
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m){
        Employee e =  service.getEmpById(id);
        m.addAttribute("emp",e);
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmp(@PathVariable int id, HttpSession session){
        service.delEmp(id);
        session.setAttribute("msg","Employee data Deleted successfully");
        return "redirect:/";
    }
}

