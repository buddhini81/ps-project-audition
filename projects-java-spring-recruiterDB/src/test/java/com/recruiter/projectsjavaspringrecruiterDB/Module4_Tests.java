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
public class Module4_Tests {
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
        Method removeApplicant = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("removeApplicant")) {
                removeApplicant = m;
                break;
            }
        }

        try {
            removeApplicant.invoke(applicantController,modelMap);
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

        assertTrue("Step 17: Did not call findAllApplicants method of applicantService inside removeApplicant method of ApplicantController", findAllApplicantsCalled);

        List<Applicant> a = new ArrayList<Applicant>();
        Mockito.when(modelMap.put("all", a)).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("all", a);
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 17: Did not call ModelMap's put() method with all in removeApplicant method inside ApplicantController", calledPut);
    }


    @Test
    public void test_2() {
        Method delete = null;
        if (cService != null) {
            Method[] methods = cService.getDeclaredMethods();


                for (Method m : methods) {
                    if (m.getName().equals("delete") && m.getReturnType().equals(void.class)) {
                        delete = m;
                        break;
                    }
                }

            try {
                delete.invoke(applicantService, 1L);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            boolean deleteByIdCalled = false;
            try {
                Mockito.verify(applicantRepository).deleteById(1L);
                deleteByIdCalled = true;
            } catch (Error e) {
                //e.printStackTrace();
            }

            assertTrue("Step 18: Did not call deleteById method of applicantRepository inside delete method of ApplicantService.", deleteByIdCalled);
        }
    }

    @Test
    public void test_3() {
        Method delete = null;
        ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("delete")) {
                delete = m;
                break;
            }
        }

        try {
            delete.invoke(applicantController, 1L,modelMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        boolean deleteCalled = false;
        try {
            Mockito.verify(service).delete(1L);
            deleteCalled = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 18: Did not call delete method of applicantService inside delete method of ApplicantController", deleteCalled);

        Mockito.when(modelMap.put("message", "Applicant with ID 1 deleted!")).thenReturn(null);

        boolean calledPut = false;
        try {
            Mockito.verify(modelMap).put("message", "Applicant with ID 1 deleted!");
            calledPut = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 18: Did not call ModelMap's put() method with message in delete method inside ApplicantController", calledPut);

    }
}
