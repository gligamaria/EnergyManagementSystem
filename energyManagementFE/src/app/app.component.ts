import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "./services/auth.service";
import {Token} from "./common/token";
import {PersonService} from "./services/person.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'energyManagement';
  token: Token;
  role: String;

  constructor(public router: Router,
              public authService: AuthService,
              public personService: PersonService) {
    this.token = authService.getTokenFromLocalStorage();
    this.personService.getRole(this.token.jwtToken).subscribe(
      data => {
        this.role = data;
      }
    );
  }

  logOut() {
    this.authService.removeTokenFromLocalStorage();
    this.authService.setAuthInLocalStorage(false);
    this.router.navigateByUrl(`/login`).then();
  }
}
