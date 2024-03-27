import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class HourlyConsumptionService {

  private url = 'http://localhost:8082/hourlyConsumption';
  private headers: HttpHeaders;

  constructor(private httpClient: HttpClient,
              private authService: AuthService) {
    this.headers = new HttpHeaders({
      'Authorization': `Bearer ${authService.getTokenFromLocalStorage().jwtToken}`
    });
  }

  getAll(deviceId: string): Observable<number[]> {
    return this.httpClient.get<number[]>(`${this.url}/${deviceId}`, { headers: this.headers })
  }

}
