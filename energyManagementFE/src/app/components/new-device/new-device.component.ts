import {Component, Input} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormControl} from '@angular/forms';
import {PersonService} from "../../services/person.service";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {Observable} from "rxjs";
import {Router} from "@angular/router";
import {Device} from "../../common/device";
import {DeviceService} from "../../services/device.service";
import {Person} from "../../common/person";
@Component({
  selector: 'app-new-device',
  templateUrl: './new-device.component.html',
  styleUrls: ['./new-device.component.css']
})
export class NewDeviceComponent {

  fileName = '';
  device: Device;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  tagCtrl = new FormControl('');
  filteredTags: Observable<String[]>;
  allFreeDevices: Device[];
  someTags: Device[];
  tags: string[] = [];
  allFreeIds: String[];
  submitPressed: boolean = false;
  fileUrl: string | null = null;
  allUsers: String[];
  @Input() buttonText: string = "Add device";
  @Input() descriptionInputValue: string;
  @Input() addressInputValue: string;
  @Input() consumptionInputValue: string;
  @Input() userInputValue: string;
  @Input() updateDevice: boolean;
  @Input() givenDevice: Device;

  constructor(private http: HttpClient,
              private personService: PersonService,
              private deviceService: DeviceService,
              private router: Router) {

    this.personService.getUserIds().subscribe(
      data => {
        this.allUsers = data;
      }
    );
  }

  onSubmit() {

    this.device = new Device("",this.userInputValue, this.addressInputValue, this.descriptionInputValue,
                  Number(this.consumptionInputValue));

    if(this.updateDevice){

      this.deviceService.updateDevice(this.givenDevice.id, this.device).subscribe(
        (response) => {
          // Handle the successful response here, if needed
          console.log('Device updated successfully', response);
          this.router.navigateByUrl(`/devices`).then();
        },
        (error) => {
          // Handle errors here, if needed
          console.error('Error updating device', error);
        }
      );

    }
    else{
      this.deviceService.addDevice(this.device).subscribe(
        (response) => {
          // Handle the successful response here, if needed
          console.log('Device added successfully', response);
          this.router.navigateByUrl(`/devices`).then();
        },
        (error) => {
          // Handle errors here, if needed
          console.error('Error adding device', error);
        }
      );
    }
  }

}
