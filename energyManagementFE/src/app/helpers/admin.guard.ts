import { Injectable } from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {Person} from "../common/person";
import {AuthService} from "../services/auth.service";
import {PersonService} from "../services/person.service";
import {Token} from "../common/token";

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  person: Person;
  role: string;
  token: Token;

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

  canActivate(): boolean {
    if (this.role == '1') {
      return true;
    } else {
      return false
    }
  }

}
