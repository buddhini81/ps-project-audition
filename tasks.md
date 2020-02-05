About the project
-----------------
By following this project you will learn how to manage CRUD operations of a Recruiter Database using Spring Data JPA.
This application allows to create new applicants, find all applicants sorted by first and last names, find and edit applicant details as well as to remove applicants from the Recruiter Database.

How to run the tests
--------------------
There are 19 tests in total for the four modules. T0 run the tests, right click on the test package and run all tests or run module tests individually.
Initially, all 19 test will fail. After the completion of each module, all tests of that module should pass.


Module 1
--------

Task 1 - Go to the Applicant class inside data package and annotate the class with @Entity annotation.
To the 'id' property, add @Id and @GeneratedValue(strategy= GenerationType.AUTO) annotations.

Note: Module1_Tests -> test_1 should pass after this step.

Task 2 - Go to ApplicantRepository interface inside data package and extend it by JpaRepository<Applicant,Long>.
Make sure you remove the dummy method declarations inside ApplicantRepository interface.

Note: Module1_Tests -> test_2 should pass after this step.

Task 3 - Go to ApplicantService class inside service package and add field applicantRepository of type ApplicantRepository.
Annotate the field with @Autowired annotation.

Note: Module1_Tests -> test_3 should pass after this step.


Task 4 - Inside the save method of ApplicantService class, add the below line of code.

		 applicantRepository.save(applicant);
		 
Note: Module1_Tests -> test_4 should pass after this step.

Task 5 - Go to ApplicantController class inside controller package and add field applicantService of type ApplicantService.
Annotate the field with @Autowired annotation.

Note: Module1_Tests -> test_5 should pass after this step.

Task 6 - Go to the create method of ApplicantController and add the following three lines at the top inside the method.

		applicantService.save(applicant);
        String message = "Applicant with ID " + applicant.getId() + " saved!";
        modelMap.put("message",message);
		
Note: Module1_Tests -> test_6 should pass after this step.
At this point, all Module1_Tests should pass.

Now right click on RecruiterDbApplication class to run the application. Open a web browser and navigate to localhost:8080.
In the main menu, click on Create. You should be able to enter details and save, to create a new applicant. 

Module 2
--------

Task 1 - Inside the findAllApplicants method of ApplicantService class, add the below line of code.

		 return applicantRepository.findAll();
		 
Note: Module2_Tests -> test_1 should pass after this step.

Task 2 - Go to ApplicantController and in to the method getAll. Add the following lines of code at the top inside the method.

	    List<Applicant> applicants = applicantService.findAllApplicants();
        modelMap.put("all",applicants);
		
Note: Module2_Tests -> test_2 should pass after this step.

Now right click on RecruiterDbApplication to run the application. Navigate to localhost:8080 in your browser.
In the main menu, click on Read. In the next page that opens, click on All Applicants. You should see all the applicants in the database. 

Task 3 - Inside the findApplicantsSortByFName method of ApplicantService class, add the below line of code.
		 return applicantRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
		 
(Be sure to add the required imports.)

Note: Module2_Tests -> test_3 should pass after this step.

Task 4 - Go to ApplicantController and in to the method getAllSortByFirstName method. Add the following lines of code at the top inside the method.

			List<Applicant> applicants = applicantService.findApplicantsSortByFName();
			modelMap.put("byFirstName",applicants);
			
Note: Module2_Tests -> test_4 should pass after this step.

Now right click on RecruiterDbApplication class to run the application. Navigate to localhost:8080 in your browser.
In the main menu, click on Read. In the next page that opens, click on Sorted By First Name. You should see all the applicants in the database sorted by First Name. 

Task 5 - Inside the findApplicantsSortByLName method of ApplicantService class, add the below line of code.

		 return applicantRepository.findAll(Sort.by(Sort.Direction.ASC, "lastName"));
		 

Note: Module2_Tests -> test_5 should pass after this step.

Task 6 - Go to ApplicantController and in to the method getAllSortByLastName. Add the following lines of code at the top inside the method.

		 List<Applicant> applicants = applicantService.findApplicantsSortByLName();
		 modelMap.put("byLastName",applicants);
		 
Note: Module2_Tests -> test_6 should pass after this step.
At this point, all Module2_Tests should pass.

Now right click on RecruiterDbApplication class to run the application. Navigate to localhost:8080 in your browser.
In the main menu, click on Read. In the next page that opens, click on Sorted By Last Name. You should see all the applicants in the database sorted by Last Name. 

Module 3
--------

Task 1 - Inside the findApplicantById method of ApplicantService class, add the below line of code.

		 return applicantRepository.findById(id).orElse(null);
		 
Note: Module3_Tests -> test_1 should pass after this step.

Task 2 - Go to ApplicantController and in to the method getById. Add the following lines of code at the top inside the method.

		 Applicant applicant = applicantService.findApplicantById(id);
         modelMap.put("applicant",applicant);

Note: Module3_Tests -> test_2 should pass after this step.

Task 3 - In ApplicantController, go to editApplicant method and add the below lines of code at the top inside the method.

		 List<Applicant> applicants = applicantService.findAllApplicants();
		 modelMap.put("all",applicants);
		 
Note: Module3_Tests -> test_3 should pass after this step.

Now when you run the application and navigate to localhost:8080 in your browser and go to Update in the main menu,
you should see the list of existing applicant IDs listed to be selected for updating. When you select an Applicant ID from the drop down box,
it should take you to a page populated with the applicant's data. Nothing will happen at this stage since we have not yet
implemented the update logic.

Task 4 - Go to ApplicantController and in to the method update. Add the following lines of code at the top inside the method.

		applicantService.save(applicant);
        String message = "Applicant with ID " + applicant.getId() + " updated!";
        modelMap.put("message",message);
		
Note: Module3_Tests -> test_4 should pass after this step.
At this point, all Module3_Tests should pass.
 
Now run the application and navigate to localhost:8080 in your browser and go to Update in the main menu.
Select an ID for updating. In the next screen, change the details and hit Submit. The changes should now be updated.

Module 4
--------

Task 1 - In ApplicantController, go to removeApplicant method and add the below lines of code at the top inside the method.

		 List<Applicant> applicants = applicantService.findAllApplicants();
		 modelMap.put("all",applicants);
		 
Note: Module4_Tests -> test_1 should pass after this step.

Now when you run the application and navigate to localhost:8080 in your browser and go to Delete in the main menu,
you should see the list of existing applicant IDs listed to be selected for delete. When you select an Applicant ID from the drop down box,
it will prompt you to enter the applicant ID to be deleted for confirmation. However, if you enter the ID nothing will happen at this stage since we have not yet
implemented the delete logic.

Task 2 - Go to the delete method of ApplicantService class, and add the following line of code at the top inside the method.

		 applicantRepository.deleteById(id);
		 
Note: Module4_Tests -> test_2 should pass after this step.
		 
Task 3 - Go to ApplicantController and in to the method delete. Add the following lines of code at the top inside the method.

		applicantService.delete(id);
        String message = "Applicant with ID " + id + " deleted!";
        modelMap.put("message",message);
		
Note: Module4_Tests -> test_3 should pass after this step.
At this point, all Module4_Tests should pass.

Now run the application and navigate to localhost:8080 in your browser and go to Delete in the main menu.
Select an ID to delete and enter the ID when prompted. When you click OK, the applicant should now be deleted successfully.

Congratulations!!! You've successfully completed a project to manage CRUD operations of a Recruiter Database using Spring Data JPA.
