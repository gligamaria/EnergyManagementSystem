import { Component } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Token} from "../../common/token";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  email: string;
  password: string;
  isLoggingIn: boolean = false;

  constructor(private authService: AuthService) { }

  // login() {
  //   this.isLoggingIn = true;
  //   this.authService.login(this.email, this.password)
  //     .subscribe(
  //       (user: Person) => {
  //         if (user) {
  //           // Store the user in local storage
  //           this.authService.setUserInLocalStorage(user);
  //           this.authService.setAuthInLocalStorage(true);
  //           // Redirect to the desired URL after successful login
  //           window.location.href = 'http://localhost:4200/users';
  //         } else {
  //           console.log('Invalid email or password');
  //         }
  //       }
  //     ).add(() => {
  //     this.isLoggingIn = false; // Set isLoggingIn to false after the login request completes
  //   });
  // }

  login() {
    this.isLoggingIn = true;
    this.authService.authenticate(this.email, this.password)
      .subscribe(
        (token: Token) => {
          if (token) {
            // Store the token in local storage
            console.log(token);
            this.authService.setTokenInLocalStorage(token);
            this.authService.setAuthInLocalStorage(true);
            // Redirect to the desired URL after successful login
            window.location.href = 'http://localhost:4200/users';
          } else {
            console.log('Invalid email or password');
          }
        }
      ).add(() => {
      this.isLoggingIn = false; // Set isLoggingIn to false after the login request completes
    });
  }
}
