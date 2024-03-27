import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Device} from "../common/device";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  private url = 'http://localhost:8080/device';
  private headers: HttpHeaders;

  constructor(private httpClient: HttpClient,
              private authService: AuthService) {
    this.headers = new HttpHeaders({
      'Authorization': `Bearer ${authService.getTokenFromLocalStorage().jwtToken}`
    });
  }

  getDevicesList(): Observable<Device[]> {
    return this.httpClient.get<Device[]>(`${this.url}/getAll`, { headers: this.headers })
  }

  addDevice(device: Device): Observable<any>{
    return this.httpClient.post(`${this.url}/insertDevice`, device, { headers: this.headers });
  }

  deleteDevice(deviceId: string): Observable<any> {
    return this.httpClient.delete<any>(`${this.url}/deleteById/${deviceId}`, { headers: this.headers });
  }

  updateDevice(deviceId: String, device: Device): Observable<any>{
    return this.httpClient.put(`${this.url}/updateDevice/${deviceId}`, device, { headers: this.headers });
  }

  getFreeIds(): Observable<String[]>{
    return this.httpClient.get<String[]>(`${this.url}/getFreeIds`, { headers: this.headers })
  }

  getFreeDevices(): Observable<Device[]>{
    return this.httpClient.get<Device[]>(`${this.url}/getFreeDevices`, { headers: this.headers })
  }

  getIdsByUser(userId: string): Observable<string[]>{
    return this.httpClient.get<string[]>(`${this.url}/getIdsByUserId/${userId}`, { headers: this.headers });
  }

  getDevicesByUser(userId: string): Observable<Device[]>{
    return this.httpClient.get<Device[]>(`${this.url}/getByUserId/${userId}`, { headers: this.headers })
  }

  getDevice(deviceId: string): Observable<Device> {
    const deviceUrl = `${this.url}/getById/${deviceId}`;
    return this.httpClient.get<Device>(deviceUrl, { headers: this.headers });
  }

  removePerson(id: string): Observable<any>{
    return this.httpClient.put(`${this.url}/updateDeviceWithUser/${id}`,null, { headers: this.headers });
  }

  addUserToDevice(deviceId: string, userId: string): Observable<any> {
    console.log(deviceId, userId);
    return this.httpClient.put(`${this.url}/addUserToDevice/${deviceId}/${userId}`,null, { headers: this.headers });
  }
}
