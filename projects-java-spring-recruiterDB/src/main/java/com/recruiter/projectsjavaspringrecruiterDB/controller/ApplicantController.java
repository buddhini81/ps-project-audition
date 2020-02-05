package com.recruiter.projectsjavaspringrecruiterDB.controller;

import com.recruiter.projectsjavaspringrecruiterDB.data.Applicant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ApplicantController {


    @RequestMapping("/")
    public String goToHome(ModelMap modelMap) {
       return "index";
    }

    @RequestMapping("/new")
    public String newApplicant(ModelMap modelMap) {
        modelMap.put("applicant",new Applicant());
        return "create";
    }

    @RequestMapping("/view")
    public String viewApplicant() {
        return "read";
    }

    @RequestMapping("/edit")
    public String editApplicant(ModelMap modelMap) {
        return "update";
    }

    @RequestMapping("/remove")
    public String removeApplicant(ModelMap modelMap) {
        return "delete";
    }

    @RequestMapping("/create")
    public String create(@ModelAttribute Applicant applicant, ModelMap modelMap) {
        return "create";
    }

    @RequestMapping("/update")
    public String update(@ModelAttribute Applicant applicant, ModelMap modelMap) {
        return "update";
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, ModelMap modelMap) {
        return "delete";
    }

    @RequestMapping("/get/{id}")
    public String getById(@PathVariable Long id, ModelMap modelMap) {
       return "update";
    }

    @RequestMapping("/get/all")
    public String getAll(ModelMap modelMap) {
        return "read";
    }

    @RequestMapping("/get/all/sorted/fname")
    public String getAllSortByFirstName(ModelMap modelMap) {
        return "read";
    }

    @RequestMapping("/get/all/sorted/lname")
    public String getAllSortByLastName(ModelMap modelMap) {
        return "read";
    }

}
