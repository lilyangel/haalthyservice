package com.haalthy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.haalthy.service.domain.User;
import com.haalthy.service.persistence.UserMapper;
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    static Set Employees;
    @Autowired
    UserMapper userMapper;
    static {
        Employees = new HashSet();
        Employee foobar = null;
        for (int i = 0; i < 10; i++) {
            double sal = new SecureRandom().nextInt(400) * 500;
            foobar = new Employee(i, "Employee " + i, sal);
            Employees.add(foobar);
        }
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Employee getFoobar(@PathVariable int employeeId) {
        Iterator X = Employees.iterator();
        while (X.hasNext()) {
            Employee f = (Employee) X.next();
            if (f.getId() == employeeId) return f;
        }
        return null;
    }

    @RequestMapping(value = "/htmllist", method = RequestMethod.GET, headers = "Accept=text/html", produces = {"text/html"})
    @ResponseBody
    public String getFoobarListHTML() {
        String retVal = "<html><body><table border=1>";
        Iterator X = Employees.iterator();
        while (X.hasNext()) {
            Employee f = (Employee) X.next();
            retVal += "<tr><td>" + f.getId() + "</td><td>" + f.getName() + "</td><td>" + f.getSalary() + "</td></tr>";
        }
        retVal += "</table></body></html>";

        return retVal;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Set getFoobarList() {
//    	 String user = userMapper.getUserByUsername("user1");
//    	 System.out.println(user);
        return Employees;
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public Set getFoobars() {
        return Employees;
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.PUT, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public Employee editFoobar(@RequestBody Employee foobar, @PathVariable int employeeId) {
        Iterator X = Employees.iterator();
        while (X.hasNext()) {
            Employee f = (Employee) X.next();
            if (employeeId == f.getId()) {
                f.setId(foobar.getId());
                f.setName(foobar.getName());
                return f;
            }
        }
        return null;
    }

    @RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE, headers = "Accept=application/json", produces = {"application/json"})
    @ResponseBody
    public boolean deleteFoobar(@PathVariable int employeeId) {
        System.out.println("Delete call.");
        Iterator fooIterator = Employees.iterator();
        while (fooIterator.hasNext()) {
            Employee foobar = (Employee) fooIterator.next();
            System.out.println(foobar);
            if (foobar.getId() == employeeId) {
                fooIterator.remove();
                return true;
            }
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json", produces = {"application/json"}, consumes = {"application/json"})
    @ResponseBody
    public boolean createFoobar(@RequestBody Employee employee) {
        return Employees.add(employee);
    }
}