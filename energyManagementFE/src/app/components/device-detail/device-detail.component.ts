import {Component, Input} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {PersonService} from "../../services/person.service";
import {DeviceService} from "../../services/device.service";
import {Device} from "../../common/device";
import {Person} from "../../common/person";
import {WebsocketService} from "../../services/websocket.service";
import {ConsumptionService} from "../../services/consumption.service";
import {Observable, Subscription} from "rxjs";
import {HourlyConsumptionService} from "../../services/hourly-consumption.service";

@Component({
  selector: 'app-device-detail',
  templateUrl: './device-detail.component.html',
  styleUrls: ['./device-detail.component.css']
})
export class DeviceDetailComponent {

  device: Device | null;
  safeDelete: String;
  edit: boolean = false;
  consumption: string;
  consumptions: number[] = [];
  messageSubscription: Subscription;
  consumptionSubscription: Subscription;
  showNotification: boolean = false;
  notification: String;
  monitoring: boolean = false;
  selectedDate: Date | null = null;
  showDateWarning: boolean = false;

  constructor(private deviceService: DeviceService,
              private route: ActivatedRoute,
              private router: Router,
              private websocketService: WebsocketService,
              private hourlyConsumptionService: HourlyConsumptionService,
              private consumptionService: ConsumptionService) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleDeviceDetails()
    })
    this.messageSubscription = this.websocketService.Obs.subscribe(message => {
      console.log('Received message from WebSocket:', message);
      console.log('Message as string: ' + this.websocketService.notification);
      this.notification = this.websocketService.notification;
      console.log('Device id: ' + this.device?.id);
      this.showNotification = this.websocketService.notification == this.device?.id;

      // Subscribe to the consumption observable
      this.consumptionSubscription = this.hourlyConsumptionService.getAll(this.websocketService.notification).subscribe(data => {
        this.consumptions = data;
        console.log('Consumptions:', this.consumptions);
      });
    });
  }

  ngOnDestroy() {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
    if (this.consumptionSubscription) {
      this.consumptionSubscription.unsubscribe();
    }
  }

  start(){
    if(this.selectedDate){
      this.showDateWarning = false;
      this.monitoring = true;
      this.consumptionService.start().subscribe(
        (response) => {
          // Handle the successful response here, if needed
          console.log('start', response);
        },
        (error) => {
          // Handle errors here, if needed
          console.error('Error adding user', error);
        }
      );
    }
    else {
      this.showDateWarning = true;
    }
  }

  stop(){
    this.monitoring = false;
    this.consumptionService.stop().subscribe(
      (response) => {
        // Handle the successful response here, if needed
        console.log('start', response);
      },
      (error) => {
        // Handle errors here, if needed
        console.error('Error adding user', error);
      }
    );
  }

  deleteDevice(){
    this.deviceService.deleteDevice(this.device!.id).subscribe({
      complete: () => {
        this.router.navigateByUrl(`devices`).then();
      }
    });
  }

  private handleDeviceDetails() {

    const  deviceId: string = this.route.snapshot.paramMap.get('id')!;
    this.deviceService.getDevice(deviceId).subscribe(
      data => {
        this.device = data;
        this.consumption = this.device.consumption.toString();
      }
    )

  }

  editDevice() {
    this.edit = true;
  }
}
