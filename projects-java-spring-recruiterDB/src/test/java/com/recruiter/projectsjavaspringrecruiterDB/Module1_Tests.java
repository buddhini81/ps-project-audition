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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Module1_Tests {

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
            cController = Class.forName(basePackage + ".controller.ApplicantController");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}

	@Test
	public void test_1() {
		Annotation entityAnnotation = entityClass.getDeclaredAnnotation(Entity.class);
        boolean entityAnnotationExists = false;

		if(entityAnnotation != null) {
			entityAnnotationExists = true;
		}
		assertTrue("Step 1: @Entity annotation does not exist in Applicant class.",
				entityAnnotationExists);
		try {
			Field idField = entityClass.getDeclaredField("id");
			Annotation[] annotations = idField.getDeclaredAnnotations();
			boolean idAnnotationExists = false;
			boolean genValAnnotationExists = false;
			boolean isGenTypeAuto = false;

			for(Annotation annotation : annotations) {
				if(annotation instanceof Id) {
					idAnnotationExists = true;
				}
				if(annotation instanceof GeneratedValue) {
					genValAnnotationExists = true;
					GeneratedValue gv = (GeneratedValue)annotation;
					if(gv.strategy() == GenerationType.AUTO) {
						isGenTypeAuto = true;
					}
				}


			}

			assertTrue("Step 1: @Id annotation does not exist in the id field.",
					idAnnotationExists);
			assertTrue("Step 1: @GeneratedValue annotation does not exist in the id field.",
					genValAnnotationExists);
			assertTrue("Step 1: GenerationType is not AUTO in the id field.",
					isGenTypeAuto);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_2() {
			boolean extendsJpaRepo = false;
			boolean hasTypeParams = false;

		try {
			iRepo = Class.forName(basePackage + ".data.ApplicantRepository");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Class[] interfaces = iRepo.getInterfaces();
			for(Class i : interfaces) {
				if(i.getName().equals("org.springframework.data.jpa.repository.JpaRepository")) {
					extendsJpaRepo = true;
					break;
				}
			}

			assertTrue("Step 2: ApplicantRepository should extend JpaRepository.",extendsJpaRepo);

			Type[] iTypes = iRepo.getGenericInterfaces();
			String typeName = iTypes[0].getTypeName();
			if(typeName.equals("org.springframework.data.jpa.repository.JpaRepository<com.recruiter.projectsjavaspringrecruiterDB.data.Applicant, java.lang.Long>")) {
				hasTypeParams = true;
			}

			assertTrue("Step 2: JpaRepository should have type parameters <Applicant,Long> .",hasTypeParams);

	}

	@Test
	public void test_3() {
			boolean appRepoFieldExists = false;
			boolean autowiredExists = false;

		try {
			cService = Class.forName(basePackage + ".service.ApplicantService");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Field[] fields = cService.getDeclaredFields();
			if(fields.length == 0) {
				assertFalse("Step 3: ApplicantService does not have a field named applicantRepository of type ApplicantRepository.",true);
			} else if(fields.length > 1) {
				assertFalse("Step 3: ApplicantService has more fields than expected.",true);
			} else {
				Field f = fields[0];
				if(f.getName().equals("applicantRepository") && f.getType().equals(ApplicantRepository.class)) {
					appRepoFieldExists = true;
				}
				Annotation autowiredAnnotation = f.getDeclaredAnnotation(Autowired.class);
				if(autowiredAnnotation != null) {
					autowiredExists = true;
				}
			}

			assertTrue("Step 3: applicantRepository field of type ApplicantRepository does not exist in ApplicantService class.",appRepoFieldExists);
			assertTrue("Step 3: applicantRepository field inside ApplicantService is not annotated with @Autowired.",autowiredExists);
	}

	@Test
	public void test_4() {
		Method save = null;
		if(cService != null) {
			Method[] methods = cService.getDeclaredMethods();

				for (Method m : methods) {
					if(m.getName().equals("save") && m.getReturnType().equals(void.class)) {
                        save = m;
						break;
					}
				}

            try {
                save.invoke(applicantService,applicant);
            } catch (Exception e) {
                //e.printStackTrace();
            }

            boolean saveCalled = false;
            try {
                Mockito.verify(applicantRepository).save(applicant);
                saveCalled = true;
            } catch (Error e) {
                //e.printStackTrace();
            }

            assertTrue("Step 4: Did not call save method of applicantRepository inside save method of ApplicantService.",saveCalled);
		}
	}

    @Test
    public void test_5() {
            Field[] fields = cController.getDeclaredFields();
            boolean appServiceExists = false;
            boolean autowiredExists = false;

            if(fields.length == 0) {
                assertFalse("Step 5: ApplicantController doesn't declare a field named applicantService in it.",true);
            } else if(fields.length > 1) {
                assertFalse("Step 5: ApplicantController has more fields than expected.",true);
            } else {
                Field f = fields[0];
                if(f.getName().equals("applicantService") && f.getType().equals(ApplicantService.class)) {
                    appServiceExists = true;
                }
                Annotation autowiredAnnotation = f.getDeclaredAnnotation(Autowired.class);
                if(autowiredAnnotation != null) {
                    autowiredExists = true;
                }
            }

            assertTrue("Step 5: applicantService field does not exist in ApplicantController class.",appServiceExists);
            assertTrue("Step 5: applicantService field is not annotated with @Autowired.",autowiredExists);
    }

    @Test
    public void test_6() {
	    Method create = null;
	    ModelMap modelMap = Mockito.mock(ModelMap.class);

        Method[] methods = cController.getDeclaredMethods();
        for(Method m : methods) {
            if(m.getName().equals("create")) {
                create = m;
                break;
            }
        }

        try {
            create.invoke(applicantController, applicant, modelMap);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        boolean saveCalled = false;
        try {
			Mockito.verify(service).save(applicant);
            saveCalled = true;
        } catch (Error e) {
            //e.printStackTrace();
        }

        assertTrue("Step 6: Did not call save method of applicantService inside create method of ApplicantController", saveCalled);

		Mockito.when(modelMap.put("message", "Applicant with ID " + applicant.getId() + " saved!")).thenReturn(null);

		boolean calledPut = false;
		try {
			Mockito.verify(modelMap).put("message", "Applicant with ID " + applicant.getId() + " saved!");
			calledPut = true;
		} catch (Error e) {
			//e.printStackTrace();
		}

		assertTrue("Step 6: Did not call ModelMap's put() method with message in create method inside ApplicantController", calledPut);

    }

}
