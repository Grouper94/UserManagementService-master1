package com.example.usermanagementservice;


import com.example.usermanagementservice.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Api App", version = "1.0.0"),
        tags = {@Tag(name = "Note", description = "CRUD operations")})

public class UserManagementServiceApplication implements CommandLineRunner {
public static void main(String[] args) {
        SpringApplication.run(UserManagementServiceApplication.class, args);
    }


//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(new ApiInfoBuilder()
//                        .title("First API ever")
//                        .description("A CRUD API to demonstrate Springfox 3 integration")
//                        .version("0.0.1")
//                        .build())
//                .tags(new Tag("Note", "Endpoints for CRUD operations on notes"))
//                .select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .build();
//    }
@Autowired
private UserService userService;

   // @Autowired
   // public void setUserService(UserService userService) {
       // this.userService = userService;
   // }

    @Override
    public void run(String... args) throws Exception {

      //  List<User> userList2 = userService.getUserByName("George");
       // for(User u2 :userList2)
        //{
       //     System.out.println(u2.toString());
      //  }



       // User user1 = new User(1,"Andreas","Pagonas",28);
       // User user2 = new User(3,"George","Georgiou",20);
       // User user3 = new User(2,"John","Petrov",68);
      //  userService.addUser(user1);
       // userService.addUser(user2);
       // userService.addUser(user3);


    }


    //  @Override
  //  public void run(String... args) throws Exception {}


    // userService.deleteUser(1);
    //  userService.deleteUser(2);
    //  userService.deleteUser(3);
    //   List<User> userList1 = userService.getAllUsers();
    // List<User> userList2 = userService.getUser(1);
        /*
        for(User u1 :userList1)
        {
            System.out.println(u1.toString());
        }
        for(User u2 :userList2)
        {
           System.out.println(u2.toString());
        }



    }
    @GetMapping(path="{userId}")
    public List<User> returnUser(@PathVariable("userId") String id) {
        return List.of(new User(11,id,"ddd",33));
    }
     */

}
