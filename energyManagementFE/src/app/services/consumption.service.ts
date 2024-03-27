import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class ConsumptionService {

  private url = 'http://localhost:8083/consumption';

  constructor(private httpClient: HttpClient) { }

  start(): Observable<boolean> {
    return this.httpClient.get<any>(`${this.url}/start`)
  }

  stop(): Observable<any> {
    return this.httpClient.get<any>(`${this.url}/stop`)
  }

}

