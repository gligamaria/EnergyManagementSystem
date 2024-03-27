import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {DeviceService} from "../../services/device.service";
import {Device} from "../../common/device";
import {WebsocketService} from "../../services/websocket.service";
import {PersonService} from "../../services/person.service";
import {Token} from "../../common/token";

@Component({
  selector: 'app-user-list',
  templateUrl: './device-list.component.html',
  styleUrls: ['./device-list.component.css']
})
export class DeviceListComponent implements OnInit{

  devices: Device[];
  token: Token;
  role: string;
  id: string;

  constructor(private deviceService: DeviceService,
              private authService: AuthService,
              private route: ActivatedRoute,
              private websocketService: WebsocketService,
              private personService: PersonService) {
    this.token = authService.getTokenFromLocalStorage();
  }

  ngOnInit(){
    this.personService.getRole(this.token.jwtToken).subscribe(
      data => {
        this.role = data;
      }
    );
    this.personService.getId(this.token.jwtToken).subscribe(
      data => {
        this.id = data;
        this.route.paramMap.subscribe(() =>{
          this.listDevices();
        });
      }
    );

  }

  show(){
    this.websocketService.sendMessage("miau");
  }

  listDevices(){
    this.handleList();
  }

  handleList(){

    if(this.role == '0'){
      // user is a client, should only see his/her devices
      console.log("is client");
      this.deviceService.getDevicesByUser(this.id).subscribe(
        data => {
          this.devices = data;
        }
      )
    }
    else {
      // user is an admin, should see all the devices
      console.log("is admin " + this.role);
      this.deviceService.getDevicesList().subscribe(
        data => {
          this.devices = data;
        }
      )
    }

  }
}
