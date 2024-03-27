import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Person} from "../common/person";

@Injectable({
  providedIn: 'root'
})
export class PersonService {

  private url = 'http://localhost:8081/person';
  private readonly headers: HttpHeaders;

  constructor(private httpClient: HttpClient) { }

  getUsersList(): Observable<Person[]> {
    return this.httpClient.get<Person[]>(`${this.url}/getAll`, { headers: this.headers })
  }

  addPerson(person: Person): Observable<any>{
    return this.httpClient.post(`${this.url}/insertPerson`, person, { headers: this.headers });
  }

  deletePerson(userId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.url}/deleteById/${userId}`, { headers: this.headers });
  }

  updatePerson(person: Person, userId: string): Observable<any>{
    return this.httpClient.put(`${this.url}/updatePerson/${userId}`, person, { headers: this.headers });
  }

  getUser(userId: string): Observable<Person> {
    const userUrl = `${this.url}/getById/${userId}`;
    return this.httpClient.get<Person>(userUrl, { headers: this.headers });
  }

  getEmail(token: String): Observable<any> {
    const userUrl = `${this.url}/getEmail`;
    return this.httpClient.post(userUrl, token, {
      headers:{
        'Authorization':'Bearer ' + token
      },
      responseType: 'text'
    });
  }

  getRole(token: String): Observable<any> {
    const userUrl = `${this.url}/getRole`;
    return this.httpClient.post(userUrl, token, {
      headers:{
        'Authorization':'Bearer ' + token
      },
      responseType: 'text'
    });
  }

  getId(token: String): Observable<any> {
    const userUrl = `${this.url}/getId`;
    return this.httpClient.post(userUrl, token, {
      headers:{
        'Authorization':'Bearer ' + token
      },
      responseType: 'text'
    });
  }

  getUserIds(): Observable<String[]>{
    return this.httpClient.get<String[]>(`${this.url}/getUserIds`, { headers: this.headers })
  }
}
