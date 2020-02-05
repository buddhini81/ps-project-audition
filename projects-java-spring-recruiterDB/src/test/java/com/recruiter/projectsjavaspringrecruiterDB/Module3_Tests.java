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
import org.springframework.ui.ModelMap;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Module3_Tests {
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
        Method findById = null;
        if (cService != null) {
            Method[] methods = cService.getDeclaredMethods();
                for (Method m : methods) {
                    if (m.getName().equals("findApplicantById") && m.getReturnType().equals(Applicant.class)) {
                        findById = m;
                        break;
                    }
                }

            try {
                findById.invoke(applicantService, 1L);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            boolean findByIdCalled = false;
            try {
                Mockito.verify(applicantRepository).findById(1L);
                findByIdCalled = true;
            } catch (Error e) {
                //e.printStackTrace();
            }

            assertTrue("Step 13: Did not call findById method of applicantRepository inside findApplicantById method of ApplicantService.", findByIdCalled);
        }
    }

    @Test
    public void test_2() {
        Method getById = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("getById")) {
                getById = m;
                break;
            }
        }

        try {
            getById.invoke(applicantController, 1L,modelMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        boolean findApplicantByIdCalled = false;
        try {
            Mockito.verify(service).findApplicantById(1L);
            findApplicantByIdCalled = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 14: Did not call findApplicantById method of applicantService inside getById method of ApplicantController", findApplicantByIdCalled);

        Mockito.when(modelMap.put("applicant", null)).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("applicant", null);
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 14: Did not call ModelMap's put() method with applicant in getById method inside ApplicantController", calledPut);
    }

    @Test
    public void test_3() {
        Method editApplicant = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("editApplicant")) {
                editApplicant = m;
                break;
            }
        }

        try {
            editApplicant.invoke(applicantController,modelMap);
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

        assertTrue("Step 15: Did not call findAllApplicants method of applicantService inside editApplicant method of ApplicantController", findAllApplicantsCalled);

        List<Applicant> a = new ArrayList<Applicant>();
        Mockito.when(modelMap.put("all", a)).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("all", a);
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 15: Did not call ModelMap's put() method with all in editApplicant method inside ApplicantController", calledPut);
    }

    @Test
    public void test_4() {
        Method update = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);
        Applicant a = new Applicant();
        a.setId(1L);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("update")) {
                update = m;
                break;
            }
        }

        try {
            update.invoke(applicantController, a,modelMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        boolean saveCalled = false;
        try {
            Mockito.verify(service).save(a);
            saveCalled = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 16: Did not call save method of applicantService inside update method of ApplicantController", saveCalled);

        Mockito.when(modelMap.put("message", "Applicant with ID " + a.getId() + " updated!")).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("message", "Applicant with ID " + a.getId() + " updated!");
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 16: Did not call ModelMap's put() method with message in update method inside ApplicantController", calledPut);

    }
}
