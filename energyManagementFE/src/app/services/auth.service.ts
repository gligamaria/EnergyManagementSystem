import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Token} from "../common/token";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private backendUrl = 'http://localhost:8081';

  constructor(private httpClient: HttpClient) { }

  setAuthInLocalStorage(auth: boolean) {
    localStorage.setItem('auth', JSON.stringify(auth));
  }

  getAuthFromLocalStorage(): boolean {
    const auth = localStorage.getItem('auth');
    return auth ? JSON.parse(auth) : null;
  }

  authenticate(email: string, password: string): Observable<any> {
    const body = {
      email: email,
      password: password
    };
    return this.httpClient.post(`${this.backendUrl}/person/authenticate`, body);
  }

  setTokenInLocalStorage(token: Token) {
    console.log(token);
    localStorage.setItem('token', JSON.stringify(token));
  }

  getTokenFromLocalStorage(): Token {
    const token = localStorage.getItem('token');
    return token ? JSON.parse(token) : null;
  }

  removeTokenFromLocalStorage() {
    localStorage.removeItem('token');
  }

}
