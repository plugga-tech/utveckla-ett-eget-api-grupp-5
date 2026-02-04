// package org.acme.config;

// import jakarta.enterprise.event.Observes;
// import jakarta.inject.Singleton;
// import jakarta.transaction.Transactional;

// import org.acme.model.User;

// import io.quarkus.runtime.StartupEvent;

// @Singleton
// public class Startup {


//     // Den här skapar en admin vid start av servern, om det inte redan finns en i databasen. 
//     // I dev profil när vi kör drop-and-create i import.sql så kommer databasen vara tom vid serverstart
//     // På detta sätt kan vi skapa en admin vid launch, och se till att den bara skapas om den inte redan finns (för prod)

//     // Skapar även en user. Detta är enbart för testsyfte, och behövs inte i prod.

//     // Värt att notera. I dev är den här klassen onödig eftersom vi kan göra det i import.sql med en query,
// /* 
//     @Transactional
//     void loadUsers(@Observes StartupEvent evt) {
//         if (User.count("username", "admin") == 0) {
//             User.add("admin", "admin", "admin");
//             User.add("user","user","user");
//         }
//     }
//  */
// }
