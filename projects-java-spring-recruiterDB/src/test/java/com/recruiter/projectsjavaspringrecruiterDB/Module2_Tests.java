package com.recruiter.projectsjavaspringrecruiterDB;

import com.recruiter.projectsjavaspringrecruiterDB.controller.ApplicantController;
import com.recruiter.projectsjavaspringrecruiterDB.data.Applicant;
import com.recruiter.projectsjavaspringrecruiterDB.data.ApplicantRepository;
import com.recruiter.projectsjavaspringrecruiterDB.service.ApplicantService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Module2_Tests {

    private Class entityClass;
    private Class iRepo;
    private Class cService;
    private Class cController;
    private String basePackage;

    @Mock
    private Applicant applicant;

    @Mock
    ApplicantRepository applicantRepository;

    @Mock
    ApplicantService service;

    @Autowired
    @InjectMocks
    ApplicantService applicantService;

    @Autowired
    @InjectMocks
    private ApplicantController applicantController;

    @BeforeAll
    public void init() {
        MockitoAnnotations.initMocks(this);
        basePackage = getClass().getPackage().getName();

        try {
            entityClass = Class.forName(basePackage + ".data.Applicant");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            cService = Class.forName(basePackage + ".service.ApplicantService");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            cController = Class.forName(basePackage + ".controller.ApplicantController");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_1() {
        Method findAll = null;
        if(cService != null) {
            Method[] methods = cService.getDeclaredMethods();
                for (Method m : methods) {
                    if(m.getName().equals("findAllApplicants") && m.getReturnType().equals(List.class)) {
                        findAll = m;
                        break;
                    }
                }

            try {
               findAll.invoke(applicantService);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            boolean findAllCalled = false;
            try {
                Mockito.verify(applicantRepository).findAll();
                findAllCalled = true;
            } catch (Error e) {
                //e.printStackTrace();
            }

            assertTrue("Step 7: Did not call findAll method of applicantRepository inside findAllApplicants method of ApplicationService.",findAllCalled);
        }
    }

    @Test
    public void test_2() {
        Method getAll = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("getAll")) {
                getAll = m;
                break;
            }
        }

        try {
            getAll.invoke(applicantController, modelMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        boolean findAllApplicantsCalled = false;
        try {
            Mockito.verify(service).findAllApplicants();
            findAllApplicantsCalled = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 8: Did not call findAllApplicants method of applicantService inside getAll method of ApplicantController", findAllApplicantsCalled);

        List<Applicant> a = new ArrayList<Applicant>();
        Mockito.when(modelMap.put("all", a)).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("all", a);
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 8: Did not call ModelMap's put() method with all in getAll method of ApplicantController", calledPut);
    }

    @Test
    public void test_3() {
        Method findApplicantsSorted = null;
        if(cService != null) {
            Method[] methods = cService.getDeclaredMethods();
                for (Method m : methods) {
                    if(m.getName().equals("findApplicantsSortByFName") && m.getReturnType().equals(List.class)) {
                        findApplicantsSorted = m;
                        break;
                    }
                }

            try {
                findApplicantsSorted.invoke(applicantService);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            boolean methodCalled = false;
            try {
                Sort s = Mockito.mock(Sort.class);
                Mockito.verify(applicantRepository).findAll(s.by(Sort.Direction.ASC, "firstName"));
                methodCalled = true;
            } catch (Error e) {
                //e.printStackTrace();
            }

            assertTrue("Step 9: Did not call findAll sorted by firstName method of applicantRepository inside findApplicantsSortByFName method of ApplicantService.",methodCalled);
        }

    }

    @Test
    public void test_4() {
        Method getAllSorted = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("getAllSortByFirstName")) {
                getAllSorted = m;
                break;
            }
        }

        try {
            getAllSorted.invoke(applicantController, modelMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        boolean findApplicantsSortedCalled = false;
        try {
            Mockito.verify(service).findApplicantsSortByFName();
            findApplicantsSortedCalled = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 10: Did not call findApplicantsSortByFName method of applicantService inside getAllSortByFirstName method of ApplicantController", findApplicantsSortedCalled);

        List<Applicant> a = new ArrayList<Applicant>();
        Mockito.when(modelMap.put("byFirstName", a)).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("byFirstName", a);
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 10: Did not call ModelMap's put() method with byFirstName in getAllSortByFirstName method of ApplicantController", calledPut);
    }


    @Test
    public void test_5() {
        Method findApplicantsSorted = null;
        if(cService != null) {
            Method[] methods = cService.getDeclaredMethods();
                for (Method m : methods) {
                    if(m.getName().equals("findApplicantsSortByLName") && m.getReturnType().equals(List.class)) {
                        findApplicantsSorted = m;
                        break;
                    }
                }


            try {
                findApplicantsSorted.invoke(applicantService);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            boolean methodCalled = false;
            try {
                Sort s = Mockito.mock(Sort.class);
                Mockito.verify(applicantRepository).findAll(s.by(Sort.Direction.ASC, "lastName"));
                methodCalled = true;
            } catch (Error e) {
                //e.printStackTrace();
            }

            assertTrue("Step 11: Did not call findAll sorted by lastName method of applicantRepository inside findApplicantsSortByLName method of ApplicantService .",methodCalled);
        }
    }

    @Test
    public void test_6() {
        Method getAllSorted = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("getAllSortByLastName")) {
                getAllSorted = m;
                break;
            }
        }

        try {
            getAllSorted.invoke(applicantController, modelMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        boolean findApplicantsSortedCalled = false;
        try {
            Mockito.verify(service).findApplicantsSortByLName();
            findApplicantsSortedCalled = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 12: Did not call findApplicantsSortByLName method of applicantService inside getAllSortByLastName method of ApplicantController", findApplicantsSortedCalled);

        List<Applicant> a = new ArrayList<Applicant>();
        Mockito.when(modelMap.put("byLastName", a)).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("byLastName", a);
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 12: Did not call ModelMap's put() method with byLastName in getAllSortByLastName method inside ApplicantController", calledPut);
    }
}
