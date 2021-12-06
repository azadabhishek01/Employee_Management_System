package com.project.controller;

import com.project.entity.Employee;
import com.project.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EmpController {

    @Autowired
    private EmpService service;

    @GetMapping("/")
    public String home(Model model) {
        return findPaginated(1, "id", "asc", model);
    }

    @GetMapping("/addEmp")
    public String addEmp() {
        return "add_emp";
    }

    @PostMapping("/register")
    public String empRegister(@ModelAttribute Employee e, HttpSession session) {
        System.out.println(e);
        try {
            service.addEmp(e);
            session.setAttribute("msg", "Employee data Added successfully");
        } catch (Exception exception) {
            session.setAttribute("msg", "Please enter valid input");
        }
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m) {
        Employee e = service.getEmpById(id);
        m.addAttribute("emp", e);
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmp(@PathVariable int id, HttpSession session) {
        service.delEmp(id);
        session.setAttribute("msg", "Employee data Deleted successfully");
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam String sortField,
                                @RequestParam String sortDirection,
                                Model model) {

        int pageSize = 5;
        Page < Employee > page = service.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List < Employee > listEmployees = page.getContent();


        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("emp", listEmployees);
        return "index";
    }

}

